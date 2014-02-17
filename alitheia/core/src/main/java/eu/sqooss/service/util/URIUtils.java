package eu.sqooss.service.util;

import java.net.URI;

public class URIUtils {

	public static URI toURI(String url) {
		if(null == url || url.isEmpty() || url.length() == 0)
			return null;
		
		try {
			url = url.replace("\\", "/").replace(" ", "%20");
			return URI.create(url);
		} catch (IllegalArgumentException iae) {
			return null;
		}
	}
}
