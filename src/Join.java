import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class DBConn{
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String user = "test";
	String password = "test";
	
	Connection c;
	Statement st;
	ResultSet rs;
	
	
	
	public DBConn(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			c = DriverManager.getConnection(url, user, password);
			st = c.createStatement();
			
			c.close();
		}catch(Exception e){
			System.out.println("Database ���� ����");
		}
	}
}

class JoinUI extends JFrame{
	JoinImagePanel pn_Join = new JoinImagePanel();
	JLabel lb_Join = new JLabel("ȸ������");
	JLabel lb_ID = new JLabel("���̵�");
	JLabel lb_PW = new JLabel("��й�ȣ");
	JLabel lb_PW2 = new JLabel("��й�ȣ Ȯ��");
	JLabel lb_Name = new JLabel("�̸�");
	JLabel lb_pwcheck = new JLabel("��й�ȣ Ȯ�� �Ϸ�");
	JButton btn_Join = new JButton("����");
	JButton btn_cancel = new JButton("���");
	JTextField tf_JoinID = new JTextField();
	JPasswordField pf_JoinPW = new JPasswordField();
	JPasswordField pf_JoinPW2 = new JPasswordField();
	JTextField tf_JoinName = new JTextField();
	
	DBConn dbConnection = new DBConn();
	
	public JoinUI(){	
		pn_Join.setBackground(Color.WHITE);
		pn_Join.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_Join);
		pn_Join.setLayout(null);
		
		lb_Join.setFont(new Font("���� ���", Font.PLAIN, 16));
		lb_Join.setBounds(90, 50, 75, 28);
		pn_Join.add(lb_Join);
		
		
		lb_ID.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_ID.setBounds(25, 93, 57, 15);
		pn_Join.add(lb_ID);
		
		lb_PW.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_PW.setBounds(25, 131, 57, 15);
		pn_Join.add(lb_PW);
		
		lb_PW2.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_PW2.setBounds(25, 169, 86, 15);
		pn_Join.add(lb_PW2);
		
		lb_Name.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_Name.setBounds(25, 204, 57, 15);
		pn_Join.add(lb_Name);
		
		lb_pwcheck.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pwcheck.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_pwcheck.setBounds(25, 243, 199, 15);
		pn_Join.add(lb_pwcheck);
		
		btn_Join.setFont(new Font("���� ���", Font.PLAIN, 12));
		btn_Join.setBounds(25, 280, 82, 23);
		pn_Join.add(btn_Join);
		
		btn_cancel.setFont(new Font("���� ���", Font.PLAIN, 12));
		btn_cancel.setBounds(142, 280, 82, 23);
		pn_Join.add(btn_cancel);
		
		tf_JoinID.setBounds(108, 91, 116, 21);
		pn_Join.add(tf_JoinID);
		tf_JoinID.setColumns(10);
		
		pf_JoinPW.setBounds(108, 129, 116, 21);
		pn_Join.add(pf_JoinPW);
		
		pf_JoinPW2.setBounds(108, 167, 116, 21);
		pn_Join.add(pf_JoinPW2);
		
		tf_JoinName.setColumns(10);
		tf_JoinName.setBounds(108, 202, 116, 21);
		pn_Join.add(tf_JoinName);
		
		btn_Join.addActionListener(new JoinListener());
		setResizable(false);
		setTitle("�ؽ�Ʈ ������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 252, 342);
		setVisible(true);
	}
	
	class JoinListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btn_Join){
				/*
				// ���̵� Ȯ��
				String sql = "SELECT COUNT(*) FROM USERINFO WHERE ID = \'"
							+ tf_JoinID.getText() + "\'";
				
				String sql2 = "SELECT * FROM USERINFO";
				try{
					dbConnection.rs = dbConnection.st.executeQuery(sql);
									
					dbConnection.rs = dbConnection.st.executeQuery(sql2);
					System.out.println(dbConnection.rs.getString(0));
					if(dbConnection.rs.getInt("COUNT(*)") > 0){
						lb_pwcheck.setForeground(Color.red);
						lb_pwcheck.setText("���̵� �̹� �����մϴ�.");
					}
					dbConnection.c.close();
				}catch(Exception ex){
					System.out.println("���̵� Ȯ�� ����(DB����)");
				}
				*/
				// ��й�ȣ Ȯ��
				if(!pf_JoinPW.getText().equals(pf_JoinPW2.getText())){
					lb_pwcheck.setForeground(Color.red);
					lb_pwcheck.setText("��й�ȣ�� �ٸ��ϴ�.");
					pf_JoinPW.setText("");
					pf_JoinPW2.setText("");
				}else{
					lb_pwcheck.setForeground(Color.black);
					lb_pwcheck.setText("��й�ȣ Ȯ�� �Ϸ�");
				}
			}
		}
	}
	
	class JoinImagePanel extends JPanel{
		ImageIcon im = new ImageIcon("JoinImage.png");
		Image img = im.getImage();
		
		@ Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 97, 10, 50, 43, this);
		}
	}
}
public class Join {
	public static void main(String[] args) {
		new JoinUI();
	}
}
