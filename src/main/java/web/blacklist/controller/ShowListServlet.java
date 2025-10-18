package web.blacklist.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import web.blacklist.service.BlackListService;
import web.blacklist.service.impl.BlackListServiceImpl;
import web.user.pojo.User;


@WebServlet("/blacklist/webBlock")
public class ShowListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BlackListService blackListService;
	
	@Override
	public void init() throws ServletException {
		try {
			blackListService = new BlackListServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 List<User> list =  blackListService.findAll();
		 Gson gson = new Gson();
		 String json = gson.toJson(list);
		 PrintWriter pw = resp.getWriter();
		 pw.write(json);
	}
}
