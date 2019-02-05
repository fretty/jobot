package com.dell.jobot;

import lombok.Value;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static com.dell.jobot.HyperlinkUtil.getHrefAttrValues;

@Value
public class HttpUrlProcessingTask
implements Runnable {

	private final ExecutorService executor;
	private final URL url;

	public HttpUrlProcessingTask(final ExecutorService executor, final URL url) {
		this.executor = executor;
		this.url = url;
	}

	@Override
	public void run() {
		try(val contentReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String line;
			while(null != (line = contentReader.readLine())) {
				submitStream(executor, getHrefAttrValues(line).stream());
			}
			System.out.println("Processing done: " + url);
		} catch(final IOException e) {
			System.err.println("I/O failure while reading the content from the url: \"" + url + "\"");
		} catch(final Throwable t) {
			t.printStackTrace(System.err);
		}
	}

	static void submitStream(final ExecutorService executor, final Stream<String> inStream) {
		inStream
			.map(UrlUtil::convertToUrl)
			.filter(Objects::nonNull)
			.filter(UrlUtil::isHttp)
			.map(url -> new HttpUrlProcessingTask(executor, url))
			.forEach(executor::submit);
	}
}
