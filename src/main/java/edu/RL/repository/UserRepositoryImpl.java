package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public ResultSet getAll() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM user").executeQuery();
    }

    @Override
    public ResultSet getNextId() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1").executeQuery();
    }

    @Override
    public void addUser(User addUser) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO user Values(? ,? ,? ,? ,?)");
        psTm.setObject(1,addUser.getUserId());
        psTm.setObject(2,addUser.getUsername());
        psTm.setObject(3,addUser.getPassword());
        psTm.setObject(4,addUser.getRole());
        psTm.setObject(5,addUser.getStatus());

        psTm.executeUpdate();
    }

    @Override
    public void updateUser(User updateUser) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("UPDATE user SET username = ? ,password = ? ,role = ? ,status = ?  WHERE user_id =?");
        psTm.setObject(1,updateUser.getUserId());
        psTm.setObject(2,updateUser.getUsername());
        psTm.setObject(3,updateUser.getPassword());
        psTm.setObject(4,updateUser.getRole());
        psTm.setObject(5,updateUser.getStatus());

        psTm.executeUpdate();
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("DELETE FROM user WHERE user_id =?");
        psTm.setObject(1,userId);

        psTm.executeUpdate();
    }
}
