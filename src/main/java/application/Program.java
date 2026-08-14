package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program {
    public static void main(String[] args){
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department = new Department(3, "Clothes");

        departmentDao.update(department);

        System.out.println(department);
    }
}
