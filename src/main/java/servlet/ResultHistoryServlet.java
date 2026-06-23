package servlet;

import java.io.IOException;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResultHistoryServlet
 */
@SuppressWarnings("serial")
@WebServlet("/ResultHistoryServlet")
public class ResultHistoryServlet extends HttpServlet {
    private static final String STORAGE_DIR = "D:/IDE/Storage";
    private static final String SCORES_FILE = STORAGE_DIR + "/scores.txt";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("email") == null) { resp.sendRedirect("login.html"); return; }

        String email = (String) s.getAttribute("email");
        List<String[]> attempts = new ArrayList<>();

        File f = new File(SCORES_FILE);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", 4);
                    if (parts.length == 4 && parts[0].equalsIgnoreCase(email)) {
                        attempts.add(parts);
                    }
                }
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>History</title><link rel='stylesheet' href='styles.css'></head><body>");
        out.println("<div class='header'><form action='logout' method='post'><button class='top-left'>Logout</button></form></div>");
        out.println("<div class='container'><h1>Your Attempts</h1>");

        if (attempts.isEmpty()) {
            out.println("<p>No attempts found. <a class='link' href='home'>Take a quiz</a></p>");
        } else {
            out.println("<table class='table'><tr><th>Section</th><th>Score</th><th>Date/Time</th></tr>");
            for (String[] a : attempts) {
                out.println("<tr><td>" + escape(a[1]) + "</td><td>" + escape(a[2]) + "</td><td>" + escape(a[3]) + "</td></tr>");
            }
            out.println("</table>");
        }

        out.println("<p class='small'><a class='link' href='home'>Back to Home</a></p>");
        out.println("</div></body></html>");
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}