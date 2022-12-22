import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDao {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";

	Connection conn;
	ResultSet rs;
	PreparedStatement ps;

	AlbumDao() {
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

	public ArrayList<AlbumBean> getAllAlbums() {
		getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();

		String sql = "select * from albums order by num";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String song = rs.getString("song");
				String singer = rs.getString("singer");
				String company = rs.getString("company");
				int price = rs.getInt("price");
				//String release_date = rs.getString("release_date");
				Date d = rs.getDate("release_day");
				String s = String.valueOf(d);

				AlbumBean bean = new AlbumBean();
				bean.setNum(num);
				bean.setSong(song);
				bean.setSinger(singer);
				bean.setCompany(company);
				bean.setPrice(price);
				bean.setRelease_date(s);

				list.add(bean);

			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public ArrayList<AlbumBean> albumsBySearch(String colName, String search_word) {
		//select*from albums singer like '% K %' ;
		// select song, upper(song) from albums;
		System.out.println(colName + "," + search_word);
		getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		
		String sql = "select * from albums where upper(" + colName + ") like ? order by num";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search_word.toUpperCase() + "%");
		
			rs = ps.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String song = rs.getString("song");
				String singer = rs.getString("singer");
				String company = rs.getString("company");
				int price = rs.getInt("price");
				Date d = rs.getDate("release_day");
				String s = String.valueOf(d);
				
				AlbumBean ab = new AlbumBean();
				ab.setNum(num);
				ab.setSong(song);
				ab.setSinger(singer);
				ab.setCompany(company);
				ab.setPrice(price);
				ab.setRelease_date(s);
				
				list.add(ab);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		System.out.println("The result is " + list.size() + " albums.");
				return list;
	} //albumsBySearch

	public ArrayList<AlbumBean> getAlbumsByRange(int from, int to) {
		getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql= "select num, song, singer, company, price, release_day from (select num, song, singer, company, price, release_day, rank()over(order by price desc, singer asc) rank from albums) where rank between "+from+" and "+to;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
	
		try {
			ps = conn.prepareStatement(sql);
//			ps.setInt(1, from);
//			ps.setInt(2, to);

			rs = ps.executeQuery();
			
			while(rs.next()) {
//				int num = rs.getInt("num");
//				String song = rs.getString("song");
//				String singer = rs.getString("singer");
//				String company = rs.getString("company");
//				int price = rs.getInt("price");
//				//String release_date = rs.getString("release_date");
//				Date d = rs.getDate("release_day");
//				String s = String.valueOf(d);

				AlbumBean bean = new AlbumBean();
				bean.setNum(rs.getInt("num"));
				bean.setSong(rs.getString("song"));
				bean.setSinger(rs.getString("singer"));
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("price"));
				bean.setRelease_date(String.valueOf(rs.getDate("release_day")));
				
				list.add(bean);

			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	}//getAlbumsByRange
	
	public int insertData(AlbumBean ab) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;
		
		String sql = "insert into albums values(albumseq.nextval, ?,?,?,?,?)";
		
		try {  
			ps = conn.prepareStatement(sql);
			ps.setString(1,  ab.getSong());
			ps.setString(2,  ab.getSinger());
			ps.setString(3,  ab.getCompany());
			ps.setInt(4,  ab.getPrice());
			ps.setString(5,  ab.getRelease_date());
			
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

	public int updataData(AlbumBean ab) {
		getConnection();
		PreparedStatement ps = null;

		int cnt = -1;
		
		String sql = "update albums set song=?, singer=?, company=?, price=?, release_day=? where num=?";	
		
		try {  
			ps = conn.prepareStatement(sql);
			ps.setString(1,ab.getSong());
			ps.setString(2, ab.getSinger());
			ps.setString(3, ab.getCompany());
			ps.setInt(4, ab.getPrice());
			ps.setString(5, ab.getRelease_date());
			ps.setInt(6, ab.getNum());

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

	public int deleteData(int num) {
		getConnection();
		PreparedStatement ps = null;
		int cnt = -1;
		
		String sql = "delete from albums where num=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			
			 cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

	public ArrayList<AlbumBean> align(String colName, String way) {
		//System.out.println(colName + "," + way);
		//singer, desc
		//song, asc
		getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		//select * from albums order by singer desc
		String sql = "select * from albums order by " +colName + " " + way;
	
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				AlbumBean ab = new AlbumBean();
				ab.setNum(rs.getInt("num"));
				ab.setSong(rs.getString("song"));
				ab.setSinger(rs.getString("singer"));
				ab.setCompany(rs.getString("company"));
				ab.setPrice(rs.getInt("price"));
				ab.setRelease_date(String.valueOf(rs.getDate("release_day")));
		
				list.add(ab);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		return list;
	}


}
