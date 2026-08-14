package model.dao;

import db.DbException;
import model.entities.Department;

import javax.xml.transform.Result;
import java.sql.*;
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

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }

    private void setDepartmentId(Department department, ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            department.setId(id);
        } else {
            throw new DbException("Unexpected error! No rows affected!");
        }
    }
}
