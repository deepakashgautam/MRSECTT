package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dao.FeedbackDAO;
import in.org.cris.mrsectt.dao.LoginDAO;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class LoginController extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	// static Logger log = LogManager.getLogger(LoginController.class);

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doPost(arg0, arg1);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			String loginid = req.getParameter("loginid") != null ? req.getParameter("loginid") : "";
			String password = req.getParameter("password") != null ? req.getParameter("password") : "";
			//for password enc
            String encpass = passenc(password);  
            /* Displaying the unencrypted and encrypted passwords. */
           // System.out.println("userType" +loginid);
            /*System.out.println (" Original password: " + password); 
            //System.out.println("Encrypted password using MD5: " + encpass);*/
            
            // ends here
			
			
			MstLogin bn = new MstLogin();
			LoginDAO ldao = new LoginDAO();
			FeedbackDAO fdao= new FeedbackDAO();
			
			String userType = ldao.checkUserType(loginid);	
			//System.out.println("userType" +userType);
			
			if(userType.equalsIgnoreCase("H") || userType.equalsIgnoreCase("D") || userType.equalsIgnoreCase("S")||userType.equalsIgnoreCase("R")
					/*loginid.equalsIgnoreCase("me") 
					|| loginid.equalsIgnoreCase("ms") 
					|| loginid.equalsIgnoreCase("mt") 
					|| loginid.equalsIgnoreCase("mrs") 
					|| loginid.equalsIgnoreCase("mtraction") 
					|| loginid.equalsIgnoreCase("crb") 
					|| loginid.equalsIgnoreCase("fc") 
					
					|| loginid.equalsIgnoreCase("ederes")
					|| loginid.equalsIgnoreCase("ederrb")
					|| loginid.equalsIgnoreCase("eden")
					|| loginid.equalsIgnoreCase("edmecoaching")
					|| loginid.equalsIgnoreCase("edheritage")
					|| loginid.equalsIgnoreCase("edfm")
					|| loginid.equalsIgnoreCase("edfc")
					|| loginid.equalsIgnoreCase("edrsc")
					|| loginid.equalsIgnoreCase("edtkp")
					|| loginid.equalsIgnoreCase("edjv")
					|| loginid.equalsIgnoreCase("edeedev")
					|| loginid.equalsIgnoreCase("edse")
					|| loginid.equalsIgnoreCase("edenhmce")
					|| loginid.equalsIgnoreCase("edmobilityst")*/
					)
			{
				bn = ldao.validateLoginOther(loginid, encpass, session.getId());	
			}
			
			else{
			bn = ldao.validateLogin(loginid, encpass, session.getId());
			}
			if ("Y".equalsIgnoreCase(bn.getISVALIDUSER())) {
				session.setAttribute("MstLogin", bn);
				session.setAttribute("loginid", loginid);
				session.setAttribute("theme", bn.getTHEME());
				session.setAttribute("themeColor", bn.getTHEMECOLOR());
				session.setAttribute("sessionmsg", "");
				session.setAttribute("userType", userType);
				boolean bypasscheckfeedback;
				bypasscheckfeedback=fdao.checkExistingFeedbackforMonth(loginid);
				if (bypasscheckfeedback){
				
				if( userType.equalsIgnoreCase("D")
						/*bn.getLOGINID().equalsIgnoreCase("ederes")
						|| bn.getLOGINID().equalsIgnoreCase("ederrb")
						|| bn.getLOGINID().equalsIgnoreCase("eden")
						|| bn.getLOGINID().equalsIgnoreCase("edmecoaching")
						|| bn.getLOGINID().equalsIgnoreCase("edheritage")
						|| bn.getLOGINID().equalsIgnoreCase("edfm")
						|| bn.getLOGINID().equalsIgnoreCase("edfc")
						|| bn.getLOGINID().equalsIgnoreCase("edrsc")
						|| bn.getLOGINID().equalsIgnoreCase("edtkp")
						|| bn.getLOGINID().equalsIgnoreCase("edjv")
						|| bn.getLOGINID().equalsIgnoreCase("edeedev")
						|| bn.getLOGINID().equalsIgnoreCase("edse")
						|| bn.getLOGINID().equalsIgnoreCase("edenhmce")
						|| bn.getLOGINID().equalsIgnoreCase("edmobilityst")*/
						)
				{
					req.getRequestDispatcher("/HomeOther.jsp").forward(req, res);
				}
				else if(userType.equalsIgnoreCase("H")
						/*bn.getLOGINID().equalsIgnoreCase("me") 
						|| bn.getLOGINID().equalsIgnoreCase("ms") 
						|| bn.getLOGINID().equalsIgnoreCase("mt") 
						|| bn.getLOGINID().equalsIgnoreCase("mrs") 
						|| bn.getLOGINID().equalsIgnoreCase("mtraction") 
						|| bn.getLOGINID().equalsIgnoreCase("crb") 
						|| bn.getLOGINID().equalsIgnoreCase("fc") */
						)
				{
					req.getRequestDispatcher("/HomeOther_SubjectWise.jsp").forward(req, res);
					
				} 
				else if(userType.equalsIgnoreCase("S"))
				{
					req.getRequestDispatcher("/HomeOther_SubHeads.jsp").forward(req, res);
					
				} 
				else if(userType.equalsIgnoreCase("R"))
				{
					req.getRequestDispatcher("/HomeOther_Railway.jsp").forward(req, res);
					
				} 
				
				else {
				req.getRequestDispatcher("/HomeIframe.jsp").forward(req, res);
				}
			}
				else{
					String userAgent=req.getHeader("user-agent");
					  if(userAgent.contains("Chrome")){ //checking if Chrome
						  req.getRequestDispatcher("/Feedback4.jsp").forward(req, res);
					    }else{
					    	req.getRequestDispatcher("/Feedback.jsp").forward(req, res);
					    }
					//req.getRequestDispatcher("/Feedback4.jsp").forward(req, res);
				}
			} else {
				session.setAttribute("sessionmsg", "Invalid Username or Password !!!");
				req.getRequestDispatcher("/Login.jsp").forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String passenc(String password)
	{
		String encpass = null; 
		try 
		{ 
			MessageDigest m1 = MessageDigest.getInstance("MD5"); 
			/* Use the MD5 update() function to add plain-text password bytes to the digest. */ 
			m1.update(password.getBytes()); 
			/*The hash value should be converted to bytes.*/ 
			byte[] bt = m1.digest(); 
			/*Decimal bytes are contained in the bytes array. Changing the format to hexadecimal. */
			StringBuilder str = new StringBuilder(); 
			for(int i=0; i< bt.length ;i++) 
			{ 
				str.append(Integer.toString((bt[i] & 0xff) + 0x100, 16).substring(1)); 
			} 
			encpass = str.toString(); 
		} 
		catch (NoSuchAlgorithmException e) 
		{ 
			e.printStackTrace(); 
		}
		return encpass;
	}
}