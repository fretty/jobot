package com.dell.jobot;

import lombok.val;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory
implements ThreadFactory {

	@Override
	public Thread newThread(final Runnable r) {
		val thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	}
}
