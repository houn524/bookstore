/* 파일 : BookSpec.java				*
 * 과목명 : 객체지향시스템분석및설꼐			*
 * 서술 : 책의 상세한 속성들을 담는 클래스		*/
package entity;

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
}