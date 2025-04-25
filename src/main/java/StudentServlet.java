import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/register")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college", "root", "Nilesh@7");

            String sql = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h2>Student registered successfully!</h2>");
            } else {
                out.println("<h2>Failed to register student.</h2>");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace(out); 
        }
    }
}
