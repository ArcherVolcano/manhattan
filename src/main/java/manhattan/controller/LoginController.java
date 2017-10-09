package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import manhattan.domain.impl.Admin;
import manhattan.service.AdminService;
import manhattan.service.impl.AdminServiceImpl;

public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 6360385323581706228L;
	private final AdminService adminService;
	private final String login = "/Login";
	private final String logout = "/Logout";
	
	public LoginController() {
		adminService = new AdminServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if("/IsLogged".equals(req.getServletPath())) {
			HttpSession session = req.getSession();
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			JSONObject jsonObject = new JSONObject();
			if (session.getAttribute("admin") != null) {
				jsonObject.put("result", true);
			} else jsonObject.put("result", false);
			out.print(jsonObject);
			out.close();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		switch (req.getServletPath()) {
		case login:
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			Admin admin = adminService.login(email, password);
			if(admin != null) {
				HttpSession session = req.getSession();
				session.setAttribute("admin", email);
				jsonObject.put("result", true);
				out.println(jsonObject);
			} 
			else {
				jsonObject.put("result", false);
				out.println(jsonObject);
				out.close();
			}
			break;
		case logout:
			req.getSession().invalidate();
			jsonObject.put("result", true);
			out.println(jsonObject);
			out.close();
			break;

		default:
			break;
		}
		
	}

}
