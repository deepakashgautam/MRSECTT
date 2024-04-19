package in.org.cris.mrsectt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Row")
public class Row {

	
	@XmlElement(name = "Column")
	private List <Column> columns= new ArrayList<Column>();
	
	@Override
	public String toString() {
		return "<Row> " + listToString(columns) + "</Row>";
	}
	public static String listToString(List<?> list) {
	    String result = " ";
	    for (int i = 0; i < list.size(); i++) {
	        result += " " + list.get(i);
	    }
	    return result;
	}
}
