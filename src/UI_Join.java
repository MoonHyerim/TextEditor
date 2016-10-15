import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;


public class UI_Join extends JFrame {

	private JPanel contentPane;
	private JTextField tf_JoinID;
	private JPasswordField pf_JoinPW;
	private JPasswordField pf_JoinPW2;
	private JTextField tf_JoinName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_Join frame = new UI_Join();
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
	public UI_Join() {
		setResizable(false);
		setTitle("\uD14D\uC2A4\uD2B8 \uC5D0\uB514\uD130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 252, 342);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lb_Join = new JLabel("\uD68C\uC6D0\uAC00\uC785");
		lb_Join.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 16));
		lb_Join.setBounds(90, 50, 75, 28);
		contentPane.add(lb_Join);
		
		JLabel lb_ID = new JLabel("\uC544\uC774\uB514");
		lb_ID.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_ID.setBounds(25, 93, 57, 15);
		contentPane.add(lb_ID);
		
		JLabel lb_PW = new JLabel("\uD328\uC2A4\uC6CC\uB4DC");
		lb_PW.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_PW.setBounds(25, 131, 57, 15);
		contentPane.add(lb_PW);
		
		JLabel lb_PW2 = new JLabel("\uD328\uC2A4\uC6CC\uB4DC\uD655\uC778");
		lb_PW2.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_PW2.setBounds(25, 169, 86, 15);
		contentPane.add(lb_PW2);
		
		JLabel lb_Name = new JLabel("\uC774\uB984");
		lb_Name.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_Name.setBounds(25, 204, 57, 15);
		contentPane.add(lb_Name);
		
		JLabel lb_pwcheck = new JLabel("\uBE44\uBC00\uBC88\uD638 \uD655\uC778 \uC644\uB8CC");
		lb_pwcheck.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pwcheck.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_pwcheck.setBounds(25, 243, 199, 15);
		contentPane.add(lb_pwcheck);
		
		JButton btn_Join = new JButton("\uAC00\uC785");
		btn_Join.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		btn_Join.setBounds(25, 280, 82, 23);
		contentPane.add(btn_Join);
		
		JButton btn_cancel = new JButton("\uCDE8\uC18C");
		btn_cancel.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		btn_cancel.setBounds(142, 280, 82, 23);
		contentPane.add(btn_cancel);
		
		tf_JoinID = new JTextField();
		tf_JoinID.setBounds(108, 91, 116, 21);
		contentPane.add(tf_JoinID);
		tf_JoinID.setColumns(10);
		
		pf_JoinPW = new JPasswordField();
		pf_JoinPW.setBounds(108, 129, 116, 21);
		contentPane.add(pf_JoinPW);
		
		pf_JoinPW2 = new JPasswordField();
		pf_JoinPW2.setBounds(108, 167, 116, 21);
		contentPane.add(pf_JoinPW2);
		
		tf_JoinName = new JTextField();
		tf_JoinName.setColumns(10);
		tf_JoinName.setBounds(108, 202, 116, 21);
		contentPane.add(tf_JoinName);
		
		JPanel panel = new JPanel();
		panel.setBounds(97, 10, 50, 43);
		contentPane.add(panel);
	}

}
