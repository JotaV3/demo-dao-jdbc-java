package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program {
    public static void main(String[] args){
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        List<Department> departmentList = departmentDao.findAll();

        for(Department department : departmentList){
            System.out.println(department);
        }
    }
}
