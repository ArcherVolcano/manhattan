package manhattan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;

import manhattan.constants.Constants;
import manhattan.domain.impl.Band;
import manhattan.service.BandService;
import manhattan.service.impl.BandServiceImpl;

@MultipartConfig(fileSizeThreshold = Constants.MEMORY_THRESHOLD, maxFileSize = Constants.MAX_FILE_SIZE, maxRequestSize = Constants.MAX_REQUEST_SIZE)
public class BandController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final BandService bandService;
	private FileUploader fileUpload;
	private JSONObject jsonObject;
	private JSONArray errors;
	private PrintWriter out;

	public BandController() {
		this.bandService = new BandServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/AllBands".equals(req.getServletPath())) {
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			JSONArray jsonArray = new JSONArray();
			List<Band> entities = bandService.getlAll();
			jsonArray.put(entities);
			out.print(jsonArray);
			out.close();
		} else
			req.getRequestDispatcher("/admin/BandSettings.html").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Ajax methodunda contentType: false olduðundan req caharter encoding
		// null geliyor biz utf8 set ediyoruz karakterleri doðru okuyabilmek
		// için.
		req.setCharacterEncoding("UTF-8");
		Band band = null;
		jsonObject = new JSONObject();
		errors = new JSONArray();
		fileUpload = new FileUploader(req);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out = resp.getWriter();
		String servletPath = req.getServletPath();
		

		switch (servletPath) {
		case "/AddBand":
			String imageName = fileUpload.uploadFile();
			// Eðer resmi null ise demekki hata vardýr. Hatayý basýp return
			// diyerek kodun devam etmesini önlüyoruz. Çünkü devam etmesine
			// gerek yok.
			if (imageName == null) {
				errors.put(fileUpload.getErrors());
				outPrintErrors();
				return;
			}
			band = createEntity(req);
			band.getPicture().setLink(imageName);
			saveEntity(band);
			break;

		case "/UpdateBand":
			band = createEntity(req);
			// Requestte dosya varsa eskisini sil yenisini ekle.
			if (fileUpload.isRequestHaveFile()) {
				// Eski Dosya silindi mi?
				if (fileUpload.deleteFile(band.getPicture().getLink())) {
					String imgName = fileUpload.uploadFile();
					band.getPicture().setLink(imgName);
				} else {
					errors.put("Eski Resim Silinemedi");
					outPrintErrors();
					return;
				}
			}
			updateEntity(band);
			break;

		case "/DeleteBand":
			band = createEntity(req);
			fileUpload.deleteFile(band.getPicture().getLink());
			deleteEntity(band);
			break;

		default:
			break;
		}
	}

	private void saveEntity(Band band) {
		try {
			if (bandService.save(band)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.ADD_BAND_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.ADD_BAND_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			if (e instanceof ConstraintViolationException) {
				errors.put("Böyle bir grup zaten var");
			} else
				errors.put(e.toString());
			outPrintErrors();
		} finally {
			out.close();
		}
	}

	private void updateEntity(Band band) {
		try {
			if (bandService.update(band)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.UPDATE_BAND_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.UPDATE_BAND_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			errors.put(e.toString());
			outPrintErrors();
		} finally {
			out.close();
		}
	}

	private void deleteEntity(Band band) {
		try {
			if (bandService.delete(band)) {
				jsonObject.put("result", true);
				jsonObject.put("resultMsg", Constants.DELETE_BAND_SUCCESS);
			} else {
				jsonObject.put("result", false);
				jsonObject.put("resultMsg", Constants.DELETE_BAND_FAIL);
			}
			out.print(jsonObject);
		} catch (HibernateException e) {
			errors.put(e.toString());
			outPrintErrors();
		} finally {
			out.close();
		}
	}

	private void outPrintErrors() {
		jsonObject.put("errors", this.errors);
		out.print(jsonObject);
		out.close();
	}

	private Band createEntity(HttpServletRequest req) {
		String bandId = req.getParameter("bandId");
		String bandName = req.getParameter("bandName");
		String[] members = req.getParameterValues("memberList");
		String bandInfo = req.getParameter("info");
		String imgName = req.getParameter("imgName");
		String imgId = req.getParameter("imgId");
		boolean isStandartBand = req.getParameter("standartBand") != null;
		Band band = new Band();
		band.setId(("".equals(bandId) || null == bandId) ? null : Long.parseLong(bandId));
		band.setBandName(bandName);
		band.setBandMembers((members == null || members[0] == null) ? null : Arrays.asList(members));
		band.setBandInfo(bandInfo);
		band.setStandartBand(isStandartBand);
		band.getPicture().setId(("".equals(imgId) || null == imgId) ? null : Long.parseLong(imgId));
		band.getPicture().setLink(imgName);
		return band;
	}

}
