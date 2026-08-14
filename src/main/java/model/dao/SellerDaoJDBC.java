package model.dao;

import db.DbException;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                setSellerId(seller, resultSet);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ?")
        ){
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM seller "
                + "WHERE Id = ?")
        ){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT seller.*, department.Name as DepartmentName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "ORDER BY Name"
        )){
            ResultSet resultSet = preparedStatement.executeQuery();

            return instantiateSellerList(resultSet);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT seller.*, department.Name as DepartmentName "
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

    private void setSellerId(Seller seller, ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            seller.setId(id);
        } else{
            throw new DbException("Unexpected error! No rows affected!");
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