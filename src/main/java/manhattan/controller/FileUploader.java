package manhattan.controller;

import static manhattan.constants.Constants.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import eu.medsea.mimeutil.MimeException;
import eu.medsea.mimeutil.MimeUtil;


public class FileUploader {
	
	// Yüklenecek dizin
	
	
	private final HttpServletRequest req;
	private final String uploadPath;
	private Set<String> errors;
	private Part filePart;
	private String fileName;
	
	
	public FileUploader(HttpServletRequest req) {
		this.req = req;
		String realpath =  req.getServletContext().getRealPath("/");
		File webapps = new File(realpath);
		uploadPath =  webapps.getParent() + UPLOAD_DIRECTORY;
	}
	public Set<String> getErrors() {
		return errors;
	}

	
	//Geriye null veya srting path dönecek. Eðer update baþarýlýysa path(fileName) dönecek.
	String uploadFile() throws IOException, ServletException {
		errors = new HashSet<>();
		if (!validateReqAndGetFile()) {
			return null;
		} 
		errors = new HashSet<>();
		if (!isMimeTypeImage(filePart.getInputStream())) {
			errors.add(ERROR_FILE_MIME_TYPE);
		}
		fileName = fileName.substring(0, fileName.indexOf(".")) + System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		//write methoduna parametre olarak dosyanýn yazýlacaðý dizini belirtip yazdýrýyoruz.
		filePart.write(uploadPath + File.separator + fileName);
		return fileName;
	}
	
	boolean deleteFile(String fileName) {
		File file = new File(uploadPath + File.separator + fileName);
		boolean result = false;
		try {
			result = file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result;
	}
	
	private boolean validateReqAndGetFile() {
		
		try {
			filePart = req.getPart("img");
			fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		} catch (IOException | ServletException | IllegalStateException e) {
			if (e instanceof IllegalStateException) {
				errors.add(ERROR_MAX_FILE_SIZE);
				errors.add(e.toString());
			}
			else {
				errors.add(e.toString());
				e.printStackTrace();
			}
			return false;
		} 
		//Dosya adý yoksa zaten dosyada yoktur hatayý bas.
		if ("".equals(fileName)) {
			errors.add(ERROR_NO_FILE);
			return false;
		}
		return true;
	}

	//Dosya formatýný doðruluyoruz..
	private boolean isMimeTypeImage(InputStream input) throws MimeException, IOException {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		Collection<?> mimeTypes = MimeUtil.getMimeTypes(IOUtils.toByteArray(input));
		if (mimeTypes.contains("image/jpeg")) {
			return true;
		}
		return false;
	}

	
	 boolean isRequestHaveFile() throws IOException, ServletException {
		String fileName = "";
		Part filePart = req.getPart("img");
		fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		return !"".equals(fileName);
	}
}
