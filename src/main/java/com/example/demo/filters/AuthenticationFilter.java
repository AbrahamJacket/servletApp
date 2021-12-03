package com.example.demo.filters;

import com.example.demo.session.LoginData;
import com.example.demo.session.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.context.log("Started doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);


        this.context.log("Requested Resource::http://localhost:8080" + uri);

        this.context.log("session == null - " + (session == null));
        this.context.log("uri.endsWith(demo/RegisterServlet) - " + (uri.endsWith("demo/RegisterServlet")));

        if (session == null && !uri.endsWith("demo/loginServlet")) {
            this.context.log("<<< User is not authorized >>>");
            PrintWriter out = res.getWriter();
            out.println("You are not logged in");
        } else if (session != null && LoginData.getUserByName((String) Objects.requireNonNull(session).getAttribute("user")).getAccessType() == Users.AccessType.USER
                && (uri.endsWith("demo/DeleteUserServlet") || uri.endsWith("demo/EditUserServlet") || uri.endsWith("demo/RegisterServlet")
                || uri.endsWith("demo/deleteServlet") || uri.endsWith("demo/putServlet") || uri.endsWith("demo/saveServlet"))) {
            this.context.log("<<<User has no access>>>");
            PrintWriter out = res.getWriter();
            out.println("Access Denied");
        } else if (session != null && LoginData.getUserByName(session.getAttribute("user").toString()).getAccessType() == Users.AccessType.MODERATOR
                && (uri.endsWith("demo/DeleteUserServlet") || uri.endsWith("demo/EditUserServlet") || uri.endsWith("demo/RegisterServlet"))) {
            this.context.log("<<<User has no access>>>");
            PrintWriter out = res.getWriter();
            out.println("Access Denied");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
    }
}
