package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editExpense")
public class editscreenservlet extends HttpServlet {

    private static final String query = "SELECT * FROM EXPENSEDATA WHERE ID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///expense_tracker", "root", "shreya123");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pw.println("<h2 style='color:blue;'>✏️ Edit Expense</h2>");
                pw.println("<form action='" + req.getContextPath() + "/updateExpense' method='post'>");
                pw.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'/>");
                pw.println("Name: <input type='text' name='name' value='" + rs.getString("EXPENSENAME") + "'/><br><br>");
                pw.println("Category: <input type='text' name='category' value='" + rs.getString("CATEGORY") + "'/><br><br>");
                pw.println("Amount: <input type='text' name='amount' value='" + rs.getDouble("AMOUNT") + "'/><br><br>");
                pw.println("Date: <input type='date' name='date' value='" + rs.getString("DATE") + "'/><br><br>");
                pw.println("<input type='submit' value='Update' style='background-color:green;color:white;'/>");
                pw.println("</form>");
            } else {
                pw.println("<h3 style='color:red;'>Record not found!</h3>");
            }

        } catch (Exception e) {
            pw.println("<h3 style='color:red;'>" + e.getMessage() + "</h3>");
        }
    }
}
