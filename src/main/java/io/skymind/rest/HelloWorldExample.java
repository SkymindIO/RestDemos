package io.skymind.rest;

/**
 * Created by tomhanlon on 10/26/17.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldExample extends HttpServlet {
    public HelloWorldExample() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\'>");
            String title = "Simple Rest Wrapper for Deep Learning Models";
            out.println("<title>" + title + "</title></head>");
            out.println("<body>");
            out.println("<h1>" + title + "</h1>");
            out.println("<p><a href=\"iris\">Iris Model</a> </p>");
            out.println("<p><a href=\"abelone\">Abelone Model</a> </p>");

            out.println("</body></html>");
        } finally {
            out.close();
        }

    }
}
