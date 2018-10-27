package com.gem.hsx.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gem.hsx.bean.User;
import com.gem.hsx.daoimpl.UserDaoImpl;
import com.gem.hsx.json.JsonUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@SuppressWarnings("serial")
public class Register extends HttpServlet {


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
		String jsondata=request.getParameter("jsonstring");
		JsonUtil jsonUtil=new JsonUtil();
		System.out.println(jsondata);
		List<User> list=jsonUtil.StringFromJson(jsondata);
		User user=list.get(0);
	    System.out.println(user.getSex());
		UserDaoImpl userDaoImpl=new UserDaoImpl();
		boolean b=userDaoImpl.register(user);
		if (b) 
		{
			out.write("t");
		}
		else {
			out.write("f");
		}
		out.flush();
		out.close();

	}

}
