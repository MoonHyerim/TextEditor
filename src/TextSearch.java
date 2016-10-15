import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


class TextSearchUI extends JFrame{
	JPanel pn_TextSearch = new JPanel();
	JLabel lb_SearchText = new JLabel("ã�� ����");
	JLabel lb_ChangeText = new JLabel("�ٲ� ����");
	JTextField tf_Search = new JTextField();
	JTextField tf_Change = new JTextField();
	JButton bt_Search = new JButton("����ã��");
	JButton bt_Change = new JButton("�ٲٱ�");
	JButton bt_AllChagne = new JButton("��ιٲٱ�");
	JButton bt_Cancel = new JButton("���");
	JCheckBox cb_UpLower = new JCheckBox("��/�ҹ��� ����");
	
	public TextSearchUI(){
		pn_TextSearch.setBackground(Color.white);
		pn_TextSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_TextSearch);
		pn_TextSearch.setLayout(null);
		
		lb_SearchText.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_SearchText.setBounds(12, 19, 57, 15);
		pn_TextSearch.add(lb_SearchText);
		
		lb_ChangeText.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_ChangeText.setBounds(12, 58, 57, 15);
		pn_TextSearch.add(lb_ChangeText);
		
		tf_Search.setFont(new Font("���� ���", Font.PLAIN, 12));
		tf_Search.setBounds(81, 18, 190, 21);
		pn_TextSearch.add(tf_Search);
		tf_Search.setColumns(10);
		
		tf_Change.setFont(new Font("���� ���", Font.PLAIN, 12));
		tf_Change.setColumns(10);
		tf_Change.setBounds(81, 57, 190, 21);
		pn_TextSearch.add(tf_Change);
		
		bt_Search.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Search.setBounds(283, 17, 97, 23);
		pn_TextSearch.add(bt_Search);
		
		bt_Change.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Change.setBounds(283, 56, 97, 23);
		pn_TextSearch.add(bt_Change);
		
		bt_AllChagne.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_AllChagne.setBounds(283, 95, 97, 23);
		pn_TextSearch.add(bt_AllChagne);
		
		bt_Cancel.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Cancel.setBounds(283, 134, 97, 23);
		pn_TextSearch.add(bt_Cancel);
		
		cb_UpLower.setBackground(Color.white);
		cb_UpLower.setFont(new Font("���� ���", Font.PLAIN, 12));
		cb_UpLower.setBounds(12, 134, 115, 23);
		pn_TextSearch.add(cb_UpLower);
		
		setResizable(false);
		setTitle("\uD14D\uC2A4\uD2B8 \uC5D0\uB514\uD130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 199);
		setVisible(true);
	}
}
public class TextSearch {
	public static void main(String[] args) {
		new TextSearchUI();
	}
}
