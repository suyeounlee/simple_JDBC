import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {


	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";

	Connection conn;
	ResultSet rs;
	PreparedStatement ps;

	ProductDao() {
		System.out.println("ProductDao Constructor");

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

	public ArrayList<ProductBean> getAllProducts() {
		getConnection();

		ArrayList<ProductBean> list = new ArrayList<ProductBean>();


		String sql = "Select * from products order by id asc";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				//String inputdate = rs.getString("inputdate");
				Date d = rs.getDate("inputdate"); //Date Type
				String s = String.valueOf(d); //String type


				ProductBean pb = new ProductBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category);
				pb.setInputdate(s);

				list.add(pb);

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


	}//getAllProducts();

	public ArrayList<ProductBean> getProductById(int id) {
		getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;

		ArrayList<ProductBean> list = new ArrayList<ProductBean>();

		String sql = "select * from products where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if(rs.next()) {
				int id2 = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				//String inputdate = rs.getString("inputdate");
				Date d = rs.getDate("inputdate"); //Date Type
				String s = String.valueOf(d); //String type

				ProductBean pb = new ProductBean();
				pb.setId(id2);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category);
				pb.setInputdate(s);

				list.add(pb);
			}

		} catch (SQLException e) {
			System.out.println("Connection failure");
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public ArrayList<ProductBean> getProductsByCategory(String cate) {
		getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();

		String sql = "select * from products where upper(category)=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, cate.toUpperCase());

			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				//String inputDate = rs.getString("inputDate");
				Date d = rs.getDate("inputdate");
				String s = String.valueOf(d);

				ProductBean pb = new ProductBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);;
				pb.setCategory(category);
				pb.setInputdate(s);

				list.add(pb);

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

	}

	public int insertData(ProductBean pb) {
		//System.out.println(pb.getName());
		//System.out.println(pb.getCategory());

		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;

		String sql = "insert into products values(perseq.nextval, ?, ?, ?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pb.getName());
			ps.setInt(2, pb.getStock());
			ps.setInt(3,  pb.getPrice());
			ps.setString(4,  pb.getCategory());
			ps.setString(5, pb.getInputdate());

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

	public int deleteData(int id) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "delete from products where id=?";

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

	public int updateData(ProductBean pb) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;

		String sql = "update products set name=?, stock=?, price=?, category=?, inputdate=? where id=?";

		try {
			conn.setAutoCommit(false); 

			ps = conn.prepareStatement(sql);
			ps.setString(1, pb.getName());
			ps.setInt(2, pb.getStock());
			ps.setInt(3,  pb.getPrice());
			ps.setString(4, pb.getCategory());
			ps.setString(5,  pb.getInputdate());
			ps.setInt(6, pb.getId());

			cnt = ps.executeUpdate();

			conn.commit();

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
		}
		return cnt;
	}

}
