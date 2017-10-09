package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import manhattan.constants.Constants;
import manhattan.domain.impl.Event;
import manhattan.service.EventService;
import manhattan.service.impl.EventServiceImpl;
import manhattan.util.TimeUtil;

@MultipartConfig(fileSizeThreshold = Constants.MEMORY_THRESHOLD, maxFileSize = Constants.MAX_FILE_SIZE, maxRequestSize = Constants.MAX_REQUEST_SIZE)
public class EventController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final EventService eventService;
	private final String add = "/AddEvent";
	private final String byId = "/EventById";
	private final String update = "/UpdateEvent";
	private final String delete = "/DeleteEvent";
	private final String all = "/AllEvents";
	private final String publishedEventByLimit = "/PublishedEventByLimit";
	private final String allPublishedEvent = "/AllPublishedEvent";
	private final String unPublishedByLimit = "/UnPublishedEventByLimit";
	private final String allUnPublishedEvent = "/AllUnPublishedEvent";
	private FileUploader fileUpload;
	private JSONObject jsonObject;
	private JSONArray errors;
	private PrintWriter out;

	public EventController() {
		this.eventService = new EventServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONArray jsonArray = new JSONArray();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out = resp.getWriter();
		int limit = ("".equals(req.getParameter("limit")) || null == req.getParameter("limit")) ? 0 : Integer.parseInt(req.getParameter("limit"));
		switch (req.getServletPath()) {
		case all:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			List<Event> entities = eventService.getlAll();
			jsonArray.put(entities);
			out.print(jsonArray);
			out.close();
			break;
		case allPublishedEvent:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			List<Event> publishedEntities = eventService.getAllPublishEntities();
			jsonArray.put(publishedEntities);
			out.print(jsonArray);
			out.close();
			break;
		case allUnPublishedEvent:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			List<Event> unPublishedEntities = eventService.getAllUnPublishEntities();
			jsonArray.put(unPublishedEntities);
			out.print(jsonArray);
			out.close();
			break;
		case byId:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			String id = req.getParameter("id");
			Event event = eventService.getById(Long.parseLong(id));
			JSONObject jsonObject = new JSONObject();
			Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setPrettyPrinting().create();
			System.out.println(Locale.getDefault());
			jsonObject.put("entity", gson.toJson(event));
			out.print(jsonObject);
			out.close();
			break;
		case publishedEventByLimit:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			List<Event> publishedEntitiesLimit = eventService.getByPublishEntitiesLimit(limit);
			jsonArray.put(publishedEntitiesLimit);
			out.print(jsonArray);
			out.close();
			break;
		case unPublishedByLimit:
			// Response nesnemize göndereceðimiz datanýn json olacaðýný
			// söylüyoruz.
			jsonArray = new JSONArray();
			List<Event> unPublishedEntitiesLimit = eventService.getByUnPublishEntitiesLimit(limit);
			jsonArray.put(unPublishedEntitiesLimit);
			out.print(jsonArray);
			out.close();
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Ajax methodunda contentType: false olduðundan req caharter encoding
		// null geliyor biz utf8 set ediyoruz karakterleri doðru okuyabilmek
		// için.
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		Event event = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		fileUpload = new FileUploader(req);
		out = resp.getWriter();
		String servletPath = req.getServletPath();
		
		switch (servletPath) {
		case add:
			String imageName = fileUpload.uploadFile();
			// Eðer resmi null ise demekki hata vardýr. Hatayý basýp return
			// diyerek kodun devam etmesini önlüyoruz. Çünkü devam etmesine
			// gerek yok.
			if (imageName == null) {
				errors.put(fileUpload.getErrors());
				outPrintErrors();
				return;
			}
			event = createEntity(req);
			event.getPicture().setLink(imageName);
			saveEntity(event);
			break;
		case update:
			event = createEntity(req);
			// Requestte dosya varsa eskisini sil yenisini ekle.
			if (fileUpload.isRequestHaveFile()) {
				// Eski Dosya silindi mi?
				if (fileUpload.deleteFile(event.getPicture().getLink())) {
					String imgName = fileUpload.uploadFile();
					event.getPicture().setLink(imgName);
				} else {
					errors.put("Eski Resim Silinemedi");
					outPrintErrors();
					return;
				}
			}
			updateEntity(event);
			break;
		case delete:
			event = createEntity(req);
			deleteEntity(event);
			break;

		default:
			break;
		}
	}

	private void saveEntity(Event event) {
		try {
			if (eventService.save(event)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.ADD_EVENT_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.DELETE_EVENT_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			if (e instanceof ConstraintViolationException) {
				errors.put("Böyle bir etkinlik zaten var");
				outPrintErrors();
			}
		} finally {
			out.close();
		}
	}

	private void updateEntity(Event event) {
		try {
			if (eventService.update(event)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.UPDATE_EVENT_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.UPDATE_EVENT_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			errors.put(e.toString());
			outPrintErrors();
		} finally {
			out.close();
		}
	}

	private void deleteEntity(Event event) {
		try {
			if (eventService.delete(event)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.DELETE_EVENT_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.DELETE_EVENT_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			errors.put(e.toString());
			outPrintErrors();
		} finally {
			out.close();
		}
	}

	private Event createEntity(HttpServletRequest req) {
		String eventName = req.getParameter("eventHeader");
		String eventId = req.getParameter("eventId");
		String bandId = req.getParameter("bandId");
		String imgId = req.getParameter("imgId");
		String imgName = req.getParameter("imgName");
		String eventInfo = req.getParameter("eventInfo");
		String catering = req.getParameter("catering");
		String ticketPrice = req.getParameter("ticketPrice");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		Event event = new Event();
		event.setId(("".equals(eventId) || null == eventId) ? null : Long.parseLong(eventId));
		event.setEventName(eventName);
		event.getBand().setId(("".equals(bandId) || null == bandId) ? null : Long.parseLong(bandId));
		event.setStartDate(TimeUtil.htmlDateTimeLocalToDate(startDate));
		event.setEndDate(TimeUtil.htmlDateTimeLocalToDate(endDate));
		event.setCatering(catering);
		event.setTicketPrice(ticketPrice);
		event.setEventInfo(eventInfo);
		event.getPicture().setId(("".equals(imgId) || null == imgId) ? null : Long.parseLong(imgId));
		event.getPicture().setLink(imgName);
		return event;
	}

	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}
}
