package in.masti.service;

import in.masti.entity.Employee;
import in.masti.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    public Employee save(Employee employee) {
        return employeeRepo.saveAndFlush(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No employee found for this id(%d)".formatted(id)));
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No employee found for this id(%d) for deletion".formatted(id)));
        employeeRepo.delete(employee);
    }

    @Override
    public List<Employee> getEmployeesByDesignation(String designation) {
        return employeeRepo.getEmployeesByDesignation(designation);
    }
}
