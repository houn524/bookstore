/* 파일 : DatabaseController.java			 *
 * 과목명 : 소프트웨어개발프로세스			 *
 * 서술 : 데이터베이스를 이용한 연산들을 모두 관리해주는 클래스*/

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DatabaseController {
	private Connection connection;
	private String[] columnNames;

	/********************** 데이터베이스 연동 코드 ****************************/
	public void connectSQL() {
		connection = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("드라이버 검색 성공!!");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패!");
		}

//		String url = "jdbc:mysql://182.230.171.118:3306/ricksguitarsdb?autoReconnect=true&useSSL=false";
//		String url = "jdbc:mysql://localhost:3306/ricksguitarsdb";
		String url = "jdbc:mysql://localhost:3306/bookstoredb";
		String user = "root";
		String password = "root";

		try {
			connection = (Connection) DriverManager.getConnection(url, user, password);
			System.out.println("MySQL 접속 성공!!");
		} catch (SQLException e) {
			System.out.println("MySQL 접속 실패");
		}
	}
	
	/********************** 데이터베이스에 책 추가 ****************************/
	public boolean insertBook(Book book) {
		Statement statement = null;

		ArrayList specList = new ArrayList(book.getSpec().getProperties().values());
		String sql = "insert ignore into book(id, title, publisher, author) values(?, ?, ?, ?)";
	    PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(sql);
		
			pstmt.setString(1, String.valueOf(book.getSerialNumber()));
			pstmt.setString(2, specList.get(0).toString());
			pstmt.setString(3, specList.get(1).toString());
			pstmt.setString(4, specList.get(2).toString());
			int n = pstmt.executeUpdate();
			
			if (n > 0) {
				System.out.println("책 추가 성공");
				return true;
			} else {
				System.out.println("책 추가 실패");
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}

	/********************** 데이터베이스에서 책 제거 ****************************/
	public boolean deleteInstrument(Book oldBook) {
		Statement statement = null;
		String sql = "delete from book where id=?";
	    PreparedStatement pstmt;
	    try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(oldBook.getSerialNumber()));
			
			int n = pstmt.executeUpdate();
			
			if (n > 0) {
				System.out.println("책 제거 성공");
				return true;
			} else {
				System.out.println("책 제거 실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String sql = "delete from " + oldInstrument.getSpec().getProperty("instrumentType").toString()
//				+ " where SerialNumber = '" + oldInstrument.getSerialNumber().toString() + "';";
//		try {
//			statement = (Statement) connection.createStatement();
//			int n = statement.executeUpdate(sql);
//			if (n > 0) {
//				System.out.println("악기 제거 성공");
//				return true;
//			} else {
//				System.out.println("악기 제거 실패");
//				return false;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}



	/********************** 데이터베이스에 있는 모든 책 불러오기 ****************************/
	public Object[][] getInstruments() {
		Statement statement = null;
		ResultSet resultSet = null;
		int colCount = 0, rowCount = 0;
		String sql = "select * from book";
		Object[][] datas = null;

		try {
			statement = (Statement) connection.createStatement();
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

	/********************** 데이터베이스에 있는 데이터 수정 ****************************/
	public void updateData(Book book) {
		Statement statement = null;
		ArrayList<String> specList = new ArrayList<String>(book.getSpec().getProperties().keySet());
//		specList.remove("instrumentType");
//
		PreparedStatement pstmt;
		String sql = "update book set title=?, publisher=?, author=? where id=?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, book.getSpec().getProperty("제목").toString());
			pstmt.setString(2, book.getSpec().getProperty("출판사").toString());
			pstmt.setString(3, book.getSpec().getProperty("저자").toString());
			pstmt.setString(4, String.valueOf(book.getSerialNumber()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String sql = "update " + instrument.getSpec().getProperty("instrumentType").toString() + " set Price='"
//				+ instrument.getPrice() + "'";
//		for (String spec : specList) {
//			if (instrument.getSpec().getProperty(spec).toString().contains("'"))
//				instrument.getSpec().getProperties().put(spec,
//					instrument.getSpec().getProperty(spec).toString().replaceAll("'", "''"));
//			sql += ", " + spec + "='" + instrument.getSpec().getProperty(spec) + "'";
//		}
//
//		sql += " where SerialNumber='" + instrument.getSerialNumber() + "';";
//		try {
//			statement = (Statement) connection.createStatement();
//			statement.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	/********************** 테이블의 컬럼명 가져오기 ****************************/
	public String[] getColumnNames() {
		return columnNames;
	}
}