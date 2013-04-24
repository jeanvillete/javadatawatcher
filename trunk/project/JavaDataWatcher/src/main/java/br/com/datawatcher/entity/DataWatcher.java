/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import br.com.datawatcher.common.DataWatcherConstants;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.service.TimeWatcher;

/**
 * @author Jean Villete
 *
 */
public class DataWatcher implements Job {

	private static Logger 			log = Logger.getLogger(DataWatcher.class);
	
	private List<DataMapping> 		mappings = new ArrayList<DataMapping>();

	// controller to preview overload process 
	private static boolean executing = false;
	
	public DataWatcher() { }

	public DataWatcher addMapping(DataMapping dataMapping) {
		if (dataMapping != null) {
			this.mappings.add(dataMapping);
			return this;
		} else throw new IllegalArgumentException("parameter can not be null");
	}
	
	public void start() throws DataWatcherException {
		log.info("starting DataWatcher scheduling");
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
		
			for (DataMapping dataMapping : this.mappings) {
				log.info("analyzing and scheduling data mapping: " + dataMapping.getIdentifier());
				
				JobDataMap jobDataMap = new JobDataMap();
				jobDataMap.put(dataMapping.getIdentifier(), dataMapping);
				
				CronTriggerImpl cron = new CronTriggerImpl();
				cron.setName(dataMapping.getIdentifier());
				cron.setKey(new TriggerKey(dataMapping.getIdentifier(), DataWatcherConstants.GROUP_TRIGGER));
				cron.setCronExpression(dataMapping.getCheckChange().getCronExpression());
				cron.setJobDataMap(jobDataMap);

				JobDetailImpl job = new JobDetailImpl();
				job.setName(dataMapping.getIdentifier());
				job.setJobClass(this.getClass());
				
				scheduler.scheduleJob(job, cron);
				
				TimeWatcher timeWatcher = new TimeWatcher().start();
				dataMapping.startup();
				log.info("It was spent " + timeWatcher.stop().getTime() + " miliseconds to STARTUP the data mapping: "
						+ dataMapping.getIdentifier());
			}
			
			scheduler.start();
		} catch (Exception e) {
			throw new DataWatcherException(e);
		}
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (executing) return;
		executing = true;
		try {
			String triggerKeyName = context.getTrigger().getKey().getName();
			DataMapping dataMapping = (DataMapping) context.getTrigger().getJobDataMap().get(triggerKeyName);
			
			log.info("starting execute CHECK CHANGES to data mapping: " + dataMapping.getIdentifier());
			TimeWatcher timeWatcher = new TimeWatcher().start();
			dataMapping.checkChange();
			log.info("It was spent " + timeWatcher.stop().getTime() + " miliseconds to CHECK CHANGES to the data mapping: "
					+ dataMapping.getIdentifier());
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
		} finally {
			executing = false;
		}
	}
}
