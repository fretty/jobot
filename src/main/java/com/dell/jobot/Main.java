package com.dell.jobot;

import lombok.val;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

	public static final int QUEUE_CAPACITY = 1_000_000;

	public static void main(final String... args) {
		if(0 == args.length) {
			printUsage();
		} else {
			val parallelism = Runtime.getRuntime().availableProcessors();
			val queue = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);
			val threadFactory = new DaemonThreadFactory();
			val rejectionHandler = new IgnoringRejectionExecutionHandler();
			val executor = new ThreadPoolExecutor(
				parallelism, parallelism, 0, SECONDS, queue, threadFactory, rejectionHandler
			);
			val handler = new HttpUrlStreamHandler(executor);
			try {
				handler.handle(null, Arrays.stream(args));
				try {
					do {
						executor.awaitTermination(1, SECONDS);
					} while(executor.getActiveCount() != 0 && !queue.isEmpty());
					System.out.println("No more work to do");
				} catch(final InterruptedException ok) {
				}
			} catch(final Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	static void printUsage() {
		System.out.println("Useless internet crawler command line options: url1 [url2 [url3 ...]]");
	}
}
