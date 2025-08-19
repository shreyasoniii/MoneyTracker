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

@WebServlet("/deleteExpense")

public class deleteservlet extends HttpServlet {

    private static final String query = "DELETE FROM EXPENSEDATA WHERE ID = ?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            pw.println("<h2 style='color:red;'>MySQL Driver not found!</h2>");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///expense_tracker", "root", "shreya123");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            int count = ps.executeUpdate();

            if (count > 0) {
                pw.println("<h2 style='color:green;'>✅ Expense Deleted Successfully!</h2>");
            } else {
                pw.println("<h2 style='color:red;'>❌ Expense Not Found!</h2>");
            }

        } catch (SQLException se) {
            pw.println("<h2 style='color:red;'>" + se.getMessage() + "</h2>");
        }

        pw.println("<form action='index.html' method='get'>");
        pw.println("<input type='submit' value='🏠 Home' style='padding:10px; background-color:lightgreen; border:none; cursor:pointer;'/>");
        pw.println("</form>");

        pw.println("<form action='index.html' method='get'>");
        pw.println("<input type='submit' value='➕ Add Expense' style='padding:10px; background-color:lightblue; border:none; cursor:pointer;'/>");
        pw.println("</form>");
    
    }
}
