package com.example.demo.session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        String access = request.getParameter("access");

        int status = 0;
        int result = 1;

        List<Users> list = LoginData.getAllUsers();

        for(Users listUsers : list) {
            if (listUsers.getUser().equals(user)){
                out.println("This user already exists!");
                result = 0;
            }
        }

        if (!access.equals(Users.AccessType.USER.toString()) && !access.equals(Users.AccessType.MODERATOR.toString())
                && !access.equals(Users.AccessType.ADMIN.toString())){
            out.println("Wrong access type!");
            result = 0;
        }

        if (result == 1){
            Users users = new Users();

            users.setUser(user);
            users.setPwd(pwd);
            users.setAccessType(Users.AccessType.valueOf(access));

            status = LoginData.save(users);
        }

        if (status > 0) {
            out.print("Record saved successfully!");
        } else {
            out.println("Sorry! unable to save record");
        }
    {
        out.close();
    }
}
}

