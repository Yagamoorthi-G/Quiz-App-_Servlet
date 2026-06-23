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
 * Servlet implementation class QuizServlet
 */
@SuppressWarnings("serial")
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
    // each row: {question, opt1, opt2, opt3, correctAnswer}
    public String[][] getQuestions(String section) {
        switch (section) {
            case "Java":
                return new String[][] {
                    {"Which keyword is used to inherit a class?", "super", "extends", "this", "extends"},
                    {"Java is a ____ language.", "Compiled", "Interpreted", "Both", "Both"},
                    {"Which company originally developed Java?", "Microsoft", "Sun Microsystems", "Oracle", "Sun Microsystems"},
                    {"What does JVM stand for?", "Java Virtual Machine", "Java Visual Model", "Java Variable Memory", "Java Virtual Machine"},
                    {"Which method is entry point of Java program?", "main()", "start()", "run()", "main()"},
                    {"Which is not an OOP concept?", "Encapsulation", "Inheritance", "Compilation", "Compilation"},
                    {"Which keyword creates an object?", "object", "new", "class", "new"},
                    {"Java supports ___ inheritance (directly).", "Multiple", "Single", "Hybrid", "Single"},
                    {"Which package contains Scanner class?", "java.util", "java.io", "java.lang", "java.util"},
                    {"Which operator is used for equality comparison?", "=", "==", "equals", "=="}
                };
            case "DBMS":
                return new String[][] {
                    {"DBMS stands for?", "Database Management System", "Data Base Main Server", "Database Managing Software", "Database Management System"},
                    {"Which language is used for queries?", "C", "SQL", "Python", "SQL"},
                    {"Primary key can be?", "Duplicate", "Null", "Unique", "Unique"},
                    {"Which command removes a table?", "REMOVE", "DROP", "DELETE", "DROP"},
                    {"Normalization is used to?", "Remove redundancy", "Add redundancy", "Speed up queries", "Remove redundancy"},
                    {"Which SQL clause is used to sort?", "ORDER BY", "GROUP BY", "SORT", "ORDER BY"},
                    {"Which key uniquely identifies a record?", "Foreign Key", "Primary Key", "Candidate Key", "Primary Key"},
                    {"Which join returns all rows from both tables?", "INNER JOIN", "FULL JOIN", "LEFT JOIN", "FULL JOIN"},
                    {"Which command modifies table structure?", "MODIFY", "ALTER", "UPDATE", "ALTER"},
                    {"Which stores data permanently?", "View", "Table", "Trigger", "Table"}
                };
            default: // DSA
                return new String[][] {
                    {"Stack works on which principle?", "FIFO", "LIFO", "FILO", "LIFO"},
                    {"Queue works on which principle?", "FIFO", "LIFO", "LILO", "FIFO"},
                    {"Binary search requires data to be?", "Sorted", "Unsorted", "Random", "Sorted"},
                    {"Which data structure naturally uses recursion?", "Queue", "Stack", "Array", "Stack"},
                    {"Which sorting algorithm is average-case fast?", "Bubble Sort", "Quick Sort", "Insertion Sort", "Quick Sort"},
                    {"Which uses priority ordering?", "Queue", "Stack", "Priority Queue", "Priority Queue"},
                    {"Linked list nodes contain?", "Data only", "Data & Address", "Address only", "Data & Address"},
                    {"Which traversal is DFS variant?", "Level Order", "Inorder", "Breadth First", "Inorder"},
                    {"Which algorithm uses divide and conquer?", "Merge Sort", "Selection Sort", "Bubble Sort", "Merge Sort"},
                    {"Which is not a linear data structure?", "Stack", "Array", "Graph", "Graph"}
                };
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String section = req.getParameter("section");
        if (section == null) { resp.sendRedirect("home"); return; }

        s.setAttribute("section", section);
        String[][] Q = getQuestions(section);

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>" + section + " Quiz</title><link rel='stylesheet' href='styles.css'></head><body>");
        out.println("<div class='header'><form action='logout' method='post'><button class='top-left'>Logout</button></form></div>");
        out.println("<div class='container'><h1>" + escape(section) + " Quiz</h1>");
        out.println("<form action='submitQuiz' method='post'>");

        for (int i = 0; i < Q.length; i++) {
            out.println("<div style='margin-bottom:12px;'>");
            out.println("<p><strong>" + (i+1) + ". " + escape(Q[i][0]) + "</strong></p>");
            out.println("<label><input type='radio' name='q" + i + "' value='" + escape(Q[i][1]) + "' required> " + escape(Q[i][1]) + "</label><br>");
            out.println("<label><input type='radio' name='q" + i + "' value='" + escape(Q[i][2]) + "'> " + escape(Q[i][2]) + "</label><br>");
            out.println("<label><input type='radio' name='q" + i + "' value='" + escape(Q[i][3]) + "'> " + escape(Q[i][3]) + "</label><br>");
            out.println("</div>");
        }

        out.println("<input type='submit' value='Submit Quiz'>");
        out.println("</form></div></body></html>");
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}