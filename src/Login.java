import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

class LoginUI extends JFrame{
	LoginImagePanel pn_Login = new LoginImagePanel();
	JLabel lb_ID = new JLabel("¾ÆÀÌµð");
	JLabel lb_Password = new JLabel("ÆÐ½º¿öµå");
	JTextField tf_ID = new JTextField();
	JPasswordField pf_Password = new JPasswordField();
	JButton bt_Join = new JButton("È¸¿ø°¡ÀÔ");
	JButton bt_Login = new JButton("·Î±×ÀÎ");
	JLabel lb_Login = new JLabel("·Î±×ÀÎ");
		
	public LoginUI(){
		pn_Login.setBackground(Color.WHITE);
		pn_Login.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_Login);
		pn_Login.setLayout(null);
		
		lb_ID.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_ID.setBounds(25, 109, 57, 15);
		pn_Login.add(lb_ID);
		
		lb_Password.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_Password.setBounds(25, 148, 57, 15);
		pn_Login.add(lb_Password);
		
		tf_ID.setBounds(100, 107, 116, 21);
		pn_Login.add(tf_ID);
		tf_ID.setColumns(10);
		
		pf_Password.setBounds(100, 146, 116, 21);
		pn_Login.add(pf_Password);
		
		bt_Join.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Join.setBounds(16, 186, 97, 23);
		pn_Login.add(bt_Join);
		
		bt_Login.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Login.setBounds(125, 186, 97, 23);
		pn_Login.add(bt_Login);
		
		JLabel lb_Login = new JLabel("·Î±×ÀÎ");
		lb_Login.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 16));
		lb_Login.setBounds(93, 57, 57, 34);
		pn_Login.add(lb_Login);
		
		setTitle("ÅØ½ºÆ® ¿¡µðÅÍ");
		setResizable(false);
		setBounds(100, 100, 244, 249);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	class LoginImagePanel extends JPanel{
		ImageIcon im = new ImageIcon("LoginImage.png");
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
	
}
public class Login {
	public static void main(String[] args) {
		new LoginUI();
	}
}
