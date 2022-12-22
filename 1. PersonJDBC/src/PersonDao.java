import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";
	Connection conn;

	PersonDao() {
		System.out.println("PersonDao() constructor");

		try {
			Class.forName(driver);
			System.out.println("Driver load Success");


		} catch (ClassNotFoundException e) {
			System.out.println("Driver load failure");
		}
	} //constructor

	public void getConnection() {
		//2
		try {
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("Connection success");
		} catch (SQLException e) {
			System.out.println("Connection failure");
		}

	}
	public ArrayList<PersonBean> getAllPerson() {
		getConnection();

		ArrayList<PersonBean> list = new ArrayList<PersonBean>();

		String sql="select * from person";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");

				//System.out.println(num + "," + name + "," + age + "," + gender);

				PersonBean pb = new PersonBean();
				pb.setNum(num);
				pb.setName(name);
				pb.setAge(age);
				pb.setGender(gender);

				list.add(pb);

			}

			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("sql failure");
		}

		return list;



	} //getAllPerson

	public ArrayList<PersonBean> findGender(String gender) {
		getConnection();
		ArrayList<PersonBean> list = new ArrayList<PersonBean>();

		String sql = "select * from person where gender = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, gender);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				gender = rs.getString("gender");

				PersonBean pb = new PersonBean();
				pb.setNum(num);
				pb.setName(name); 
				pb.setAge(age);
				pb.setGender(gender);

				list.add(pb);

			}// while
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Sql failure");
		}
		return list;

	}

	public void insertData(PersonBean bean) {
		getConnection();
		String sql = "insert into person values(perseq.nextval, ?, ?, ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setInt(2,  bean.getAge());
			ps.setString(3, bean.getGender());

			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	} //insertData

	public void updateData(PersonBean bean) {
		getConnection();
		
		String sql = "update person set name=?, age=?, gender=? where num=?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getAge());
			ps.setString(3,  bean.getGender());
			ps.setInt(4, bean.getNum());
			
			ps.executeUpdate();
			 
			ps.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void deleteData(int num) {
		getConnection();
		
		String sql = "delete from person where num=?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
