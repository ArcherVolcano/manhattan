package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import manhattan.constants.Constants;
import manhattan.domain.impl.Link;
import manhattan.service.LinkService;
import manhattan.service.impl.LinkServiceImpl;

@MultipartConfig(fileSizeThreshold=Constants.MEMORY_THRESHOLD, 
maxFileSize=Constants.MAX_FILE_SIZE, maxRequestSize=Constants.MAX_REQUEST_SIZE)
public class VideoController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LinkService linkService;
	private PrintWriter out;
	private JSONObject jsonObject;
	private JSONArray errors;
	
	public VideoController() {
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
		case "/AllVideos":
			List<Link> entities = linkService.getAllVideos();
			jsonArray.put(entities);
			out.print(jsonArray);
			out.close();
			break;
			
		case "/VideoCount":
			JSONObject jsonObject = new JSONObject();
			int count = linkService.getVideoCount();
			jsonObject.put("count", count);
			out.print(jsonObject);
			out.close();
			break;
		case "/VideosByLimitAndOffset":
			List<Link> entitiesByLimitAndOffset = linkService.getVideosByLimitAndOffset(limit, offset);
			jsonArray.put(entitiesByLimitAndOffset);
			out.print(jsonArray);
			out.close();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		Link video = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		out = resp.getWriter();
		switch (req.getServletPath()) {
		case "/AddVideo":
			video = createEntity(req);
			try {
				if (linkService.save(video)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.ADD_VIDEO_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.ADD_VIDEO_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
		
		case "/UpdateVideo":
			video = createEntity(req);
			try {
				if (linkService.update(video)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.UPDATE_VIDEO_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.UPDATE_VIDEO_FAIL);
				}
				out.println(jsonObject);
			} catch (Exception e) {
				errors.put(e.toString());
				outPrintErrors();
			} finally {
				out.close();
			}
			break;
			
		case "/DeleteVideo":
			video = createEntity(req);
			try {
				if (linkService.delete(video)) {
					jsonObject.put("result", true);
					jsonObject.put("resultMsg", Constants.DELETE_VIDEO_SUCCESS);
				} 
				else {
					jsonObject.put("result", false);
					jsonObject.put("resultMsg", Constants.DELETE_VIDEO_FAIL);
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

	private Link createEntity(HttpServletRequest req) {
		String header = req.getParameter("videoHeader");
		String videoLink = req.getParameter("embededLink");
		String id = req.getParameter("videoId");
		Link link = new Link();
		link.setId(("".equals(id) || null == id) ? null : Long.parseLong(id));
		link.setLink(videoLink);
		link.setName(header);
		link.setIsVideoItem(true);
		return link;
	}
	
	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}

}
