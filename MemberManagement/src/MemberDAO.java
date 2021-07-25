import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MemberDAO {
	
	private Connection getConnection() {
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/management";
		String user = "root";
		String password = "1234";
		
		try {
			Class.forName(driver); // 드라이버 로드
			Connection con = DriverManager.getConnection(url, user, password); // DB 연결
			return con;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패 - " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB 연결 실패 - " + e.getMessage());
		}
		
		return null;
		
	} 

	// 자원 반환
	private void close(Connection con) {
		if(con != null) try { con.close(); } catch(Exception e) {}
	}
	private void close(PreparedStatement pstmt) {
		if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
	}
	private void close(ResultSet rs) {
		if(rs != null) try { rs.close(); } catch(Exception e) {}
	}

	// 로그인 작업
	public boolean login(String dbUsername, String dbPassword) throws LoginFailedException {

		Connection con = getConnection(); // Connection 객체 가져오기(DB 연결)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT password FROM user WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dbUsername);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("password").equals(dbPassword)) {
					return true; // 로그인 성공 -> true 리턴
				} else {
					throw new LoginFailedException("패스워드 틀림");
				}
			} else { 
				throw new LoginFailedException("아이디 없음");
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - " + e.getMessage());
		} finally {
			close(rs); 
			close(pstmt);
			close(con);
		}
		return false;
	}

	public int insert(MemberDTO dto) {
		
		int insertCount = 0;
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO customer VALUES(null,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getJumin());
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return insertCount;
		
	}
	
	public int update(MemberDTO dto) {

		int updateCount = 0;
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE customer SET name=?,age=?,email=?,jumin=? WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getJumin());
			pstmt.setInt(5, dto.getIdx());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return updateCount;
		
	}

	public int delete(int idx) {
		
		int deleteCount = 0;
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM customer WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return deleteCount;
		
	}

	public Vector<Vector> select() {
		
		Vector<Vector> memberList = null; // 전체 회원 목록 저장
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM customer";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			memberList = new Vector<Vector>();
			
			while(rs.next()) { 
				Vector member = new Vector(); // 1개 레코드 정보 저장
				member.add(rs.getInt("idx"));
				member.add(rs.getString("name"));
				member.add(rs.getInt("age"));
				member.add(rs.getString("email"));
				member.add(rs.getString("jumin"));
				
				memberList.add(member); // 전체 레코드 객체(memberList)에 추가
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return memberList;
		
	}

}
















