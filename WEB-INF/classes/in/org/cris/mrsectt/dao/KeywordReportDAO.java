package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.GenBean;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class KeywordReportDAO {
	static Logger log = LogManager.getLogger(KeywordReportDAO.class);
	
	public ArrayList<GenBean>  getReport(String INDATECONDITION,String INDATEFROM,String INDATETO,String LETDATECONDITION,String LETDATEFROM,String LETDATETO,String RECEIVEDFROM,String[] REFCLASS,String[] VIPTYPE,String [] VIPPARTY,String STATUS,String subjectcode,String keyword1,String keyword3){
		ArrayList<GenBean> list = new ArrayList<GenBean>();
		String sql=getReportSQL(INDATECONDITION, INDATEFROM, INDATETO, LETDATECONDITION, LETDATEFROM, LETDATETO, RECEIVEDFROM, REFCLASS, VIPTYPE, VIPPARTY,STATUS,subjectcode,keyword1,keyword3);
		list = new CommonDAO().getCodeDesc(sql, 8);
		
		
		return list;
	}
	
	
	public String  getReportSQL(String INDATECONDITION,String INDATEFROM,String INDATETO,String LETDATECONDITION,String LETDATEFROM,String LETDATETO,String RECEIVEDFROM,String[] REFCLASS,String[] VIPTYPE,String [] VIPPARTY,String STATUS,String subjectcode,String keyword1,String keyword3){
		StringBuilder whereCond = new StringBuilder();
		if(INDATECONDITION!=null && INDATEFROM!=null && INDATEFROM.length()>0)
		{
			if(INDATECONDITION.equals("="))
			{
				whereCond.append(" AND INCOMINGDATE=TO_DATE('"+INDATEFROM+"','dd/mm/yyyy') "); 
			}else if(INDATECONDITION.equals("BETWEEN") && INDATETO!=null && INDATETO.length()>0)
			{
				whereCond.append(" AND INCOMINGDATE between TO_DATE('"+INDATEFROM+"','dd/mm/yyyy') AND TO_DATE('"+INDATETO+"','dd/mm/yyyy') "); 
			}
		}
		
		
		if(RECEIVEDFROM!=null && RECEIVEDFROM.length()>0){
			whereCond.append(" AND UPPER(REFERENCENAME) LIKE UPPER('%"+RECEIVEDFROM+"%') "); 
		}
		
		if(REFCLASS!=null && REFCLASS.length>0){
			String temp="";
			for(String ref : REFCLASS){
				temp+=" OR REFCLASS='"+ref+"'";
			}
			whereCond.append("AND ("+ temp.substring(3) +")");
		}
		
		if(VIPTYPE!=null && VIPTYPE.length>0){
			String temp="";
			for(String viptype : VIPTYPE){
				temp+=" OR ADDVIPTYPE='"+viptype+"'";
			}
			whereCond.append("AND ("+ temp.substring(3) +")");
		}
		
		if(VIPPARTY!=null && VIPPARTY.length>0){
			String temp="";
			for(String vipparty : VIPPARTY){
				temp+=" OR VIPPARTY='"+vipparty+"'";
			}
			whereCond.append("AND ("+ temp.substring(3) +")");
		}
		
		if(subjectcode!=null && subjectcode.length()>0){
			whereCond.append("AND ( SUBJECTCODE='"+ subjectcode +"')");
		}
			
		
		if(STATUS!=null && STATUS.length()>0){
			whereCond.append("AND ( UPPER(VIPSTATUS) like UPPER('%"+ STATUS +"%'))");
		}
		
		
		if(keyword1!=null && keyword1.length()>0){
			whereCond.append("AND ( KEYWORD1='"+ keyword1 +"' OR KEYWORD2='"+ keyword1 +"')");
		}
		
		if(keyword3!=null && keyword3.length()>0){
			whereCond.append("AND ( KEYWORD3='"+ keyword3 +"') ");
		}
		//String [] VIPPARTY
		
		String sql="SELECT KEYWORD1 STN1,KEYWORD2 STN2,KEYWORD3 TRN,REFERENCENAME,VIPSTATUS,SUBJECT,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INDATE,REFNO" +
		" from trnreference" +
		" where 1=1 " +
		//" and  rownum <50" +
		" " +whereCond+
		" order by KEYWORD1,KEYWORD2,KEYWORD3,REFERENCENAME,VIPSTATUS";
		
		log.debug("KEYWORD REPORT SQL : "+ sql);
		
		
		return sql;
	}
	
}
