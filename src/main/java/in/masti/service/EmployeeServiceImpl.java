package in.masti.service;

import in.masti.entity.Employee;
import in.masti.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    public Employee save(Employee employee) {
        return employeeRepo.saveAndFlush(employee);
    }
}
