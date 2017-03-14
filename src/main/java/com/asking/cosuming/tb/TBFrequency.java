package com.asking.cosuming.tb;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import com.asking.cosuming.download.Download;
import com.asking.cosuming.main.TaskPoolExecutors;
import com.asking.cosuming.register.Register;
import com.asking.cosuming.routing.LastModified;
import com.asking.cosuming.routing.Routing;
import com.asking.cosuming.thread.RequestTask;
import com.asking.cusuming.frequency.Frequency;
import com.asking.getting.request.GenerateRequestChain;
import com.asking.getting.request.Request;
import com.asking.template.Template;

public class TBFrequency extends LastModified implements Frequency {

	private Timer timer = new Timer();

	private Register register;

	private TimerTask scheduleTask;
	
	private long delay;
	
	private long period;
	
	public TBFrequency(){
		
	}

	@Override
	public void schedule() {
		timer.scheduleAtFixedRate(scheduleTask, delay, period);
	}

	@Override
	public void setRegister(Register register) {
		
		this.register = register;
		final Register transferRegister = register;

		final Routing routing = register.routing();

		final Download download = register.download();
		final ThreadLocal<Download> locals = new ThreadLocal<Download>() {

			@Override
			protected Download initialValue() {
				return register.download();
			}

		};

		final TreeSet<GenerateRequestChain> requestChains = register.requestChains();

		final String charset = register.charset();
		
		final Map<String, Template> templates = register.templates();

		scheduleTask = new TimerTask() {

			@Override
			public void run() {
				Request request = null;
				do {
					request = routing.routing();
					request.template(templates.get(request.keyword()));
					request.option();
					RequestTask requestTask = RequestTask.build();
					requestTask.request(request);
					requestTask.locals(locals);
					locals.get();
					requestTask.requestChains(requestChains);
					requestTask.charset(charset);

					TaskPoolExecutors.execute(requestTask);
				} while (request != null);
			}
		};

	}

	@Override
	public void close() {

	}

	@Override
	public void modified() {
		super.modified = System.currentTimeMillis();
	}

	public void delay(long delay){
		this.delay = delay;
	}
	public long delay(){
		return this.delay;
	}
	
	public void period(long period){
		this.period = period;
	}
	public long period(){
		return this.period;
	}
}
