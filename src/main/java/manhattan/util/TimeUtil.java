package manhattan.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public final class TimeUtil {

	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.forLanguageTag("tr"));
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private TimeUtil() {}
	
	public static Date htmlDateTimeLocalToDate(String stringDate) {
		//Kaç tane ":" var. 1 tane ise buna :00 ekle. Yani saniye yoktur saniyeyi ekliyoruz hata almamak için.
		if (StringUtils.countMatches(stringDate, ":") == 1) {
		    stringDate += ":00";
		}
		Timestamp timestamp = Timestamp.valueOf(stringDate.replace("T", " "));
		Date date = new Date(timestamp.getTime());
		return date;
	}
}
