package frames;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import VO.Write;

public class DBConn {
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String user = "texteditor";
	private String password = "texteditor";
	
	Connection c;
	Statement st;
	ResultSet rs;
	
	public DBConn(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			c = DriverManager.getConnection(url, user, password);
			st = c.createStatement();
		}catch(Exception e){
			System.out.println("Database 연동 실패");
		}
	}
	
	public void CloseDBConn(Connection conn){
		try{
			conn.close();
		}catch(Exception e){
			System.out.println("DB CLOSE FAIL");
		}
	}
	
	// 아이디 중복 체크
	boolean IDCheck(String joinid){
		String sql = "SELECT * FROM USERINFO WHERE ID = \'"
					+ joinid + "\'";
		boolean returnFlag = false;
		try{
			rs = st.executeQuery(sql);
			if(rs.next())
				returnFlag = true;
		}catch(Exception e){
			System.out.println("DB 쿼리 실패");
		}
		return returnFlag;
	}
	
	// 유저 등록
	void InsertUser(String userid, String username, String userpw){
		try{
			String sql = "INSERT INTO USERINFO(ID, NAME, PASSWORD) VALUES(\'"
						+ userid + "\', \'" + username + "\', \'" + userpw + "\')";
			
			st.executeQuery(sql);
			st.executeQuery("Commit");
		}catch(Exception e){
			System.out.println("유저 등록 실패");
		}
	}		
	
	// 로그인 체크
	String LoginCheck(String id, String pw){
		String sql = "SELECT ID, PASSWORD FROM USERINFO WHERE ID=\'"
					+ id + "\'";
		String returnFlag="";
		try{
			rs = st.executeQuery(sql);
			if(!rs.next()) // 조회된 결과가 없을때
				returnFlag =  "NOID";
			else{ // 아이디 존재
				if(!pw.equals(rs.getString("PASSWORD")))
					returnFlag = "NOPW";
				else
					returnFlag = "OK";
			}
		}catch(Exception e){
			System.out.println("DB 조회 실패");
		}
		return returnFlag;
	}
	
	/*
	 * return 성공 1
	 *        실패 0
	 */
	public int insertWrite(Write write) {
		Timestamp created = new Timestamp(new java.util.Date().getTime());
		Timestamp modified = created;
		String sql = "INSERT INTO writelist (id, userid, title, content, created, modified) "
				+ "VALUES (wl_seq.nextval, '"+write.getUserid()+"', '"+write.getTitle()+"', '"+write.getContent()+"', to_timestamp('"+created+"','yyyy/mm/dd hh24:mi:ss.ff'), to_timestamp('"+modified+"','yyyy/mm/dd hh24:mi:ss.ff'))";
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			System.out.println("글 저장 실패...");
			result = 0;
			return result;
		}
	}
	
	public int modifyWrite(long id, String content) {
		// TODO 실행 확인
		Timestamp modified = new Timestamp(new java.util.Date().getTime());
		String sql = "UPDATE writelist SET content='"+content+"', modified=to_timestamp('"+modified+"','yyyy/mm/dd hh24:mi:ss.ff')"
				+ " WHERE id="+id;
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("글 수정 실패..");
			result = 0;
			return result;
		}
	}
	
	public int deleteWrite(long id) {
		// TODO 실행 확인
		String sql = "DELETE FROM writelist "
				+ "WHERE id="+id;
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			System.out.println("지정된 글 삭제 실패..");
			result = 0;
			return result;
		}
	}
	
	public int deleteWrites(String userid) {
		// TODO 실행 확인
		String sql = "DELETE FROM writelist "
				+ "WHERE userid='"+userid+"'";
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			System.out.println("사용자 전체 글 삭제 실패..");
			result = 0;
			return result;
		}
	}
	
	public ArrayList<Write> getWrites(String userid) {
		ArrayList<Write> writeList = new ArrayList<Write>();
		
		String sql = "SELECT id, title, created, modified, content "
				+ "FROM WRITELIST "
				+ "WHERE userid='"+userid+"' "
				+ "ORDER BY ID";

		try{
			rs = st.executeQuery(sql);
			while(rs.next()){ // 조회결과가 남아있을때까지.
				Write write = new Write();
				write.setId(rs.getLong("id"));
				write.setTitle(rs.getString("title"));
				write.setCreated(rs.getDate("created"));
				write.setModified(rs.getDate("modified"));
				write.setContent(rs.getString("content"));
				
				writeList.add(write);
			}
				
		}catch(Exception e){	}
		return writeList;
	}
	
//	public Write getWrite(long id) {
//		String sql = "SELECT id, userid, title, content, created, modified "
//				+ "WHERE id="+id;
//		
//	}
}
