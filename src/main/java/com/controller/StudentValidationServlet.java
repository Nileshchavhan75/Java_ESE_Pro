package com.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/validateStudent")
public class StudentValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String rollInput = request.getParameter("rollNo");

        try {
            int rollNo = Integer.parseInt(rollInput.trim());

            if (rollNo <= 0) {
                out.println("<h3 style='color:red'>❌ Roll number must be a positive number.</h3>");
            } else {
                // JDBC Setup
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/college", "root", "Nilesh@7");

                CallableStatement cst = con.prepareCall("{ call getStudentName(?) }");
                cst.setInt(1, rollNo);

                ResultSet rs = cst.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    out.println("<h3 style='color:green'>✅ Student Name: " + name + "</h3>");
                } else {
                    out.println("<h3 style='color:red'>❌ No student found with that roll number.</h3>");
                }

                rs.close();
                cst.close();
                con.close();
            }

        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red'>❌ Invalid input. Please enter a numeric roll number.</h3>");
        } catch (Exception e) {
            out.println("<h3 style='color:red'>❌ Server error: " + e.getMessage() + "</h3>");
        }

        out.close();
    }
}
