package br.com.tscpontual.scheduler;

import java.io.Serializable;

public interface CronScheduler extends Serializable {

	void run();
	
}
