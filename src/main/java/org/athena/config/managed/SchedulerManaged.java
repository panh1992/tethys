package org.athena.config.managed;

import io.dropwizard.lifecycle.Managed;
import org.athena.config.quartz.Scheduled;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class SchedulerManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(SchedulerManaged.class);

    private Scheduler scheduler;

    /**
     * 构造任务管理器
     */
    public SchedulerManaged() throws SchedulerException, IOException {
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResourceAsStream("quartz.properties")));
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory(properties);
        this.scheduler = stdSchedulerFactory.getScheduler();
    }

    @Override
    public void start() throws Exception {

        logger.info("Scheduler - Starting...");

        Reflections reflections = new Reflections("org.athena.tasks");

        Set<Class<? extends Job>> jobTasks = reflections.getSubTypesOf(Job.class);

        for (Class<? extends Job> task : jobTasks) {
            Scheduled scheduled = task.getAnnotation(Scheduled.class);
            JobDetail jobDetail = JobBuilder.newJob(task).withIdentity(scheduled.jobName()).build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(scheduled.jobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduled.cron())).startNow().build();
            if (!scheduler.checkExists(cronTrigger.getKey())) {
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        }

        this.scheduler.start();

        logger.info("Scheduler - Start completed");
    }

    @Override
    public void stop() throws Exception {

        logger.info("Scheduler - Shutdown initiated...");

        this.scheduler.shutdown();

        logger.info("Scheduler - Shutdown completed");

    }

}
