

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
		//定義「關於」視窗的字體
		Font bigfont = new Font("微軟正黑體", Font.BOLD, 16);
		Font font = new Font("微軟正黑體", Font.PLAIN, 14);
		Font smallfont = new Font("微軟正黑體", Font.PLAIN, 10);
		
		//繪製「關於」視窗
		JFrame frm = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frm.setSize(320,320);
		frm.setTitle("Chasing v"+ versionstring);
		
		//設定視窗在螢幕中央
		int w = frm.getSize().width;
		int h = frm.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frm.setLocation(x, y);
		frm.getContentPane().setLayout(null);
		
		//載入圖片
		ImageIcon icon = createImageIcon("images/app.png", "image icon");
		
		//顯示圖片的標籤
		JLabel image = new JLabel(icon,JLabel.CENTER);
		image.setBounds(0, 20, 320, 180);
		frm.getContentPane().add(image);
		
		//顯示文字的標籤
		JLabel lb1 = new JLabel("Chasing v"+versionstring,JLabel.CENTER);
		lb1.setBounds(0, 170, 320, 30);
		lb1.setFont(bigfont);
		frm.getContentPane().add(lb1);
		
		JLabel lb2 = new JLabel("Ice Cream Hamburger",JLabel.CENTER);
		lb2.setBounds(0, 190, 320, 30);
		lb2.setFont(font);
		frm.getContentPane().add(lb2);
		
		JLabel version = new JLabel("版本"+versionstring+"("+versionstring+")",JLabel.CENTER);
		version.setBounds(0, 240, 320, 30);
		version.setFont(smallfont);
		frm.getContentPane().add(version);
		
		//先不要顯示視窗
		frm.setVisible(false);
		return frm;
	}
	//載入圖檔
	public static ImageIcon createImageIcon(String path,String description) 
	{	
		java.net.URL imgURL = Chasing.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL, description);
		}
		else
		{
			System.err.println("無法載入檔案：" + path);
			return null;
		}
	}
	
}
