<%@page import="org.quartz.Trigger.TriggerState"%>
<%@page import="org.quartz.Trigger"%>
<%@page import="org.quartz.impl.matchers.GroupMatcher"%>
<%@page import="org.quartz.JobKey"%>
<%@page import="org.quartz.impl.StdSchedulerFactory"%>
<%@page import="org.quartz.Scheduler"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:useBean id="schedulerFactory" class="org.quartz.impl.StdSchedulerFactory"  />



        <title>Application Status</title>
  
      
    </head>
    
    <body>
    

  
  <table  class="dataTable" >
 <thead>
 
 <tr><th>Job</th><th>Group</th><th>Last Run</th><th>Next Run</th><th>State</th></tr>
 </thead>
 <tbody>
<%
Scheduler scheduler = new StdSchedulerFactory().getScheduler();
 int i=0;
   for (String groupName : scheduler.getJobGroupNames()) {
 
     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
 
	  String jobName = jobKey.getName();
	  String jobGroup = jobKey.getGroup();
 
	  //get job's trigger
	  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
	  TriggerState triggerState = scheduler.getTriggerState(triggers.get(0).getKey());
 %>
	<tr <%= i++%2==1?"trodd":"treven"%> ><td><%= jobName  %></td><td><%= jobGroup  %></td><td><%=triggers.get(0).getPreviousFireTime() %></td><td><%=triggers.get(0).getNextFireTime() %></td><td><%=triggerState.toString() %> (<%=triggerState.name() %>) </td></tr>
 <% 
	  }
 
    }
 %>
 </tbody>
</table>
  


    </body>
</html>