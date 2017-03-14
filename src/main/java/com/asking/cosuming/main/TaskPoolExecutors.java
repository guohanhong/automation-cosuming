package com.asking.cosuming.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskPoolExecutors {
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public static void execute(Runnable task){
		executorService.execute(task);
	}
}
