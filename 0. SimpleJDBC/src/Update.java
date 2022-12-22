import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

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
			

			//String sql = "update test set name='Oh', addr='Jeju' where num=1"; 
			
			int updateNum = 2;
			String updateName = "Bae";
			String updateAddr = "Quebec";
			
			String sql = "update test set name=?, addr=? where num=?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, updateName);
			ps.setString(2, updateAddr);
			ps.setInt(3,  updateNum);
			
			int count = ps.executeUpdate();
			System.out.println("count: " + count);
			if(count==0) {
				System.out.println("Update failure");
			} else 
				System.out.println("Update success");
			
			
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
