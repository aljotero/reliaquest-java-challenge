package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    /**
     * Mock data
     */
    private final Map<UUID, Employee> employeeMap = new HashMap<>();

    public EmployeeService() {
        Employee employee1 = new EmployeeImpl(
                UUID.randomUUID(),
                "Juan",
                "Perez",
                "Juan J. Perez",
                100000,
                25,
                "SWE",
                "juan@gmail.com",
                Instant.now(),
                null);

        employeeMap.put(employee1.getUuid(), employee1);

        Employee employee2 = new EmployeeImpl(
                UUID.randomUUID(),
                "Maria",
                "Gonzalez",
                "Maria L. Gonzalez",
                120000,
                30,
                "Manager",
                "maria@gmail.com",
                Instant.now(),
                Instant.now().plusSeconds(50000));

        employeeMap.put(employee2.getUuid(), employee2);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    public Employee getEmployeeByUuid(UUID uuid) {
        return employeeMap.get(uuid);
    }

    public EmployeeImpl createEmployee(EmployeeImpl employee) {
        validateEmployee(employee);

        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (employeeMap.containsKey(uuid));

        employee.setUuid(uuid);
        employeeMap.put(uuid, employee);

        return employee;
    }

    private void validateEmployee(Employee employee) {
        /**
         * Assumptions:
         *  1. Initially, only the UUID and the contract termination date can be null.
         *  2. fullName must be consistent with firstName and lastName.
         *  3. Salary cannot be negative.
         *  4. Age cannot be negative.
         *  5. The contract termination date can be null, but if it is not, it must be later than the contract hire date.
         */
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (employee.getFullName() == null
                || !employee.getFullName().startsWith(employee.getFirstName())
                || !employee.getFullName().endsWith(employee.getLastName())) {
            throw new IllegalArgumentException("Full name must be consistent with first name and last name");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be null or negative");
        }
        if (employee.getAge() == null || employee.getAge() < 0) {
            throw new IllegalArgumentException("Age cannot be null or negative");
        }
        if (employee.getJobTitle() == null || employee.getJobTitle().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be null or empty");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (employee.getContractHireDate() == null) {
            throw new IllegalArgumentException("Contract hire date cannot be null");
        }
        if (employee.getContractTerminationDate() != null
                && !employee.getContractTerminationDate().isAfter(employee.getContractHireDate())) {
            throw new IllegalArgumentException("Contract termination date must be after contract hire date");
        }
    }
}
