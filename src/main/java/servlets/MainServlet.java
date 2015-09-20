package servlets;


import org.eclipse.jetty.util.log.Log;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by ivan on 18.09.15.
 */
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        //String hello = "Hello!!";
        resp.setContentType("text/html");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.getPage("index.html"));
        System.out.println("in the end");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (resp != null) {
            resp.setContentType("text/html;charset=utf-8");
        }
    }
}
