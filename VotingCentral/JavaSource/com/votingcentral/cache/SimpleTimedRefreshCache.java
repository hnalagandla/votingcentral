/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cache;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SimpleTimedRefreshCache {

	private Object o = null;

	private long cacheRefreshIntervalMillis = 0;

	private TimerTask dTask = null;

	/**
	 * @param Object
	 *            in cache
	 * @param cacheRefreshIntervalMillis
	 * @param task
	 *            to execute on DB to refresh the object
	 */
	public SimpleTimedRefreshCache(Object o, long cacheRefreshIntervalMillis,
			TimerTask task) {
		super();
		this.o = o;
		this.cacheRefreshIntervalMillis = cacheRefreshIntervalMillis;
		dTask = task;
		task.run();
		timedRefresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	public Object get() {
		if (o == null) {
			dTask.run();
			timedRefresh();			
		}
		return o;
	}

	/*
	 * (non-Javadoc) DBDAO dao, long refreshIntervalMillis
	 * 
	 */
	public void timedRefresh() {
		Timer cacheRefresher = new Timer(); 		
		cacheRefresher.schedule(dTask, 0, cacheRefreshIntervalMillis);
	}

}