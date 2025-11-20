package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.User;
import edu.RL.repository.Repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
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
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO user Values(? ,? ,? ,? ,? ,?)");
        psTm.setObject(1,addUser.getUserId());
        psTm.setObject(2,addUser.getUsername());
        psTm.setObject(3,addUser.getPassword());
        psTm.setObject(4,addUser.getRole());
        psTm.setObject(5,addUser.getStatus());
        psTm.setObject(6,addUser.getEmail());

        psTm.executeUpdate();
    }

    @Override
    public void updateUser(String userId, String username, String role, String status, String email) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("UPDATE user SET username = ? ,role = ? ,status = ?, email = ? WHERE user_id =?");
        psTm.setObject(1,username);
        psTm.setObject(2,role);
        psTm.setObject(3,status);
        psTm.setObject(4,email);
        psTm.setObject(5,userId);

        psTm.executeUpdate();
    }

    @Override
    public ResultSet getDailyRentalReport() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm =  connection.prepareStatement("SELECT r.rental_id, b.title AS book_title, c.name AS customer_name, r.issue_date, r.due_date, r.return_date, " +
                "CASE WHEN r.return_date IS NULL AND r.due_date < CURDATE() THEN 'Overdue' " +
                "WHEN r.return_date IS NULL THEN 'Not Returned' ELSE 'Returned' END AS status, " +
                "IFNULL(r.fine,0) AS fine " +
                "FROM rental r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "JOIN customer c ON r.customer_id = c.customer_id " +
                "WHERE DATE(r.issue_date) = CURDATE()"
        );
        return psTm.executeQuery();
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("DELETE FROM user WHERE user_id =?");
        psTm.setObject(1,userId);

        psTm.executeUpdate();
    }

    @Override
    public ResultSet login(String username, String password) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm=connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
        psTm.setObject(1,username);
        psTm.setObject(2,password);

        return psTm.executeQuery();

    }

    @Override
    public ResultSet findByUsername(String username) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm=connection.prepareStatement("SELECT * FROM user WHERE username=?");
        psTm.setObject(1,username);

        return psTm.executeQuery();
    }

    @Override
    public ResultSet findByEmail(String email) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm=connection.prepareStatement("SELECT * FROM user WHERE email=?");
        psTm.setObject(1,email);

        return psTm.executeQuery();
    }

    @Override
    public boolean saveOTP(String email, String otp) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm=connection.prepareStatement("INSERT INTO otp_store (email, otp, expiry) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE)) ON DUPLICATE KEY UPDATE otp=?, expiry=DATE_ADD(NOW(), INTERVAL 5 MINUTE)");
        psTm.setObject(1,email);
        psTm.setObject(2,otp);
        psTm.setObject(3,otp);

        return psTm.executeUpdate()>0;
    }

    @Override
    public boolean verifyOTP(String email, String otp) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm=connection.prepareStatement("SELECT * FROM otp_store WHERE email=? AND otp=? AND expiry > NOW()");
        psTm.setObject(1,email);
        psTm.setObject(2,otp);

        ResultSet resultSet =  psTm.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean updatePassword(Connection connection,String email, String hashed) throws SQLException {
        PreparedStatement psTm=connection.prepareStatement("UPDATE user SET password=? WHERE email=?");
        psTm.setObject(1,hashed);
        psTm.setObject(2,email);

        return psTm.executeUpdate()>0;
    }

    @Override
    public boolean deleteOTP(Connection connection,String email) throws SQLException {
        PreparedStatement psTm=connection.prepareStatement("DELETE FROM otp_store WHERE email=?");
        psTm.setObject(1,email);

        return psTm.executeUpdate()>0;
    }


}
