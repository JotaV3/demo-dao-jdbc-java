package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args){
        Department department = new Department(3, null);
        Seller seller = new Seller(null, "Vitoria", "vitoria@gmail.com", new Date(), 1500, department);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        sellerDao.insert(seller);

        System.out.println(seller);
    }
}
