/* ���� : DatabaseController.java			 *
 * ����� : ����Ʈ��������μ���			 		 *
 * ���� : �����ͺ��̽��� �̿��� ������� ��� �������ִ� Ŭ����*/

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DatabaseController {
	private Connection connection;
	private String[] columnNames;

	/********************** �����ͺ��̽� ���� �ڵ� ****************************/
	public void connectSQL() {
		connection = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("드라이버 검색 성공!!");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패!");
		}

//		String url = "jdbc:mysql://182.230.171.118:3306/ricksguitarsdb?autoReconnect=true&useSSL=false";
		String url = "jdbc:mysql://localhost:3306/ricksguitarsdb";
		String user = "root";
		String password = "root";

		try {
			connection = (Connection) DriverManager.getConnection(url, user, password);
			System.out.println("MySQL 접속 성공!!");
		} catch (SQLException e) {
			System.out.println("MySQL 접속 실패");
		}
	}
	
	/********************** �����ͺ��̽��� �Ǳ� �߰� ****************************/
	public boolean insertInstrument(Instrument newInstrument) {
		Statement statement = null;
		String sql = "insert ignore into " + newInstrument.getSpec().getProperty("instrumentType").toString()
				+ " values(";

		sql += "'" + newInstrument.getSerialNumber() + "'";
		sql += ", '" + newInstrument.getPrice() + "'";

		ArrayList specList = new ArrayList(newInstrument.getSpec().getProperties().values());
		for (int i = 0; i < specList.size() - 1; i++) {
			if (specList.get(i).toString().contains("'"))
				specList.set(i, specList.get(i).toString().replaceAll("'", "''"));
			sql += ", '" + specList.get(i).toString() + "'";
		}

		sql += ")";

		try {
			statement = (Statement) connection.createStatement();
			int n = statement.executeUpdate(sql);
			if (n > 0) {
				System.out.println("악기 추가 성공");
				return true;
			} else {
				System.out.println("악기 추가 실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/********************** �����ͺ��̽����� �Ǳ� ���� ****************************/
	public boolean deleteInstrument(Instrument oldInstrument) {
		Statement statement = null;
		String sql = "delete from " + oldInstrument.getSpec().getProperty("instrumentType").toString()
				+ " where SerialNumber = '" + oldInstrument.getSerialNumber().toString() + "';";
		try {
			statement = (Statement) connection.createStatement();
			int n = statement.executeUpdate(sql);
			if (n > 0) {
				System.out.println("악기 제거 성공");
				return true;
			} else {
				System.out.println("악기 제거 실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}



	/********************** �����ͺ��̽��� �ִ� ��� �Ǳ� �ҷ����� ****************************/
	public Object[][] getInstruments(InstrumentType instrumentType) {
		Statement statement = null;
		ResultSet resultSet = null;
		int colCount = 0, rowCount = 0;
		String sql = "select * from " + instrumentType.toString();
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
			for (int i = 1; i < colCount; i++) {
				this.columnNames[i] = resultSet.getMetaData().getColumnName(i);
			}

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

	/********************** �����ͺ��̽��� �ִ� ������ ���� ****************************/
	public void updateData(Instrument instrument) {
		Statement statement = null;
		ArrayList<String> specList = new ArrayList<String>(instrument.getSpec().getProperties().keySet());
		specList.remove("instrumentType");

		String sql = "update " + instrument.getSpec().getProperty("instrumentType").toString() + " set Price='"
				+ instrument.getPrice() + "'";
		for (String spec : specList) {
			if (instrument.getSpec().getProperty(spec).toString().contains("'"))
				instrument.getSpec().getProperties().put(spec,
					instrument.getSpec().getProperty(spec).toString().replaceAll("'", "''"));
			sql += ", " + spec + "='" + instrument.getSpec().getProperty(spec) + "'";
		}

		sql += " where SerialNumber='" + instrument.getSerialNumber() + "';";
		try {
			statement = (Statement) connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/********************** ���̺��� �÷��� �������� ****************************/
	public String[] getColumnNames() {
		return columnNames;
	}
}