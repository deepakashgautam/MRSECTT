package in.org.cris.mrsectt.Beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="WSResponse")
public class RecieptDetailsList {
	
	@XmlElement(name = "Row")
	List<RecieptDetails> list = new ArrayList<RecieptDetails>();

	public final List<RecieptDetails> getList() {
		return list;
	}

	public final void setList(List<RecieptDetails> list) {
		this.list = list;
	}

}
