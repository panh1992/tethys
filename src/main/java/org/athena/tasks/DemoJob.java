package org.athena.tasks;

import org.athena.config.quartz.Scheduled;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Scheduled(cron = "9/10 * * * * ? *")
public class DemoJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("quartz job 测试， 调度时间：{}", Instant.now());
    }

}
