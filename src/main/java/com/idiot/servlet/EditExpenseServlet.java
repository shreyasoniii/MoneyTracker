package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateExpense")
public class EditExpenseServlet extends HttpServlet {

    private static final String query = 
        "UPDATE EXPENSEDATA SET EXPENSENAME=?, CATEGORY=?, AMOUNT=?, DATE=? WHERE ID=?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String date = req.getParameter("date");

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///expense_tracker", "root", "shreya123");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, amount);
            ps.setString(4, date);
            ps.setInt(5, id);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h3 style='color:green;'>✅ Expense updated successfully!</h3>");
            } else {
                pw.println("<h3 style='color:red;'>❌ Failed to update expense.</h3>");
            }

        } catch (Exception e) {
            pw.println("<h3 style='color:red;'>" + e.getMessage() + "</h3>");
        }

        pw.println("<br><a href='" + req.getContextPath() + "/viewExpenses'>🔙 Back to Expenses</a>");
    }
}
