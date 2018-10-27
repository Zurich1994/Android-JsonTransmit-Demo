package com.gem.hsx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gem.hsx.daoimpl.UserDaoImpl;

@SuppressWarnings("serial")
public class Check extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	   doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		UserDaoImpl userDaoImpl=new UserDaoImpl();
		boolean b=userDaoImpl.check(username);
		if (b)  //用户名存在
		{
			String a="t";
			out.write(a);
		}
		else  //用户名不存在
		{
			 String c="f";
			 out.write(c);
		}
		out.flush();
		out.close();
	}

}
