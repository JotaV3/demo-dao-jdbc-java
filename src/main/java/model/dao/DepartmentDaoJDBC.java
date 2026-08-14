package model.dao;

import db.DbException;
import db.DbIntegrityExcetion;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao{
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO department "
                + "(Name) "
                + "VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, department.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                setDepartmentId(department, resultSet);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department department) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE department "
                + "SET Name = ? "
                + "WHERE Id = ?"
        )
        ){
            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM department "
                + "WHERE Id = ?")
        ){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbIntegrityExcetion(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * "
                + "FROM department "
                + "WHERE Id = ?")
        ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return instantiateDepartment(resultSet);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Department> findAll() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * "
                + "FROM department "
                + "ORDER BY Name")
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            return instantiateDepartmentList(resultSet);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private void setDepartmentId(Department department, ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            department.setId(id);
        } else {
            throw new DbException("Unexpected error! No rows affected!");
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));

        return department;
    }

    private List<Department> instantiateDepartmentList(ResultSet resultSet) throws  SQLException {
        List<Department> departmentList = new ArrayList<>();

        while(resultSet.next()){
            Department department = instantiateDepartment(resultSet);

            departmentList.add(department);
        }

        return departmentList;
    }
}
