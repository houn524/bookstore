/* 파일 : Instrument.java				*
 * 과목명 : 소프트웨어개발프로세스			*
 * 서술 : 책의 정보를 담는 클래스			*/

import java.util.ArrayList;

public class Book {

	private int id;
	private BookProperties spec;

	public Book(int id, BookProperties spec) {
		this.id = id;
		this.spec = spec;
	}

	public int getSerialNumber() {
		return id;
	}

	public BookProperties getSpec() {
		return spec;
	}

	/********************** 책의 속성들을 배열로 만들어서 반환 ****************************/
	public Object[] toArray() {
		ArrayList specList = new ArrayList(spec.getProperties().values());

		Object[] data = new Object[specList.size() + 2];
		data[0] = false;
		data[1] = id;

		for (int i = 0; i < spec.getProperties().size(); i++) {
			data[i + 2] = specList.get(i);
			System.out.println("spec : " + specList.get(i));
		}
		return data;
	}
}