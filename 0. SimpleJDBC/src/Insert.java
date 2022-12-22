import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

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
			
			//String sql = "insert into test values(3, 'Hong', 'Ottawa')";
			
//			int insertNum = 4;
//			String insertName = "Lee";
//			String insertAddr = "Toronto";
//			String sql = "insert into test values(" + insertNum + ",'" + insertName + "','" + insertAddr + "')"; // 'Lee', 'Toronto'
			
			String sql = "insert into test values(?,?,?)";
			
			 
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 5); //(1, insertNum)
			ps.setString(2, "Cho");
			ps.setString(3, "Vancouver");
			
			
			//4
			ps.executeUpdate();
			
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
