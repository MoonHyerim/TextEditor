package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class DevelopeInfo extends JFrame implements WindowListener{
	private InfoImagePanel contentPane;
	private MainEditor me;
	public DevelopeInfo(MainEditor me){
		this.me = me;
		me.setIsInfoView(true);
		InitComponent();
	}
	
	void InitComponent(){
		setResizable(false);
		setTitle("�ؽ�Ʈ ������");
		setBounds(100, 100, 224, 209);
		
		contentPane = new InfoImagePanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("���� ���", Font.PLAIN, 12));
		textArea.setText("������\r\n        ������, ������\r\n\r\n���߿Ϸ���\r\n         2016. 10. 28\r\n");
		textArea.setEditable(false);
		textArea.setBounds(49, 80, 124, 100);
		contentPane.add(textArea);
		
		addWindowListener(this);
		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//_HYERIM_2016_11_06 â�� ������ �ش� �÷��� �ʱ�ȭ
		me.setIsInfoView(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class InfoImagePanel extends JPanel{
	ImageIcon im = new ImageIcon(MainEditor.imgPath + "settings.png");
	Image img = im.getImage();
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 80, 10, 57, 55, this);
	}
}