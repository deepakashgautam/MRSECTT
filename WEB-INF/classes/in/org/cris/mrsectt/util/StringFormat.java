/*
 * Created on Feb 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package in.org.cris.mrsectt.util;



import in.org.cris.mrsectt.dao.CommonDAO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * This class contains a number of static methods that can be used to
 * validate the format of Strings, typically received as input from
 * a user, and to format values as Strings that can be used in
 * HTML output without causing interpretation problems.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class StringFormat {
    
    /**
     * Returns true if the specified date string represents a valid
     * date in the specified format, using the default Locale.
     *
     * @param dateString a String representing a date/time.
     * @param dateFormatPattern a String specifying the format to be used
     *   when parsing the dateString. The pattern is expressed with the
     *   pattern letters defined for the java.text.SimpleDateFormat class.
     * @return true if valid, false otherwise
     */
 
    
    /**
     * Returns true if the specified number string represents a valid
     * integer in the specified range, using the default Locale.
     *
     * @param numberString a String representing an integer
     * @param min the minimal value in the valid range
     * @param max the maximal value in the valid range
     * @return true if valid, false otherwise
     */
 
    /**
     * Returns true if the string is in the format of a valid SMTP
     * mail address: only one at-sign, except as the first or last
     * character, no white-space and at least one dot after the
     * at-sign, except as the first or last character.
     * <p>
     * Note! This rule is not always correct (e.g. on an intranet it may 
     * be okay with just a name) and it does not guarantee a valid Internet 
     * email address but it takes care of the most obvious SMTP mail 
     * address format errors.
     *
     * @param mailAddr a String representing an email address
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmailAddr(String mailAddr) {
	if (mailAddr == null) {
	    return false;
	}

        boolean isValid = true;
	mailAddr = mailAddr.trim();

	// Check at-sign and white-space usage
	int atSign = mailAddr.indexOf('@');
	if (atSign == -1 || 
	    atSign == 0 ||
	    atSign == mailAddr.length() -1 ||
	    mailAddr.indexOf('@', atSign + 1) != -1 ||
	    mailAddr.indexOf(' ') != -1 ||
	    mailAddr.indexOf('\t') != -1 ||
	    mailAddr.indexOf('\n') != -1 ||
	    mailAddr.indexOf('\r') != -1) {
	    isValid = false;
	}
	// Check dot usage
	if (isValid) {
	    mailAddr = mailAddr.substring(atSign + 1);
	    int dot = mailAddr.indexOf('.');
	    if (dot == -1 || 
		dot == 0 ||
		dot == mailAddr.length() -1) {
		isValid = false;
	    }
	}
        return isValid;
    }

    /**
     * Returns true if the specified string matches a string in the set
     * of provided valid strings, ignoring case if specified.
     *
     * @param value the String validate
     * @param validStrings an array of valid Strings
     * @param ignoreCase if true, case is ignored when comparing the value
     *  to the set of validStrings
     * @return true if valid, false otherwise
     */
    public static boolean isValidString(String value, String[] validStrings, 
            boolean ignoreCase) {
        boolean isValid = false;
        for (int i = 0; validStrings != null && i < validStrings.length; i++) {
            if (ignoreCase) {
                if (validStrings[i].equalsIgnoreCase(value)) {
                    isValid = true;
                    break;
                }
            }
            else {
                if (validStrings[i].equals(value)) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * Returns true if the strings in the specified array all match a string 
     * in the set of provided valid strings, ignoring case if specified.
     *
     * @param values the String[] validate
     * @param validStrings an array of valid Strings
     * @param ignoreCase if true, case is ignored when comparing the value
     *  to the set of validStrings
     * @return true if valid, false otherwise
     */
    public static boolean isValidString(String[] values, 
	    String[] validStrings, boolean ignoreCase) {

	if (values == null) {
	    return false;
	}
        boolean isValid = true;
        for (int i = 0; values != null && i < values.length; i++) {
	    if (!isValidString(values[i], validStrings, ignoreCase)) {
		isValid = false;
		break;
	    }
	}
	return isValid;
    }

    /**
     * Returns the specified string converted to a format suitable for
     * HTML. All signle-quote, double-quote, greater-than, less-than and
     * ampersand characters are replaces with their corresponding HTML
     * Character Entity code.
     *
     * @param in the String to convert
     * @return the converted String
     */
    public static String toHTMLString(String in) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; in != null && i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\'') {
                out.append("&#039;");
            }
            else if (c == '\"') {
                out.append("&#034;");
            }
            else if (c == '<') {
                out.append("&lt;");
            }
            else if (c == '>') {
                out.append("&gt;");
            }
            else if (c == '&') {
                out.append("&amp;");
            }
            else {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * Converts a String to a Date, using the specified pattern.
     * (see java.text.SimpleDateFormat for pattern description) and
     * the default Locale.
     *
     * @param dateString the String to convert
     * @param dateFormatPattern the pattern
     * @return the corresponding Date
     * @exception ParseException, if the String doesn't match the pattern
     */
 
    /**
     * Converts a String to a Number, using the specified pattern.
     * (see java.text.NumberFormat for pattern description) and the
     * default Locale.
     *
     * @param numString the String to convert
     * @param numFormatPattern the pattern
     * @return the corresponding Number
     * @exception ParseException, if the String doesn't match the pattern
     */
    /**
     * Replaces one string with another throughout a source string.
     *
     * @param in the source String
     * @param from the sub String to replace
     * @param to the sub String to replace with
     * @return a new String with all occurences of from replaced by to
     */
    public static String replaceInString(String in, String from, String to) {
        if (in == null || from == null || to == null) {
            return in;
        }

        StringBuffer newValue = new StringBuffer();
        char[] inChars = in.toCharArray();
        int inLen = inChars.length;
        char[] fromChars = from.toCharArray();
        int fromLen = fromChars.length;

        for (int i = 0; i < inLen; i++) {
            if (inChars[i] == fromChars[0] && (i + fromLen) <= inLen) {
                boolean isEqual = true;
                for (int j = 1; j < fromLen; j++) {
                    if (inChars[i + j] != fromChars[j]) {
                        isEqual = false;
                        break;
                    }
                }
                if (isEqual) {
                    newValue.append(to);
                    i += fromLen - 1;
                }
                else {
                    newValue.append(inChars[i]);
                }
            }
            else {
                newValue.append(inChars[i]);
            }
        }
        return newValue.toString();
    }

    /**
     * Returns a page-relative or context-relative path URI as
     * a context-relative URI.
     *
     * @param relURI the page or context-relative URI
     * @param currURI the context-relative URI for the current request
     * @exception IllegalArgumentException if the relURI is invalid
     */
    public static String toContextRelativeURI(String relURI, String currURI) 
        throws IllegalArgumentException {

        if (relURI.startsWith("/")) {
            // Must already be context-relative
            return relURI;
        }
        
        String origRelURI = relURI;
        if (relURI.startsWith("./")) {
            // Remove current dir characters
            relURI = relURI.substring(2);
        }
        
        String currDir = currURI.substring(0, currURI.lastIndexOf("/") + 1);
        StringTokenizer currLevels = new StringTokenizer(currDir, "/");
        
        // Remove and count all parent dir characters
        int removeLevels = 0;
        while (relURI.startsWith("../")) {
            if (relURI.length() < 4) {
                throw new IllegalArgumentException("Invalid relative URI: " + 
		    origRelURI);
            }
            relURI = relURI.substring(3);
            removeLevels++;
        }
        
        if (removeLevels > currLevels.countTokens()) {
            throw new IllegalArgumentException("Invalid relative URI: " + 
		origRelURI + " points outside the context");
        }
        int keepLevels = currLevels.countTokens() - removeLevels;
        StringBuffer newURI = new StringBuffer("/");
        for (int j = 0; j < keepLevels; j++) {
            newURI.append(currLevels.nextToken()).append("/");
        }
        return newURI.append(relURI).toString();
    }
    
   
    public static String OracleDateToJavaDate(String Oracledatevar) throws ParseException
	{
    	String javaDate="";
    	try {
    		if(!(Oracledatevar==null || Oracledatevar.equals("")))
    		{
    		String oradate=Oracledatevar.substring(0,10);
    		//log.info(oradate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(oradate);
			sdf.applyPattern("dd-MMM-yyyy");
			javaDate = sdf.format(date);
			//log.info(javaDate);
    		}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return javaDate.toUpperCase();
    }
    
    public static String OracleDateToJavaDate2(String Oracledatevar) throws ParseException
	{
    	String javaDate="";
    	try {
    		if(!(Oracledatevar==null || Oracledatevar.equals("")))
    		{
    		String oradate=Oracledatevar.substring(0,10);
    		//System.out.println(oradate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(oradate);
			sdf.applyPattern("dd/MM/yyyy");
			javaDate = sdf.format(date);
			//System.out.println(javaDate);
    		}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return javaDate.toUpperCase();
    }
    
    public static String OracleDateToJavaDate1(String Oracledatevar) throws ParseException
	{
    	String javaDate="";
    	try {
    		if(!(Oracledatevar==null || Oracledatevar.equals("")))
    		{
    		String oradate=Oracledatevar.substring(0,10);
    		//System.out.println(oradate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(oradate);
			sdf.applyPattern("MMM-yyyy");
			javaDate = sdf.format(date);
			//System.out.println(javaDate);
    		}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return javaDate.toUpperCase();
    } 
    public static BigDecimal ToBigDecimal(String value){
    	value=nullString(value);
    	BigDecimal returnValue;
    	//System.out.println("initial s value is"+ value);
    	if(value==null||value.equals("") ){
    		returnValue=new BigDecimal(0.0);
    	}
    	
    	else{
    		returnValue=new BigDecimal(value);
    	}
    	//System.out.println("value is"+returnValue);
    	return returnValue;
    }
    
    public static BigDecimal ToBigDecimalBig(BigDecimal value)
    {
    	
    	BigDecimal returnValue;
    	//System.out.println("initial s value is"+ value);
    	if( value==null)
    	{
    		returnValue=new BigDecimal(0.0);
    	}
    	else
    	{
    		returnValue=value;
    	}
    	//System.out.println("value is"+returnValue);
    	return returnValue;
    }
    
    public static int nullInt(String value){
    	int returnValue = 0;
    	if(value==null){
    		returnValue=0;
    	}
    	else if(value.equals("")){
    		returnValue=0;
    	}
    	else{	
    		returnValue=Integer.parseInt(value);
    	}
    	return returnValue;
    }
    
    
    public static double nullDecimal(String value){
    	double returnValue = 0.0;
    	if(value==null){
    		returnValue=0.0;
    	}
    	else if(value.equals("")){
    		returnValue=0.0;
    	}
    	else{	
    		returnValue=Double.parseDouble(value);
    	}
    	return returnValue;
    }
    
    /**
     ** pad a string s with a size of n with char c 
     ** on the left (L) or on the right(R)
     **/
     public synchronized static String leftPad
         ( String s, int n, char c ) {
       StringBuffer str = new StringBuffer(s);
       int strLength  = str.length();
       if ( n > 0 && n > strLength ) {
         for ( int i = 0; i <= n ; i ++ ) {
           
                 if ( i < n - strLength ) str.insert( 0, c );
            
         }
       }
       return str.toString();
     }
     
     public synchronized static String leftPad1
     ( String s, int n, String c ) {
   StringBuffer str = new StringBuffer(s);
   int strLength  = str.length();
   if ( n > 0 && n > strLength ) {
     for ( int i = 0; i <= n ; i ++ ) {
       
             if ( i < n - strLength ) str.insert( 0, c );
        
     }
   }
   return str.toString();
 }
     
     public synchronized static String rightPad
     ( String s, int n, char c) {
   StringBuffer str = new StringBuffer(s);
   int strLength  = str.length();
   if ( n > 0 && n > strLength ) {
     for ( int i = 0; i <= n ; i ++ ) {
          
             if ( i > strLength ) str.append( c );
         
     }
   }
   return str.toString();
 }

     public synchronized static String rightPad1
     ( String s, int n, String c) {
   StringBuffer str = new StringBuffer(s);
   int strLength  = str.length();
   if ( n > 0 && n > strLength ) {
     for ( int i = 0; i <= n ; i ++ ) {
          
             if ( i > strLength ) str.append( c );
         
     }
   }
   return str.toString();
 }

     public static void main(String[] args) {
		
    	// StringFormat stf = new StringFormat();
    	 //System.out.println("Bigdecimal chekc is :: " stf.ToBigDecimal("0"));
    	 
	}
     
     public static String nullString(String value)    {
      	String returnValue="";
      	if(value==null)	{
      		returnValue="";
      	}
      	else if(value.equals("")) 	{
      		returnValue="";
      	}
      	else   	{
      		returnValue =CommonDAO.numberFormet(value);
      		//returnValue=value;//.replaceAll("'","''");
      	}
      	return returnValue;
      }
      
      public static String nullStringNumber(String value)    {
      	String returnValue="";
      	if(value==null)	{
      		returnValue="";
      	}
      	else if(value.equals("")) 	{
      		returnValue="";
      	}
      	else   	{
      		returnValue =CommonDAO.numberFormet(value);
      	}
      	return returnValue;
      }
      public static String numberFormet(String var) {
  		String x = "";
  		try	{
  			if(var.indexOf(".") != -1)	{
  				
  			//	double retD = (new Double(var)).doubleValue();
  				BigDecimal ret = new BigDecimal(var);
  				x = "" + ret;
  				int j = x.indexOf(".");
  				String y = x.substring(j + 1);
  				
  				double decret = (new Double("1."+y)).doubleValue();
  					String xx = ""+decret;
  					int jj = xx.indexOf("."); 
  					y = xx.substring(jj+1);
  					if(y.equals("0"))
  						y="";
  				
  				String z = x.substring(0, j);
  				String returnvalue;
  				if(y.length() == 0)	{
  					returnvalue = "" + z;
  				}else {
  					returnvalue = z + "." + y;
  				}
  				return returnvalue;
  			}
  			return var;
  		}catch(NumberFormatException nfe)	{
  			return var;
  		}
  		catch(StringIndexOutOfBoundsException e)	{
  			return x;
  		}
  	}
     
}
