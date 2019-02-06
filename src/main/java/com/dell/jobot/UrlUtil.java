package com.dell.jobot;

import lombok.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

public interface UrlUtil {

	static URL convertToUrlWithoutAnchorAndQuery(final @NonNull String raw) {
		String t = raw;
		if(t.contains("#")) {
			t = t.substring(0, t.indexOf("#"));
		}
		if(t.contains("?")) {
			t = t.substring(0, t.indexOf("?"));
		}
		try {
			return new URL(t);
		} catch(final MalformedURLException e) {
			System.err.println("Failed to convert \"" + raw + "\" to URL");
			return null;
		} catch(final Exception e) {
			throw new AssertionError("Unexpected failure while converting \"" + raw + "\" to URL", e);
		}
	}

	static boolean isHttp(final @NonNull URL url) {
		return url.getProtocol().startsWith("http");
	}
}
