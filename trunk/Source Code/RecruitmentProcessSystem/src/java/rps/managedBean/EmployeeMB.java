/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import rps.business.EmployeeService;
import rps.entities.Account;
import rps.entities.Department;
import rps.entities.Employee;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class EmployeeMB {

    private String employeeID;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private Date dob;
    private String phoneNumber;
    private String email;
    private String address;
    private String position;
    private Account account;
    private Department department;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    private EmployeeService employeeService;

    /** Creates a new instance of EmployeeMB */
    public EmployeeMB() {
        employeeService = new EmployeeService();
    }

    public Employee getEmployeeInfo() {
        return employeeService.getEmployeeInfo(employeeID);
    }
}
