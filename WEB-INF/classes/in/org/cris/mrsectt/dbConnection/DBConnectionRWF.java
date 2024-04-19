/*
 * Created on Dec 22, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package in.org.cris.mrsectt.dbConnection;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author administrator hello To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class DBConnectionRWF {
	private static DataSource datasource = null;
	Connection con = null;

	public synchronized Connection getConnection() throws Exception {
		try {
			InitialContext ctx = new InitialContext();
			datasource = (DataSource) ctx.lookup("jndi/MRSECTT");
			con = datasource.getConnection();
			//datasource = (DataSource) ctx.lookup("jdbc/ipastest");
			
			//System.out.println("ctx" + ctx);
		} catch (Exception e) {
			System.out.println("exception in before returning --"
					+ e.toString());
		}
	
		//System.out.println("sucessfully returned --" + datasource);
		return con;
	}

}
