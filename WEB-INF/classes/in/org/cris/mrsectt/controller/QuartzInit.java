package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.util.EmailJob;
import in.org.cris.mrsectt.util.EmailMonthlyReportJob;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class QuartzInit
 */
@WebServlet(value="/QuartzInit",loadOnStartup=1)
public class QuartzInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int count=0;
       
    /**
	 * @see HttpServlet#HttpServlet()
	 */
	
	public QuartzInit() {
	    super();
	   if (System.getProperty("os.name").toString().startsWith(
				"Windows")) {
	    // TODO Auto-generated constructor stub
	    try {
	        // Grab the Scheduler instance from the Factory 
	    	// Quartz 1.6.3
			// JobDetail job = new JobDetail();
			// job.setName("dummyJobName");
			// job.setJobClass(HelloJob.class);
	   //System.out.println("TRying to start Quartz");
	// if(count==0)
	    	//return;
			/*JobDetail job = JobBuilder.newJob(AppCheckerJob.class)
				.withIdentity("AppCrawlerJob", "InfraStatus_Group").build();*/
			
		/*	JobDetail job_email = JobBuilder.newJob(EmailJob.class)
					.withIdentity("AppCrawlerEmailJob", "InfraStatus_Group").build();
					*/
			
			JobDetail job_email_monthly = JobBuilder.newJob(EmailMonthlyReportJob.class)
					.withIdentity("AppCrawlerEmailJobMonthly", "InfraStatus_Group").build();
			
			
	                //Quartz 1.6.3
			// SimpleTrigger trigger = new SimpleTrigger();
			// trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
			// trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			// trigger.setRepeatInterval(30000);
	
			// Trigger the job to run on the next round minute
		/*Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("AppCrawlerJobTRigger", "InfraStatus_Group")
				.withSchedule(        
				//		SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24*15).repeatForever())
						
								CronScheduleBuilder.cronSchedule("0 /15 * * * ?")
						
						//CronScheduleBuilder.weeklyOnDayAndHourAndMinute(1, 12, 00)
				).build();*/
	
		/*	Trigger trigger_email = TriggerBuilder
					.newTrigger()
					.withIdentity("AppCrawlerEmailJobTRigger", "InfraStatus_Group")
					.withSchedule(        
					//		SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24*15).repeatForever())
							
							//		CronScheduleBuilder.cronSchedule("0 /30 * * * ?")
							
							CronScheduleBuilder.dailyAtHourAndMinute(19,45)
					).build();
			*/
			
			Trigger trigger_email_monthly = TriggerBuilder
					.newTrigger()
					.withIdentity("AppCrawlerEmailJobTRigger", "InfraStatus_Group")
					
					//.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 12, 0)
							.withSchedule(
						//	SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24*15).repeatForever())
							//WithCronSchedule()
								//	CronScheduleBuilder.cronSchedule("0 0 12 1 1/1 ? *")
							
							CronScheduleBuilder.dailyAtHourAndMinute(10,10)
					).build();
			
			// schedule it
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			//scheduler.shutdown();
			scheduler.start();
			
			//if (sched.checkExists(job.getKey())) {sched.rescheduleJob(trigger.getKey(), trigger); } else {sched.scheduleJob(job, trigger); } 
			
			/*if(scheduler.checkExists(job.getKey())){
				//System.out.println("deleting old job netry");
				scheduler.deleteJob(job.getKey());
				//scheduler.rescheduleJob(trigger.getKey(), trigger);
			}*/
			if(scheduler.checkExists(job_email_monthly.getKey())){
				//System.out.println("deleting old email job netry");
				scheduler.deleteJob(job_email_monthly.getKey());
				//scheduler.rescheduleJob(trigger.getKey(), trigger);
			}
				
			
			
		//	scheduler.scheduleJob(job, trigger);			
		//	scheduler.scheduleJob(job_email_monthly, trigger_email_monthly);
		//	scheduler.scheduleJob(job_email_monthly, trigger_email_monthly);
	        
		
	
	
	    } catch (Exception se) {
	        se.printStackTrace();
	    }
	    }
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
