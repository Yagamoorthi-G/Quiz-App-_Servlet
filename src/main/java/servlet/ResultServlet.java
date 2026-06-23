package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.http.*;
/**
 * Servlet implementation class ResultServlet
 */
@SuppressWarnings("serial")
@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // if someone GETs /result, redirect to home
        resp.sendRedirect("home");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // allow forwarded POST from SubmitQuizServlet (it forwards)
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) { resp.sendRedirect("login.html"); return; }

        Object lastScoreObj = req.getAttribute("lastScore");
        Object totalObj = req.getAttribute("total");

        String username = (String) s.getAttribute("username");
        String section = (String) s.getAttribute("section");

        int lastScore = (lastScoreObj != null) ? (Integer) lastScoreObj : -1;
        int total = (totalObj != null) ? (Integer) totalObj : -1;

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Result</title><link rel='stylesheet' href='styles.css'></head><body>");
        out.println("<div class='header'><form action='logout' method='post'><button class='top-left'>Logout</button></form></div>");
        out.println("<div class='container'>");
        out.println("<h1>Hi, " + escape(username) + "!</h1>");
        if (lastScore >= 0 && total > 0) {
            out.println("<h2>Section: " + escape(section) + "</h2>");
            out.println("<h2>Your Score: " + lastScore + " / " + total + "</h2>");
        } else {
            out.println("<h2>No recent attempt to show.</h2>");
        }
        out.println("<p class='small'><a class='link' href='home'>Back to Home</a></p>");
        out.println("</div></body></html>");
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}