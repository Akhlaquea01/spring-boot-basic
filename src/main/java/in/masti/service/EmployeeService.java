package in.masti.service;

import in.masti.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getEmployees();

    Employee getEmployeeById(Integer id);

    void deleteEmployeeById(Integer id);

    List<Employee> getEmployeesByDesignation(String designation);
}
