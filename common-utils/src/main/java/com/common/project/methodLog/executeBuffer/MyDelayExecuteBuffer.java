package com.common.project.methodLog.executeBuffer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * 涓轰簡淇 婧愬寘涓殑鍙傛暟浼犻�掗敊璇�
 * @author <a href="mailto:zhuqingwei@zhexinit.com" >鏈辨櫞钄�</a>
 * @version 1.0.0
 */
@SuppressWarnings("hiding")
public class MyDelayExecuteBuffer<T> extends DelayExecuteBuffer<T>{
	public static final Logger logger = LoggerFactory.getLogger(MyDelayExecuteBuffer.class);
	private String name;

	private IBatchExecutor<T> batchExecutor;

	private AtomicReference<RecordPool<T>> recordPool = new AtomicReference<RecordPool<T>>();

	private ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor();

	private long checkInterval = 1000;

	private int poolSize = 1024;
	private int batchSize = 10;
	private int threads = 1;


	public synchronized boolean add(T record) {
		if (!full()) {
			boolean ret = getRecordPool().add(record);

			if (logger.isTraceEnabled()) {
				logger.trace(
						"add record to pool [{}]. poolSize=[{}], remainCapacity=[{}], record=[{}], ret=[{}]",
						new Object[] { name, poolSize,
								getRecordPool().remainCapacity(), record, ret });
			}
			return ret;
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("record pool [{}] is full.", name);
			}
			return false;
		}
	}

	public void flush() {
		List<T> records = getRecordPool().getWholeRecords();
		execute(records);
	}

	public void start() {

		this.recordPool.set(new RecordPool<T>(poolSize));
		this.recordPool.get().setBatchSize(batchSize);

		for (int i = 0; i < threads; i++) {

			scheduler.scheduleWithFixedDelay(new Runnable() {

				@Override
				public void run() {

					if (logger.isTraceEnabled()) {
						logger.trace(
								"schedule: pop from record pool [{}]. poolSize=[{}], remainCapacity=[{}]",
								new Object[] { name, poolSize,
										getRecordPool().remainCapacity() });
					}

					final List<T> records = getRecordPool().asList();

					if (!records.isEmpty()) {
						if (logger.isTraceEnabled()) {
							logger.trace(
									"schedule: pop from record pool [{}]. poolSize=[{}], remainCapacity=[{}], size=[{}]",
									new Object[] { name, poolSize,
											getRecordPool().remainCapacity(),
											records.size() });
						}
						execute(records);
					}

				}
			}, checkInterval, checkInterval, TimeUnit.MILLISECONDS);
		}
	}

	private void execute(List<T> records) {
		if (logger.isDebugEnabled()) {
			logger.debug("Execute records. size=[{}]", records.size());
		}

		try {
			batchExecutor.execute(records);
		} catch (Exception e) {
			logger.error("", e);
			
		}
	}

	public boolean full() {
		return getRecordPool().remainCapacity() == 0;
	}

	public int size() {
		return getRecordPool().size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public RecordPool<T> getRecordPool() {
		return recordPool.get();
	}

	public void setThreads(int threads) {
		this.threads = threads;
		this.scheduler = Executors.newScheduledThreadPool(threads);
	}

	public void setCheckInterval(long checkInterval) {
		this.checkInterval = checkInterval;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}


	public void setBatchExecutor(IBatchExecutor<T> batchExecutor) {
		this.batchExecutor = batchExecutor;
	}
}
