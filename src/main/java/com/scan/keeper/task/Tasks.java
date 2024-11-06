package com.scan.keeper.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Think
 */
@Component
public class Tasks {

    public static Logger logger = LoggerFactory.getLogger(Tasks.class);


    //
    @Async
    @Scheduled(cron = "0/20 * * * * ?")
    public void monitorTransaction() {

    }

}
