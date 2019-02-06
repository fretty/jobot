package com.dell.jobot;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class IgnoringRejectionExecutionHandler
implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
	}
}
