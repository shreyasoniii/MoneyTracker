package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addExpense")
public class addservlet extends HttpServlet {

    private static final String query =
        "INSERT INTO EXPENSEDATA(expense_name, expense_category, amount, expense_date) VALUES(?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String expenseName = req.getParameter("expenseName");
        String expenseCategory = req.getParameter("expenseCategory");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String expenseDate = req.getParameter("expenseDate");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            pw.println("<h2 style='color:red;'>MySQL Driver not found!</h2>");
            cnf.printStackTrace();
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///expense_tracker", "root", "root");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, expenseName);
            ps.setString(2, expenseCategory);
            ps.setDouble(3, amount);
            ps.setString(4, expenseDate);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2 style='color:green;'>✅ Expense Added Successfully!</h2>");
            } else {
                pw.println("<h2 style='color:red;'>❌ Failed to Add Expense!</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 style='color:red;'>" + se.getMessage() + "</h2>");
        }

        pw.println("<br><a href='home.html'>🏠 Home</a>");
        pw.println("<br><a href='viewExpenses'>📊 View Expenses</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
