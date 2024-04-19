package test;

import in.org.cris.mrsectt.dao.Row;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="WSResponse") 
public class WSResponse {

	@XmlElement(name = "Row")
private List <Row> row= new ArrayList<Row>();

	@Override
	public String toString() {
		return "<WSResponse> " + listToString(row) + "</WSResponse>";
	}


	public static String listToString(List<?> list) {
	    String result = " ";
	    for (int i = 0; i < list.size(); i++) {
	        result += " " + list.get(i);
	    }
	    return result;
	}


	
}