import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignUpDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";
	Connection conn;


	SignUpDao() {
		System.out.println("SignUp Dao Constructor");
		try {
			Class.forName(driver);
			System.out.println("Driver Load success");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Load failure");
		}
	} //constructor

	public void getConnection() {

		try {
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("Connection success");
		} catch (SQLException e) {
			System.out.println("Connection failure");
		}

	} //getConnection

	public int insertData(SignUpBean sign) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "insert into members values(memseq.nextval, ?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, sign.getId());
			ps.setString(2,  sign.getPw());
			ps.setString(3, sign.getName());

			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return cnt;
	} //insertData

	public ArrayList<SignUpBean> checkId(String id, String pw) {
		getConnection();
		ArrayList<SignUpBean> list = new ArrayList<SignUpBean>();
		
		String sql = "select * from members where id=? and pw=?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			
			ResultSet rs = ps.executeQuery();
		
			while(rs.next()) {
				 id  = rs.getString("id");
				 pw = rs.getString("pw");
				 
				 SignUpBean sb = new SignUpBean();
				 sb.setId(id);
				 sb.setPw(pw);
				 
				 list.add(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}


}
