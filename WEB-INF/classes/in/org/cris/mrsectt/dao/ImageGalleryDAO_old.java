package in.org.cris.mrsectt.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.dbConnection.*;

public class ImageGalleryDAO_old
{
	static Logger log = LogManager.getLogger(ImageGalleryDAO_old.class);
	public ImageGalleryDAO_old()
	{
		super();
	}
	
	public ArrayList<String> getImageList(String searchKey,String type)
	{
		//System.out.println("typetypetypetype----------------"+ type);
		ArrayList<String> arr = new ArrayList<String>();
		DBConnection con = new DBConnection();
		try
		{
			con.openConnection();
			String strSQL = "";
			strSQL = " SELECT REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME FROM TRNATTACHMENT" +
					 " WHERE REFID = '"+searchKey+"' AND STATUSFLAG = '1' AND TYPE='"+type+"' ORDER BY ATTACHMENTID";
			log.debug(strSQL);
			ResultSet rs = con.select(strSQL);
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
