package com.registration.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String url = "jdbc:mysql://localhost:3306/registration?useSSL=false";
		String user = "root";
		String password = "root";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String uname = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String contact = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection connection = null;
		try {

			// Establishing a connection
			connection = DriverManager.getConnection(url, user, password);
			
			String query = "insert into user (uname,upassword,uemail,contact) values(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, uname);
			ps.setString(2, pass);
			ps.setString(3, email);
			ps.setString(4, contact);
			int rowcount = ps.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowcount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "falied");
			}

			dispatcher.forward(request, response);
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
