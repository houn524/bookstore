/* 파일 : CompareLogic.java						*
 * 과목명 : 객체지향시스템분석및설계						*
 * 서술 : entity 객체들을 액세스하여 책들의 속성을 비교 및 검색	*/
package application_logic;

import java.util.ArrayList;
import java.util.Iterator;

import entity.Book;
import entity.BookProperties;
import entity.Inventory;

public class CompareLogic {

	public boolean matches(BookProperties spec, BookProperties otherSpec) {
		for (Iterator i = otherSpec.getProperties().keySet().iterator(); i.hasNext();) {
			String propertyName = (String) i.next();
			System.out.println(propertyName);
			if (otherSpec.getProperty(propertyName).equals("")) {
				continue;
			}
				
			if (!((String) spec.getProperties().get(propertyName)).contains((CharSequence) otherSpec.getProperty(propertyName))) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<Book> search(Inventory inventory, BookProperties searchSpec) {
		ArrayList<Book> matchingBooks = new ArrayList<Book>();
		for (Iterator i = inventory.getInventory().iterator(); i.hasNext();) {
			Book book = (Book) i.next();
			if (matches(book.getSpec(), searchSpec))
				matchingBooks.add(book);
		}
		return matchingBooks;
	}
}
