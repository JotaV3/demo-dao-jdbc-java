package model.dao;

public class DaoFactory {
    public SellerDao createSellerDao(){
        return new SellerDaoJDBC();
    }

    public DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC();
    }
}
