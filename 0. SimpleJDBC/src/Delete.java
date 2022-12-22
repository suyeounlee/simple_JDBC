import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url ="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="jspid";
		String pw="jsppw";

		try {
			Class.forName(driver);
			System.out.println("Driver load success");

			Connection conn = DriverManager.getConnection(url, id, pw);
			System.out.println("connection Success");

			//String sql = "delete test where num=5";

			int deleteNum = 2;

			String sql = "delete test where num=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,deleteNum);

			int count = ps.executeUpdate();

			if(count == 0) {
				System.out.println("Delete success");
			} else {
				System.out.println("Delete failure");
			}



			ps.close();
			conn.close();
			System.out.println("connection close");

		} catch (ClassNotFoundException e) {
			System.out.println("Driver load failure");
		} catch (SQLException e) {
			System.out.println("Connection failure");
		}


	}

}
