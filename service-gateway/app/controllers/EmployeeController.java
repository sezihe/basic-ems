package controllers;

import com.encentral.employee.api.IEmployee;
import com.encentral.employee.model.*;
import com.encentral.employee.model.requestmodel.AddEmployeeRequestModel;
import com.encentral.employee.model.requestmodel.EmployeeLoginRequestModel;
import com.encentral.scaffold.commons.util.MyObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.eclipse.persistence.exceptions.DatabaseException;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */

@Transactional
@Api(value = "Employee")
public class EmployeeController extends Controller {
    @Inject
    IEmployee iEmployee;

    @Inject
    FormFactory formFactory;

    @Inject
    MyObjectMapper myObjectMapper;

    @ApiOperation(
            value = "Logs In An Employee",
            produces = "application/json",
            tags = {"Employee", "Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Login Successful"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 401, message = "Wrong details")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "body",
                    value = "Login request details",
                    paramType = "body",
                    dataType = "com.encentral.employee.model.requestmodel.EmployeeLoginRequestModel",
                    required = true
            )
    })
    public Result login() {
        Form<EmployeeLoginRequestModel> loginRequestForm = formFactory.form(EmployeeLoginRequestModel.class).bindFromRequest();
        if (loginRequestForm.hasErrors())
            return badRequest(loginRequestForm.errorsAsJson());

        EmployeeLoginResponseModel responseModel = iEmployee.login(loginRequestForm.get());
        if (!responseModel.isLoginSuccessful()) {
            return unauthorized(myObjectMapper.toJsonString(responseModel));
        } else
            return ok(myObjectMapper.toJsonString(responseModel));
    }

    @ApiOperation(
            value = "Updates an Employee Password",
            notes = "Can only be done by a logged in Employee",
            produces = "application/json",
            tags = {"Employee"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Password updated successful"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 401, message = "Wrong details"),
                    @ApiResponse(code = 500, message = "Unexpected Internal Server Error. Try again later")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "body",
                    value = "Password update request details",
                    paramType = "body",
                    dataType = "com.encentral.employee.model.EmployeeChangePasswordModel",
                    required = true
            )
    })
    public Result changePassword(String userToken) {
        Form<EmployeeChangePasswordModel> changePasswordModelForm = formFactory.form(EmployeeChangePasswordModel.class).bindFromRequest();
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        if (changePasswordModelForm.hasErrors())
            return badRequest(changePasswordModelForm.errorsAsJson());

        try {
            EmployeeChangePasswordModel changePasswordModel = changePasswordModelForm.get();
            if (iEmployee.changePassword(userToken, changePasswordModel)) {
                responseJson.put("status", "Success");
                responseJson.put("message", "Password Updated Successfully");
                return ok(responseJson);
            } else {
                responseJson.put("status", "Error");
                responseJson.put("message", "An unexpected error has occurred. Please try again later");
                return internalServerError(responseJson);
            }
        } catch (IllegalAccessException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getMessage());
            return unauthorized(responseJson);
        }
    }

    @ApiOperation(
            value = "Marks Daily Attendance for an employee",
            notes = "Can only be done by a logged in Employee",
            produces = "application/json",
            tags = {"Employee", "Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Attendance Marked Successfully"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Unexpected Internal Server Error. Try again later")
            }
    )
    @ApiImplicitParams({

    })
    public Result markAttendance(String userToken) {
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();

        if (!Objects.equals(userToken, "NULL")) {
            try {
                if (iEmployee.markAttendance(userToken)) {
                    responseJson.put("status", "success");
                    responseJson.put("message", "Attendance for today has been marked successfully");
                    return ok(responseJson);
                } else {
                    responseJson.put("status", "Error");
                    responseJson.put("message", "An unexpected error has occurred. Please try again later");
                    return internalServerError(responseJson);
                }
            } catch (UnsupportedOperationException e) {
                responseJson.put("status", "Error");
                responseJson.put("message", e.getMessage());
                return badRequest(responseJson);
            } catch (IllegalAccessException e) {
                responseJson.put("status", "Error");
                responseJson.put("message", e.getMessage());
                return unauthorized(responseJson);
            }
        } else {
            responseJson.put("status", "Error");
            responseJson.put("message", "Wrong user Token");
            return badRequest(responseJson);
        }
    }

    @ApiOperation(
            value = "Adds an employee to the Database",
            notes = "Can only be done by an Admin",
            produces = "application/json",
            tags = {"Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, response = EmployeeModel.class, message = "Employee Added Successfully"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 401, message = "Wrong Admin User Token"),
                    @ApiResponse(code = 404, message = "Not Found")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "body",
                    value = "Admin userToken and new Employee to be added",
                    paramType = "body",
                    dataType = "com.encentral.employee.model.requestmodel.AddEmployeeRequestModel",
                    required = true
            )
    })
    public Result addEmployee() {
        Form<AddEmployeeRequestModel> requestForm = formFactory.form(AddEmployeeRequestModel.class).bindFromRequest();
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        if (requestForm.hasErrors()) {
            return badRequest(requestForm.errorsAsJson());
        }
        try {
            Optional<EmployeeModel> newEmployeeOptional = iEmployee.addEmployee(requestForm.get());
            if (newEmployeeOptional.isPresent()) {
                EmployeeModel newEmployee = newEmployeeOptional.get();
                newEmployee.setEmployeePassword("hidden");
                return created(myObjectMapper.toJsonString(newEmployee));
            } else
                return notFound();
        } catch (IllegalAccessException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getMessage());
            return unauthorized(responseJson);
        } catch (DatabaseException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getInternalException().getMessage());
            return badRequest(responseJson);
        }
    }

    @ApiOperation(
            value = "Removes an employee from the Database",
            notes = "Can only be done by an Admin",
            produces = "application/json",
            tags = {"Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, response = EmployeeModel.class, message = "Employee Removed Successfully"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 401, message = "Wrong Admin User Token"),
                    @ApiResponse(code = 404, message = "Not Found")
            }
    )
    @ApiImplicitParams({

    })
    public Result removeEmployee(String adminUserToken, String employeeId) {
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        try {
            return iEmployee.removeEmployee(adminUserToken, employeeId)
                    .map(e -> ok(myObjectMapper.toJsonString(e)))
                    .orElse(notFound());
        } catch (IllegalAccessException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getMessage());
            return unauthorized(responseJson);
        }
    }

    @ApiOperation(
            value = "Gets all Employees from the Database",
            notes = "Can only be done by an Admin",
            produces = "application/json",
            tags = {"Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Employees Retrieved Successfully"),
                    @ApiResponse(code = 401, message = "Wrong Admin User Token"),
                    @ApiResponse(code = 500, message = "Unexpected Internal Server Error. Try again later")
            }
    )
    @ApiImplicitParams({

    })
    public Result getEmployees(String adminUserToken) {
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        try {
            Optional<List<EmployeeModel>> optionalEmployeesList = iEmployee.getEmployees(adminUserToken);
            if (optionalEmployeesList.isPresent()) {
                List<EmployeeModel> employees = optionalEmployeesList.get();
                if (!employees.isEmpty()) {
                    employees.forEach(e -> e.setEmployeePassword("hidden"));
                    return ok(Json.toJson(employees));
                } else {
                    responseJson.put("status", "Success");
                    responseJson.put("message", "Employee List is Empty");
                    return ok(responseJson);
                }
            } else {
                responseJson.put("status", "Error");
                responseJson.put("message", "An unexpected error has occurred. Please try again later");
                return internalServerError(responseJson);
            }
        } catch (IllegalAccessException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getMessage());
            return unauthorized(responseJson);
        }
    }

    @ApiOperation(
            value = "Gets all Employee Daily Attendance from the Database",
            notes = "Can only be done by an Admin",
            produces = "application/json",
            tags = {"Admin"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Employee Daily Attendance Retrieved Successfully"),
                    @ApiResponse(code = 401, message = "Wrong Admin User Token"),
                    @ApiResponse(code = 500, message = "Unexpected Internal Server Error. Try again later")
            }
    )
    @ApiImplicitParams({

    })
    public Result getDailyAttendanceBroadSheet(String adminUserToken) {
        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        try {
            Optional<List<AttendanceBroadSheetModel>> optionalAttendanceBroadSheet = iEmployee.getDailyAttendanceBroadsheet(adminUserToken);
            if (optionalAttendanceBroadSheet.isPresent()) {
                List<AttendanceBroadSheetModel> attendanceBroadSheet = optionalAttendanceBroadSheet.get();
                if (!attendanceBroadSheet.isEmpty()) {
                    return ok(Json.toJson(attendanceBroadSheet));
                } else {
                    responseJson.put("status", "Success");
                    responseJson.put("message", "Attendance BroadSheet is Empty");
                    return ok(responseJson);
                }
            } else {
                responseJson.put("status", "Error");
                responseJson.put("message", "An unexpected error has occurred. Please try again later");
                return internalServerError(responseJson);
            }
        } catch (IllegalAccessException e) {
            responseJson.put("status", "Error");
            responseJson.put("message", e.getMessage());
            return unauthorized(responseJson);
        }
    }
}
