package com.encentral.employee.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.encentral.employee.api.IEmployee;
import com.encentral.employee.model.*;
import com.encentral.entities.JpaAdmin;
import com.encentral.entities.JpaAttendanceBroadSheet;
import com.encentral.entities.JpaEmployee;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
public class DefaultEmployeeImpl implements IEmployee {
    private final JPAApi jpaApi;

    @Inject
    public DefaultEmployeeImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public EmployeeLoginResponseModel login(EmployeeLoginRequestModel requestDetails) {
        EmployeeLoginResponseModel responseModel = new EmployeeLoginResponseModel();

        Query userWithRequestedEmailQuery = jpaApi.em().createNamedQuery("JpaEmployee.findByEmail", JpaEmployee.class).setParameter("email", requestDetails.getEmail());
        final JpaEmployee userWithRequestedEmail = (JpaEmployee) userWithRequestedEmailQuery.getSingleResult();

        if (userWithRequestedEmail == null) {
            responseModel.setLoginSuccessful(false);
            responseModel.setErrorMessage("Bad Request. User with email not found");
            return responseModel;
        }

        if (!verifyPassword(requestDetails.getPassword(), userWithRequestedEmail.getEmployeePassword())) {
            responseModel.setLoginSuccessful(false);
            responseModel.setErrorMessage("Bad Request. Incorrect Password");
        } else {
            responseModel.setLoginSuccessful(true);
            responseModel.setToken(userWithRequestedEmail.getEmployeeId());
        }
        return responseModel;
    }

    @Override
    public boolean changePassword(String userToken, EmployeeChangePasswordModel changePasswordModel) throws IllegalAccessException {
        Optional<EmployeeModel> user = findEmployeeById(userToken);
        if(user.isPresent()) {
            EmployeeModel employeeToBeUpdated = user.get();
            if(!verifyPassword(changePasswordModel.getOldPassword(), employeeToBeUpdated.getEmployeePassword())) {
                throw new IllegalAccessException("OLD_PASSWORD_INCORRECT");
            } else {
                employeeToBeUpdated.setEmployeePassword(hashPassword(changePasswordModel.getNewPassword()));
                jpaApi.em().merge(employeeToBeUpdated);
                return true;
            }
        } else
            throw new IllegalAccessException("EMPLOYEE_NOT_FOUND");
    }

    @Override
    public boolean markAttendance(String userToken) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (currentDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new UnsupportedOperationException("NOT_WORK_DAY");
        } else if (timeIsBetween(currentDateTime.toLocalTime(), LocalTime.of(0, 0), LocalTime.of(8, 59))) {
            throw new UnsupportedOperationException("TOO_EARLY");
        } else if (timeIsBetween(currentDateTime.toLocalTime(), LocalTime.of(17, 0), LocalTime.of(23, 59))) {
            throw new UnsupportedOperationException("TOO_LATE");
        } else {
            String currentDate = currentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd:MM:yyyy"));
            String currentTime = currentDateTime.toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            Optional<EmployeeModel> employeeMarkingAttendanceOptional = findEmployeeById(userToken);

            if(employeeMarkingAttendanceOptional.isPresent()) {
                JpaEmployee employeeMarkingAttendance = EmployeeMapper.employeeToJpaEmployee(employeeMarkingAttendanceOptional.get());
                JpaAttendanceBroadSheet newAttendance = new JpaAttendanceBroadSheet(0, employeeMarkingAttendance, currentDate, currentTime);
                jpaApi.em().persist(newAttendance);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<EmployeeModel> addEmployee(String userToken, EmployeeModel employeeModel) throws IllegalAccessException {
        if (userIsAdmin(userToken)) {
            JpaEmployee newEmployee = EmployeeMapper.employeeToJpaEmployee(employeeModel);
            newEmployee.setEmployeeId(randomUUID().toString());
            newEmployee.setEmployeePassword(hashPassword(employeeModel.getEmployeePassword()));
            jpaApi.em().persist(newEmployee);
            return Optional.of(newEmployee).map(EmployeeMapper::jpaEmployeeToEmployee);
        } else
            throw new IllegalAccessException("USER_NOT_AN_ADMIN");
    }

    @Override
    public Optional<EmployeeModel> removeEmployee(String userToken, String employeeId) throws IllegalAccessException {
        if (userIsAdmin(userToken)) {
            Optional<EmployeeModel> employee = findEmployeeById(employeeId);
            if (employee.isPresent()) {
                jpaApi.em().remove(employee.map(EmployeeMapper::employeeToJpaEmployee));
                return employee;
            } else
                throw new IllegalArgumentException("EMPLOYEE_WITH_GIVEN_ID_NOT_FOUND");
        } else
            throw new IllegalAccessException("USER_NOT_AN_ADMIN");
    }

    @Override
    public Optional<List<EmployeeModel>> getEmployees(String userToken) throws IllegalAccessException {
        if(userIsAdmin(userToken)) {
            EntityManager em = jpaApi.em();
            List<EmployeeModel> employees = em.createNamedQuery("JpaEmployee.findAll", JpaEmployee.class).getResultList()
                    .stream().map(EmployeeMapper::jpaEmployeeToEmployee).collect(Collectors.toList());
            return Optional.of(employees);
        } else
            throw new IllegalAccessException("USER_NOT_AN_ADMIN");
    }

    @Override
    public Optional<List<AttendanceBroadSheetModel>> getAttendanceBroadsheet(String userToken) throws IllegalAccessException {
        if(userIsAdmin(userToken)) {
            EntityManager em = jpaApi.em();
            List<AttendanceBroadSheetModel> attendanceBroadSheet = em.createNamedQuery("JpaAttendanceBroadSheet.findAll", JpaAttendanceBroadSheet.class).getResultList()
                    .stream().map(AttendanceBroadSheetMapper::jpaAttendanceBroadSheetToAttendanceBroadSheet).collect(Collectors.toList());
            return Optional.of(attendanceBroadSheet);
        } else
            throw new IllegalAccessException("USER_NOT_AN_ADMIN");
    }

    /**
     * Hashes a password String using BCrypt.
     *
     * @param password Password to be hashed
     * @return The hashed password as a String
     */
    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    /**
     * Verifies a password hash with a password string
     *
     * @param password       password string
     * @param hashedPassword password hash
     * @return true if password string and hash match else returns false
     */
    private static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    private Optional<EmployeeModel> findEmployeeById(String employeeId) {
        final var employeeWithId = jpaApi.withTransaction(em -> em.find(JpaEmployee.class, employeeId));
        return Optional.ofNullable(employeeWithId).map(EmployeeMapper::jpaEmployeeToEmployee);
    }

    private Optional<JpaAdmin> findAdminById(String employeeId) {
        final var adminWithId = jpaApi.withTransaction(em -> em.find(JpaAdmin.class, employeeId));
        return Optional.ofNullable(adminWithId);
    }

    private boolean userIsAdmin(String userToken) {
        if(findAdminById(userToken).isPresent())
            return true;
        else
            return false;
    }

    private boolean timeIsBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }
}
