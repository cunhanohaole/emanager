package br.com.tscpontual.scheduler;

import javax.annotation.Resource;

public class CheckInvalidEmailsJob {

    private @Resource
    CheckInvalidEmailsCron checkInvalidEmailsCron;

    public void execute() {
    	checkInvalidEmailsCron.run();
    }
}
