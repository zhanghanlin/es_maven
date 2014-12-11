package com.search.job;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.search.util.Constants;
import com.search.util.Jerseys;
import com.sun.jersey.api.client.WebResource;

/**
 * 刷新对象父类
 * 
 * @author zhanghanlin
 *
 */
public abstract class ExtendData<T> {

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Elsticsearch Client
	 */
	final WebResource client = Jerseys.createClient(Constants.BASE_URL);

	/**
	 * 线程锁
	 */
	final Lock lock = new ReentrantLock();

	/**
	 * 线程池默认同CPU核数加一
	 */
	final ExecutorService exec = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors() + 1);

	/**
	 * 写入数据方法
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public abstract void put(T t) throws Exception;

	/**
	 * 获取数据集合
	 * 
	 * @param param
	 * @return
	 */
	public abstract List<T> getList(List<String> param);

	/**
	 * 多线程刷新
	 * 
	 * @param param
	 */
	public void run(List<String> param) {
		logger.info("[Job Flush] begin fresh run..............");
		// 子对象集合
		List<T> list = getList(param);
		logger.info("[Job Flush] list size = " + list.size());
		// 创建计数器
		final CountDownLatch allLatch = new CountDownLatch(list.size());
		try {
			// 当前对象获得锁
			if (lock.tryLock()) {
				logger.info("[Job Flush] get lock run for");
				for (final T t : list) {
					exec.submit(new Runnable() {
						@Override
						public void run() {
							try {
								// 具体执行子对象实现方法
								put(t);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								logger.error("[Job Flush] run() error :" + e.getMessage());
							} finally {
								// 每一个线程执行完毕,调用countDown()方法,直到全部的线程(count个)执行完毕,闭锁打开
								allLatch.countDown();
							}
						}
					});
				}
				long start = System.nanoTime();
				// 当前线程在这里等待所有的线程执行完毕,最多等待5分钟
				allLatch.await(5L, TimeUnit.MINUTES);
				long end = System.nanoTime();
				logger.info("[Job Flush] time = " + (end - start)
						/ (1000 * 1000) + "milliSecond");
			} else {
				logger.info("[Job Flush] not get lock");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("[Job Flush] error:" + e.getMessage());
		} finally {
			// 释放锁
			lock.unlock();
			logger.info("[Job Flush] un lock");
		}
		logger.info("[Job Flush] end fresh run..............");
	}
}
