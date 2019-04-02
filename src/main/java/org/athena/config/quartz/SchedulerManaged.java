package org.athena.config.quartz;

import io.dropwizard.lifecycle.Managed;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class SchedulerManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(SchedulerManaged.class);

    private Scheduler scheduler;

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
            JobDetail jobDetail = JobBuilder.newJob(task).build();
            Scheduled scheduled = task.getAnnotation(Scheduled.class);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduled.cron())).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }

        this.scheduler.start();

        logger.info("Scheduler - Start...");
    }

    @Override
    public void stop() throws Exception {

        logger.info("Scheduler Shutdown initiated...");

        this.scheduler.shutdown();

        logger.info("Scheduler Shutdown completed");

    }

}
