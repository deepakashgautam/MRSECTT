package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.satisfiedCustomerBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.directwebremoting.annotations.RemoteMethod;
//import org.directwebremoting.annotations.RemoteProxy;


//@RemoteProxy(name = "feedback")
public class FeedbackDAO {
	private static final Logger log = LogManager.getLogger(FeedbackDAO.class);

	//@RemoteMethod
	public String saveFeedback(int rating, String review, String loginid) {
		String strSQL="";
		String  message="";
		Timestamp currenttime = new Timestamp(new Date().getTime());
	  DBConnection con = new DBConnection();
		
		try {
			  
			con.openConnection();
			
				int id=0;
			    strSQL="SELECT NVL(MAX(TO_NUMBER(FEEDBACKID)),0) FROM USERFEEDBACK";
			    ResultSet rs = con.select(strSQL);
				
			    if(rs.next()){
					id = rs.getInt(1)+1;
				}
			    //System.out.println("ID :"+id);
			    strSQL=" INSERT INTO USERFEEDBACK"
						+ " ("
						+ "   FEEDBACKID,CREATIONDATE,CHANGEDATE,RATING,REVIEW,LOGINID,CRISCOMMENT,MONTHYEAR"
						+ " ) " + " values " + " (" + "   ?,?,?,?,?,?,?,?" + " )";
			    PreparedStatement ps = con.setPrepStatement(strSQL);
			    ps.setInt(1, id);
			    ps.setTimestamp(2, currenttime);
			    ps.setTimestamp(3, currenttime);
			    ps.setInt(4, rating);
			    ps.setString(5, review);
			    ps.setString(6, loginid);
			    ps.setString(7, "");
			    ps.setTimestamp(8, currenttime);
			    int row =ps.executeUpdate();
				
			
			message="Saved Successfully.";
		} catch (Exception e) {
			con.rollback();
			log.fatal(e, e);
			message="Data not saved.";
		} finally {
			con.closeConnection();
		}
		
       return message;
	}
	
	//@RemoteMethod
	public boolean checkExistingFeedbackforMonth(String loginid) {

		String strSQL="";
		int id=0;
		DBConnection con = new DBConnection();
		ResultSet rs = null;
		boolean check = false;
		try {
			con.openConnection();
			 strSQL=" SELECT CASE WHEN COUNT(*)>0 THEN 1 ELSE 0 END AS RESULT  FROM USERFEEDBACK WHERE LOGINID = ? "
								+ " AND to_char(sysdate,'mm')=to_char(CREATIONDATE,'mm') "
								+ " and to_char(sysdate,'yyyy')=to_char(CREATIONDATE,'yyyy')";
			 PreparedStatement ps = con.setPrepStatement(strSQL);
			 ps.setString(1, loginid);
			  rs = ps.executeQuery();
			 if(rs.next()){
					check = rs.getInt(1)==1;
				}
			
		} catch (Exception e) {
			con.rollback();
			log.fatal(e, e);
			return false;
		} finally {
			con.closeConnection();
		}
		return check;

	}
	
	@SuppressWarnings("null")
	@RemoteMethod
	public satisfiedCustomerBean satisfiedCustomer() {
		 DBConnection con = new DBConnection();
		 satisfiedCustomerBean happyindex=new satisfiedCustomerBean();
		 int positive =0;
		 int total=0;
		try {
			con.openConnection();
			String strSQL="     select sum(case when rating >1 then 1 else 0 end) positive ,count(*) total  "
			          + "from    userfeedback where to_char(creationdate,'yyyymm')=to_char(add_months(sysdate,-1),'yyyymm') ";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			 ResultSet rs = ps.executeQuery();
			 if(rs.next()){
					 positive = rs.getInt(1);
					 total = rs.getInt(2);
					 happyindex.setPositive(positive);
					 happyindex.setTotal(total);
				}
		
		} catch (Exception e) {
			con.rollback();
			log.fatal(e, e);
			
		} finally {
			con.closeConnection();
		}
		return happyindex;
	}
	
	
}
