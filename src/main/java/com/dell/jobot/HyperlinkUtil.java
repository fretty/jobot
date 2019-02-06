package com.dell.jobot;

import lombok.NonNull;
import lombok.val;
import org.apache.commons.collections4.map.LRUMap;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public interface HyperlinkUtil {

	String GROUP_NAME_VALUE = "value";

	Pattern PATTERN_HREF_VALUE = Pattern.compile("href=\"(?<" + GROUP_NAME_VALUE + ">http://[\\S^\"]{8,256})\"");

	int LAST_UNIQUE_URLS_COUNT = 1_000_000;

	/**
	 A cache of last unique links which is used to prevent the crawler from infinite looping over a links cycle.
	 */
	Map<String, Object> LAST_UNIQUE_URLS = new LRUMap<>(LAST_UNIQUE_URLS_COUNT);

	static void extractLinks(final @NonNull String text, final @NonNull Collection<String> dstBuff) {
		val matcher = PATTERN_HREF_VALUE.matcher(text);
		while(matcher.find()) {
			val url = matcher.group(GROUP_NAME_VALUE);
			synchronized(LAST_UNIQUE_URLS) {
				LAST_UNIQUE_URLS.computeIfAbsent(
					url, u -> {
						dstBuff.add(u);
						return LAST_UNIQUE_URLS; // just any object which is the same for all
					}
				);
			}
		}
	}
}
