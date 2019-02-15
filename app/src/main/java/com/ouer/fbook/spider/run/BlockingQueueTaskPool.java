package com.ouer.fbook.spider.run;

import com.ouer.fbook.util.LogUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据队列：BlockingQueue实现
 * </p>
 * 
 * @author dijun
 */
public class BlockingQueueTaskPool<T> extends TaskPool<T> {

	private BlockingQueue<T> queue;
	private int poolSize;

	public BlockingQueueTaskPool() {
		this(DEFAULT_POOL_SIZE);
	}

	public BlockingQueueTaskPool(int poolSize) {
		this.poolSize = poolSize;
		queue = new ArrayBlockingQueue<T>(poolSize);
	}

	public int GetPoolSize() {
		return this.poolSize;
	}

	@Override
	public void push(T t) {
		try {
			queue.put(t);
		} catch (InterruptedException e) {
			LogUtils.e("[taskqueue push error]" + e.getMessage(), e);
		}
	}

	@Override
	public boolean remove(T t) {
		return queue.remove(t);
	}

	@Override
	public T get() {
		return queue.peek();
	}

	@Override
	public T pop() {
		try {
			return queue.take();
		} catch (Exception e) {
			LogUtils.e("[taskqueue pop error]" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public T pop(long timeout) {
		try {
			return queue.poll(timeout, TimeUnit.MILLISECONDS);
		}catch(Exception e){
			LogUtils.e("[taskqueue pop error {}] timeout",e.getMessage());
		}
		return null;
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void clear(){
		queue.clear();
	}

}
