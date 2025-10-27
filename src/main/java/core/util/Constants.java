package core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {
	public static final Gson GSON = new GsonBuilder().create();
	public static final String JSON_MIME_TYPE = "application/json";
	public static final String PREFIX_WEB_INF = "/WEB-INF";
}
