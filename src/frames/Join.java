package frames;

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

public class Join extends JFrame{
	private JButton btn_Join;
	private JButton btn_cancel;
	private JTextField tf_JoinID;
	private JPasswordField pf_JoinPW;
	private JTextField tf_JoinName;
	private JLabel lb_pwcheck;
	private JPasswordField pf_JoinPW2;
	
	public Join(){
		JoinImagePanel pn_Join = new JoinImagePanel();
		pn_Join.setBackground(Color.WHITE);
		pn_Join.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_Join);
		pn_Join.setLayout(null);
		
		JLabel lb_Join = new JLabel("회원가입");
		lb_Join.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		lb_Join.setBounds(90, 50, 75, 28);
		pn_Join.add(lb_Join);
		
		JLabel lb_ID = new JLabel("아이디");
		lb_ID.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_ID.setBounds(25, 93, 57, 15);
		pn_Join.add(lb_ID);
		
		JLabel lb_PW = new JLabel("비밀번호");
		lb_PW.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_PW.setBounds(25, 131, 57, 15);
		pn_Join.add(lb_PW);
		
		JLabel lb_PW2 = new JLabel("비밀번호 확인");
		lb_PW2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_PW2.setBounds(25, 169, 86, 15);
		pn_Join.add(lb_PW2);
		
		pf_JoinPW = new JPasswordField();
		pf_JoinPW2 = new JPasswordField();
		tf_JoinName = new JTextField();
		
		JLabel lb_Name = new JLabel("이름");
		lb_Name.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_Name.setBounds(25, 204, 57, 15);
		pn_Join.add(lb_Name);
		
		lb_pwcheck = new JLabel("");
		lb_pwcheck.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pwcheck.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_pwcheck.setBounds(25, 243, 199, 15);
		pn_Join.add(lb_pwcheck);
		
		btn_Join = new JButton("가입");
		btn_Join.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btn_Join.setBounds(25, 280, 82, 23);
		pn_Join.add(btn_Join);
		
		btn_cancel = new JButton("취소");
		btn_cancel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btn_cancel.setBounds(142, 280, 82, 23);
		// 취소 버튼 리스너 연결
		btn_cancel.addActionListener(new JoinListener());
		pn_Join.add(btn_cancel);
		
		tf_JoinID = new JTextField();
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
		setTitle("텍스트 에디터");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 252, 342);
		setVisible(true);
	}
	
	class JoinListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 가입 버튼
			if(e.getSource() == btn_Join){
				DBConn db = new DBConn();
				// 아이디 중복 확인
				if(db.IDCheck(tf_JoinID.getText()) == true){ 
					lb_pwcheck.setForeground(Color.red);
					lb_pwcheck.setText("이미 존재하는 아이디입니다.");
				}		
				// 비밀번호 일치 확인
				else if(!pf_JoinPW.getText().equals(pf_JoinPW2.getText())){
					lb_pwcheck.setForeground(Color.red);
					lb_pwcheck.setText("비밀번호가 다릅니다.");
					pf_JoinPW.setText("");
					pf_JoinPW2.setText("");
				}else if(tf_JoinID.getText().equals("") || tf_JoinName.getText().equals("") ||
						pf_JoinPW.getText().equals("") || pf_JoinPW2.getText().equals("")){
					lb_pwcheck.setForeground(Color.red);
					lb_pwcheck.setText("빈칸을 채워주십시요.");
				}else{ 
					lb_pwcheck.setForeground(Color.black);
					lb_pwcheck.setText("아이디, 비밀번호 확인 완료");
					db.InsertUser(tf_JoinID.getText(), tf_JoinName.getText(), pf_JoinPW.getText());
					db.CloseDBConn(db.c); // DB 연결 종료
				}					
			// 취소 버튼
			}else if(e.getSource() == btn_cancel){
				System.out.println("가입 취소");
			}
		}
	}
	
	class JoinImagePanel extends JPanel{
		ImageIcon im = new ImageIcon(MainEditor.imgPath+"JoinImage.png");
		Image img = im.getImage();
		
		@ Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 97, 10, 50, 43, this);
		}
	}
}
