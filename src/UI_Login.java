import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;


public class UI_Login extends JFrame {

	private JPanel contentPane;
	private JTextField tf_ID;
	private JPasswordField pf_Password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_Login frame = new UI_Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI_Login() {
		setTitle("\uD14D\uC2A4\uD2B8 \uC5D0\uB514\uD130");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 249);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lb_ID = new JLabel("\uC544\uC774\uB514");
		lb_ID.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_ID.setBounds(25, 109, 57, 15);
		contentPane.add(lb_ID);
		
		JLabel lb_Password = new JLabel("\uD328\uC2A4\uC6CC\uB4DC");
		lb_Password.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_Password.setBounds(25, 148, 57, 15);
		contentPane.add(lb_Password);
		
		tf_ID = new JTextField();
		tf_ID.setBounds(100, 107, 116, 21);
		contentPane.add(tf_ID);
		tf_ID.setColumns(10);
		
		pf_Password = new JPasswordField();
		pf_Password.setBounds(100, 146, 116, 21);
		contentPane.add(pf_Password);
		
		JButton bt_Join = new JButton("\uD68C\uC6D0\uAC00\uC785");
		bt_Join.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Join.setBounds(16, 186, 97, 23);
		contentPane.add(bt_Join);
		
		JButton bt_Login = new JButton("\uB85C\uADF8\uC778");
		bt_Login.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Login.setBounds(125, 186, 97, 23);
		contentPane.add(bt_Login);
		
		JLabel lb_Login = new JLabel("\uB85C\uADF8\uC778");
		lb_Login.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 16));
		lb_Login.setBounds(93, 57, 57, 34);
		contentPane.add(lb_Login);
		
		JPanel panel = new JPanel();
		panel.setBounds(93, 12, 46, 50);
		contentPane.add(panel);
	}
}
