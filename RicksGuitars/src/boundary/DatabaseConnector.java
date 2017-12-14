/* 파일 : DatabaseConnector.java				*
 * 과목명 : 객체지향시스템분석및설꼐					*
 * 서술 : JDBC를 이용해 데이터베이스	에 연결한다.		*/
package boundary;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DatabaseConnector {
	private Connection connection;
	
	public void connectSQL() {
		connection = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("드라이버 검색 성공!!");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패!");
		}

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
	
	public Connection getConnection() {
		return this.connection;
	}
}
