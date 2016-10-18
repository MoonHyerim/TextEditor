package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.Position;


public class TextSearch extends JFrame{
	JPanel pn_TextSearch = new JPanel();
	private JLabel lb_SearchText;
	private JLabel lb_ChangeText;
	JTextField tf_Search;
	JTextField tf_Change;
	
	JButton bt_Search;
	JButton bt_Change;
	JButton bt_AllChagne;
	JButton bt_Cancel;
	
	JCheckBox cb_UpLower;
	private String content; // _2016-10-18_HYERIM_�ؽ�Ʈ ���� �о��
	private String sWord; // _2016-10-18_HYERIM_�˻��� ����
	private String cWord; // _2016-10-18_HYERIM_�ٲ� ����	
	private boolean upLowerFlag; // _2016-10-18_HYERIM_��ҹ��� üũ�ڽ� �÷���
	
	public TextSearch(){
		InitComponent();	// ������Ʈ �ʱ�ȭ
		
		setResizable(false);
		setTitle("�ؽ�Ʈ ������");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 199);
		setVisible(true);
	}
	
	private void InitComponent(){
		pn_TextSearch.setBackground(Color.white);
		pn_TextSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_TextSearch);
		pn_TextSearch.setLayout(null);
		
		lb_SearchText = new JLabel("ã�� ����");
		lb_SearchText.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_SearchText.setBounds(12, 19, 57, 15);
		pn_TextSearch.add(lb_SearchText);
		
		lb_ChangeText = new JLabel("�ٲ� ����");
		lb_ChangeText.setFont(new Font("���� ���", Font.PLAIN, 12));
		lb_ChangeText.setBounds(12, 58, 57, 15);
		pn_TextSearch.add(lb_ChangeText);
		
		tf_Search = new JTextField();
		tf_Search.setFont(new Font("���� ���", Font.PLAIN, 12));
		tf_Search.setBounds(81, 18, 190, 21);
		pn_TextSearch.add(tf_Search);
		tf_Search.setColumns(10);
		
		tf_Change = new JTextField();
		tf_Change.setFont(new Font("���� ���", Font.PLAIN, 12));
		tf_Change.setColumns(10);
		tf_Change.setBounds(81, 57, 190, 21);
		pn_TextSearch.add(tf_Change);
		
		bt_Search = new JButton("����ã��");
		bt_Search.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Search.setBounds(283, 17, 97, 23);
		bt_Search.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Search);
				
		bt_Change = new JButton("�ٲٱ�");
		bt_Change.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Change.setBounds(283, 56, 97, 23);
		bt_Change.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Change);
		
		bt_AllChagne = new JButton("��ιٲٱ�");
		bt_AllChagne.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_AllChagne.setBounds(283, 95, 97, 23);
		bt_AllChagne.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_AllChagne);
		
		bt_Cancel = new JButton("���");
		bt_Cancel.setFont(new Font("���� ���", Font.PLAIN, 12));
		bt_Cancel.setBounds(283, 134, 97, 23);
		bt_Cancel.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Cancel);
		
		cb_UpLower = new JCheckBox("��/�ҹ��� ����");
		cb_UpLower.setBackground(Color.white);
		cb_UpLower.setFont(new Font("���� ���", Font.PLAIN, 12));
		cb_UpLower.setBounds(12, 134, 115, 23);
		cb_UpLower.setSelected(true); //_HYERIM_2016-10-18_����Ʈ ��ҹ��� ����
		pn_TextSearch.add(cb_UpLower);
	}
	
	// _2016-10-18_HYERIM_�ܾ� �˻�
	private void wordSearch(){
			
			ArrayList<Integer> wordIndex = new ArrayList<Integer>(); //���� ��ġ ������ ����Ʈ
			int pos = content.indexOf(sWord); // �˻��� �ܾ��� ���� ��ġ?
			
			while(pos>-1){
				wordIndex.add(pos);
				pos = content.indexOf(sWord, pos+1);
			}
			System.out.println(wordIndex);
			for(int i=0; i<wordIndex.size(); i++){
				int j = wordIndex.get(i);
				String s = content.substring(j, j+cWord.length() );
				System.out.println(s);
			}
			
			
			/*// ���̶���Ʈ ó����
			int p0 = sContent.indexOf(sWord);
			int p1 = p0 + sWord.length();
						
			Highlighter hl = MainEditor.textPane.getHighlighter();
			HighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.PINK);
			try{
				hl.addHighlight(p0, p1, p);
			}catch(Exception e){
				System.out.println("HighLighter Error");
			}
			*/
	}
	
	// Find word function
	private void FindWord(){
		
		String findStr = tf_Search.getText();
		Document doc = MainEditor.textPane.getDocument();
		
		//String strTotalText = MainEditor.textPane.getText(0, doc.getLength());
		
		int pos = MainEditor.textPane.getCaretPosition()-1; // current cursor position

		int wordLength = findStr.length(); // word length

		if (pos + wordLength > doc.getLength()) {
		    pos = 0;
		}
		pos = FindWordProcess(pos, wordLength, findStr , doc);
		if ( pos !=0) {
			setFoundWordHightlight(pos, pos+findStr.length());
		}
	}
	
	private void setFoundWordHightlight(int startpos , int endpos){
		MainEditor.textPane.setSelectionStart(startpos);
		MainEditor.textPane.setSelectionEnd(endpos);
		MainEditor.textPane.getCaret().setSelectionVisible(true);
	}
	
	private int FindWordProcess(int startpos , int wordLength, String findStr , Document doc){
		int docLength = doc.getLength();
		boolean checkTotalDoc = false;
		try {
			while (startpos + wordLength <= docLength) {
				String strFound;
				strFound = doc.getText(startpos, wordLength).toLowerCase();
			
				if (strFound.equals(findStr)) {
					return startpos;
				}
				startpos++;
            
				if(startpos >=doc.getLength() && checkTotalDoc){	// end document check
					startpos =0;
					checkTotalDoc = true;
					return 0;
				}
			}
        } catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	// _HYERIM_2016-10-18_��ιٲٱ�
	private void ChangeAllText(){
		String tmp_content;
		if(upLowerFlag == false) // ��ҹ��� ���� ����
			tmp_content = content.replaceAll("(?i)"+sWord, cWord);
		else
			tmp_content = content.replaceAll(sWord, cWord);
		MainEditor.textPane.setText(tmp_content);
	}
	
	// _HYERIM_2016-10-18_�ٲٱ�
	private void ChangeText(){
		String tmp_content;
		if(upLowerFlag == false) // ��ҹ��� ���� ����
			tmp_content = content.replaceFirst("(?i)"+sWord, cWord);
		else
			tmp_content = content.replaceFirst(sWord, cWord);
		MainEditor.textPane.setText(tmp_content);
	}
	
	class TextSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			content = MainEditor.textPane.getText();
			sWord = tf_Search.getText();
			cWord = tf_Change.getText(); 
			upLowerFlag = cb_UpLower.isSelected(); // ���������� true, �������� false
					
			if(e.getSource() == bt_Search){ // ����ã��
				//wordSearch();
				FindWord();
			}else if(e.getSource() == bt_AllChagne){ //��� �ٲٱ�
				ChangeAllText();
			}else if(e.getSource() == bt_Change){ // �ٲٱ�
				ChangeText();
			}else if(e.getSource() == bt_Cancel){ // ���
				dispose(); 
			}
		}
	}
}
