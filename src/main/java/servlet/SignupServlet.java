package servlet;

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignupServlet
 */
@SuppressWarnings("serial")
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final String STORAGE_DIR = "D:/IDE/Storage";
    private static final String USERS_FILE = STORAGE_DIR + "/users.txt";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String email = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password").trim();

        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) dir.mkdirs();

        File users = new File(USERS_FILE);

        // check duplicate
        boolean exists = false;
        if (users.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(users))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",", 3);
                    if (p.length >= 2 && p[1].equalsIgnoreCase(email)) { exists = true; break; }
                }
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if (exists) {
            out.println("<html><head><link rel='stylesheet' href='../styles.css'></head><body>");
            out.println("<div class='container'><h2>Email already registered</h2>");
            out.println("<p><a href='login.html' class='link'>Login</a></p></div></body></html>");
            return;
        }

        synchronized (SignupServlet.class) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(users, true))) {
                // store as name,email,password
                bw.write(name.replace(",", " ") + "," + email + "," + password);
                bw.newLine();
            }
        }

        resp.sendRedirect("login.html");
    }
}