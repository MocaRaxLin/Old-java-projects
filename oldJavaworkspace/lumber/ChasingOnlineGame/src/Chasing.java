import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.io.IOException;

public class Chasing
{
	static mp3 music;
	static String ver = "1.3.3";
	static boolean isFirstStep = true;
	static Font font = new Font("微軟正黑體", Font.PLAIN, 14);
	static Font bfont = new Font("微軟正黑體", Font.PLAIN, 20);
	static JFrame frm = new JFrame("Chasing Online "+ver);
	static JButton btnStartGame = new JButton("單機遊戲");
	static JButton btn1p = new JButton("單人遊戲");
	static JButton btn2p = new JButton("雙人遊戲");
	static JButton btnOnlineGame = new JButton("連線遊戲");
	static JButton btnHost = new JButton("主持遊戲");
	static JButton btnJoin = new JButton("加入遊戲");
	static JButton btnAbout = new JButton("版本資訊");
	static JFrame aboutWindow = aboutProgram.aboutWindow(ver);
	static JFrame rankingsWindow = Rankings.rankingsWindow();
	static boolean ani1Finished = true;
	static boolean ani2Finished = true;
	static playSound sound = new playSound();
	
	public static void main(String args[])
    {
		try {
		      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    } catch (Exception e) {
		      System.out.println("Running on non-Windows OS.");
		    }
		
		createGUI();
		Timer timer = new Timer();
		TimerTask musicTask = new TimerTask()
		{
		      public void run()
		      {
		    	mp3 music = new mp3("src/sounds/Mistery.mp3");
		  		music.play();
		      }
		      
		};
		timer.schedule(musicTask,0,85000);
    }

