package com.dell.jobot;

public interface CacheUniquenessFilter<T> {

	boolean checkAndPutIfMissing(T v);
}
