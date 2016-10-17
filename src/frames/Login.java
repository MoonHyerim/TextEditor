package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	//Panel
	private LoginImagePanel pn_Login = new LoginImagePanel();
	// Label 
	private JLabel lb_Check;
	
	// TextField
	private JTextField tf_ID = new JTextField();
	private JPasswordField pf_Password = new JPasswordField();
	
	// Button
	private JButton bt_Join = new JButton("ȸ������");
	private JButton bt_Login = new JButton("�α���");
	
	public Login(){
		
		//Init
		InitComponent();
		
		setTitle("�ؽ�Ʈ ������");
		setResizable(false);
		setBounds(100, 100, 244, 249);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	//Initialize 
	private void InitComponent(){
		
		// login pannel
		pn_Login.setBackground(Color.WHITE);
		pn_Login.setBorder(new EmptyBorder(5, 5, 5, 5));
		pn_Login.setLayout(null);
		
		// Label
		JLabel lb_ID = new JLabel("���̵�");
		lb_ID.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_ID.setBounds(25, 97, 57, 15);
		
		JLabel lb_Password = new JLabel("�н�����");
		lb_Password.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_Password.setBounds(25, 132, 57, 15);
		
		JLabel lb_Login = new JLabel("�α���");
		lb_Login = new JLabel("�α���");
		lb_Login.setFont(new Font("���� ���", Font.PLAIN, 16));
		lb_Login.setBounds(93, 57, 57, 34);
		
		JLabel lb_Check;
		lb_Check = new JLabel("");
		lb_Check.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Check.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_Check.setBounds(25, 161, 191, 15);
		
		// TextField
		tf_ID.setBounds(100, 95, 116, 21);
		tf_ID.setColumns(10);
		
		pf_Password.setBounds(100, 130, 116, 21);
	
		// button
		bt_Join.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Join.setBounds(16, 186, 97, 23);
		bt_Join.addActionListener(new LoginListener());
	
		bt_Login.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Login.setBounds(125, 186, 97, 23);
		bt_Login.addActionListener(new LoginListener());
		
		// add component
		pn_Login.add(lb_ID);
		pn_Login.add(lb_Password);
		pn_Login.add(tf_ID);
		pn_Login.add(pf_Password);
		pn_Login.add(bt_Join);
		pn_Login.add(bt_Login);
		pn_Login.add(lb_Check);
		pn_Login.add(lb_Login);
		
		getContentPane().add(pn_Login);
	}
	
	public void ChangePanel(String panelName){
		if(panelName.equals("Login")){
			System.out.println("in");
			getContentPane().removeAll();
			getContentPane().add(pn_Login);
			setBounds(100, 100, 244, 249);
			revalidate();
			repaint();
			System.out.println("end");
			
		}
	
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
				Join pn_Join= new Join(Login.this);
				getContentPane().removeAll();
				getContentPane().add(pn_Join);
				setBounds(100, 100, 252, 342);
				revalidate();
				repaint();
				
			}
			
		}
	}
}
