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
@WebServlet(value="/QuartzInit")
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


			JobDetail job_email_monthly = JobBuilder.newJob(EmailMonthlyReportJob.class)
					.withIdentity("AppCrawlerEmailJobMonthly", "InfraStatus_Group").build();



			Trigger trigger_email_monthly = TriggerBuilder
					.newTrigger()
					.withIdentity("AppCrawlerEmailJobTRigger", "InfraStatus_Group")

					//.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 12, 0)
							.withSchedule(


							CronScheduleBuilder.dailyAtHourAndMinute(10,10)
					).build();

			// schedule it
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			//scheduler.shutdown();
			scheduler.start();


			if(scheduler.checkExists(job_email_monthly.getKey())){
				//System.out.println("deleting old email job netry");
				scheduler.deleteJob(job_email_monthly.getKey());
				//scheduler.rescheduleJob(trigger.getKey(), trigger);
			}



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
