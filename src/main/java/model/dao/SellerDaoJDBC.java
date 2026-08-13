package model.dao;

import db.DbException;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao{
    private Connection connection;

    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT seller.*, department.Name as DepartmentName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE seller.Id = ?")
        ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Department department = instantiateDepartment(resultSet);

                return instantiateSeller(resultSet, department);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT seller.*, department.Name as departmentName "
                + " FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE DepartmentId = ? "
                + "ORDER BY Name")
        ){
            preparedStatement.setInt(1, department.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            return instantiateSellerList(resultSet);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private List<Seller> instantiateSellerList(ResultSet resultSet) throws SQLException {
        List<Seller> sellerList = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        while(resultSet.next()){
            int departmentId = resultSet.getInt("DepartmentId");

            Department department = departmentMap.get(departmentId);

            if(department == null){
                department = instantiateDepartment(resultSet);
                departmentMap.put(departmentId, department);
            }

            Seller seller = instantiateSeller(resultSet, department);
            sellerList.add(seller);
        }

        return sellerList;
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepartmentName"));

        return department;
    }
}
