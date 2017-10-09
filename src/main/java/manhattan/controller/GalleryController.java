package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.json.JSONArray;
import org.json.JSONObject;

import manhattan.constants.Constants;
import manhattan.domain.impl.Link;
import manhattan.service.LinkService;
import manhattan.service.impl.LinkServiceImpl;

@MultipartConfig(fileSizeThreshold=Constants.MEMORY_THRESHOLD, 
maxFileSize=Constants.MAX_FILE_SIZE, maxRequestSize=Constants.MAX_REQUEST_SIZE)
public class GalleryController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1857069605205902739L;
	private final LinkService linkService;
	private FileUploader fileUpload;
	private JSONObject jsonObject;
	private JSONArray errors;
	private PrintWriter out;
	private final String all = "/AllPictures";
	private final String byLimit = "/PicturesByLimit";
	private final String byLimitAndOffset = "/PicturesByLimitAndOffset";
	private final String count = "/PictureCount";
	
	public GalleryController() {
		this.linkService = new LinkServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		JSONArray jsonArray = new JSONArray();
		int limit = ("".equals(req.getParameter("limit")) || null == req.getParameter("limit")) ? 0 : Integer.parseInt(req.getParameter("limit"));
		int offset = ("".equals(req.getParameter("offset")) || null == req.getParameter("offset")) ? 0 : Integer.parseInt(req.getParameter("offset"));
		switch (req.getServletPath()) {
		case all:
			List<Link> entities = linkService.getAllPictures();
			jsonArray.put(entities);
			out.print(jsonArray);
			out.close();
			break;
		case byLimit:
			List<Link> entitiesByLimit = linkService.getPicturesByLimit(limit);
			jsonArray.put(entitiesByLimit);
			out.print(jsonArray);
			out.close();
			break;
		case byLimitAndOffset:
			List<Link> entitiesByLimitAndOffset = linkService.getPicturesByLimitAndOffset(limit, offset);
			jsonArray.put(entitiesByLimitAndOffset);
			out.print(jsonArray);
			out.close();
			break;
		case count:
			JSONObject jsonObject = new JSONObject();
			int count = linkService.getPictureCount();
			jsonObject.put("count", count);
			out.print(jsonObject);
			out.close();
			break;
		default:
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Link link = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		fileUpload = new FileUploader(req);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out = resp.getWriter();
		String servletPath = req.getServletPath();
		
		
		switch (servletPath) {
		case "/AddPicture":
			String imageName = fileUpload.uploadFile();
			//Eðer resmi null ise demekki hata vardýr. Hatayý basýp return diyerek kodun devam etmesini önlüyoruz. Çünkü devam etmesine gerek yok.
			if (imageName == null) {
				errors.put(fileUpload.getErrors());
				outPrintErrors();
				return;
			}
			link = createEntity(req);
			link.setLink(imageName);
			try {
				if (linkService.save(link)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.ADD_PICTURE_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.ADD_PICTURE_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
			
		case "/DeletePicture":
			link = createEntity(req);
			fileUpload.deleteFile(link.getLink());
			try {
				if (linkService.delete(link)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.DELETE_PICTURE_SUCCESS);
				}
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.DELETE_PICTURE_FAIL);
				}
				out.print(jsonObject);
			} catch (HibernateException e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;

		default:
			break;
		}
	}
	
	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}
	
	private Link createEntity(HttpServletRequest req) {
		String header = req.getParameter("pictureHeader");
		String imgName = req.getParameter("imgName");
		String imgId = req.getParameter("imgId");
		Link link = new Link();
		link.setId(("".equals(imgId) || null == imgId) ? null : Long.parseLong(imgId));
		link.setLink(imgName);
		link.setName(header);
		link.setIsGalleryItem(true);
		return link;
	}

}
