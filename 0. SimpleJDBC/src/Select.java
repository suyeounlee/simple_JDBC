import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
     

public class Select {

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

			String sql = "select * from test order by num asc"; //commit after insert rows.

			//3
			PreparedStatement ps = conn.prepareStatement(sql); 
			//4
			ResultSet rs = ps.executeQuery();
			while(rs.next()) { 
				int num = rs.getInt("num"); 
				String name = rs.getString("name"); 
				String addr = rs.getString("addr");
				System.out.println(num+","+name+","+addr);
			}

			//3
			rs.close();
			ps.close();
			conn.close();
			System.out.println("Connection close success");


		} catch (ClassNotFoundException e) {
			System.out.println("Driver Load Failure");
		} catch (SQLException e) {
			System.out.println("Connection failure");
		} 



	}

}
