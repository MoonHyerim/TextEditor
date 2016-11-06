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
	
	/*
	 * return ���� 1
	 *        ���� 0
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
			System.out.println("�� ���� ����...");
			result = 0;
			return result;
		}
	}
	
	public int modifyWrite(long id, String content) {
		// TODO ���� Ȯ��
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
			System.out.println("�� ���� ����..");
			result = 0;
			return result;
		}
	}
	
	public int deleteWrite(long id) {
		// TODO ���� Ȯ��
		String sql = "DELETE FROM writelist "
				+ "WHERE id="+id;
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			System.out.println("������ �� ���� ����..");
			result = 0;
			return result;
		}
	}
	
	public int deleteWrites(String userid) {
		// TODO ���� Ȯ��
		String sql = "DELETE FROM writelist "
				+ "WHERE userid='"+userid+"'";
		int result = 0;
		try {
			result = st.executeUpdate(sql);
			st.execute("commit");
			return result;
		} catch (SQLException e) {
			System.out.println("����� ��ü �� ���� ����..");
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
			while(rs.next()){ // ��ȸ����� ��������������.
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
