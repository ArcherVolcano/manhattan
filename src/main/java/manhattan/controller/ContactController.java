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
import manhattan.domain.impl.Contact;
import manhattan.service.ContactService;
import manhattan.service.impl.ContactServiceImpl;

@MultipartConfig(fileSizeThreshold=Constants.MEMORY_THRESHOLD, 
maxFileSize=Constants.MAX_FILE_SIZE, maxRequestSize=Constants.MAX_REQUEST_SIZE)
public class ContactController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ContactService contactService;
	private PrintWriter out;
	private JSONObject jsonObject;
	private JSONArray errors;

	public ContactController() {
		this.contactService = new ContactServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().equals("/GetContact")) {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out = resp.getWriter();
			jsonObject = new JSONObject();
			Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
			Contact contact = contactService.getContact();
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
		Contact contact = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		out = resp.getWriter();
		switch (req.getServletPath()) {
		case "/AddContact":
			contact = createEntity(req);
			try {
				if (contactService.save(contact)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.ADD_CONTACT_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.ADD_CONTACT_FAIL);
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
		case "/UpdateContact":
			contact = createEntity(req);
			try {
				if (contactService.update(contact)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.UPDATE_CONTACT_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.UPDATE_CONTACT_FAIL);
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

	private Contact createEntity(HttpServletRequest req) {
		String id = req.getParameter("id");
		String phone = req.getParameter("phone");
		String mobilePhone = req.getParameter("mobilePhone");
		String fax = req.getParameter("fax");
		String email = req.getParameter("email");
		String facebook = req.getParameter("facebook");
		String twitter = req.getParameter("twitter");
		String instagram = req.getParameter("facebook");
		String address = req.getParameter("address");
		Contact contact = new Contact();
		contact.setId(("".equals(id) || id == null) ? null : Long.parseLong(id));
		contact.setPhone(phone);
		contact.setMobilePhone(mobilePhone);
		contact.setFax(fax);
		contact.setEmail(email);
		contact.setFacebookLink(facebook);
		contact.setTwitterLink(twitter);
		contact.setInstagramLink(instagram);
		contact.setAddress(address);
		return contact;
	}
	
	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}

}
