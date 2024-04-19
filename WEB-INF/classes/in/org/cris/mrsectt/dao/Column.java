package in.org.cris.mrsectt.dao;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Column")
public class Column {
	@XmlAttribute
    protected String name;
	@XmlValue
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
	@Override
	public String toString() {
		return "<" + name + "><![CDATA[" + value + "]]></"+name+">";
	}

	
}
