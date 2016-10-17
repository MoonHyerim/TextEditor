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
			System.out.println("Database ���� ����");
		}
	}
	
	public void CloseDBConn(Connection conn){
		try{
			conn.close();
		}catch(Exception e){
			System.out.println("DB CLOSE FAIL");
		}
	}
	
	// ���̵� �ߺ� üũ
	boolean IDCheck(String joinid){
		String sql = "SELECT * FROM USERINFO WHERE ID = \'"
					+ joinid + "\'";
		boolean returnFlag = false;
		try{
			rs = st.executeQuery(sql);
			if(rs.next())
				returnFlag = true;
		}catch(Exception e){
			System.out.println("DB ���� ����");
		}
		return returnFlag;
	}
	
	// ���� ���
	void InsertUser(String userid, String username, String userpw){
		try{
			String sql = "INSERT INTO USERINFO(ID, NAME, PASSWORD) VALUES(\'"
						+ userid + "\', \'" + username + "\', \'" + userpw + "\')";
			
			st.executeQuery(sql);
			st.executeQuery("Commit");
		}catch(Exception e){
			System.out.println("���� ��� ����");
		}
	}		
	
	// �α��� üũ
	String LoginCheck(String id, String pw){
		String sql = "SELECT ID, PASSWORD FROM USERINFO WHERE ID=\'"
					+ id + "\'";
		String returnFlag="";
		try{
			rs = st.executeQuery(sql);
			if(!rs.next()) // ��ȸ�� ����� ������
				returnFlag =  "NOID";
			else{ // ���̵� ����
				if(!pw.equals(rs.getString("PASSWORD")))
					returnFlag = "NOPW";
				else
					returnFlag = "OK";
			}
		}catch(Exception e){
			System.out.println("DB ��ȸ ����");
		}
		return returnFlag;
	}
}
