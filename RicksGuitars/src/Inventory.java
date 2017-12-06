/* 파일 : Inventory.java				*
 * 과목명 : 소프트웨어개발프로세스			*
 * 서술 : 현재 저장되어 있는 악기들을 관리해주는 클래스	*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

	private List inventory;

	public Inventory() {
		inventory = new LinkedList();
	}

	public void addInstrument(int id, BookProperties spec) {
		Book book = new Book(id, spec);
		inventory.add(book);
	}

	public void addInstrument(Book instrument) {
		inventory.add(instrument);
	}

	public void removeInstrument(int row) {
		inventory.remove(row);
	}

	public Book getByRow(int row) {
		return (Book) inventory.get(row);
	}

	public Book get(int id) {
		for (Iterator i = inventory.iterator(); i.hasNext();) {
			Book instrument = (Book) i.next();
			if (instrument.getSerialNumber() == id) {
				return instrument;
			}
		}
		return null;
	}

	public int size() {
		return inventory.size();
	}

	public ArrayList<Book> search(BookProperties searchSpec) {
		ArrayList<Book> matchingInstruments = new ArrayList<Book>();
		for (Iterator i = inventory.iterator(); i.hasNext();) {
			Book instrument = (Book) i.next();
			if (instrument.getSpec().matches(searchSpec))
				matchingInstruments.add(instrument);
		}
		return matchingInstruments;
	}
}
