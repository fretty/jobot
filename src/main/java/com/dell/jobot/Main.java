package com.dell.jobot;

import lombok.val;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.dell.jobot.HttpUrlProcessingTask.submitStream;

public class Main {

	public static void main(final String... args) {
		if(0 == args.length) {
			printUsage();
		} else {
			val parallelism = Runtime.getRuntime().availableProcessors();
			val executor = Executors.newFixedThreadPool(parallelism);
			submitStream(executor, Arrays.stream(args));
			try {
				executor.awaitTermination(/* "forever" */ Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch(final InterruptedException ok) {
			} finally {
				printResults();
			}
		}
	}

	static void printUsage() {
		System.out.println("Useless internet crawler command line options: url1 [url2 [url3 ...]]");
	}

	static void printResults() {
		System.out.println("Results:");
	}
}
