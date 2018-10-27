package com.gem.hsx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gem.hsx.daoimpl.UserDaoImpl;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	    PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserDaoImpl userDaoImpl=new UserDaoImpl();
		boolean b=userDaoImpl.login(username,password);
		if (b) 
		{
			out.write("µÇÂ¼³É¹¦");
		}
		else 
		{
			out.write("µÇÂ¼Ê§°Ü");
		}
		out.flush();
		out.close();
	}

}
