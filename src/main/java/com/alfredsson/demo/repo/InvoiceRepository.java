package com.alfredsson.demo.repo;

import com.alfredsson.demo.db.MysqlDatabase;
import com.alfredsson.demo.model.Invoice;

 import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private MysqlDatabase db;

    public InvoiceRepository () {
        db = MysqlDatabase.getInstance();
    }

    public List<Invoice> getInvoice(String username) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "SELECT id, title, date, description, category, price FROM invoices WHERE owner_username = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List<Invoice> invoiceList = new ArrayList<>();
            while (rs.next()) {

                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setTitle(rs.getString("title"));
                invoice.setDate(String.valueOf(rs.getDate("date")));
                invoice.setDescription(rs.getString("description"));
                invoice.setCategory(rs.getString("category"));
                invoice.setPrice(rs.getInt("price"));
                invoiceList.add(invoice);

            }
            return invoiceList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addInvoice(String username, String title, String date, String description, String category, int price) {
        if(username==null){
            return;
        }

        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "INSERT INTO invoices (owner_username, title, date, description, category, price)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, title);
            pstmt.setDate(3, Date.valueOf(date));
            pstmt.setString(4, description);
            pstmt.setString(5, category);
            pstmt.setInt(6, price);

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Invoice findInvoiceById(int invoiceId) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "SELECT * FROM invoices WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();
            Invoice invoice = new Invoice();
            if (rs.next()) {
                invoice.setOwner_username(rs.getString("owner_username"));
                invoice.setId(rs.getInt("id"));
                invoice.setDate(rs.getString("date"));
                invoice.setTitle(rs.getString("title"));
                invoice.setPrice(rs.getInt("price"));
                invoice.setCategory(rs.getString("category"));
                invoice.setDescription(rs.getString("description"));
            }
            return invoice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateInvoice(int invoiceId, String title, String date, String description, String category, int price) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "UPDATE invoices SET title = ?, date = ?, description = ?, category = ?, price = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setInt(5, price);
            pstmt.setInt(6, invoiceId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteInvoice(String username, int invoiceId) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "DELETE FROM invoices WHERE id = ? AND owner_username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, invoiceId);
            pstmt.setString(2, username);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
