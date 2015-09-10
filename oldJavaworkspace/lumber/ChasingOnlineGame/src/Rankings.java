import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Rankings
{
	static JButton btnL = new JButton();
	static JButton btnR = new JButton();
	static playSound sound = new playSound();
	static int page = 1, defaultLblPosX = 65, defaultLblNamePosX = 210, defaultLblTimePosX = 460, frmSizeX = 640;
	static JLabel lbl[] = new JLabel[5];
	static JLabel lblName[] = new JLabel[5];
	static JLabel lblTime[] = new JLabel[5];
	static JLabel lblBgLights = new JLabel(new ImageIcon(Rankings.class.getResource("/images/bgRankings.png")), SwingConstants.CENTER);
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static JFrame rankingsWindow()
	{
		//定義字體
		Font bfont = new Font("微軟正黑體", Font.BOLD, 24);
		
		//繪製視窗
		JFrame frm = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frm.setSize(640,640);
		frm.setTitle("Chasing World Rank");
		
		//設定視窗在螢幕中央
		int w = frm.getSize().width;
		int h = frm.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frm.setLocation(x, y);
		frm.getContentPane().setLayout(null);
		
		for(int i=(page-1)*5;i<(page-1)*5+5;i++)
		{
			lbl[i] = new JLabel("Rank "+ (i+1));
			lbl[i].setHorizontalAlignment(SwingConstants.RIGHT);
			lbl[i].setBounds(defaultLblPosX, 223+77*i, 120, 37);
			lbl[i].setFont(bfont);
			frm.getContentPane().add(lbl[i]);
			
			lblName[i] = new JLabel("Name "+(i+1));
			lblName[i].setHorizontalAlignment(SwingConstants.LEFT);
			lblName[i].setFont(bfont);
			lblName[i].setBounds(defaultLblNamePosX, 223+77*i, 225, 37);
			frm.getContentPane().add(lblName[i]);
			
			lblTime[i] = new JLabel("0: 00");
			lblTime[i].setHorizontalAlignment(SwingConstants.LEFT);
			lblTime[i].setFont(bfont);
			lblTime[i].setBounds(defaultLblTimePosX, 223+77*i, 101, 37);
			frm.getContentPane().add(lblTime[i]);
		}
		
		btnL.setIcon(new ImageIcon(Rankings.class.getResource("/images/arrowL.png")));
		btnL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page>1)
				{
					page--;
					Timer timer = new Timer();
					TimerTask tt = new TimerTask()
					{
					      public void run()
					      {
					    	  lblBgLights.setBounds(lblBgLights.getX()+1, lblBgLights.getY(), lblBgLights.getWidth(), lblBgLights.getHeight());
					    	  for(int i=(page-1)*5;i<(page-1)*5+5;i++)
					    	  {
					    		  lbl[i%5].setBounds(lbl[i%5].getX()+1, lbl[i%5].getY(), lbl[i%5].getWidth(), lbl[i%5].getHeight());
					    		  lblName[i%5].setBounds(lblName[i%5].getX()+1, lblName[i%5].getY(), lblName[i%5].getWidth(), lblName[i%5].getHeight());
					    		  lblTime[i%5].setBounds(lblTime[i%5].getX()+1, lblTime[i%5].getY(), lblTime[i%5].getWidth(), lblTime[i%5].getHeight());
					    	  }
					    	  
					    	  if(lblBgLights.getX()>frmSizeX)
					    	  {
					    		  refresh();
					    		  lblBgLights.setBounds(-frmSizeX, lblBgLights.getY(), lblBgLights.getWidth(), lblBgLights.getHeight());
					    		  for(int i=(page-1)*5;i<(page-1)*5+5;i++)
						    	  {
						    		  lbl[i%5].setBounds(-frmSizeX+defaultLblPosX, lbl[i%5].getY(), lbl[i%5].getWidth(), lbl[i%5].getHeight());
						    		  lblName[i%5].setBounds(-frmSizeX+defaultLblNamePosX, lblName[i%5].getY(), lblName[i%5].getWidth(), lblName[i%5].getHeight());
						    		  lblTime[i%5].setBounds(-frmSizeX+defaultLblTimePosX, lblTime[i%5].getY(), lblTime[i%5].getWidth(), lblTime[i%5].getHeight());
						    	  }
					    	  }
					    	  if(lbl[0].getX() == defaultLblPosX)
					    		  this.cancel();
					      }
					};
					timer.schedule(tt,1,1);
				}
				sound.playSelectSound();
			}
		});
		btnL.setBounds(6, 358, 40, 64);
		frm.getContentPane().add(btnL);
		
		btnR.setIcon(new ImageIcon(Rankings.class.getResource("/images/arrowR.png")));
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page++;
				Timer timer = new Timer();
				TimerTask tt = new TimerTask()
				{
				     public void run()
				     {
				    	if(page<=20)
				    	{
							lblBgLights.setBounds(lblBgLights.getX()-1, lblBgLights.getY(), lblBgLights.getWidth(), lblBgLights.getHeight());
							for(int i=(page-1)*5;i<(page-1)*5+5;i++)
							{
								lbl[i%5].setBounds(lbl[i%5].getX()-1, lbl[i%5].getY(), lbl[i%5].getWidth(), lbl[i%5].getHeight());
								lblName[i%5].setBounds(lblName[i%5].getX()-1, lblName[i%5].getY(), lblName[i%5].getWidth(), lblName[i%5].getHeight());
								lblTime[i%5].setBounds(lblTime[i%5].getX()-1, lblTime[i%5].getY(), lblTime[i%5].getWidth(), lblTime[i%5].getHeight());
							}
						  
							if(lblTime[0].getX()<(0-lblTime[0].getWidth()))
							{
								refresh();
								lblBgLights.setBounds(frmSizeX, lblBgLights.getY(), lblBgLights.getWidth(), lblBgLights.getHeight());
								for(int i=(page-1)*5;i<(page-1)*5+5;i++)
						    	{
						    		lbl[i%5].setBounds(frmSizeX+defaultLblPosX, lbl[i%5].getY(), lbl[i%5].getWidth(), lbl[i%5].getHeight());
						    		lblName[i%5].setBounds(frmSizeX+defaultLblNamePosX, lblName[i%5].getY(), lblName[i%5].getWidth(), lblName[i%5].getHeight());
						    		lblTime[i%5].setBounds(frmSizeX+defaultLblTimePosX, lblTime[i%5].getY(), lblTime[i%5].getWidth(), lblTime[i%5].getHeight());
						    	}
							}
							if(lbl[0].getX() == defaultLblPosX)
								this.cancel();
				    	}
				    }
				};
				timer.schedule(tt,1,1);
				sound.playSelectSound();
			}
		});
		btnR.setBounds(594, 358, 40, 64);
		frm.getContentPane().add(btnR);
		
		lblBgLights.setBounds(0, 0, 640, 640);
		frm.getContentPane().add(lblBgLights);
		
		JLabel lblBg = new JLabel(new ImageIcon(Rankings.class.getResource("/images/bgRankings.jpg")), SwingConstants.CENTER);
		lblBg.setBounds(0, 0, 640, 640);
		frm.getContentPane().add(lblBg);
		
		//先不要顯示視窗
		frm.setVisible(false);
		return frm;
	}
	
	private static void refresh()
	{
		for(int i=(page-1)*5;i<(page-1)*5+5;i++)
		{
			lbl[i%5].setText("Rank "+ (i+1));
			lblName[i%5].setText("Name "+(i+1));
			lblTime[i%5].setText("0: 00");
		}
	}
}
