import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Font;


public class UI_TextSearch extends JFrame {

	private JPanel contentPane;
	private JTextField tf_Search;
	private JTextField tf_Change;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_TextSearch frame = new UI_TextSearch();
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
	public UI_TextSearch() {
		setResizable(false);
		setTitle("\uD14D\uC2A4\uD2B8 \uC5D0\uB514\uD130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 199);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lb_SearchText = new JLabel("\uCC3E\uC744 \uB0B4\uC6A9");
		lb_SearchText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_SearchText.setBounds(12, 19, 57, 15);
		contentPane.add(lb_SearchText);
		
		JLabel lb_ChangeText = new JLabel("\uBC14\uAFC0 \uB0B4\uC6A9");
		lb_ChangeText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lb_ChangeText.setBounds(12, 58, 57, 15);
		contentPane.add(lb_ChangeText);
		
		tf_Search = new JTextField();
		tf_Search.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		tf_Search.setBounds(81, 18, 190, 21);
		contentPane.add(tf_Search);
		tf_Search.setColumns(10);
		
		tf_Change = new JTextField();
		tf_Change.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		tf_Change.setColumns(10);
		tf_Change.setBounds(81, 57, 190, 21);
		contentPane.add(tf_Change);
		
		JButton bt_Search = new JButton("\uB2E4\uC74C\uCC3E\uAE30");
		bt_Search.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Search.setBounds(283, 17, 97, 23);
		contentPane.add(bt_Search);
		
		JButton bt_Change = new JButton("\uBC14\uAFB8\uAE30");
		bt_Change.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Change.setBounds(283, 56, 97, 23);
		contentPane.add(bt_Change);
		
		JButton bt_AllChagne = new JButton("\uBAA8\uB450 \uBC14\uAFB8\uAE30");
		bt_AllChagne.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_AllChagne.setBounds(283, 95, 97, 23);
		contentPane.add(bt_AllChagne);
		
		JButton bt_Cancel = new JButton("\uCDE8\uC18C");
		bt_Cancel.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Cancel.setBounds(283, 134, 97, 23);
		contentPane.add(bt_Cancel);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("\uB300/\uC18C\uBB38\uC790 \uAD6C\uBD84");
		chckbxNewCheckBox.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		chckbxNewCheckBox.setBounds(12, 134, 115, 23);
		contentPane.add(chckbxNewCheckBox);
	}
}
