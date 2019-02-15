package com.ouer.fbook.spider.run;


/**
 * 数据队列基类
 * 
 * @author dijun
 */
public abstract class TaskPool<T> {
	protected static final int DEFAULT_POOL_SIZE = 80;

	/**
	 * 向队列中推送单个数据
	 * 
	 * @param t
	 */
	public abstract void push(T t);

	/**
	 * 从队列中取出一个数据,但不从队列中移除该数据
	 */
	public abstract boolean remove(T t);

	/**
	 * 从队列中取出一个数据,但不从队列中移除该数据
	 */
	public abstract T get();

	/**
	 * 从队列中取出一个数据
	 */
	public abstract T pop();

	/**
	 * 从队列中取出一个数据
	 */
	public abstract T pop(long timeout);

	public abstract int size();

	public abstract void clear();
}
