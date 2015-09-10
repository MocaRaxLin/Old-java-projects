

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class aboutProgram
{
	/**
	 * @wbp.parser.entryPoint
	 */
	public static JFrame aboutWindow(String versionstring)
	{
		//�w�q�u����v�������r��
		Font bigfont = new Font("�L�n������", Font.BOLD, 16);
		Font font = new Font("�L�n������", Font.PLAIN, 14);
		Font smallfont = new Font("�L�n������", Font.PLAIN, 10);
		
		//ø�s�u����v����
		JFrame frm = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frm.setSize(320,320);
		frm.setTitle("Chasing v"+ versionstring);
		
		//�]�w�����b�ù�����
		int w = frm.getSize().width;
		int h = frm.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frm.setLocation(x, y);
		frm.getContentPane().setLayout(null);
		
		//���J�Ϥ�
		ImageIcon icon = createImageIcon("images/app.png", "image icon");
		
		//��ܹϤ�������
		JLabel image = new JLabel(icon,JLabel.CENTER);
		image.setBounds(0, 20, 320, 180);
		frm.getContentPane().add(image);
		
		//��ܤ�r������
		JLabel lb1 = new JLabel("Chasing v"+versionstring,JLabel.CENTER);
		lb1.setBounds(0, 170, 320, 30);
		lb1.setFont(bigfont);
		frm.getContentPane().add(lb1);
		
		JLabel lb2 = new JLabel("Ice Cream Hamburger",JLabel.CENTER);
		lb2.setBounds(0, 190, 320, 30);
		lb2.setFont(font);
		frm.getContentPane().add(lb2);
		
		JLabel version = new JLabel("����"+versionstring+"("+versionstring+")",JLabel.CENTER);
		version.setBounds(0, 240, 320, 30);
		version.setFont(smallfont);
		frm.getContentPane().add(version);
		
		//�����n��ܵ���
		frm.setVisible(false);
		return frm;
	}
	//���J����
	public static ImageIcon createImageIcon(String path,String description) 
	{	
		java.net.URL imgURL = Chasing.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL, description);
		}
		else
		{
			System.err.println("�L�k���J�ɮסG" + path);
			return null;
		}
	}
	
}
