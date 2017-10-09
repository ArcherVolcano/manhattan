package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import manhattan.constants.Constants;
import manhattan.domain.impl.About;
import manhattan.service.AboutService;
import manhattan.service.impl.AboutServiceImpl;

@MultipartConfig(fileSizeThreshold=Constants.MEMORY_THRESHOLD, 
maxFileSize=Constants.MAX_FILE_SIZE, maxRequestSize=Constants.MAX_REQUEST_SIZE)
public class AboutController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final AboutService aboutService;
	private PrintWriter out;
	private JSONObject jsonObject;
	private JSONArray errors;

	public AboutController() {
		this.aboutService = new AboutServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().equals("/GetAbout")) {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out = resp.getWriter();
			jsonObject = new JSONObject();
			Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
			About contact = aboutService.getAbout();
			jsonObject.put("entity", gson.toJson(contact));
			out.print(jsonObject);
			out.close();
		} else return;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		About about = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		out = resp.getWriter();
		switch (req.getServletPath()) {
		case "/AddAbout":
			about = createEntity(req);
			try {
				if (aboutService.save(about)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.ADD_ABOUT_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.ADD_ABOUT_FAIL);
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
		case "/UpdateAbout":
			about = createEntity(req);
			try {
				if (aboutService.update(about)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.UPDATE_ABOUT_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.UPDATE_ABOUT_FAIL);
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

	private About createEntity(HttpServletRequest req) {
		String id = req.getParameter("id");
		String turkishInfo = req.getParameter("turkishInfo");
		String englishInfo = req.getParameter("englishInfo");
		About about = new About();
		about.setId(("".equals(id) || id == null) ? null : Long.parseLong(id));
		about.setTurkishInfo(turkishInfo);
		about.setEnglishInfo(englishInfo);
		return about;
	}
	
	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}

}
