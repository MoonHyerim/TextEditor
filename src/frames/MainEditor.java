package frames;

import java.awt.Component;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class MainEditor extends JFrame {
	private JPanel contentPane;
	public static String imgPath = "icons\\";
	private String fileName;
	
	class MyKeyDispatcher implements KeyEventDispatcher {
		
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO 새로운 파일
				// 파일 내용의 변경이 있는지 여부에 따라
				// 1. 변경이 있는 경우, 파일을 저장할 지 여부 확인
				// 2. 변경이 없는 경우, 빈 화면 출력
			}
			if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO 저장하기
				// fileName에 경로가 저장되었는지 여부에 따라
				// 1. 처음 저장하는 경우 (file chooser 사용해서 저장하도록..)
				// 2. 저장이 되어 있는 경우 (fileName에 덮어씌우도록..)
			}
			if(e.getKeyCode() == KeyEvent.VK_O && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// 내 컴퓨터에서 파일 열기
				JTextPane textPane = getTextPaneFromMainFrame();
				openFileFromChooser(textPane);
			}
			if(e.getKeyCode() == KeyEvent.VK_I && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO DB에서 파일 열기
				new TextList();
			}
			if(e.getKeyCode() == KeyEvent.VK_Q && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// 프로그램 종료
				// TODO 파일 내용에 변경 사항이 있는지 확인
				showDialogToCloseMainFrame();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_T && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// 선택한 영역 대소문자 변환 (알파벳만 지원)
				JTextPane textPane = getTextPaneFromMainFrame();
				toUpperOrLowerCaseOnSelectedText(textPane);
			}
			if(e.getKeyCode() == KeyEvent.VK_F && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO 문자열 찾기/바꾸기
				new TextSearch();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_L && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO 로그인/아웃
				// 로그인/아웃의 경우에 따라 로그인 화면, 로그아웃 다이얼로그 화면 출력
				new Login();
			}
			
			return false;
		}
	}
	
	public MainEditor() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 465, 21);
		contentPane.add(menuBar);
		
		// 툴바
		JPanel panel = new JPanel();
		panel.setBounds(0, 20, 465, 31);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(MainEditor.imgPath+"file.png"));
		btnNewButton.setBounds(5, 3, 27, 24);
		btnNewButton.setToolTipText("새로만들기(Ctrl+N)");
		panel.add(btnNewButton);
		
		JButton openButton1 = new JButton("");
		openButton1.setIcon(new ImageIcon(MainEditor.imgPath+"folder.png"));
		openButton1.setToolTipText("내컴퓨터에서 불러오기(Ctrl+O)");
		openButton1.setBounds(34, 3, 27, 24);
		panel.add(openButton1);

		JButton openButton2 = new JButton("");
		openButton2.setIcon(new ImageIcon(MainEditor.imgPath+"import.png"));
		openButton2.setToolTipText("클라우드에서 불러오기(Ctrl+I)");
		openButton2.setBounds(63, 3, 27, 24);
		panel.add(openButton2);
		
		JButton saveButton = new JButton("");
		saveButton.setIcon(new ImageIcon(MainEditor.imgPath+"save.png"));
		saveButton.setBounds(91, 3, 27, 24);
		saveButton.setToolTipText("저장하기(Ctrl+S)");
		panel.add(saveButton);
		
		JButton saveAsButton = new JButton("");
		saveAsButton.setIcon(new ImageIcon(MainEditor.imgPath+"save-1.png"));
		saveAsButton.setBounds(120, 3, 27, 24);
		saveAsButton.setToolTipText("다른 이름으로 저장");
		panel.add(saveAsButton);

		JComboBox fontComboBox = new JComboBox();
		fontComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		fontComboBox.setBounds(153, 3, 106, 24);
		fontComboBox.setToolTipText("글꼴");
		panel.add(fontComboBox);
		fontComboBox.setModel(new DefaultComboBoxModel(new String[] {"맑은 고딕"}));
		
		JComboBox fontSizeComboBox = new JComboBox();
		fontSizeComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		fontSizeComboBox.setBounds(261, 3, 47, 24);
		fontSizeComboBox.setToolTipText("폰트 크기");
		panel.add(fontSizeComboBox);
		fontSizeComboBox.setModel(new DefaultComboBoxModel(new String[] {"10", "11", "12"}));
		
		JButton leftAlignButton = new JButton("");
		leftAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"left-align.png"));
		leftAlignButton.setBounds(314, 3, 27, 24);
		leftAlignButton.setToolTipText("왼쪽정렬");
		panel.add(leftAlignButton);
		
		JButton centerAlignButton = new JButton("");
		centerAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"center-align.png"));
		centerAlignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		centerAlignButton.setBounds(343, 3, 27, 24);
		centerAlignButton.setToolTipText("가운데정렬");
		panel.add(centerAlignButton);
		
		JButton rightAlignButton = new JButton("");
		rightAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"right-align.png"));
		rightAlignButton.setBounds(372, 3, 27, 24);
		rightAlignButton.setToolTipText("오른쪽정렬");
		panel.add(rightAlignButton);
		// end of 툴바
		
		// 에디터 영역
		JTextPane textPane = new JTextPane();
		Document doc = textPane.getDocument();
		// 텍스트 영역 ctrl+z 이벤트 바인딩
		UndoManager undo = new UndoManager();
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undo.addEdit(e.getEdit());
			}
		});
		textPane.getActionMap().put("Undo", new AbstractAction("Undo") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(undo.canUndo())
						undo.undo();
				} catch(CannotUndoException ex) {
				}
			}
		});
		textPane.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
		
		JScrollPane textAreaScrollPane = new JScrollPane(textPane);
		textAreaScrollPane.setBounds(6, 49, 453, 307);
		contentPane.add(textAreaScrollPane);
		
		// 메뉴
		// 파일 메뉴
		JMenu mnFile = new JMenu("파일");
		mnFile.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenuItem mitem_new = new JMenuItem(getMenuItemName("새로만들기", "Ctrl+N"));
		mitem_new.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		JMenu mnOpen = new JMenu("불러오기");
		mnOpen.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_openFromFileSystem = new JMenuItem(getMenuItemName("내컴퓨터", "Ctrl+O"));
		mitem_openFromFileSystem.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_openFromDB = new JMenuItem(getMenuItemName("클라우드", "Ctrl+I"));
		mitem_openFromDB.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		mnOpen.add(mitem_openFromFileSystem);
		mnOpen.add(mitem_openFromDB);
		JMenuItem mitem_save = new JMenuItem(getMenuItemName("저장하기", "Ctrl+S"));
		mitem_save.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_saveAs = new JMenuItem(getMenuItemName("다른 이름으로 저장", ""));
		mitem_saveAs.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		JMenuItem mitem_exit = new JMenuItem(getMenuItemName("종료", "Ctrl+Q"));
		mitem_exit.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		mnFile.add(mitem_new);
		mnFile.add(mnOpen);
		mnFile.add(mitem_save);
		mnFile.add(mitem_saveAs);
		mnFile.addSeparator();
		mnFile.add(mitem_exit);
		
		// 편집 메뉴
		JMenu mnEdit = new JMenu("편집");
		mnEdit.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		menuBar.add(mnEdit);
		
		JMenuItem mitem_undo = new JMenuItem(getMenuItemName("실행취소", "Ctrl+Z"));
		mitem_undo.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		// textArea에 바인딩해서 저장한 키 이벤트 액션 가져와서 취소하기 버튼 액션리스너에 할당
		mitem_undo.addActionListener(textPane.getActionForKeyStroke(KeyStroke.getKeyStroke("control Z")));
		
		JMenuItem mitem_cut = new JMenuItem();
		mitem_cut.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		mitem_cut.setAction(new DefaultEditorKit.CutAction());
		mitem_cut.setText(getMenuItemName("잘라내기", "Ctrl+X"));
		
		JMenuItem mitem_copy = new JMenuItem();
		mitem_copy.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		mitem_copy.setAction(new DefaultEditorKit.CopyAction());
		mitem_copy.setText(getMenuItemName("복사하기", "Ctrl+C"));
		
		JMenuItem mitem_paste = new JMenuItem();
		mitem_paste.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		mitem_paste.setAction(new DefaultEditorKit.PasteAction());
		mitem_paste.setText(getMenuItemName("붙여넣기", "Ctrl+V"));
		
		JMenuItem mitem_find = new JMenuItem(getMenuItemName("찾기/바꾸기", "Ctrl+F"));
		mitem_find.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_transformul = new JMenuItem(getMenuItemName("대/소문자변환", "Ctrl+T"));
		mitem_transformul.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_selectall = new JMenuItem(getMenuItemName("모두선택", "Ctrl+A"));
		mitem_selectall.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		mnEdit.add(mitem_undo);
		mnEdit.addSeparator();
		mnEdit.add(mitem_cut);
		mnEdit.add(mitem_copy);
		mnEdit.add(mitem_paste);
		mnEdit.addSeparator();
		mnEdit.add(mitem_find);
		mnEdit.add(mitem_transformul);
		mnEdit.addSeparator();
		mnEdit.add(mitem_selectall);
		
		// 로그인 메뉴
		JMenu mnLogin = new JMenu("로그인");
		mnLogin.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		menuBar.add(mnLogin);
		
		JMenuItem mitem_login = new JMenuItem(getMenuItemName("로그인/아웃", "Ctrl+L"));
		mitem_login.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		mnLogin.add(mitem_login);
		
		// 도움말 메뉴
		JMenu mnHelp = new JMenu("도움말");
		mnHelp.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		JMenuItem mitem_shortcut = new JMenuItem(getMenuItemName("단축키보기", ""));
		mitem_shortcut.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		JMenuItem mitem_info = new JMenuItem(getMenuItemName("에디터정보", ""));
		mitem_info.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		mnHelp.add(mitem_shortcut);
		mnHelp.addSeparator();
		mnHelp.add(mitem_info);
		// end of 메뉴
		
		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		keyManager.addKeyEventDispatcher(new MyKeyDispatcher());
		
		setTitle("텍스트 에디터");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 404);
		setVisible(true);
		
	}
	
	private void showDialogToCloseMainFrame() {
		int  check = JOptionPane.showConfirmDialog(MainEditor.this, "프로그램을 종료합니다."
				, "종료", JOptionPane.OK_CANCEL_OPTION
				, JOptionPane.CLOSED_OPTION, null);
		
		if(check == JOptionPane.OK_OPTION)
			System.exit(0);
	}
	
	private String getMenuItemName(String name, String shortCut) {
		if(name.length() > 10)
			name = name.substring(0, 10);
		
		int size = 0;
		int charToUni = 0;
		for(int i=0; i<name.length(); i++) {
			charToUni = name.charAt(i);
			// 한글 유니코드 범위: 44032 ~ 55203
			if(charToUni >= 44032 && charToUni <= 55203)
				size += 2;
		}
		size = 30 - size;
		return String.format("%-"+size+"s%s", name, shortCut);
	}
	
	private JTextPane getTextPaneFromMainFrame() {
		MainEditor mainFrame = MainEditor.this;
		Component[] components = mainFrame.getContentPane().getComponents();
		JTextPane textPane = null;
		for(int i=0; i<components.length; i++) {
			if(components[i] instanceof JScrollPane) {
				JViewport viewPort = ((JScrollPane)components[i]).getViewport();
				textPane = (JTextPane)viewPort.getView();
			}
		}
		return textPane;
	}
	
	private void toUpperOrLowerCaseOnSelectedText(JTextPane textPane) {
		String selectedText = textPane.getSelectedText();
		Caret caret = textPane.getCaret();
		int startPoint = caret.getDot() > caret.getMark() ? caret.getMark() : caret.getDot();
		String changedText = "";
		if(selectedText != null) {
			int i = (int)selectedText.charAt(0);
			if(i >= 97 && i <= 122) {
				changedText = selectedText.toUpperCase();
				try {
					textPane.getDocument().remove(startPoint, changedText.length());
					textPane.getDocument().insertString(startPoint, changedText, null);
				} catch (BadLocationException e1) {
				}
			} else if(i >= 65 && i <= 90) {
				changedText = selectedText.toLowerCase();
				try {
					textPane.getDocument().remove(startPoint, changedText.length());
					textPane.getDocument().insertString(startPoint, changedText, null);
				} catch (BadLocationException e1) {
				}
			}
		}
	}
	
	private void openFileFromChooser(JTextPane textPane) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
		chooser.addChoosableFileFilter(filter);
		
		int check = chooser.showOpenDialog(null);
		if(check == JFileChooser.APPROVE_OPTION) {
			fileName = chooser.getSelectedFile().getAbsolutePath();
			// 에디터에 파일 내용 출력
			try {
				FileReader fr = new FileReader(fileName);
				Scanner in = new Scanner(fr);
				String contents = "";
				while(in.hasNext()) {
					contents += in.nextLine()+"\r\n";
				}
				textPane.setText(contents);
				
				in.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(getParent(), "해당하는 파일이 없습니다.\n:"+fileName);
			}
		}
	}
}
