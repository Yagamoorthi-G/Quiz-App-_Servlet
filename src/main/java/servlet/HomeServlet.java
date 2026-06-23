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
 * Servlet implementation class HomeServlet
 */
@SuppressWarnings("serial")
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        String username = (String) s.getAttribute("username");

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Home</title><link rel='stylesheet' href='styles.css'></head><body>");
        out.println("<div class='header'><form action='logout' method='post'><button class='top-left'>Logout</button></form></div>");
        out.println("<div class='container'>");
        out.println("<h1>Welcome, " + escape(username) + "</h1>");
        out.println("<form action='quiz' method='post'>");
        out.println("<label>Select Section</label>");
        out.println("<select name='section' required>");
        out.println("<option value='Java'>Java</option>");
        out.println("<option value='DBMS'>DBMS</option>");
        out.println("<option value='DSA'>DSA</option>");
        out.println("</select>");
        out.println("<input type='submit' value='Start Quiz'>");
        out.println("</form>");
        out.println("<form action='history' method='get' style='margin-top:12px;'>");
        out.println("<input type='submit' value='View Score History'>");
        out.println("</form>");
        out.println("</div></body></html>");
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}