package com.alfredsson.demo.repo;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;


import com.alfredsson.demo.db.MysqlDatabase;

public class AccountRepository {

    private MysqlDatabase db;

    public boolean login(String username, String password) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String stored_hash = rs.getString("password");
                return checkPassword(password, stored_hash);
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String password, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            return false;
        }
        password_verified = BCrypt.checkpw(password, stored_hash);

        return (password_verified);
    }

    public String hashPassword(String password) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password, salt);

        return (hashed_password);
    }

    public boolean usernameExists(String username) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPasswordByUsername(String username) throws SQLException {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String stored_password = rs.getString("password");
                if(stored_password.equals("")) {
                    return "";
                }
            }
        }

        return "404";
    }

    public void updatePassword(String username, String password) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String hashedPass = hashPassword(password);

        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hashedPass);
            pstmt.setString(2, username);


            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


