/* ���� : InstrumentSpec.java			*
 * ����� : ����Ʈ��������μ���				*
 * ���� : �Ǳ��� ���� �Ӽ����� ��� Ŭ����		*/

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class InstrumentSpec {

	private LinkedHashMap properties;

	public InstrumentSpec(Map properties) {
		if (properties == null) {
			this.properties = new LinkedHashMap();
		} else {
			this.properties = new LinkedHashMap(properties);
		}
	}

	public Object getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public Map getProperties() {
		return properties;
	}

	public boolean matches(InstrumentSpec otherSpec) {
		for (Iterator i = otherSpec.getProperties().keySet().iterator(); i.hasNext();) {
			String propertyName = (String) i.next();
			if (otherSpec.getProperty(propertyName).equals(""))
				continue;
			if (!properties.get(propertyName).equals(otherSpec.getProperty(propertyName))) {
				return false;
			}
		}
		return true;
	}
}