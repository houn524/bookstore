/* 파일 : InstrumentSpec.java				*
 * 과목명 : 소프트웨어개발프로세스			*
 * 서술 : 악기의 상세한 속성들을 담는 클래스		*/

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookProperties {

	private LinkedHashMap properties;

	public BookProperties(Map properties) {
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

	public boolean matches(BookProperties otherSpec) {
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