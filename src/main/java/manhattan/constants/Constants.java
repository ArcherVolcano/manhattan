package manhattan.constants;

import java.io.File;
import java.text.SimpleDateFormat;

public final class Constants {
	
	//FOR ENTITY
	public final static String ADD_BAND_SUCCESS = "Grup Eklendi!";
	public final static String ADD_BAND_FAIL = "HATA: Grup Eklenmedi!";
	public final static String UPDATE_BAND_SUCCESS = "Grup Düzenlendi!";
	public final static String UPDATE_BAND_FAIL = "HATA: Grup Düzenlenemedi";
	public final static String DELETE_BAND_SUCCESS = "Grup Silindi";
	public final static String DELETE_BAND_FAIL = "HATA: Grup Silinemedi!";
	
	public final static String ADD_ADMIN_SUCCESS = "Yönetici Eklendi!";
	public final static String ADD_ADMIN_FAIL = "HATA: Yönetici Eklenmedi!";
	public final static String UPDATE_ADMIN_SUCCESS = "Yönetici Düzenlendi!";
	public final static String UPDATE_ADMIN_FAIL = "HATA: Yönetici Düzenlenemedi";
	public final static String DELETE_ADMIN_SUCCESS = "Yönetici Silindi";
	public final static String DELETE_ADMIN_FAIL = "HATA: Yönetici Silinemedi!";
	
	public final static String ADD_EVENT_SUCCESS = "Etkinlik Eklendi!";
	public final static String ADD_EVENT_FAIL = "HATA: Etkinlik Eklenmedi!";
	public final static String UPDATE_EVENT_SUCCESS = "Etkinlik Düzenlendi!";
	public final static String UPDATE_EVENT_FAIL = "HATA: Etkinlik Düzenlenemedi";
	public final static String DELETE_EVENT_SUCCESS = "Etkinlik Silindi";
	public final static String DELETE_EVENT_FAIL = "HATA: Etkinlik Silinemedi!";
	
	public final static String ADD_PICTURE_SUCCESS = "Resim Eklendi!";
	public final static String ADD_PICTURE_FAIL = "HATA: Resim Eklenmedi!";
	public final static String DELETE_PICTURE_SUCCESS = "Resim Silindi";
	public final static String DELETE_PICTURE_FAIL = "HATA: Resim Silinemedi!";
	public final static String ADD_VIDEO_SUCCESS = "Video Eklendi!";
	public final static String ADD_VIDEO_FAIL = "HATA: Video Eklenmedi!";
	
	public final static String UPDATE_VIDEO_SUCCESS = "Video Düzenlendi!";
	public final static String UPDATE_VIDEO_FAIL = "HATA: Video Düzenlenemedi";
	public final static String DELETE_VIDEO_SUCCESS = "Video Silindi";
	public final static String DELETE_VIDEO_FAIL = "HATA: Video Silinemedi!";
	
	public final static String ADD_CONTACT_SUCCESS = "Ýletiþim Bilgileri Eklendi!";
	public final static String ADD_CONTACT_FAIL = "HATA: Ýletiþim Bilgileri Eklenmedi!";
	public final static String UPDATE_CONTACT_SUCCESS = "Ýletiþim Bilgileri Düzenlendi!";
	public final static String UPDATE_CONTACT_FAIL = "HATA: Ýletiþim Bilgileri Düzenlenemedi";
	
	public final static String ADD_ABOUT_SUCCESS = "Hakkýmýzda Bilgileri Eklendi!";
	public final static String ADD_ABOUT_FAIL = "HATA: Hakkýmýzda Bilgileri Eklenmedi!";
	public final static String UPDATE_ABOUT_SUCCESS = "Hakkýmýzda Bilgileri Düzenlendi!";
	public final static String UPDATE_ABOUT_FAIL = "HATA: Hakkýmýzda Bilgileri Düzenlenemedi";
	
	//TIME FORMAT
	public final static String TIME_FORMAT = "dd-MM-yyyy HH:mm";
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(TIME_FORMAT);
	
	//FOR FILE UPLOADS
	public static final int MEMORY_THRESHOLD 	= 1024 * 1024 * 3; 	// 3MB
	public static final int MAX_FILE_SIZE 		= 1024 * 1024 * 4; // 4MB
	public static final int MAX_REQUEST_SIZE	= 1024 * 1024 * 5; // 5MB
	public static final String UPLOAD_SUCCESS = "Dosya Yüklendi";
	public static final String UPLOAD_FAIL = "Dosya Yüklenemedi";
	public static final String UPLOAD_DIRECTORY = File.separator + "upload";
	
	//UPLOAD IMAGE ERRORS
	public final static String ERROR_MAX_FILE_SIZE = "Dosya boyutu en fazla " + MAX_FILE_SIZE/(1024*1024) + "MB olmalýdýr.";
	public final static String ERROR_ENCTYPE_MULTI_PART = "enctype = multipart/form-data olmalýdýr.";
	public final static String ERROR_FILE_MIME_TYPE = "Resim dosyasý \"Jpeg\" formatýnda olmalýdýr. Seçtiðiniz dosya \"Jpeg\" deðil";
	public static final String ERROR_NO_FILE = "Lütfen Resim Seçin!";
	
	private Constants() {}
	
}
