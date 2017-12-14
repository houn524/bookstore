/* 파일 : Inventory.java					*
 * 과목명 : 객체지향시스템분석및설계				*
 * 서술 : 현재 저장되어 있는 책들을 관리해주는 클래스	*/
package entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

	private List inventory;

	public Inventory() {
		inventory = new LinkedList();
	}

	public void addBook(int id, BookProperties spec) {
		Book book = new Book(id, spec);
		inventory.add(book);
	}

	public void addBook(Book book) {
		inventory.add(book);
	}

	public void removeBook(int row) {
		inventory.remove(row);
	}

	public Book getByRow(int row) {
		return (Book) inventory.get(row);
	}
	
	public List getInventory() {
		return inventory;
	}

	public Book get(int id) {
		for (Iterator i = inventory.iterator(); i.hasNext();) {
			Book book = (Book) i.next();
			if (book.getSerialNumber() == id) {
				return book;
			}
		}
		return null;
	}

	public int size() {
		return inventory.size();
	}
}
