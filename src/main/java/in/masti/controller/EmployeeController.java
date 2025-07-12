package in.masti.controller;

import in.masti.constants.RouteConstants;
import in.masti.entity.Employee;
import in.masti.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteConstants.EMPLOYEE_BASE)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(RouteConstants.EMPLOYEE_CREATE)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }
}
