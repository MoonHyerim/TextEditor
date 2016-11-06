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
		//_HYERIM_생성자 내용 변경
		this.userid = userid;
		this.maineditor = maineditor;
		
		setDBToArrayList(); 
		InitComponent();
		
		setTitle("텍스트 에디터");
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
		
		JButton bt_Write = new JButton("새글작성");
		bt_Write.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Write.setBounds(325, 6, 97, 23);
				
		JButton bt_Delete = new JButton("선택삭제");
		bt_Delete.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Delete.setBounds(219, 6, 97, 23);
					
		JButton bt_Open = new JButton("확인");
		bt_Open.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Open.setBounds(325, 235, 97, 23);
		
		setTextList(); //_HYERIM_TextList -> Table
		
		scrollPane.setBounds(6, 36, 416, 194);
		scrollPane.setViewportView(tb_TextList);
		
		pn_TextList.add(bt_Delete);
		pn_TextList.add(bt_Write);
		pn_TextList.add(scrollPane);
		pn_TextList.add(bt_Open);
		
		//_HYERIM_선택한 열 삭제
		bt_Delete.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tb_TextList.getSelectedRow();
				if(row != -1){
					String tmp =tb_TextList.getValueAt(row, 0).toString();
					long id = Long.parseLong(tmp);
					int check = JOptionPane.showConfirmDialog(getParent(), "해당 파일을 삭제하시겠습니까?", 
																					"파일 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
					if(check == JOptionPane.YES_OPTION){
						int result = conn.deleteWrite(id); // DB에서 데이터 삭제
						if(result == 1)
							((DefaultTableModel)tb_TextList.getModel()).removeRow(row); // 테이블에서 데이터 삭제
					}
				}else{
					JOptionPane.showMessageDialog(getParent(), "삭제할 파일을 선택해주세요.","파일 삭제",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
					
		//_HYERIM_리스트에서 선택한 글 열기
		bt_Open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tb_TextList.getSelectedRow(); 
				if(row != -1){
					String id = tb_TextList.getValueAt(row, 0).toString(); // 선택한 열의 아이디값 
					for(int i=0; i<writeList.size(); i++){
						if(Long.toString(writeList.get(i).getId()).equals(id)){ 
							MainEditor.textPane.setText(writeList.get(i).getContent()); // text패널에 내용뿌리기
							maineditor.setTitle(writeList.get(i).getTitle()); // text패널 제목 변경
							maineditor.setWriteInfo(writeList.get(i).getId(), writeList.get(i).getTitle(), writeList.get(i).getContent());
							isDbConnClose();
							dispose(); // 현재 프레임 종료
						}
					}
				}else
					JOptionPane.showMessageDialog(getParent(), "불러올 파일을 선택해주세요.","파일 열기",JOptionPane.INFORMATION_MESSAGE);	
			}
		});
		
		//_HYERIM_새로운 창에 글쓰기
		bt_Write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maineditor.openNewFile();	
				isDbConnClose();
				dispose();
			}
		});
	}
	
	//_HYERIM_DB데이터를 ArrayList로 담아둠
	private void setDBToArrayList(){
		conn = new DBConn();
		this.writeList = conn.getWrites(userid); 
	}
	
	//_HYERIM_클라우드에 저장된 데이터를 테이블로 보여줌
	private void setTextList(){
		String[] colName = {"순서", "제목", "날짜"};
		int writeListCnt = writeList.size();
		String[][] rowData= new String[writeListCnt][3];
		
		for(int i=0; i<writeListCnt; i++){
			rowData[i][0] = Long.toString(writeList.get(i).getId());
			rowData[i][1] = writeList.get(i).getTitle();
			rowData[i][2] = writeList.get(i).getModified().toString();
		}
		model = new DefaultTableModel(rowData, colName){
			//_HYERIM_ 테이블 내용 수정 못하도록 함
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
			System.out.println("DB 연결 해제 오류");
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
