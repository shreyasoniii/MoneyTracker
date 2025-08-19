package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewExpenses")
public class viewServlet extends HttpServlet {

    private static final String query = "SELECT * FROM EXPENSEDATA";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            pw.println("<h2 style='color:red;'>MySQL Driver not found!</h2>");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///expense_tracker", "root", "shreya123");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            pw.println("<h2 style='color:blue;'>📊 Expense Records</h2>");
            pw.println("<table border='1' cellpadding='10' cellspacing='0'>");
            pw.println("<tr style='background-color:lightblue;'>");
            pw.println("<th>ID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Category</th>");
            pw.println("<th>Amount</th>");
            pw.println("<th>Date</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("EXPENSENAME");
                String category = rs.getString("CATEGORY");
                double amount = rs.getDouble("AMOUNT");
                String date = rs.getString("DATE");

                pw.println("<tr>");
                pw.println("<td>" + id + "</td>");
                pw.println("<td>" + name + "</td>");
                pw.println("<td>" + category + "</td>");
                pw.println("<td>" + amount + "</td>");
                pw.println("<td>" + date + "</td>");

                // ✅ Edit button
                pw.println("<td>");
                pw.println("<form action='editExpense' method='get'>");
                pw.println("<input type='hidden' name='id' value='" + id + "'/>");
                pw.println("<input type='submit' value='✏️ Edit' style='background-color:yellow; padding:5px; border:none; cursor:pointer;'/>");
                pw.println("</form>");
                pw.println("</td>");

                // ✅ Delete button
                pw.println("<td>");
                pw.println("<form action='deleteExpense' method='post' onsubmit=\"return confirm('Are you sure?')\">");
                pw.println("<input type='hidden' name='id' value='" + id + "'/>");
                pw.println("<input type='submit' value='Delete' style='background-color:red; color:white; padding:5px; border:none; cursor:pointer;'/>");
                pw.println("</form>");
                pw.println("</td>");

                pw.println("</tr>");
            }

            pw.println("</table>");

        } catch (SQLException se) {
            pw.println("<h2 style='color:red;'>" + se.getMessage() + "</h2>");
        }

        // ✅ Buttons for Home and Add Expense
        pw.println("<br><br>");
        pw.println("<form action='index.html' method='get'>");
        pw.println("<input type='submit' value='🏠 Home' style='padding:10px; background-color:lightgreen; border:none; cursor:pointer;'/>");
        pw.println("</form>");

        pw.println("<form action='index.html' method='get'>");
        pw.println("<input type='submit' value='➕ Add Expense' style='padding:10px; background-color:lightblue; border:none; cursor:pointer;'/>");
        pw.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
