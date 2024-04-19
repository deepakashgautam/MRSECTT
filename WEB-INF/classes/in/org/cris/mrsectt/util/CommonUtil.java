package in.org.cris.mrsectt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommonUtil {
	
	public String StripHTML(String str)
	{
		return str.replaceAll("\\<.*?>","");
	}
	public String handleNull(String str){
		return str!=null?str:"";
	}

	

	//*------********************START IndianCurrencyFormat************************----*/
	/**
	 * @author spo
	 * @param var1
	 * @return
	 * @converts "78923456234.259" to "78,92,34,56,234.259" indian currency format
	 */
	public static  String IndianCurrencyFormat(String var1){
		
		if(var1.length()<=3 || var1==null){
			return var1;
		}
		
		String var2="";
		String x="";
		String y="";
		int decIndex=var1.indexOf('.');// index of decimal
		
		/////-------breaking into mantissa(x) and exponent(y)--------- ///
		if(decIndex != -1){ // if decimal is present 
			if(decIndex==0 ){//.234
				y=var1.substring(decIndex+1);	
			}else if(decIndex==var1.length()-1){//234.
				x=var1.substring(0, decIndex);	
			}else{//234.456
				x=var1.substring(0, decIndex);
				y=var1.substring(decIndex+1);	
			}
		}else{//345
			x=var1;
			y="";
		}
		///////////////////////////////
		
		/////////-----procesing ------///////
		int xlength=x.length();
		if(xlength > 3)
		{
			int i=xlength;
			StringBuffer xbuf = new StringBuffer(x);
			while(i>2){
				if(i==xlength)
				{
					i= i-3;
					xbuf = xbuf.insert(i, ",");
				}
				else
				{
					i=i-2;
					xbuf = xbuf.insert(i, ",");
				}
			}
			x=xbuf.toString();
		}
		////////////////////////////////
		
		//////////-------concatenating------//////////
		//System.out.println("x="+x+" y="+y);
		if(decIndex != -1){ 
			var2=x+"."+y;
		}else{
			var2=x;
		}
		///////////////////////////////////////
		
		return var2;
	}
	//************************************************END *******************/
	
	public void zipDir(String dir2zip, ZipOutputStream zos) 
	{ 
	    try 
	   { 
	        //create a new File object based on the directory we have to zip 
	    	File zipDir = new File(dir2zip); 
	        //get a listing of the directory content 
	        String[] dirList = zipDir.list(); 
	        byte[] readBuffer = new byte[2156]; 
	        int bytesIn = 0; 
	        //loop through dirList, and zip the files 
	        for(int i=0; i<dirList.length; i++) 
	        { 
	            File f = new File(zipDir, dirList[i]); // new File(zipDir, dirList[i]); 
	        if(f.isDirectory()) 
	        { 
	                //if the File object is a directory, call this 
	                //function again to add its content recursively 
	            String filePath = f.getPath(); 
	            zipDir(filePath, zos); 
	                //loop again 
	            continue; 
	        } 
	            //if we reached here, the File object f was not a directory 
	            //create a FileInputStream on top of f 
	            FileInputStream fis = new FileInputStream(f); 
	           // create a new zip entry 
	        ZipEntry anEntry = new ZipEntry(f.getName()); 
	            //place the zip entry in the ZipOutputStream object 
	        zos.putNextEntry(anEntry); 
	            //now write the content of the file to the ZipOutputStream 
	            while((bytesIn = fis.read(readBuffer)) != -1) 
	            { 
	                zos.write(readBuffer, 0, bytesIn); 
	            } 
	           //close the Stream 
	           fis.close(); 
	    } 
	} 
	catch(Exception e) 
	{ 
	    //handle exception
		e.printStackTrace();
	} 
	}
	
	public static boolean deleteDir(File dir) {
		//System.out.println("Directory is :"+ dir + "\n" + dir.isDirectory());
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        //System.out.println("No of Files is :"+ dir + "\n" + children.length);
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	           // System.out.println("DELETEING FILE "+dir+File.separator+children[i]+"::: "+ success);
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
	
	
	//**********---start InputStreamToString ------*****//
    public static String InputStreamToString(InputStream is) throws IOException {
        //InputStream is = null;
    	if(is==null){return "";}
        StringBuilder sb = new StringBuilder();  
        String line;  
        
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
            while ((line = reader.readLine()) != null) {  
                sb.append(line).append("\n");  
            }  
            // Print the content of data.txt  
            //System.out.println(sb.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            is.close();  
        }  
        return sb.toString();
    }
	//**********---start InputStreamToString ------*****//
	
}
