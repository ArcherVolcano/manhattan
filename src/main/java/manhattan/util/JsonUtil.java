package manhattan.util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import manhattan.domain.GenericEntity;

public final class JsonUtil {
	
	private JsonUtil() {}
	
	public static JSONObject entityToJson(GenericEntity entity) {
		JSONObject jsonObject = null;
		try {
			entity = (GenericEntity) new JSONTokener(entity.toString()).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			if (entity instanceof JSONObject) {
				jsonObject = (JSONObject) entity;
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return jsonObject;
	}
	
	public static JSONArray entityToJsonArray(GenericEntity entity) {
		JSONArray jsonArray = null;
		try {
			entity = (GenericEntity) new JSONTokener(entity.toString()).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			if (entity instanceof JSONArray) {
				jsonArray = (JSONArray) entity;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}
