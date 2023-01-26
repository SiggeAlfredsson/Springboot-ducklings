package com.alfredsson.demo.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


import com.alfredsson.demo.db.MysqlDatabase;

public class AccountRepository {

    private static MysqlDatabase db;

    public static boolean login(String username, String password) {
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

    public static boolean checkPassword(String password, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            return false;
        }
        password_verified = BCrypt.checkpw(password, stored_hash);

        return(password_verified);
    }

    public static String hashPassword(String password) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password, salt);

        return (hashed_password);
    }

}
