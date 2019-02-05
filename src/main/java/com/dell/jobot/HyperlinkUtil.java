package com.dell.jobot;

import lombok.val;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.dell.jobot.UrlUtil.HTTP;

public interface HyperlinkUtil {

	String GROUP_NAME_VALUE = "value";
	Pattern PATTERN_HREF_VALUE = Pattern.compile(
		"href=\"(?<" + GROUP_NAME_VALUE + ">" + HTTP + "[\\S^\"]{8,256})\""
	);
	int LAST_UNIQUE_URLS_COUNT = 1_000;
	Map<String, String> LAST_UNIQUE_URLS = new LRUMap<>(LAST_UNIQUE_URLS_COUNT);

	static List<String> getHrefAttrValues(final String text) {
		val matcher = PATTERN_HREF_VALUE.matcher(text);
		val urls = new ArrayList<String>();
		while(matcher.find()) {
			val url = matcher.group(GROUP_NAME_VALUE);
			LAST_UNIQUE_URLS.computeIfAbsent(
				url, u -> {
					urls.add(u);
					return u;
				}
			);
		}
		return urls;
	}
}
