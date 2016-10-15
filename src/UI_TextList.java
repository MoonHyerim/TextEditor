import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;


public class UI_TextList extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_TextList frame = new UI_TextList();
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
	public UI_TextList() {
		setResizable(false);
		setTitle("\uD14D\uC2A4\uD2B8 \uC5D0\uB514\uD130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 431, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton bt_Delete = new JButton("\uC120\uD0DD\uC0AD\uC81C");
		bt_Delete.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Delete.setBounds(325, 6, 97, 23);
		contentPane.add(bt_Delete);
		
		JButton bt_Write = new JButton("\uC0C8\uAE00\uC791\uC131");
		bt_Write.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Write.setBounds(219, 6, 97, 23);
		contentPane.add(bt_Write);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 36, 416, 194);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"\uC81C\uBAA9", "\uC791\uC131\uC77C\uC790"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton bt_Open = new JButton("\uD655\uC778");
		bt_Open.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		bt_Open.setBounds(325, 235, 97, 23);
		contentPane.add(bt_Open);
	}
}
