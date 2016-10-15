import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


class TextListUI extends JFrame{
	JPanel pn_TextList = new JPanel();
	JButton bt_Delete = new JButton("새글작성");
	JButton bt_Write = new JButton("선택삭제");
	JScrollPane scrollPane = new JScrollPane();
	JTable tb_TextList = new JTable();
	JButton bt_Open = new JButton("확인");
	public TextListUI(){		
		pn_TextList.setBackground(Color.white);
		pn_TextList.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_TextList);
		pn_TextList.setLayout(null);
		
		bt_Delete.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Delete.setBounds(325, 6, 97, 23);
		pn_TextList.add(bt_Delete);
		
		bt_Write.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Write.setBounds(219, 6, 97, 23);
		pn_TextList.add(bt_Write);
		
		
		scrollPane.setBounds(6, 36, 416, 194);
		pn_TextList.add(scrollPane);
		
		
		tb_TextList.setModel(new DefaultTableModel(
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
				"제목", "날짜"
			}
		));
		scrollPane.setViewportView(tb_TextList);
		
		bt_Open.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Open.setBounds(325, 235, 97, 23);
		pn_TextList.add(bt_Open);
		
		setTitle("텍스트 에디터");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 431, 291);
		setVisible(true);
	}
}
public class TextList {
	public static void main(String[] args) {
		new TextListUI();
	}
}
