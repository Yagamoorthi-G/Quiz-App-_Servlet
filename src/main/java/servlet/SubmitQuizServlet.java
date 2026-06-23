package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubmitQuizServlet
 */
@SuppressWarnings("serial")
@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    private static final String STORAGE_DIR = "D:/IDE/Storage";
    private static final String SCORES_FILE = STORAGE_DIR + "/scores.txt";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("email") == null) { resp.sendRedirect("login.html"); return; }

        String section = (String) s.getAttribute("section");
        QuizServlet qs = new QuizServlet();
        String[][] Q = qs.getQuestions(section);

        int score = 0;
        for (int i = 0; i < Q.length; i++) {
            String answer = req.getParameter("q" + i);
            if (answer != null && answer.equals(Q[i][4])) score++;
        }

        // persist attempt
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) dir.mkdirs();
        File f = new File(SCORES_FILE);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(fmt);
        String email = (String) s.getAttribute("email");

        synchronized (SubmitQuizServlet.class) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
                bw.write(email + "," + section + "," + score + "," + now);
                bw.newLine();
            }
        }

        // set lastScore and forward to result
        req.setAttribute("lastScore", score);
        req.setAttribute("total", Q.length);
        req.getRequestDispatcher("result").forward(req, resp);
    }
}