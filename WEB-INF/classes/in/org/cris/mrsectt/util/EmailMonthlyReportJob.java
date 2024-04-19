package in.org.cris.mrsectt.util;

import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.MonthlyReportDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmailMonthlyReportJob  implements  Job{
	static Logger log = LogManager.getLogger(EmailJob.class);
//	String emailTo = "mrcell@rb.railnet.gov.in";
	String emailTo = "rounakgupta1992@gmail.com";
	public static final Map<Integer, String> statusSymbol;
    static
    {
    	statusSymbol = new HashMap<Integer, String>();
    	statusSymbol.put(-1, "&#916;");
    	statusSymbol.put(1, "&#10004;");
    	statusSymbol.put(0, "&#8709;");
    }
    public void execute(JobExecutionContext context)
			throws JobExecutionException {
				//log.warn("Trying to Execute  Job : "+QuartzInit.count++);
    	//System.out.println("In Job Execute");
				
		 String serverDate="";
		 serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
		
		 MonthlyReportDAO mr = new MonthlyReportDAO();
		// HttpSession session;
		 
		 String result="";
		 String strsql="";
		 DBConnection con = new DBConnection();
			try {

				con.openConnection();
				strsql = " SELECT FILEPATH FROM CONFIG_MAST ";
				//System.out.println(strSQL);
				//
				ResultSet rs = con.select(strsql);

				if (rs.next()) {
					result = rs.getString(1);
				}
				rs.close();

			} catch (Exception e) {
				con.rollback();
				// log.fatal(e,e);
			} finally {
				con.closeConnection();
			}
			
			
			//System.out.println("result+++++++++++++++++"+ result);
		//String serverpath = session.getAttribute("filepath") != null ? session.getAttribute("filepath").toString(): "";
		 
		 
		 String condMARKTOFinal ="and b.MARKINGTO in  ('690','691','436','208','1055','602','1047','379','380','1100','51','209','314','313','375','1077','1066','640','608','114','1','256','107','654','871','4','669','50','118','318','319','113','316','210','924','162','1163','163','317','2','115','3','1134','164','596','112','52','255','315','617','639','386','387','123','8','324','214','60','262','122','616','61','325','215','326','385','6','7','504','58','5','1169','1060','695','1151','922','1130','958','979','1079','389','9','59','297','1087','1075','1102','974','1069','299','384','605','975','969','1068','977','1089','1080','1088','1090','667','53','704','1059','983','502','637','907','624','266','216','10','330','499','74','55','607','870','268','592','591','589','496','488','213','587','222','167','321','495','373','174','203','322','382','383','634','585','666','1175','582','580','579','577','307','56','966','934','868','494','261','483','57','491','490','636','573','323','571','1170','568','567','566','565','564','563','1176','1070','559','707','1103','665','1154','331','169','265','678','557','507','1157','391','334','67','1065','633','335','950','657','220','68','267','393','65','66','212','54','919','259','392','260','884','552','170','13','933','172','632','11','865','394','205','219','399','217','21','395','336','396','1064','14','69','551','70','1177','506','173','550','20','631','340','1051','159','129','376','130','1081','400','547','1099','1002','1003','1027','1015','1038','980','1043','1028','946','76','993','1000','1006','1023','23','1044','1016','1004','1030','1010','1001','1011','984','991','987','1033','1025','962','1031','1042','952','1097','274','943','989','967','1020','920','1039','226','544','895','543','125','126','15','16','1172','971','709','175','712','252','713','714','397','718','720','176','398','357','479','19','619','615','630','520','517','223','75','625','688','24','343','25','344','337','133','338','228','179','276','1153','229','478','270','345','401','28','1178','651','968','284','518','953','80','862','650','185','233','84','85','285','527','526','882','140','481','406','1148','1085','957','306','287','31','515','480','142','237','283','404','184','916','405','145','30','235','351','186','147','238','355','965','289','86','356','232','277','182','1117','290','81','353','509','135','281','440','441','136','230','26','78','308','180','926','231','956','402','513','505','134','187','1084','475','408','291','409','1046','346','279','347','348','687','1156','519','374','349','642','410','1061','137','411','511','29','282','188','412','234','82','138','350','240','241','413','477','649','242','476','139','227','533','275','531','530','529','528','286','407','1112','732','903','887','1078','879','736','627','292','743','746','88','1101','358','89','34','414','243','191','359','192','149','37','91','196','248','95','959','96','423','33','197','45','198','945','748','246','206','362','944','471','754','674','1161','193','461','194','417','92','90','1092','40','1174','469','420','309','48','421','247','465','416','604','463','42','195','1159','418','873','361','941','415','43','419','36','311','955','760','458','761','762','524','312','152','1086','39','44','936','298','153','294','766','295','190','767','1132','35','960','918','433','97','431','424','768','1133','769','99','770','927','928','1107','100','303','948','304','771','774','981','1062','778','369','781','370','46','610','783','250','302','821','818','1082','976','829','158','365','453','832','366','837','1111','849','816','834','448','855','804','103','447','925','104','446','444','445','202','644','429','1149','826' )";
		 String condYearFinal = "and to_char(incomingdate,'yyyy') in   ( '2017','2016','2015','2014' )";
		 String year ="'2017','2016','2015','2014'";
		 String[] Refclass = {"A","B","C","E"};
		 
		 String filepath = mr.generateConsolidatedReportWithFormat("D://", Refclass, condMARKTOFinal, condYearFinal, year,"");
		 
		 //DBConnection con = new DBConnection();
		// con.openConnection();
	 		/*String dept =	" SELECT DISTINCT A.MARKINGTO,GETROLENAME(A.MARKINGTO) ROLENAME,(select emailid from mstrole where roleid=A.MARKINGTO)"+
	 					" FROM TRNMARKING A,TRNREFERENCE B"+
	 						" WHERE TO_CHAR(A.TARGETDATE+1,'DD/MM/YYYY')= TO_CHAR(sysdate,'DD/MM/YYYY')"+
							"  AND A.REFID=B.REFID AND B.TENUREID='12' and b.refclass in ('A','M') "+
							" AND A.MARKINGSEQUENCE=1 AND B.FILENO IS NULL AND A.MARKINGTO IS NOT NULL ORDER BY ROLENAME";
		ArrayList<CommonBean> deptNameList = (new CommonDAO()).getSQLResult(dept, 3);
		//System.out.println("dept:::" + dept);
		String olddept="";*/
		int mailcount=0;
		
			
			//CommonBean cb = (CommonBean) deptNameList.get(i);
			//emailTo="".equals(cb.getField3())?"mrcell@rb.railnet.gov.in":cb.getField3();
			//emailTo="".equals(cb.getField3())?"mrcell@rb.railnet.gov.in":"mrcell@rb.railnet.gov.in";
					//  if(!cb.getField1().equalsIgnoreCase(olddept)){
			
				//ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
				//dataArr =  (new ReminderAutoDAO()).getReminderReportDataEMAIL(cb.getField1());
				//for (int j = 0; j < dataArr.size(); j++) {
					//  TrnReminder rb = (TrnReminder) dataArr.get(j);
					  String msgText = "<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>";
					  msgText+="<div id='reportData' align='left' style='vertical-align: top;'>"+
					  			"<table width='100%'  align='center' >"+
					  			"<tr align='center'><td colspan='5'><FONT size='4'><b></b></FONT></td></tr>"+
								"<tr align='center'><td colspan='5'><FONT size='2'></FONT></td></tr>"+
								"<tr align='center'><td colspan='5' style='height: 5px;' align='right'><br /><br />&nbsp;<font><b><u>" +
								"</u></b></font></td></tr>"+
								"<tr><td colspan='5'><p style='text-align: justify; font-size: 15px; font: Tahoma;'>"+
								"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>"+
								"<p style='text-align: justify; font-size: 15px; font: Tahoma;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
								"Please find attachment of monthly Report of RFMS" +
								" kind perusal of Hon’ble MR.</p></td></tr>"+
								"<tr><td colspan='5'><br /><br /><table align='center' width='100%'><tr><td colspan='3'> <b><u></u></b></td>"+
								"<td colspan='2' align='right'><b></b><br /></td></tr></table></td></tr>"+
								"<tr><td><table align='center'>	<tr><td nowrap='nowrap' style='text-align: left;'>" +
								"<u>Details of the Pending Case</u></td>	</tr></table></td></tr></table>" +
								"<br><table width='100%'  align='center' style='page-break-after: always;display: table-header-group;'>" +
								"<thead  style='display: table-header-group;'>" +
								"<tr><td class='bototmDashedth'><b><nobr></nobr>&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr></b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</nobr>&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr></b></td></tr>	</thead><br>" +
								"<tbody id='content'>" ;
					 
					  msgText+="</tbody></table></div>";
					  SendMail sm=new SendMail();
					  mailcount++;
					  try{
						 
						sm.send(emailTo, "RFMS Monthly Report for A,B and Email Category", msgText,1, new String [] {filepath});	
						  if(mailcount==9)
						  {
						  mailcount=0;
						  SendMail.transport.close();
						  Thread.sleep(70000);
						  }
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
				//}
				
			
			
			
		//	}
			
	
			
		con.closeConnection();		
		 
			}

}