	private static void createGUI()
	{
		frm.setSize(640, 640);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frm.getSize().width;
		int h = frm.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		frm.setLocation(x, y);
		frm.getContentPane().setLayout(null);
		btn1p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound.playSelectSound();
				new Chasing_single();
			}
		});
		btn1p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1p.setIcon(new ImageIcon(Chasing.class.getResource("/images/one.png")));
		
		btn1p.setFont(bfont);
		btn1p.setBounds(120, 190, 200, 53);
		frm.getContentPane().add(btn1p);
		btn1p.setVisible(false);
		btn2p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound.playSelectSound();
				new Chasing_double();
			}
		});
		btn2p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2p.setIcon(new ImageIcon(Chasing.class.getResource("/images/two.png")));

		btn2p.setFont(bfont);
		btn2p.setBounds(320, 190, 200, 53);
		frm.getContentPane().add(btn2p);
		btn2p.setVisible(false);
		btnHost.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound.playSelectSound();
			}
		});
		btnHost.setIcon(new ImageIcon(Chasing.class.getResource("/images/host.png")));
		btnHost.setFont(bfont);
		btnHost.setBounds(120, 260, 200, 53);
		btnHost.setVisible(false);
		btnStartGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnStartGame.setIcon(new ImageIcon(Chasing.class.getResource("/images/start.png")));
		btnStartGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if(ani2Finished)
					new playSound().playExpandSound();
				Timer timer = new Timer();
				TimerTask tt = new TimerTask()
				{
				      public void run()
				      {
				    	  if(ani2Finished)
				    	  {
				    		  ani1Finished = false;
					    	  btnOnlineGame.setVisible(true);
					    	  btnHost.setVisible(false);
					    	  btnJoin.setVisible(false);
					    	  btnStartGame.setBounds(btnStartGame.getX()-1, btnStartGame.getY(), btnStartGame.getWidth()+2, btnStartGame.getHeight());
					    	  if(btnOnlineGame.getWidth()>200)
					    		  btnOnlineGame.setBounds(btnOnlineGame.getX()+1, btnOnlineGame.getY(), btnOnlineGame.getWidth()-2, btnOnlineGame.getHeight());
					    	  if(btnStartGame.getWidth()>=400)
					    	  {
					    		  btn1p.setVisible(true);
					    		  btn2p.setVisible(true);
					    		  btnStartGame.setVisible(false);
					    		  ani1Finished = true;
						    	  this.cancel();
					    	  }
				    	  }
				      }
				};
				timer.schedule(tt,0,5);
			}
		});
		
		btnStartGame.setBounds(220, 190, 200, 53);
		btnStartGame.setFont(bfont);
		frm.getContentPane().add(btnStartGame);
		
		frm.getContentPane().add(btnHost);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.playSelectSound();
			}
		});
		btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnJoin.setIcon(new ImageIcon(Chasing.class.getResource("/images/join.png")));
		btnJoin.setFont(bfont);
		btnJoin.setBounds(320, 260, 200, 53);
		btnJoin.setVisible(false);
		
		frm.getContentPane().add(btnJoin);
		btnOnlineGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnOnlineGame.setIcon(new ImageIcon(Chasing.class.getResource("/images/online.png")));
		btnOnlineGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(ani1Finished)
					sound.playExpandSound();
				Timer timer = new Timer();
				TimerTask tt = new TimerTask()
				{
				      public void run()
				      {
				    	  if(ani1Finished)
				    	  {
				    		  ani2Finished = false;
					    	  btnStartGame.setVisible(true);
					    	  btn1p.setVisible(false);
					    	  btn2p.setVisible(false);
					    	  btnOnlineGame.setBounds(btnOnlineGame.getX()-1, btnOnlineGame.getY(), btnOnlineGame.getWidth()+2, btnOnlineGame.getHeight());
					    	  if(btnStartGame.getWidth()>200)
					    	  {
					    		  btnStartGame.setBounds(btnStartGame.getX()+1, btnStartGame.getY(), btnStartGame.getWidth()-2, btnStartGame.getHeight());
					    	  }
					    	  if(btnOnlineGame.getWidth()>=400)
					    	  {
					    		  btnHost.setVisible(true);
					    		  btnJoin.setVisible(true);
					    		  btnOnlineGame.setVisible(false);
					    		  ani2Finished = true;
						    	  this.cancel();
					    	  }
				    	  }
				      }
				};
				timer.schedule(tt,0,5);
			}
		});
		btnOnlineGame.setFont(bfont);
		btnOnlineGame.setBounds(220, 260, 200, 53);
		frm.getContentPane().add(btnOnlineGame);
		
		JButton button = new JButton("世界排行");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound.playSelectSound();
				rankingsWindow.setLocationRelativeTo(frm);
				rankingsWindow.setVisible(true);
			}
		});
		button.setIcon(new ImageIcon(Chasing.class.getResource("/images/rankings.png")));
		button.setFont(bfont);
		button.setBounds(220, 330, 200, 53);
		frm.getContentPane().add(button);
		
		// 主持遊戲
				btnHost.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							new Server();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				});
				// 加入遊戲
				btnJoin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							new Client();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
					}
				});
		
		JButton btnExit = new JButton("離開遊戲");
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.setIcon(new ImageIcon(Chasing.class.getResource("/images/exit.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.playSelectSound();
				Timer timer = new Timer();
				TimerTask tt = new TimerTask()
				{
				      public void run()
				      {
				    	  System.exit(0);
				      }
				};
				timer.schedule(tt,250);
			}
		});
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound.playSelectSound();
				aboutWindow.setLocationRelativeTo(frm);
				aboutWindow.setVisible(true);
			}
		});
		btnAbout.setIcon(new ImageIcon(Chasing.class.getResource("/images/about.png")));
		
		btnAbout.setFont(bfont);
		btnAbout.setBounds(220, 400, 200, 53);
		frm.getContentPane().add(btnAbout);
		btnExit.setFont(bfont);
		btnExit.setBounds(220, 470, 200, 53);
		frm.getContentPane().add(btnExit);
		
		ImageIcon icon = createImageIcon("images/bg.jpg", "image icon");
		JLabel label = new JLabel(icon,JLabel.CENTER);
		label.setBounds(0, 0, 640, 640);
		frm.getContentPane().add(label);
		frm.setVisible(true);
	}
	
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
