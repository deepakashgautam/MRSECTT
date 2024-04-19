package in.org.cris.mrsectt.Beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Row") 
public class RecieptDetails {

	@XmlElement(name = "ComputerNumber")
	String computernumber;
	@XmlElement(name = "Url")
	String url;
	@XmlElement(name = "ReceiptNature")
	String receiptnature;
	@XmlElement(name = "Receiptnumber")
	String ReceiptNumber;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "LetterReferenceNumber")
	String LetterReferenceNumber;
	
	
	
	public String getLetterReferenceNumber() {
		return LetterReferenceNumber;
	}
	public void setLetterReferenceNumber(String letterReferenceNumber) {
		LetterReferenceNumber = letterReferenceNumber;
	}
	public String getFileNumber() {
		return FileNumber;
	}
	public void setFileNumber(String fileNumber) {
		FileNumber = fileNumber;
	}
	public String getClassified() {
		return Classified;
	}
	public void setClassified(String classified) {
		Classified = classified;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getLetterDate() {
		return LetterDate;
	}
	public void setLetterDate(String letterDate) {
		LetterDate = letterDate;
	}
	public String getReceivedDate() {
		return ReceivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		ReceivedDate = receivedDate;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getMinistry() {
		return Ministry;
	}
	public void setMinistry(String ministry) {
		Ministry = ministry;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}

	@XmlElement(name = "FileNumber")
	String FileNumber;
	
	@XmlElement(name = "Classified")
	String Classified;
	@XmlElement(name = "Type")
	String Type;
	
	@XmlElement(name = "Language")
	String Language;
	
	@XmlElement(name = "CorredpondenceType")
	String CorredpondenceType;
	
	@XmlElement(name = "CorredpondenceTypeId")
	String CorredpondenceTypeId;
	
	@XmlElement(name = "DeliveryModeId")
	String DeliveryModeId;
	
	@XmlElement(name = "LanguageId")
	String LanguageId;
	
	@XmlElement(name = "SubjectCategoryId")
	String SubjectCategoryId;
	
	@XmlElement(name = "ClassifiedId")
	String ClassifiedId;
	
	@XmlElement(name = "PostDetailId")
	String PostDetailId;
	
	public String getPostDetailId() {
		return PostDetailId;
	}
	public void setPostDetailId(String postDetailId) {
		PostDetailId = postDetailId;
	}
	public String getClassifiedId() {
		return ClassifiedId;
	}
	public void setClassifiedId(String classifiedId) {
		ClassifiedId = classifiedId;
	}
	public String getSubjectCategoryId() {
		return SubjectCategoryId;
	}
	public void setSubjectCategoryId(String subjectCategoryId) {
		SubjectCategoryId = subjectCategoryId;
	}
	public String getCorredpondenceTypeId() {
		return CorredpondenceTypeId;
	}
	public void setCorredpondenceTypeId(String corredpondenceTypeId) {
		CorredpondenceTypeId = corredpondenceTypeId;
	}
	public String getDeliveryModeId() {
		return DeliveryModeId;
	}
	public void setDeliveryModeId(String deliveryModeId) {
		DeliveryModeId = deliveryModeId;
	}
	public String getLanguageId() {
		return LanguageId;
	}
	public void setLanguageId(String languageId) {
		LanguageId = languageId;
	}

	@XmlElement(name = "Letterdate")
	String LetterDate;
	
	

	@XmlElement(name = "Receiveddate")
	String ReceivedDate;
	
	@XmlElement(name = "Subject")
	String Subject;
	
	@XmlElement(name = "Ministry")
	String Ministry;
	
	
	@XmlElement(name = "Department")
	String Department;
	
	@XmlElement(name = "Organization")
	String Organization;
	
	@XmlElement(name = "SenderName")
	String SenderName;
	@XmlElement(name = "SenderDesignation")
	String SenderDesignation;
	@XmlElement(name = "Country")
	String Country;
	@XmlElement(name = "State")
	String State;
	@XmlElement(name = "City")
	String City;
	@XmlElement(name = "Address")
	String Address;
	
	@XmlElement(name = "Remarks")
	String Remarks;
	@XmlElement(name = "SentTo")
	String SentTo;
	@XmlElement(name = "SentOn")
	String SentOn;
	
	public String getCorredpondenceType() {
		return CorredpondenceType;
	}
	public void setCorredpondenceType(String corredpondenceType) {
		CorredpondenceType = corredpondenceType;
	}
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public String getSenderName() {
		return SenderName;
	}
	public void setSenderName(String senderName) {
		SenderName = senderName;
	}
	public String getSenderDesignation() {
		return SenderDesignation;
	}
	public void setSenderDesignation(String senderDesignation) {
		SenderDesignation = senderDesignation;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getSentTo() {
		return SentTo;
	}
	public void setSentTo(String sentTo) {
		SentTo = sentTo;
	}
	public String getSentOn() {
		return SentOn;
	}
	public void setSentOn(String sentOn) {
		SentOn = sentOn;
	}
	public String getReceiptNumber() {
		return ReceiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		ReceiptNumber = receiptNumber;
	}
	public RecieptDetails()
	{}
	
	String errorCode;
	String errorMessage;
	
	
	public final String getComputernumber() {
		return computernumber;
	}
	public final void setComputernumber(String computernumber) {
		this.computernumber = computernumber;
	}
	public final String getReceiptnature() {
		return receiptnature;
	}
	public final void setReceiptnature(String receiptnature) {
		this.receiptnature = receiptnature;
	}
	public final String getErrorCode() {
		return errorCode;
	}
	public final void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public final String getErrorMessage() {
		return errorMessage;
	}
	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
