package service;

import dao.EmployeeDAO;
import domain.Employee;

public class EmployeeService {
    //定义一个DAO属性
    private EmployeeDAO employeeDAO =  new EmployeeDAO();

    //方法，根据empId和pwd返回一个employee对象
    //如果查询不到则返回空
    public Employee getEmplyeeByIdAndPwd(String empId,String pwd){
        return (Employee) employeeDAO.querySingle("select * from employee where empId=? and pwd=md5(?)",Employee.class,empId,pwd);
    }
}
