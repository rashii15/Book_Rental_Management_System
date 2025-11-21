package edu.RL.service.Service;

import java.sql.SQLException;

public interface ResetPasswordService {
    void resetPassword(String newpassword, String confirmpassword, String email) throws SQLException;
}
