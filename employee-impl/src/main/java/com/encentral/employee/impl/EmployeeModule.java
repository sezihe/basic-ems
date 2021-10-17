package com.encentral.employee.impl;

import com.encentral.employee.api.IEmployee;
import com.google.inject.AbstractModule;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class EmployeeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IEmployee.class).to(DefaultEmployeeImpl.class);
    }
}
