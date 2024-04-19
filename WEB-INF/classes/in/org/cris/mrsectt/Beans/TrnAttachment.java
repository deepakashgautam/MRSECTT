package in.org.cris.mrsectt.Beans;

public class TrnAttachment {
	
	private String REFID = "";
	private String ATTACHMENTID = "";
	private String ORIGINALFILENAME = "";
	private String GENFILENAME = "";
	private String PATH = "";
	private String EXT = "";
	private String type="";
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEXT() {
		return EXT;
	}
	public void setEXT(String ext) {
		EXT = ext;
	}
	public String getREFID() {
		return REFID;
	}
	public void setREFID(String refid) {
		REFID = refid;
	}
	public String getATTACHMENTID() {
		return ATTACHMENTID;
	}
	public void setATTACHMENTID(String attachmentid) {
		ATTACHMENTID = attachmentid;
	}
	public String getORIGINALFILENAME() {
		return ORIGINALFILENAME;
	}
	public void setORIGINALFILENAME(String originalfilename) {
		ORIGINALFILENAME = originalfilename;
	}
	public String getGENFILENAME() {
		return GENFILENAME;
	}
	public void setGENFILENAME(String genfilename) {
		GENFILENAME = genfilename;
	}
	public String getPATH() {
		return PATH;
	}
	public void setPATH(String path) {
		PATH = path;
	}
}
