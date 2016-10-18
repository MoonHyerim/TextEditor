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
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


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
	
	// _2016-10-18_HYERIM_단어 검색
	private void wordSearch(){
			
			ArrayList<Integer> wordIndex = new ArrayList<Integer>(); //문자 위치 저장할 리스트
			int pos = content.indexOf(sWord); // 검색할 단어의 시작 위치?
			
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
			
			
			/*// 하이라이트 처리부
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
		String tmp_content;
		if(upLowerFlag == false) // 대소문자 구분 안함
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
			upLowerFlag = cb_UpLower.isSelected(); // 선택했을시 true, 안했을시 false
					
			if(e.getSource() == bt_Search){ // 다음찾기
				wordSearch();
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
