package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
	private String content; // _2016-10-18_HYERIM_텍스트 글자 읽어옴
	private String sWord; // _2016-10-18_HYERIM_검색할 글자
	private String cWord; // _2016-10-18_HYERIM_바꿀 글자	
	private boolean upLowerFlag; // _2016-10-18_HYERIM_대소문자 체크박스 플래그
	
	public TextSearch(){
		InitComponent();	// 컴포넌트 초기화
		
		setResizable(false);
		setTitle("텍스트 에디터");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 199);
		setVisible(true);
	}
	
	private void InitComponent(){
		pn_TextSearch.setBackground(Color.white);
		pn_TextSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn_TextSearch);
		pn_TextSearch.setLayout(null);
		
		lb_SearchText = new JLabel("찾을 내용");
		lb_SearchText.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_SearchText.setBounds(12, 19, 57, 15);
		pn_TextSearch.add(lb_SearchText);
		
		lb_ChangeText = new JLabel("바꿀 내용");
		lb_ChangeText.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lb_ChangeText.setBounds(12, 58, 57, 15);
		pn_TextSearch.add(lb_ChangeText);
		
		tf_Search = new JTextField();
		tf_Search.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		tf_Search.setBounds(81, 18, 190, 21);
		pn_TextSearch.add(tf_Search);
		tf_Search.setColumns(10);
		
		tf_Change = new JTextField();
		tf_Change.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		tf_Change.setColumns(10);
		tf_Change.setBounds(81, 57, 190, 21);
		pn_TextSearch.add(tf_Change);
		
		bt_Search = new JButton("다음찾기");
		bt_Search.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Search.setBounds(283, 17, 97, 23);
		bt_Search.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Search);
				
		bt_Change = new JButton("바꾸기");
		bt_Change.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Change.setBounds(283, 56, 97, 23);
		bt_Change.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Change);
		
		bt_AllChagne = new JButton("모두바꾸기");
		bt_AllChagne.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_AllChagne.setBounds(283, 95, 97, 23);
		bt_AllChagne.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_AllChagne);
		
		bt_Cancel = new JButton("취소");
		bt_Cancel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		bt_Cancel.setBounds(283, 134, 97, 23);
		bt_Cancel.addActionListener(new TextSearchListener());
		pn_TextSearch.add(bt_Cancel);
		
		cb_UpLower = new JCheckBox("대/소문자 구분");
		cb_UpLower.setBackground(Color.white);
		cb_UpLower.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		cb_UpLower.setBounds(12, 134, 115, 23);
		cb_UpLower.setSelected(true); //_HYERIM_2016-10-18_디폴트 대소문자 구분
		pn_TextSearch.add(cb_UpLower);
	}
	
	private void FindWord(){	
		String findStr = tf_Search.getText();
		Document doc = MainEditor.textPane.getDocument();

		int pos = MainEditor.textPane.getCaretPosition();
		int wordLength = findStr.length(); // word length
		
		pos = FindWordProcess(pos, wordLength, findStr , doc);
		
		if(pos>=0)
			setFoundWordHightlight(pos, pos+findStr.length());
	}
	
	private void setFoundWordHightlight(int startpos , int endpos){
		MainEditor.textPane.setSelectionStart(startpos);
		MainEditor.textPane.setSelectionEnd(endpos);
		MainEditor.textPane.getCaret().setSelectionVisible(true);
	}
	
	private int FindWordProcess(int startpos , int wordLength, String findStr , Document doc){
		int docLength = doc.getLength();
		try {
			// 커서가 맨 끝에 있을때
			if(startpos >= docLength)
				return -1;
			
			while (startpos + wordLength <= docLength) {
				String strFound;
				
				// 대소문자 구분
				if(upLowerFlag==true)
					strFound = doc.getText(startpos, wordLength); 
				else{
					strFound = doc.getText(startpos, wordLength).toLowerCase();
					findStr = findStr.toLowerCase();
				}

				if (strFound.equals(findStr)) {
					return startpos;
				}
				
				startpos++;
			}
        } catch (BadLocationException e) {
			e.printStackTrace();
		}
		return -1;	
	}
	
	// _HYERIM_2016-10-18_모두바꾸기
	private void ChangeAllText(){
		String tmp_content;
		if(upLowerFlag == false) // 대소문자 구분 안함
			tmp_content = content.replaceAll("(?i)"+sWord, cWord);
		else
			tmp_content = content.replaceAll(sWord, cWord);
		MainEditor.textPane.setText(tmp_content);
	}
	
	// _HYERIM_2016-10-18_바꾸기
	private void ChangeText(){
		Document doc = MainEditor.textPane.getDocument();
		int findStrLength = sWord.length();
		int pos = MainEditor.textPane.getCaretPosition();
		int wordLength = sWord.length(); // word length
		
		pos = FindWordProcess(pos, wordLength, sWord , doc);
		
		if(pos>=0){
			String tmpContent = content.substring(0, pos);
			content = content.substring(pos, content.length());
			if(upLowerFlag == false)
				content = content.replaceFirst("(?i)"+sWord, cWord);
			else
				content = content.replaceFirst(sWord, cWord);
			
			MainEditor.textPane.setText(tmpContent + content);
			MainEditor.textPane.setCaretPosition(pos+cWord.length());	
		}
	}
	
	class TextSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			content = MainEditor.textPane.getText();
			sWord = tf_Search.getText();
			cWord = tf_Change.getText(); 
			upLowerFlag = cb_UpLower.isSelected(); // 선택했을시 true, 안했을시 false
					
			if(e.getSource() == bt_Search){ // 다음찾기
				FindWord();
			}else if(e.getSource() == bt_AllChagne){ //모두 바꾸기
				ChangeAllText();
			}else if(e.getSource() == bt_Change){ // 바꾸기
				ChangeText();
			}else if(e.getSource() == bt_Cancel){ // 취소
				dispose(); 
			}
		}
	}
}
