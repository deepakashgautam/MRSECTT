package in.org.cris.mrsectt.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;



import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.DOMConfigurator;*/
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;


public class Log4jInit extends HttpServlet implements Servlet {
	/**
	 * 
	 */
	static Logger log = LogManager.getLogger(Log4jInit.class);
	private static final long serialVersionUID = 1L;

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Log4jInit() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/* (non-Java-doc)
	 * @see javax.servlet.Servlet#init(ServletConfig arg0)
	 */
	public void init(ServletConfig config) throws ServletException {

		//rollLogFile(root);
		super.init(config);
		startLog4jCustom(config);
	}
	
	
	public void startLog4jCustom(ServletConfig config)
	{
		System.out.println("  Log4jInit: Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		
		ServletContext sc = config.getServletContext();
		System.out.println("  Log4jInit: Java Version             - " + System.getProperty("java.version"));
		System.out.println("  Log4jInit: Java Vendor              - " + System.getProperty("java.vendor"));
		System.out.println("  Log4jInit: Operating System name    - " + System.getProperty("os.name"));
		System.out.println("  Log4jInit: Operating System version - " + System.getProperty("os.version"));
		
		try{
		
		if (log4jLocation == null) {
			System.err.println("  Log4jInit: No log4j-properties-location init param, so initializing log4j with BasicConfigurator.");
			Configurator.initialize(new DefaultConfiguration());
		    Configurator.setRootLevel(Level.INFO);
			//BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + File.separator + log4jLocation+File.separator;
			if(System.getProperty("os.name").toString().startsWith("Windows"))
			{
				log4jProp += "log4j2.xml"; 
			} else
			{
				log4jProp += "log4j2_unix.xml";
			}
			System.out.println("  Log4jInit: Checking Log4j properties at " + log4jProp);
			File log4jPropfile = new File(log4jProp);
			if (log4jPropfile.exists()) {
				System.out.println("  Log4jInit: Initializing log4j.");
				// load custom XML configuration
				//DOMConfigurator.configure(log4jProp);
				File file = new File(log4jProp);
				LoggerContext.getContext().setConfigLocation(file.toURI());
				//LoggerContext loggerContext =  (LoggerContext) LogManager.getContext(false);
				//File file = new File(log4jProp);
				//loggerContext.setConfigLocation(file.toURI());

				
			} else {
				System.err.println("  Log4jInit: " + log4jProp + " file not found, so initializing with BasicConfigurator.");
				Configurator.initialize(new DefaultConfiguration());
			    Configurator.setRootLevel(Level.INFO);
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("  Log4jInit: Log4j initialization failed with Exception: --> " + e.getMessage());	
			e.printStackTrace();
			
		}
		System.out.println("  Log4jInit: Log4j initialized succesfully.");	
	}

	public void rollLogFile(Logger logger) { 
		System.out.println("Starting Rolling Old Log files before starting MRSECTT.");
		  while (logger != null && !((Enumeration) ((LoggerConfig) logger).getAppenders()).hasMoreElements()) { 
		    logger = (Logger)((LoggerConfig) logger).getParent(); 
		  } 
		 
		  if (logger == null) { 
		    return; 
		  } 
		 
		  for (Enumeration e2 = (Enumeration) ((LoggerConfig) logger).getAppenders(); e2.hasMoreElements();) { 
		    final Appender appender = (Appender)e2.nextElement(); 
		    if (appender instanceof RollingFileAppender) { 
		      final RollingFileAppender rfa = (RollingFileAppender)appender; 
		      final File logFile = new File(rfa.getFileName()); 
		      System.out.println("Rolling Old Log length -->."+logFile.length());
		      if (logFile.length() > 0) { 
		        
		    	  rfa.getManager().rollover();
		      } 
		    } 
		  } 
		} 
	/*	public void rollLogFile(Logger logger) { 
		System.out.println("Starting Rolling Old Log files before starting MRSECTT.");
		  while (logger != null && !logger.getAllAppenders().hasMoreElements()) { 
		    logger = (Logger)logger.getParent(); 
		  } 
		 
		  if (logger == null) { 
		    return; 
		  } 
		 
		  for (Enumeration e2 = logger.getAllAppenders(); e2.hasMoreElements();) { 
		    final Appender appender = (Appender)e2.nextElement(); 
		    if (appender instanceof RollingFileAppender) { 
		      final RollingFileAppender rfa = (RollingFileAppender)appender; 
		      final File logFile = new File(rfa.getFile()); 
		      System.out.println("Rolling Old Log length -->."+logFile.length());
		      if (logFile.length() > 0) { 
		        rfa.rollOver(); 
		      } 
		    } 
		  } 
		} */

}