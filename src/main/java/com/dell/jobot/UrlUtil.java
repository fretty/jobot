package com.dell.jobot;

import java.net.MalformedURLException;
import java.net.URL;

public interface UrlUtil {

	String HTTP = "http";

	static URL convertToUrl(final String raw) {
		try {
			return new URL(raw);
		} catch(final MalformedURLException e) {
			System.err.println("Failed to convert \"" + raw + "\" to URL");
			return null;
		} catch(final Exception e) {
			throw new AssertionError("Unexpected failure while converting \"" + raw + "\" to URL", e);
		}
	}

	static boolean isHttp(final URL url) {
		return url.getProtocol().startsWith(HTTP);
	}
}
