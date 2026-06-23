package servlet;

import java.io.IOException;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final String STORAGE_DIR = "D:/IDE/Storage";
    private static final String USERS_FILE = STORAGE_DIR + "/users.txt";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password").trim();

        File users = new File(USERS_FILE);
        String nameFound = null;

        if (users.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(users))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",", 3);
                    if (p.length == 3 && p[1].equalsIgnoreCase(email) && p[2].equals(password)) {
                        nameFound = p[0];
                        break;
                    }
                }
            }
        }

        if (nameFound != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", nameFound);
            session.setAttribute("email", email);
            resp.sendRedirect("home");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><link rel='stylesheet' href='../styles.css'></head><body>");
            out.println("<div class='container'><h2>Invalid email or password</h2>");
            out.println("<p><a href='login.html' class='link'>Try again</a></p></div></body></html>");
        }
    }
}