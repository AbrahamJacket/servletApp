package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter ("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();

        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);

        if (session == null && !(uri.endsWith("demo/saveServlet") || uri.endsWith("demo/loginServlet") || uri.endsWith("demo/.\\viewServlet"))){
            this.context.log("<<<Unauthorized access request");
            PrintWriter out = res.getWriter();
            out.println("No access!!!");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
