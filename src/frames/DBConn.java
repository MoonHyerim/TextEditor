package frames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
