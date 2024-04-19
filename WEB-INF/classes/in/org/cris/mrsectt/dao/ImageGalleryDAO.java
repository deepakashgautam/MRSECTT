package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.dbConnection.*;

public class ImageGalleryDAO
{
	static Logger log = LogManager.getLogger(ImageGalleryDAO.class);
	public ImageGalleryDAO()
	{
		super();
	}
	
	public ArrayList<String> getImageList(String searchKey,String type)
	{
		//System.out.println("typetypetypetype----------------"+ type);
		ArrayList<String> arr = new ArrayList<String>();
		DBConnection con = new DBConnection();
		PreparedStatement ps =null;
		try
		{
			con.openConnection();
			String strSQL = "";
			strSQL = " SELECT REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME FROM TRNATTACHMENT" +
					 " WHERE REFID = ? AND STATUSFLAG = '1' AND TYPE=? ORDER BY ATTACHMENTID";
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, searchKey);
			ps.setString(2, type);
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				arr.add(rs.getString("NEWFILENAME").substring(0, rs.getString("NEWFILENAME").length() - 4)+"~"+rs.getString("NEWFILENAME").substring(rs.getString("NEWFILENAME").length() - 3,rs.getString("NEWFILENAME").length()));
			}
		} catch (Exception e)
		{
			log.fatal(e, e);
		} finally
		{
			con.closeConnection();
		}
		return arr;
	}
}
