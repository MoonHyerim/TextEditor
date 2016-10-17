package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Login extends JFrame{
	LoginImagePanel pn_Login = new LoginImagePanel();
	JLabel lb_ID = new JLabel("���̵�");
	JLabel lb_Password = new JLabel("�н�����");
	JTextField tf_ID = new JTextField();
	JPasswordField pf_Password = new JPasswordField();
	JButton bt_Join = new JButton("ȸ������");
	JButton bt_Login = new JButton("�α���");
	JLabel lb_Login = new JLabel("�α���");
	private JLabel lb_Check;
		
	public Login(){
		pn_Login.setBackground(Color.WHITE);
		pn_Login.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_Login);
		pn_Login.setLayout(null);
		
		lb_ID.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_ID.setBounds(25, 97, 57, 15);
		pn_Login.add(lb_ID);
		
		lb_Password.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_Password.setBounds(25, 132, 57, 15);
		pn_Login.add(lb_Password);

		tf_ID.setBounds(100, 95, 116, 21);
		pn_Login.add(tf_ID);
		tf_ID.setColumns(10);
		
		pf_Password.setBounds(100, 130, 116, 21);
		pn_Login.add(pf_Password);
		
		bt_Join.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Join.setBounds(16, 186, 97, 23);
		bt_Join.addActionListener(new LoginListener());
		pn_Login.add(bt_Join);
		
		bt_Login.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Login.setBounds(125, 186, 97, 23);
		bt_Login.addActionListener(new LoginListener());
		pn_Login.add(bt_Login);
		
		JLabel lb_Login = new JLabel("�α���");
		lb_Login.setFont(new Font("���� ���", Font.PLAIN, 16));
		lb_Login.setBounds(93, 57, 57, 34);
		pn_Login.add(lb_Login);
		
		lb_Check = new JLabel("");
		lb_Check.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Check.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_Check.setBounds(25, 161, 191, 15);
		pn_Login.add(lb_Check);
		
		setTitle("�ؽ�Ʈ ������");
		setResizable(false);
		setBounds(100, 100, 244, 249);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class LoginImagePanel extends JPanel{
		ImageIcon im = new ImageIcon(MainEditor.imgPath+"LoginImage.png");
		Image img = im.getImage();
		public LoginImagePanel() {
			//setOpaque(false);
		}
		@ Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 93, 12, 46, 50, this);
		}
	}
	
	class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bt_Login){ 
				DBConn db = new DBConn(); // DB����
				String result = db.LoginCheck(tf_ID.getText(), pf_Password.getText());
				if(result.equals("NOID")){
					lb_Check.setForeground(Color.red);
					lb_Check.setText("���̵� ���������ʽ��ϴ�.");
				}else if(result.equals("NOPW")){
					lb_Check.setForeground(Color.red);
					lb_Check.setText("��й�ȣ�� ��ġ�����ʽ��ϴ�.");
				}else{
					//
				}
				db.CloseDBConn(db.c);
			}else if(e.getSource() == bt_Join){ //ȸ������ȭ�� ��ȯ
				new Join();
			}
			
		}
	}
}
