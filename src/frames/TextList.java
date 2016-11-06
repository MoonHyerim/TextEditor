package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import VO.Write;


public class TextList extends JFrame implements WindowListener{
	JPanel pn_TextList = new JPanel();
	JScrollPane scrollPane = new JScrollPane();
	JTable tb_TextList;
	
	private String userid;
	private DBConn conn;
	private Write write;
	private ArrayList<Write> writeList;
	private DefaultTableModel model;
	private MainEditor maineditor;
	
	public TextList(String userid, MainEditor maineditor){		
		//_HYERIM_������ ���� ����
		this.userid = userid;
		this.maineditor = maineditor;
		
		setDBToArrayList(); 
		InitComponent();
		
		setTitle("�ؽ�Ʈ ������");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 431, 291);
		setVisible(true);
	}
	
	private void InitComponent(){
		pn_TextList.setBackground(Color.white);
		pn_TextList.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_TextList);
		pn_TextList.setLayout(null);
		
		JButton bt_Write = new JButton("�����ۼ�");
		bt_Write.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Write.setBounds(325, 6, 97, 23);
				
		JButton bt_Delete = new JButton("���û���");
		bt_Delete.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Delete.setBounds(219, 6, 97, 23);
					
		JButton bt_Open = new JButton("Ȯ��");
		bt_Open.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Open.setBounds(325, 235, 97, 23);
		
		setTextList(); //_HYERIM_TextList -> Table
		
		scrollPane.setBounds(6, 36, 416, 194);
		scrollPane.setViewportView(tb_TextList);
		
		pn_TextList.add(bt_Delete);
		pn_TextList.add(bt_Write);
		pn_TextList.add(scrollPane);
		pn_TextList.add(bt_Open);
		
		//_HYERIM_������ �� ����
		bt_Delete.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tb_TextList.getSelectedRow();
				if(row != -1){
					String tmp =tb_TextList.getValueAt(row, 0).toString();
					long id = Long.parseLong(tmp);
					int check = JOptionPane.showConfirmDialog(getParent(), "�ش� ������ �����Ͻðڽ��ϱ�?", 
																					"���� ����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
					if(check == JOptionPane.YES_OPTION){
						int result = conn.deleteWrite(id); // DB���� ������ ����
						if(result == 1)
							((DefaultTableModel)tb_TextList.getModel()).removeRow(row); // ���̺��� ������ ����
					}
				}else{
					JOptionPane.showMessageDialog(getParent(), "������ ������ �������ּ���.","���� ����",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
					
		//_HYERIM_����Ʈ���� ������ �� ����
		bt_Open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tb_TextList.getSelectedRow(); 
				if(row != -1){
					String id = tb_TextList.getValueAt(row, 0).toString(); // ������ ���� ���̵� 
					for(int i=0; i<writeList.size(); i++){
						if(Long.toString(writeList.get(i).getId()).equals(id)){ 
							MainEditor.textPane.setText(writeList.get(i).getContent()); // text�гο� ����Ѹ���
							maineditor.setTitle(writeList.get(i).getTitle()); // text�г� ���� ����
							maineditor.setWriteInfo(writeList.get(i).getId(), writeList.get(i).getTitle(), writeList.get(i).getContent());
							isDbConnClose();
							dispose(); // ���� ������ ����
						}
					}
				}else
					JOptionPane.showMessageDialog(getParent(), "�ҷ��� ������ �������ּ���.","���� ����",JOptionPane.INFORMATION_MESSAGE);	
			}
		});
		
		//_HYERIM_���ο� â�� �۾���
		bt_Write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maineditor.openNewFile();	
				isDbConnClose();
				dispose();
			}
		});
	}
	
	//_HYERIM_DB�����͸� ArrayList�� ��Ƶ�
	private void setDBToArrayList(){
		conn = new DBConn();
		this.writeList = conn.getWrites(userid); 
	}
	
	//_HYERIM_Ŭ���忡 ����� �����͸� ���̺�� ������
	private void setTextList(){
		String[] colName = {"����", "����", "��¥"};
		int writeListCnt = writeList.size();
		String[][] rowData= new String[writeListCnt][3];
		
		for(int i=0; i<writeListCnt; i++){
			rowData[i][0] = Long.toString(writeList.get(i).getId());
			rowData[i][1] = writeList.get(i).getTitle();
			rowData[i][2] = writeList.get(i).getModified().toString();
		}
		model = new DefaultTableModel(rowData, colName){
			//_HYERIM_ ���̺� ���� ���� ���ϵ��� ��
			@ Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tb_TextList = new JTable(model);
		tb_TextList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.getColumnModel().getColumn(0).setPreferredWidth(38);
		tb_TextList.getColumnModel().getColumn(0).setPreferredWidth(38);
	}
	
	private void isDbConnClose(){
		try{
			if(conn.c.isClosed() == false){
				conn.CloseDBConn(conn.c);	
			}
		}catch(Exception ex){
			System.out.println("DB ���� ���� ����");
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		isDbConnClose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
}
