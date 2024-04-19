<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.NetClientGet"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.RecieptDetails"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%
	
	String q = request.getParameter("q");
	
	//ArrayList<CommonBean> statusArrList = NetClientGet.main2();
	RecieptDetails obj = new NetClientGet().main2(q);
	%>
	<table border="1">
	<tr><td>Receipt Nature</td>
	<td>Receipt Number</td><td>Department</td><td>Letter Date</td>
	<td>ReceivedDate</td><td>Subject</td><td>Type</td>
	
	</tr>
	<tr>
	<td><%=obj.getReceiptnature() %></td>
	<td><%=obj.getReceiptNumber() %></td>
	<td><%=obj.getDepartment() %></td>
	<td><%=obj.getLetterDate() %></td>
	<td><%=obj.getReceivedDate() %></td>
	<td><%=obj.getSubject() %></td>
	
	<td><%=obj.getType() %></td>
	
	</tr>
	</table>
	
	<%
			// StringBuffer status = new StringBuffer(bean.getField1());
			out.println(" Receipt Nature "+obj.getReceiptnature());
			out.println(" Receipt Number "+obj.getReceiptNumber());
			out.println(" Department  "+obj.getDepartment());
			out.println("Date "+obj.getLetterDate());
			out.println(obj.getReceivedDate());
			out.println(obj.getSubject());
			out.println(obj.getType());
			
%>