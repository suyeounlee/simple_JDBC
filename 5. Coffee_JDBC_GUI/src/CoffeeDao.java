import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoffeeDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";

	Connection conn;
	ResultSet rs;
	PreparedStatement ps;

	CoffeeDao() {
		System.out.println("CoffeeDao Constructor");

		try {
			Class.forName(driver);
			System.out.println("Driver load success");

		} catch (ClassNotFoundException e) {
			System.out.println("Driver load failure");
		}

	} //constructor

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			System.out.println("Connection failure");
		}

	}//getConnection

	public ArrayList<CoffeeBean> getList() {
		getConnection();
		ArrayList<CoffeeBean> list = new ArrayList<CoffeeBean>();

		String sql = "select * from coffee order by id asc";
		try {
			 ps = conn.prepareStatement(sql);
			 rs = ps.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String origin = rs.getString("origin");
				int price = rs.getInt("price");
				String tasting = rs.getString("tasting");
				Date d = rs.getDate("roasting");
				String s = String.valueOf(d);

				CoffeeBean cb = new CoffeeBean();
				cb.setId(id);
				cb.setName(name);
				cb.setOrigin(origin);
				cb.setPrice(price);
				cb.setTasting(tasting);
				cb.setRoasting(s);

				list.add(cb);
			}

		} catch (SQLException e) {
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;

	}// getList

	public int insertData(CoffeeBean cb) {
		//System.out.println(pb.getName());
		//System.out.println(pb.getCategory());

		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;

		String sql = "insert into coffee values(cafeseq.nextval, ?, ?, ?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, cb.getName());
			ps.setString(2, cb.getOrigin());
			ps.setInt(3,  cb.getPrice());
			ps.setString(4,  cb.getTasting());
			ps.setString(5, cb.getRoasting());

			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} //finally

		return cnt;
	}

	public int updateData(CoffeeBean cb) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;

		String sql = "update coffee set name=?, origin=?, price=?, tasting=?, roasting=? where id=?";

		try {
			conn.setAutoCommit(false); 

			ps = conn.prepareStatement(sql);
			ps.setString(1, cb.getName());
			ps.setString(2, cb.getOrigin());
			ps.setInt(3,  cb.getPrice());
			ps.setString(4, cb.getTasting());
			ps.setString(5, cb.getRoasting());
			ps.setInt(6, cb.getId());

			cnt = ps.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return cnt;
	}

		public int deleteData(int id) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "delete from coffee where id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			cnt = ps.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return cnt;
	}

}


