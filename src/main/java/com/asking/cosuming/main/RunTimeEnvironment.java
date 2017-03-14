package com.asking.cosuming.main;

import com.asking.cosuming.register.Register;
import com.asking.cusuming.frequency.Frequency;

public class RunTimeEnvironment {

	public void runTime(Register register) {
		Frequency frequency = register.frequency();
		frequency.setRegister(register);
		frequency.schedule();
	}
}
