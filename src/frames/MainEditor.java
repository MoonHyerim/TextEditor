package frames;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import VO.Write;

public class MainEditor extends JFrame {
	private JPanel contentPane;
	public static JTextPane textPane;
	
	public static String imgPath = "icons\\";
	private String fileName;
	private boolean isLoggedIn;
	private boolean isSaved = true;
	//_HYERIM_2016_11_06 ����������â �ѹ��� ����
	private boolean isInfoView = false;
	
	private String userid;
	private String userpw;
	private Write write;
	
	class MyKeyDispatcher implements KeyEventDispatcher {
		
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// ���ο� ���� ����
				openNewFile();
			}
			if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// ���ÿ� ���� 
				saveToLocal();
			}
			if(e.getKeyCode() == KeyEvent.VK_W && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// TODO DB�� ����
				if(isLoggedIn) {
					saveToDB();
				} else{
					new Login(MainEditor.this,"SAVETODB");
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_O && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				// �� ��ǻ�Ϳ��� ���� ����
				openFileFromChooser();
			}
			if(e.getKeyCode() == KeyEvent.VK_I && e.isControlDown()  && e.getID() == KeyEvent.KEY_PRESSED) {
				if(isLoggedIn)
					new TextList(userid, MainEditor.this);
				else
					new Login(MainEditor.this, "TEXTLIST");
			}
			if(e.getKeyCode() == KeyEvent.VK_Q && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// ���α׷� ����
				closeMainFrame();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_T && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// ������ ���� ��ҹ��� ��ȯ (���ĺ��� ����)
				toUpperOrLowerCaseOnSelectedText();
			}
			if(e.getKeyCode() == KeyEvent.VK_F && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// ���ڿ� ã��/�ٲٱ�
				new TextSearch();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_L && e.isControlDown() && e.getID() == KeyEvent.KEY_PRESSED) {
				// �α���/�ƿ� _HYERIM_
				if(isLoggedIn) {
					// �α׾ƿ� ���̾�α�
					showDialogToLogout();
					
				} else {
					// �α��� ȭ��
					new Login(MainEditor.this, "SAVETODB");
				}
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
		
		// ����
		JPanel panel = new JPanel();
		panel.setBounds(0, 20, 465, 31);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(MainEditor.imgPath+"file.png"));
		btnNewButton.setBounds(5, 3, 27, 24);
		btnNewButton.setToolTipText("���θ����(Ctrl+N)");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openNewFile();
			}
		});
		panel.add(btnNewButton);
		
		JButton openButton1 = new JButton("");
		openButton1.setIcon(new ImageIcon(MainEditor.imgPath+"folder.png"));
		openButton1.setToolTipText("����ǻ�Ϳ��� �ҷ�����(Ctrl+O)");
		openButton1.setBounds(34, 3, 27, 24);
		openButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileFromChooser();
			}
		});
		panel.add(openButton1);

		JButton openButton2 = new JButton("");
		openButton2.setIcon(new ImageIcon(MainEditor.imgPath+"import.png"));
		openButton2.setToolTipText("Ŭ���忡�� �ҷ�����(Ctrl+I)");
		openButton2.setBounds(63, 3, 27, 24);
		openButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//_HYERIM_
				if(isLoggedIn)
					new TextList(userid, MainEditor.this);
				else
					new Login(MainEditor.this, "TEXTLIST");
			}
		});
		panel.add(openButton2);
		
		JButton saveToDBButton = new JButton("");
		saveToDBButton.setIcon(new ImageIcon(MainEditor.imgPath+"export.png"));
		saveToDBButton.setBounds(92, 3, 27, 24);
		saveToDBButton.setToolTipText("Ŭ���忡 �����ϱ�(Ctrl+W)");
		saveToDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isLoggedIn) {
					saveToDB();
				} else{
					//_HYERIM_2016-10-20_
					new Login(MainEditor.this, "SAVETODB");
				}
			}
		});
		panel.add(saveToDBButton);
		
		JButton saveButton = new JButton("");
		saveButton.setIcon(new ImageIcon(MainEditor.imgPath+"save.png"));
		saveButton.setBounds(121, 3, 27, 24);
		saveButton.setToolTipText("�����ϱ�(Ctrl+S)");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToLocal();
			}
		});
		panel.add(saveButton);
		
		JButton saveAsButton = new JButton("");
		saveAsButton.setIcon(new ImageIcon(MainEditor.imgPath+"save-1.png"));
		saveAsButton.setBounds(150, 3, 27, 24);
		saveAsButton.setToolTipText("�ٸ� �̸����� ����");
		saveAsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsToLocal();
			}
		});
		panel.add(saveAsButton);

		JComboBox fontComboBox = new JComboBox();
		fontComboBox.setFont(new Font("���� ���", Font.PLAIN, 12));
		fontComboBox.setBounds(183, 3, 106, 24);
		fontComboBox.setToolTipText("�۲�");
		fontComboBox.setModel(new DefaultComboBoxModel(new String[] {"���� ���"}));
		fontComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO � ��Ʈ�ִ��� Ȯ��,
				if(e.getSource() instanceof JComboBox) {
					String fontName = (String)((JComboBox)(e.getSource())).getSelectedItem();
					changeFontInTextPanel(fontName);
				}
			}
		});
		panel.add(fontComboBox);
		
		JComboBox fontSizeComboBox = new JComboBox();
		fontSizeComboBox.setFont(new Font("���� ���", Font.PLAIN, 12));
		fontSizeComboBox.setBounds(291, 3, 47, 24);
		fontSizeComboBox.setToolTipText("��Ʈ ũ��");
		String[] fontSize = new String[] {"10","12","14","16","18","20"};
		fontSizeComboBox.setModel(new DefaultComboBoxModel(fontSize));
		fontSizeComboBox.setSelectedItem(fontSize[1]);
		fontSizeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() instanceof JComboBox) {
					String tmp = (String)((JComboBox)e.getSource()).getSelectedItem();
					int fontSize = Integer.parseInt(tmp);
					changeFontSizeInTextPanel(fontSize);
				}
			}
		});
		panel.add(fontSizeComboBox);
		
		JButton leftAlignButton = new JButton("");
		leftAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"left-align.png"));
		leftAlignButton.setBounds(344, 3, 27, 24);
		leftAlignButton.setToolTipText("��������");
		leftAlignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleAttributeSet attSet = new SimpleAttributeSet();
				StyleConstants.setAlignment(attSet, StyleConstants.ALIGN_LEFT);
				MainEditor.textPane.setParagraphAttributes(attSet, true);
			}
		});
		panel.add(leftAlignButton);
		
		JButton centerAlignButton = new JButton("");
		centerAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"center-align.png"));
		centerAlignButton.setBounds(373, 3, 27, 24);
		centerAlignButton.setToolTipText("�������");
		centerAlignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleAttributeSet attSet = new SimpleAttributeSet();
				StyleConstants.setAlignment(attSet, StyleConstants.ALIGN_CENTER);
				MainEditor.textPane.setParagraphAttributes(attSet, true);
			}
		});
		panel.add(centerAlignButton);
		
		JButton rightAlignButton = new JButton("");
		rightAlignButton.setIcon(new ImageIcon(MainEditor.imgPath+"right-align.png"));
		rightAlignButton.setBounds(402, 3, 27, 24);
		rightAlignButton.setToolTipText("����������");
		rightAlignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleAttributeSet attSet = new SimpleAttributeSet();
				StyleConstants.setAlignment(attSet, StyleConstants.ALIGN_RIGHT);
				MainEditor.textPane.setParagraphAttributes(attSet, true);
			}
		});
		panel.add(rightAlignButton);
		// end of ����
		
		// ������ ����
		MainEditor.textPane = new JTextPane();
		MainEditor.textPane.setFont(new Font("���� ���", Font.PLAIN, 12));
		Document doc = textPane.getDocument();
		// �ؽ�Ʈ ���� ���� ���� ����
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				changeTitleSaveNeeded();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				changeTitleSaveNeeded();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("changedUpdate");
				changeTitleSaveNeeded();
			}
			private void changeTitleSaveNeeded() {
				isSaved = false;
				String title = MainEditor.this.getTitle();
				if(!title.startsWith("*"))
					MainEditor.this.setTitle("*"+title);
			}
		});
		// �ؽ�Ʈ ���� ctrl+z �̺�Ʈ ���ε�
		UndoManager undo = new UndoManager();
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undo.addEdit(e.getEdit());
			}
		});
		MainEditor.textPane.getActionMap().put("Undo", new AbstractAction("Undo") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(undo.canUndo())
						undo.undo();
				} catch(CannotUndoException ex) {
				}
			}
		});
		MainEditor.textPane.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
		
		JScrollPane textAreaScrollPane = new JScrollPane(MainEditor.textPane);
		textAreaScrollPane.setBounds(6, 49, 453, 307);
		contentPane.add(textAreaScrollPane);
		
		// �޴�
		// ���� �޴�
		JMenu mnFile = new JMenu("����");
		mnFile.setFont(new Font("���� ���", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenuItem mitem_new = new JMenuItem(getMenuItemName("���θ����", "Ctrl+N"));
		mitem_new.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_new.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openNewFile();
			}
		});
		
		JMenu mnOpen = new JMenu("�ҷ�����");
		mnOpen.setFont(new Font("���� ���", Font.PLAIN, 12));
		JMenuItem mitem_openFromFileSystem = new JMenuItem(getMenuItemName("����ǻ��", "Ctrl+O"));
		mitem_openFromFileSystem.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_openFromFileSystem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileFromChooser();
			}
		});
		JMenuItem mitem_openFromDB = new JMenuItem(getMenuItemName("Ŭ����", "Ctrl+I"));
		mitem_openFromDB.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_openFromDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//_HYERIM_
				if(isLoggedIn)
					new TextList(userid, MainEditor.this);
				else{
					new Login(MainEditor.this, "TEXTLIST");
				}
			}
		});
		mnOpen.add(mitem_openFromFileSystem);
		mnOpen.add(mitem_openFromDB);
		JMenu mnSave = new JMenu("�����ϱ�");
		mnSave.setFont(new Font("���� ���", Font.PLAIN, 12));
		JMenuItem mitem_saveToFileSystem = new JMenuItem(getMenuItemName("����ǻ��", "Ctrl+S"));
		mitem_saveToFileSystem.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_saveToFileSystem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToLocal();
			}
		});
		JMenuItem mitem_saveToDB = new JMenuItem(getMenuItemName("Ŭ����", "Ctrl+W"));
		mitem_saveToDB.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_saveToDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isLoggedIn) {
					saveToDB();
				} else{
					new Login(MainEditor.this,"SAVETODB");
				}
			}
		});
		mnSave.add(mitem_saveToFileSystem);
		mnSave.add(mitem_saveToDB);
		JMenuItem mitem_saveAs = new JMenuItem(getMenuItemName("�ٸ� �̸����� ����", ""));
		mitem_saveAs.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsToLocal();
			}
		});
		
		JMenuItem mitem_exit = new JMenuItem(getMenuItemName("����", "Ctrl+Q"));
		mitem_exit.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeMainFrame();
			}
		});
		
		mnFile.add(mitem_new);
		mnFile.add(mnOpen);
		mnFile.add(mnSave);
		mnFile.add(mitem_saveAs);
		mnFile.addSeparator();
		mnFile.add(mitem_exit);
		
		// ���� �޴�
		JMenu mnEdit = new JMenu("����");
		mnEdit.setFont(new Font("���� ���", Font.PLAIN, 12));
		menuBar.add(mnEdit);
		
		JMenuItem mitem_undo = new JMenuItem(getMenuItemName("�������", "Ctrl+Z"));
		mitem_undo.setFont(new Font("���� ���", Font.PLAIN, 12));
		// textArea�� ���ε��ؼ� ������ Ű �̺�Ʈ �׼� �����ͼ� ����ϱ� ��ư �׼Ǹ����ʿ� �Ҵ�
		mitem_undo.addActionListener(MainEditor.textPane.getActionForKeyStroke(KeyStroke.getKeyStroke("control Z")));
		
		JMenuItem mitem_cut = new JMenuItem();
		mitem_cut.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_cut.setAction(new DefaultEditorKit.CutAction());
		mitem_cut.setText(getMenuItemName("�߶󳻱�", "Ctrl+X"));
		
		JMenuItem mitem_copy = new JMenuItem();
		mitem_copy.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_copy.setAction(new DefaultEditorKit.CopyAction());
		mitem_copy.setText(getMenuItemName("�����ϱ�", "Ctrl+C"));
		
		JMenuItem mitem_paste = new JMenuItem();
		mitem_paste.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_paste.setAction(new DefaultEditorKit.PasteAction());
		mitem_paste.setText(getMenuItemName("�ٿ��ֱ�", "Ctrl+V"));
		
		JMenuItem mitem_find = new JMenuItem(getMenuItemName("ã��/�ٲٱ�", "Ctrl+F"));
		mitem_find.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TextSearch();
			}
		});
		JMenuItem mitem_transformul = new JMenuItem(getMenuItemName("��/�ҹ��ں�ȯ", "Ctrl+T"));
		mitem_transformul.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_transformul.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toUpperOrLowerCaseOnSelectedText();
			}
		});
		JMenuItem mitem_selectall = new JMenuItem(getMenuItemName("��μ���", "Ctrl+A"));
		mitem_selectall.setFont(new Font("���� ���", Font.PLAIN, 12));
		mitem_selectall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainEditor.textPane.selectAll();
			}
		});
		
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
		
		// �α��� �޴�
		JMenu mnLogin = new JMenu("�α���");
		mnLogin.setFont(new Font("���� ���", Font.PLAIN, 12));
		menuBar.add(mnLogin);		
		JMenuItem mitem_login = new JMenuItem(getMenuItemName("�α���/�ƿ�", "Ctrl+L"));
		//_HYERIM_
		mitem_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isLoggedIn) {
					// �α׾ƿ� ���̾�α�
					showDialogToLogout();
					
				} else {
					// �α��� ȭ��
					new Login(MainEditor.this, "SAVETODB");
				}
			}
		});
		mitem_login.setFont(new Font("���� ���", Font.PLAIN, 12));
		
		mnLogin.add(mitem_login);
		
		// ���� �޴�
		JMenu mnHelp = new JMenu("����");
		mnHelp.setFont(new Font("���� ���", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		JMenuItem mitem_info = new JMenuItem(getMenuItemName("����������", ""));
		//_HEYRIM_2016-11-06
		mitem_info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isInfoView == false)
					new DevelopeInfo(MainEditor.this);				
			}
		});
		mitem_info.setFont(new Font("���� ���", Font.PLAIN, 12));
		
		mnHelp.add(mitem_info);
		// end of �޴�
		
		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		keyManager.addKeyEventDispatcher(new MyKeyDispatcher());
		
		// main frame x ��ư���� â �ݴ� ��� �̺�Ʈ ó��
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeMainFrame();
			}
		});
		
		setTitle("�ؽ�Ʈ ������");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 481, 404);
		setVisible(true);
		
	}
	
	private void closeMainFrame() {
		int check = JFileChooser.APPROVE_OPTION;
		if(!isSaved)
			check = showSaveCheckDialogAndSave();
		
		if(check == JFileChooser.APPROVE_OPTION)
			showDialogToCloseMainFrame();
	}
	
	private void showDialogToCloseMainFrame() {
		int  check = JOptionPane.showConfirmDialog(MainEditor.this, "���α׷��� �����մϴ�."
				, "����", JOptionPane.OK_CANCEL_OPTION
				, JOptionPane.CLOSED_OPTION, null);
		
		if(check == JOptionPane.OK_OPTION)
			System.exit(0);
	}
	
	// return JFileChooser.APPROVE_OPTION : ���� or ���� �ܰ� ���� ����
	//          JFileChooser.CANCEL_OPTION : ���(�۾��ϴ� ȭ������ ���ư�)
	int showSaveCheckDialogAndSave() {
		Object[] options = {"�� ��ǻ�Ϳ� ����", "Ŭ���忡 ����", "�ƴϿ�", "���"};
		int result = JOptionPane.showOptionDialog(getParent(), "��������� �����Ͻðڽ��ϱ�?", "�����������", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		switch(result) {
		case 0:
			return saveToLocal();
		case 1:
			if(isLoggedIn) {
				return saveToDB();
			} else{
				new Login(MainEditor.this, "SAVETODB");
				return JFileChooser.CANCEL_OPTION;
			}
		case 2:
			// �ƴϿ� ��ư�� Ŭ���� ��� �������� �ʰ� ���� ���μ��� ����
			return JFileChooser.APPROVE_OPTION;
		case 3:
			return JFileChooser.CANCEL_OPTION;
		default:
			return JFileChooser.CANCEL_OPTION;
		}
	}
	
	void initEditor() {
		MainEditor.textPane.setText("");
		MainEditor.this.setTitle("�ؽ�Ʈ ������");
		fileName = "";
		isSaved = true;
	}
	
	private String getMenuItemName(String name, String shortCut) {
		if(name.length() > 10)
			name = name.substring(0, 10);
		
		int size = 0;
		int charToUni = 0;
		for(int i=0; i<name.length(); i++) {
			charToUni = name.charAt(i);
			// �ѱ� �����ڵ� ����: 44032 ~ 55203
			if(charToUni >= 44032 && charToUni <= 55203)
				size += 2;
		}
		size = 30 - size;
		return String.format("%-"+size+"s%s", name, shortCut);
	}
	
	private void toUpperOrLowerCaseOnSelectedText() {
		String selectedText = MainEditor.textPane.getSelectedText();
		String changedText = "";
		if(selectedText != null) {
			int i = (int)selectedText.charAt(0);
			if(i >= 97 && i <= 122) {
				changedText = selectedText.toUpperCase();
				MainEditor.textPane.replaceSelection(changedText);
			} else if(i >= 65 && i <= 90) {
				changedText = selectedText.toLowerCase();
				MainEditor.textPane.replaceSelection(changedText);
			}
		}
	}
	
	/*
	 * ���� ������ ������ �ִ��� ���ο� ����
	 * 1. ������ �ִ� ���, ������ ������ �� ���� Ȯ��
	 * 2. ������ ���� ���, �� ȭ�� ���
	 */
	void openNewFile() {
		if(MainEditor.textPane.getText().isEmpty() || isSaved) {
			initEditor();
			setNullToWriteObj();
		} else {
			int check = showSaveCheckDialogAndSave();
			// ������ �߰ų� �������� �Ѿ�� �ȴٸ�
			if(check == JFileChooser.APPROVE_OPTION) {
				initEditor();
				setNullToWriteObj();
			}
		}
	}

	private void setNullToWriteObj() {
		if(write != null)
			write = null;
	}
	
	private void openFileFromChooser() {
		int check1 = JFileChooser.APPROVE_OPTION;
		if(!isSaved)
			check1 = showSaveCheckDialogAndSave();
		
		if(check1 == JFileChooser.APPROVE_OPTION) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
			chooser.addChoosableFileFilter(filter);
			
			int check2 = chooser.showOpenDialog(null);
			if(check2 == JFileChooser.APPROVE_OPTION) {
				fileName = chooser.getSelectedFile().getAbsolutePath();
				// �����Ϳ� ���� ���� ���
				try {
					FileReader fr = new FileReader(fileName);
					Scanner in = new Scanner(fr);
					String contents = "";
					while(in.hasNext()) {
						contents += in.nextLine()+"\r\n";
					}
					MainEditor.textPane.setText(contents);
					
					in.close();
					
					MainEditor.this.setTitle(fileName+" - �ؽ�Ʈ ������");
					isSaved = true;
					setNullToWriteObj();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(getParent(), "�ش��ϴ� ������ �����ϴ�.\n:"+fileName);
				}
			}
		}
	}
	
	/*
	  * fileName�� ��ΰ� ����Ǿ�����, ��ο� ������ �����ϴ��� ���ο� ����
	  * 1. ó�� �����ϴ� ��� (file chooser ����ؼ� ����)
	  * 2. ������ �Ǿ� �ִ� ��� (fileName ��ο� �����)
	  */
	private int saveToLocal() {
		int result = JFileChooser.APPROVE_OPTION;
		if(fileName == null || !(new File(fileName).exists()))
			result = openSaveFileChooser();
		
		if(result == JFileChooser.APPROVE_OPTION) {
			saveFileToLocal();
			return JFileChooser.APPROVE_OPTION;
		} else {
			return JFileChooser.CANCEL_OPTION;
		}
	}
	
	private int saveToDB() {
		int result = 0;
		// ������ save��ɰ� ���� ����
		if(write != null) {
			DBConn db = new DBConn();
			result = db.modifyWrite(write.getId(), MainEditor.textPane.getText());
			db.CloseDBConn(db.c);
			if(result == 1) {
				isSaved = true;
				MainEditor.this.setTitle(write.getTitle()+" - �ؽ�Ʈ ������");
				return JFileChooser.APPROVE_OPTION;
			} else
				return JFileChooser.CANCEL_OPTION;
		} else {
			String input = JOptionPane.showInputDialog(null, "���� ������ �Է����ּ���.(50�� �̳�)", "Ŭ���� ����", JOptionPane.PLAIN_MESSAGE);
			if(input == null){
				return JFileChooser.CANCEL_OPTION;	
			} else if(input.length() < 1 || input.length() > 50) {
				JOptionPane.showMessageDialog(getParent(), "���Ŀ� �°� �Է����ּ���.");
				return JFileChooser.CANCEL_OPTION;
			} else {
				write = new Write();
				write.setUserid(userid);
				write.setTitle(input);
				write.setContent(MainEditor.textPane.getText());
				DBConn db = new DBConn();
				result = db.insertWrite(write);
				db.CloseDBConn(db.c);
				if(result == 1) {
					isSaved = true;
					MainEditor.this.setTitle(input+" - �ؽ�Ʈ ������");
					return JFileChooser.APPROVE_OPTION;
				} else
					return JFileChooser.CANCEL_OPTION;
			}
		}
	}
	
	private void saveAsToLocal() {
		int result = openSaveFileChooser();
		if(result == JFileChooser.APPROVE_OPTION)
			saveFileToLocal();
	}
	
	private int openSaveFileChooser() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
		chooser.addChoosableFileFilter(filter);
		
		if(fileName != null) {
			chooser.setCurrentDirectory(new File(fileName));;
			chooser.setSelectedFile(new File(fileName));
		}
		
		int check = chooser.showSaveDialog(null);
		if(check == JFileChooser.APPROVE_OPTION) {
			fileName = chooser.getSelectedFile().getAbsolutePath();
			return JFileChooser.APPROVE_OPTION;
		} else
			return JFileChooser.CANCEL_OPTION;
	}
	
	private void saveFileToLocal() {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(MainEditor.textPane.getText());
			
			writer.close();
			
			MainEditor.this.setTitle(fileName+" - �ؽ�Ʈ ������");
			isSaved = true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getParent(), "������ �������� ���߽��ϴ�.\n:"+fileName);
		} catch (NullPointerException e) {
			System.out.println("������ ��ҵǾ����ϴ�.");
		}
	}
	
	private void changeFontInTextPanel(String fontName) {
		Font font = MainEditor.textPane.getFont();
		MainEditor.textPane.setFont(new Font(fontName, font.getStyle(), font.getSize()));
	}
	
	private void changeFontSizeInTextPanel(int fontSize) {
		Font font = MainEditor.textPane.getFont();
		MainEditor.textPane.setFont(new Font(font.getFamily(), font.getStyle(), fontSize));
	}
	
	void setUserInfo(String id, String pw){
		this.userid = id;
		this.userpw = pw;
		this.isLoggedIn = true; // �α��� ���� ����	
	}
	
	void setWriteInfo(long id, String title, String content) {
		write = new Write();
		write.setId(id);
		write.setTitle(title);
		write.setContent(content);
		write.setUserid(userid);
	}
	
	private void showDialogToLogout() {
		int  check = JOptionPane.showConfirmDialog(MainEditor.this, "�α׾ƿ��� �մϴ�.",
				"�α׾ƿ�", JOptionPane.OK_CANCEL_OPTION, JOptionPane.CANCEL_OPTION, null);
		if(check == JOptionPane.OK_OPTION)
			isLoggedIn = false;
	}
	
	void calledSaveToDB(){
		saveToDB();
	}
	
	//_HYERIM_2016_11_06 Infoview get/set �޼ҵ� �ۼ�
	boolean getIsInfoView(){
		return isInfoView;
	}
	
	void setIsInfoView(boolean isInfoView){
		this.isInfoView = isInfoView;
	}
}
