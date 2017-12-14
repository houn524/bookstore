/* ?��?�� : DatabaseController.java				 *
 * 과목�? : 객체�??��?��?��?��분석및설�?					 *
 * ?��?�� : ?��?��?��베이?���? ?��?��?�� ?��?��?��?�� 모두 �?리해주는 ?��?��?��*/
package boundary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.Book;

/**
 * @uml.annotations
 *    uml_generalization="mmi:///#jsrctype^name=DatabaseConnector[jcu^name=DatabaseConnector.java[jpack^name=boundary[jsrcroot^srcfolder=src[project^id=bookstore]]]]$uml.Class" 
 *    uml_realization="mmi:///#jsrctype^name=DatabaseConnector[jcu^name=DatabaseConnector.java[jpack^name=boundary[jsrcroot^srcfolder=src[project^id=bookstore]]]]$uml.Class"
 */
public class DatabaseController {
	private Connection connection;
	private String[] columnNames;
	private DatabaseConnector dbConnector;

	public DatabaseController() {
		dbConnector = new DatabaseConnector();
		dbConnector.connectSQL();
	}
	
	/********************** ?��?��?��베이?��?�� �? 추�? ****************************/
	public boolean insertBook(Book book) {
		Statement statement = null;

		ArrayList specList = new ArrayList(book.getSpec().getProperties().values());
		String sql = "insert ignore into book(id, title, publisher, author) values(?, ?, ?, ?)";
	    PreparedStatement pstmt;
		try {
			pstmt = dbConnector.getConnection().prepareStatement(sql);
		
			pstmt.setString(1, String.valueOf(book.getSerialNumber()));
			pstmt.setString(2, specList.get(0).toString());
			pstmt.setString(3, specList.get(1).toString());
			pstmt.setString(4, specList.get(2).toString());
			int n = pstmt.executeUpdate();
			
			if (n > 0) {
				System.out.println("�? 추�? ?���?");
				return true;
			} else {
				System.out.println("�? 추�? ?��?��");
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}

	/********************** ?��?��?��베이?��?��?�� �? ?���? ****************************/
	public boolean deleteBook(Book oldBook) {
		Statement statement = null;
		String sql = "delete from book where id=?";
	    PreparedStatement pstmt;
	    try {
			pstmt = dbConnector.getConnection().prepareStatement(sql);
			pstmt.setString(1, String.valueOf(oldBook.getSerialNumber()));
			
			int n = pstmt.executeUpdate();
			
			if (n > 0) {
				System.out.println("�? ?���? ?���?");
				return true;
			} else {
				System.out.println("�? ?���? ?��?��");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	/********************** ?��?��?��베이?��?�� ?��?�� 모든 �? 불러?���? ****************************/
	public Object[][] getBooks() {
		Statement statement = null;
		ResultSet resultSet = null;
		int colCount = 0, rowCount = 0;
		String sql = "select * from book";
		Object[][] datas = null;

		try {
			statement = (Statement) dbConnector.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			colCount = resultSet.getMetaData().getColumnCount() + 1;
			resultSet.last();
			rowCount = resultSet.getRow();
			resultSet.first();

			this.columnNames = new String[colCount];
			columnNames[0] = "";
			columnNames[1] = "일련번호";
			columnNames[2] = "제목";
			columnNames[3] = "출판사";
			columnNames[4] = "저자";
			columnNames[5] = "대출";
//			for (int i = 1; i < colCount; i++) {
//				this.columnNames[i] = resultSet.getMetaData().getColumnName(i);
//			}

			datas = new Object[rowCount][colCount];
			for (int i = 0; i < rowCount; i++) {
				datas[i][0] = false;
				for (int j = 0; j + 1 < colCount; j++) {
					datas[i][j + 1] = resultSet.getString(j + 1);
				}
				resultSet.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return datas;
	}

	/********************** ?��?��?��베이?��?�� ?��?�� ?��?��?�� ?��?�� ****************************/
	public void updateData(Book book) {
		Statement statement = null;
		ArrayList<String> specList = new ArrayList<String>(book.getSpec().getProperties().keySet());

		PreparedStatement pstmt;
		String sql = "update book set title=?, publisher=?, author=? where id=?";
		try {
			pstmt = dbConnector.getConnection().prepareStatement(sql);
			pstmt.setString(1, book.getSpec().getProperty("?���?").toString());
			pstmt.setString(2, book.getSpec().getProperty("출판?��").toString());
			pstmt.setString(3, book.getSpec().getProperty("???��").toString());
			pstmt.setString(4, String.valueOf(book.getSerialNumber()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkoutBook(int id, boolean checked) {
		PreparedStatement pstmt;
		String sql = "update book set checkout=? where id=?";
		try {
			pstmt = dbConnector.getConnection().prepareStatement(sql);
			pstmt.setBoolean(1, checked);
			pstmt.setInt(2, id);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				System.out.println("?���?");
				return true;
			} else {
				System.out.println("?��?��");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/********************** �?리�? 계정 ?��?�� ****************************/
	public boolean checkAccount(String id, String pw) {
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		String sql = "select * from admin where id=? and pw=?";
		try {
			pstmt = dbConnector.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			resultSet = pstmt.executeQuery();
			if(resultSet.isBeforeFirst()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/********************** ?��?��블의 컬럼�? �??��?���? ****************************/
	public String[] getColumnNames() {
		return columnNames;
	}
}