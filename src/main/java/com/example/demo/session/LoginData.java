package com.example.demo.session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginData {
    public static List<Users> list = LoginData.getAllUsers();

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/system_users";
        String user = "postgres";
        String password = "1234";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return connection;
    }

    public static int save(Users users) {
        int status = 0;
        try {
            Connection connection = LoginData.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into admin_users(\"user\",pwd,access) values (?,?,?)");
            ps.setString(1, users.getUser());
            ps.setString(2, users.getPwd());
            ps.setString(3, users.getAccessType().toString());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Users users) {

        int status = 0;

        try {
            Connection connection = LoginData.getConnection();
            PreparedStatement ps = connection.prepareStatement("update admin_users set \"user\"=?,pwd=?,access=? where id=?");
            ps.setString(1, users.getUser());
            ps.setString(2, users.getPwd());
            ps.setString(3, users.getAccessType().toString());
            ps.setInt(4, users.getId());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            Connection connection = LoginData.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from admin_users where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Users getUserByName(String name) {

        Users users = new Users();

        try {
            Connection connection = LoginData.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from admin_users where \"user\"=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                users.setId(rs.getInt(1));
                users.setUser(rs.getString(2));
                users.setPwd(rs.getString(3));
                users.setAccessType(Users.AccessType.valueOf(rs.getString(4)));
            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    public static List<Users> getAllUsers() {

        List<Users> listUsers = new ArrayList<>();

        try {
            Connection connection = LoginData.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from admin_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Users users = new Users();

                users.setId(rs.getInt(1));
                users.setUser(rs.getString(2));
                users.setPwd(rs.getString(3));
                listUsers.add(users);
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    public static boolean checkInList(String login, String password) {
        list = LoginData.getAllUsers();
        for (Users user : list) {
            if (user.getUser().equals(login) && user.getPwd().equals(password)) {
                return true;
            }
        }
        return false;
    }
}