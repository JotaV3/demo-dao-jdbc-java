package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args){
        SellerDao sellerDao = DaoFactory.createSellerDao();
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(1, null));

        for (Seller seller : sellerList){
            System.out.println(seller);
        }
    }
}
