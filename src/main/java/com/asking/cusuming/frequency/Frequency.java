package com.asking.cusuming.frequency;

import com.asking.cosuming.register.Register;

public interface Frequency {

	public void schedule();

	public void setRegister(Register requestTask);

	public void close();
	
	public void period(long period);
	
	public void delay(long delay);
}
