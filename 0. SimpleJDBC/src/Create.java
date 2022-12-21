import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Create {

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url ="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="jspid";
		String pw="jsppw";

		try {
			
			//1
			Class.forName(driver);
			System.out.println("Driver Load Success");
			
			//2
			Connection conn = DriverManager.getConnection(url, id, pw);
			
			System.out.println("Connection Success");
			
			String sql = "drop table test";
			
			//3 
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//4
			ps.executeUpdate(sql);
			
			sql = "create table test(num number, name varchar2(20), addr varchar2(30))";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate(sql);
			ps.close();
			
			conn.close();
			System.out.println("Close Success");

		}catch (ClassNotFoundException e) {
			System.out.println("Driver load failure");

		} catch (SQLException e) {
			System.out.println("connection/close failure");
		}
	}

}
