package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;

import manhattan.constants.Constants;
import manhattan.domain.impl.Admin;
import manhattan.service.AdminService;
import manhattan.service.impl.AdminServiceImpl;

@MultipartConfig(fileSizeThreshold=Constants.MEMORY_THRESHOLD, 
maxFileSize=Constants.MAX_FILE_SIZE, maxRequestSize=Constants.MAX_REQUEST_SIZE)
public class AdminController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final AdminService adminService;
	private PrintWriter out;
	private JSONObject jsonObject;
	private JSONArray errors;

	public AdminController() {
		this.adminService = new AdminServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		JSONArray jsonArray = new JSONArray();
		List<Admin> admins = adminService.getlAll();
		out.println(jsonArray.put(admins));
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		Admin admin = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		out = resp.getWriter();
		switch (req.getServletPath()) {
		case "/AddAdmin":
			admin = createEntity(req);
			try {
				if (adminService.save(admin)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.ADD_ADMIN_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.ADD_ADMIN_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				if (e instanceof ConstraintViolationException) {
					errors.put("Böyle bir yönetici zaten var");
				} else errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
		case "/UpdateAdmin":
			admin = createEntity(req);
			try {
				if (adminService.update(admin)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.UPDATE_ADMIN_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.UPDATE_ADMIN_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
		case "/DeleteAdmin":
			admin = createEntity(req);
			try {
				if (adminService.delete(admin)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.DELETE_ADMIN_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.DELETE_ADMIN_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
		}
	}

	private Admin createEntity(HttpServletRequest req) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		Admin admin = new Admin();
		admin.setId(("".equals(id) || id == null) ? null : Long.parseLong(id));
		admin.setEmail(email);
		admin.setPassword(password);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		return admin;
	}
	
	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}

}
