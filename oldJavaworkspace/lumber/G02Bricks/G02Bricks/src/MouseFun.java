import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Bricks.Brick;
import Bricks.HardBrick;
import Bricks.NoBrick;
import Bricks.PlainBrick;
import Bricks.BlueBrick;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MouseFun extends JFrame implements MouseMotionListener, KeyListener
{
	String ver = "3.2";
	boolean paused = true,test = true;
	int defaultdx=3, defaultdy=-4, dx = defaultdx, dy = defaultdy;
	static JTextArea textArea_debug_1 = new JTextArea("除錯區1：\n");
	static JTextArea textArea_debug_2 = new JTextArea("除錯區2\n");
	static JTabbedPane ribbon = new JTabbedPane(JTabbedPane.TOP);
	static JPanel panel_ribbon_1 = new JPanel();
	static JScrollPane scrollPane_debug_1 = new JScrollPane();
	static JScrollPane scrollPane_debug_2 = new JScrollPane();
	static JLabel lblCongras = new JLabel();
	static JPanel panel = new JPanel(){
		  public void paintComponent(Graphics g)
		  {
		    ImageIcon icon=new ImageIcon(ClassLoader.getSystemResource("img/bg.jpg"));
		    Rectangle rect=g.getClipBounds();
		    g.fillRect(rect.x, rect.y, rect.width, rect.height);
		    g.drawImage(icon.getImage(), 0, 0, Color.green, null);
		  }
		};
	static JLabel theBall = new JLabel();
	static JLabel theStick = new JLabel();
	static Brick[][] theBricks = new Brick[15][];
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	private final JLabel lblGameOver = new JLabel("按一下滑鼠發球");
	private final JButton btnRestart = new JButton("重新開始");
	static Font mFont = new Font("微軟正黑體", Font.PLAIN, 14);
	static Font bFont = new Font("微軟正黑體", Font.BOLD, 30);
	private final JLabel label_score = new JLabel("0");
	static int score, scoreUpup = 0, row = 15, bCount = -1, stage = 1;
	boolean fireBall = false, passBall = false; //功能球

	static playSound music1 = new playSound();
	static JLabel clickTOGoJLabel = new JLabel();

	public MouseFun()
	{
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MouseFun.class
								.getResource("/img/game.png")));
		drawGUI();
	}

	@SuppressWarnings("deprecation")
	public void drawGUI()
	{
		music1.setMusic(1);
		//繪製視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,625);
		setTitle("GO2 BRICK v" + ver);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		panel.setBounds(2, 2, 606, 587);
		getContentPane().add(panel);
		panel.setLayout(null);
		final Title title = new Title();
		getContentPane().add(title);
		panel.setVisible(false);
		//繪製球
		theBall.setIcon(new ImageIcon(MouseFun.class.getResource("/img/aquaBall.png")));
		theBall.setBounds(295, 546, 16, 16);
		panel.add(theBall);
		//繪製遊戲版面
		panel.addMouseMotionListener(this);
		btnRestart.addKeyListener(this);
		//ribbon.addKeyListener(this);
		panel.setCursor(blankCursor);
		//panel.setBackground(Color.white);
		panel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e)
			{
				if (bCount<0) {
					bCount=0;
				}
				if(paused)
				{
					theBall.setBounds(theStick.getX()+theStick.getWidth()/2, theStick.getY()-theBall.getHeight(), theBall.getWidth(), theBall.getHeight());
					theBall.show();
					dx = defaultdx;
					dy = defaultdy;
					lblGameOver.setVisible(false);
					paused = false;
					new playSound().playServeSound();
					timerStart();
				}
			}
		});
		//繪製桿子
		theStick.setIcon(new ImageIcon(MouseFun.class.getResource("/img/aquaStick.png")));
		theStick.setBounds(261, 565, 80, 16);
		panel.add(theStick);

		//過關
		lblCongras.setVisible(false);
		lblCongras.setIcon(new ImageIcon(MouseFun.class.getResource("/img/congratulation.png")));
		lblCongras.setBounds(-160, 2, 768, 587);
		getContentPane().add(lblCongras);

		//繪製介面
		//ribbon.setBounds(6, 6, 628, 135);
		//ribbon.setFont(mFont);
		//getContentPane().add(ribbon);
		//ribbon.addTab("常用功能", null, panel_ribbon_1, null);
		//panel_ribbon_1.setLayout(null);
		//lblGameOver.setFont(bFont);
		//lblGameOver.setBounds(196, 6, 237, 55);
		//lblGameOver.hide();
		//panel_ribbon_1.add(lblGameOver);
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				music1.setMusic(1);
				for(int i=0; i<theBricks.length; i++)
				{
					for(int j=0; j<row; j++)
					{
						theBricks[i][j].delete();
					}
				}
				lblCongras.setVisible(false);
				bCount =-1;
				stage = 0;
				score = 0;
				scoreUpup = 0;
				label_score.setText(String.valueOf(score));
				panel.setVisible(false);
				title.setVisible(true);
			}
		});
		btnRestart.setFont(mFont);
		getContentPane().add(panel);
		//繪製磚塊
		stage0();
		//自動捲動
		/*scrollPane_debug_1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e){
				textArea_debug_1.select(textArea_debug_1.getHeight()+1000,0);
				}});
		scrollPane_debug_2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e){
				textArea_debug_2.select(textArea_debug_1.getHeight()+1000,0);
				}});

		//繪製除錯訊息視窗
		scrollPane_debug_1.setBounds(16, 500, 376, 112);
		getContentPane().add(scrollPane_debug_1);
		textArea_debug_1.setAutoscrolls(false);
		scrollPane_debug_1.setViewportView(textArea_debug_1);
		textArea_debug_1.setEditable(false);

		scrollPane_debug_2.setBounds(404, 500, 218, 112);
		getContentPane().add(scrollPane_debug_2);
		textArea_debug_2.setAutoscrolls(false);
		scrollPane_debug_2.setViewportView(textArea_debug_2);
		textArea_debug_2.setEditable(false);*/

		JPanel panel_side = new JPanel(){
			  public void paintComponent(Graphics g)
			  {
			    ImageIcon icon=new ImageIcon(ClassLoader.getSystemResource("img/side.png"));
			    Rectangle rect=g.getClipBounds();
			    g.fillRect(rect.x, rect.y, rect.width, rect.height);
			    g.drawImage(icon.getImage(), 0, 0, Color.green, null);
			  }
			};
		panel_side.setBackground(Color.WHITE);
		panel_side.setBounds(610, 2, 180, 587);
		getContentPane().add(panel_side);
		panel_side.setLayout(null);
		btnRestart.setBounds(32, 302, 117, 29);
		panel_side.add(btnRestart);
		label_score.setForeground(new Color(64, 254, 10));
		label_score.setHorizontalAlignment(SwingConstants.RIGHT);
		label_score.setFont(bFont);
		label_score.setBounds(6, 43, 168, 35);

		panel_side.add(label_score);

		timerStart();
		repaint();
		show();
	}

	public void timerStart()
	{
		final Timer timer = new Timer();
		TimerTask tt = new TimerTask()
		{
			public void run()
			{
				if(paused){
					timer.cancel();
					theBall.setBounds(theStick.getX()+theStick.getWidth()/2-theBall.getWidth()/2, theStick.getY()-theBall.getHeight(), theBall.getWidth(), theBall.getHeight());
				} else {
					theBall.setBounds(theBall.getX()+dx , theBall.getY()+dy, theBall.getWidth(), theBall.getHeight());
				}
				if(		theBall.getX()	<=0
					||	theBall.getX()	>=panel.getWidth()-16)//球碰到左右邊界要彈回來
				{
					dx = -dx;
					new playSound().playHitWallSound();
				}
				if(theBall.getY()>=panel.getHeight()-32)//球有機會碰到桿子的區域
				{
					if(touched(theStick, theBall))//如果球碰到桿子
					{
						dy = -dy;
						if(theBall.getX()-theStick.getX()<=theStick.getWidth()*3/8)
						{
							dx--;
							setV();
						} else if(theBall.getX()-theStick.getX()>=theStick.getWidth()*5/8) {
							dx++;
							setV();
						}
						new playSound().playHitStickSound();
					}
					if(		theBall.getY()
						>=	panel.getHeight())//如果球掉下去.....
					{
						lblGameOver.show();
						theBall.setBounds(theStick.getX()+theStick.getWidth()/2-theBall.getWidth()/2, theStick.getY()-theBall.getHeight(), theBall.getWidth(), theBall.getHeight());
						paused = true;
						new playSound().playFallSound();
					}
				} else if(theBall.getY()<=0)//球碰到頂端要彈回來
				{
					dy = -dy;
					new playSound().playHitWallSound();
				}

				outside:
					for(int i=0; i<theBricks.length; i++) //判斷球有沒有打到磚塊
					{
						for(int j=0; j<row; j++)
						{
							if(		touched(theBricks[i][j], theBall)
								&&	passBall)//穿透球模式
							{
								break outside;
							}
							if(		touched(theBricks[i][j], theBall)
								&&	fireBall)//火球模式
							{
								theBricks[i][j].setBounds(theBricks[i][j].getX()+1000, theBricks[i][j].getY(), theBricks[i][j].getWidth(), theBricks[i][j].getY());
								if(!(theBricks[i][j] instanceof HardBrick) && !(theBricks[i][j] instanceof NoBrick))
								{
									if(theBricks[i][j] instanceof BlueBrick)
										bCount-=2;
									else
										bCount--;
									textArea_debug_2.append("bCount = "+bCount+"\n");
								}
								break outside;
							}
							if(touched(theBricks[i][j], theBall) && !theBricks[i][j].pass)
							{
								double relX = Math.abs((theBall.getX()+theBall.getWidth()/2)-(theBricks[i][j].getX()+theBricks[i][j].getWidth()/2));
								double relY = Math.abs((theBall.getY()+theBall.getHeight()/2)-(theBricks[i][j].getY()+theBricks[i][j].getHeight()/2));
								if(relX!=0)
								{
									if(		(relY/relX)
										>=	((double)theBricks[i][j].getHeight()/(double)theBricks[i][j].getWidth())) //球碰到方塊上下部分
									{
										textArea_debug_1.append("Touched UP/DOWN\t"+(relY/relX)+", "+((double)theBricks[i][j].getHeight()/(double)theBricks[i][j].getWidth())+"\n");
										dy = -dy;
										theBricks[i][j].hit();
										score+=theBricks[i][j].score;
										addScore();
										if(!(theBricks[i][j] instanceof HardBrick))
										{
											bCount--;
											textArea_debug_2.append("bCount = "+bCount+"\n");
										}
										new playSound().playHitSound();
										break outside; //每一畫面只能消一個
									}
									if(		(relY/relX)
										<=	((double)theBricks[i][j].getHeight()/(double)theBricks[i][j].getWidth())) //球碰到方塊左右部分
									{
										textArea_debug_1.append("Touched LEFT/RIGHT\t"+(relY/relX)+", "+((double)theBricks[i][j].getHeight()/(double)theBricks[i][j].getWidth())+"\n");
										dx = -dx;
										theBricks[i][j].hit();
										if(!(theBricks[i][j] instanceof HardBrick))
										{
											score+=theBricks[i][j].score+50;
											addScore();
											bCount--;
											textArea_debug_2.append("bCount = "+bCount+"\n");
										}
										new playSound().playHitSound();
										break outside; //每一畫面只能消一個
									}
								} else {
									textArea_debug_1.append("Touched UP/DOWN (by Center)\n");
									dy = -dy;
									theBricks[i][j].hit();
									if(!(theBricks[i][j] instanceof HardBrick))
									{
										score+=theBricks[i][j].score;
										addScore();
										bCount--;
										textArea_debug_2.append("bCount = "+bCount+"\n");
									}
									new playSound().playHitSound();
								}
							}
						}
					}
				if(bCount == 0)
				{
					new playSound().playStageSound();
					switch(++stage)
					{
						case 1:
							//System.out.println("stage1");
							stage1();
							break;
						case 2:
							//System.out.println("stage2");
							stage2();
							break;
						case 3:
							//System.out.println("stage3");
							stage3();
							break;
						case 4:
							//System.out.println("stage4");
							stage4();
							break;
						case 5:
							//System.out.println("stage5");
							stage5();
							break;
						case 6:
							//System.out.println("stage6");
							panel.setVisible(false);
							music1.setMusic(0);
							music1.sound.play();
							bCount = 1;
							paused = true;
							lblCongras.setVisible(true);
							repaint();
							break;
					}
					repaint();
					reset();
				}
			}
		};
		timer.schedule(tt,10,10);
	}

	public void addScore()
	{
		Timer timer = new Timer();
		TimerTask tt = new TimerTask()
		{
			public void run()
			{
					if (score >= scoreUpup)
						label_score.setText(String.valueOf(scoreUpup++));
					else
					{
						this.cancel();
						textArea_debug_1.append("addScore() timer terminated.\n");
					}
			}

		};
		timer.schedule(tt,10,5);
	}

	public boolean touched( JLabel lbl1, JLabel lbl2){
		return(lbl1.getY()-lbl2.getY()<= lbl2.getHeight() && lbl2.getY()-lbl1.getY()<=lbl1.getHeight()
				&& lbl1.getX()-lbl2.getX()<= lbl2.getWidth() && lbl2.getX()-lbl1.getX()<=lbl1.getWidth());
	}

	public void setV(){
		double Ek = defaultdx*defaultdx+defaultdy*defaultdy;
		dy = (-(int) Math.sqrt(Ek - dx*dx))-1;
		if (dx>5)
			dx = 5;
		if (dx<-5)
			dx = -5;
		if (test){
			textArea_debug_2.append("Vx:"+dx+" Vy:"+dy +" Ek:"+Ek+"\n");
		}
	}

	@SuppressWarnings("deprecation")
	public void reset()
	{
		theBall.setBounds(theStick.getX()+theStick.getWidth()/2-theBall.getWidth()/2, theStick.getY()-theBall.getHeight(), theBall.getWidth(), theBall.getHeight());
		theBall.show();
		dx = 2;
		dy = -3;
		lblGameOver.setVisible(true);
		paused = true;
	}
	public static void stage0()
	{
		for(int i=0; i<theBricks.length; i++)
		{
			theBricks[i] = new Brick[15];
			for(int j=0; j<row; j++)
			{
				int allpass[][]={ {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}
								, {0,0,1,1,1,0,1,0,0,0,1,0,0,0,0}
								, {0,0,1,0,1,0,1,0,0,0,1,0,0,0,0}
								, {0,0,1,1,1,0,1,0,0,0,1,0,0,0,0}
								, {0,0,1,0,1,0,1,1,1,0,1,0,0,0,0}
								, {0,0,1,0,1,0,0,0,0,0,1,1,1,0,0}
								, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
								, {1,1,1,0,0,0,0,0,1,1,1,0,0,0,0}
								, {1,0,1,0,0,0,0,0,1,0,0,0,0,0,0}
								, {1,1,1,0,1,1,1,0,1,1,1,0,1,1,1}
								, {1,0,0,0,1,0,1,0,0,0,1,0,1,0,0}
								, {1,0,0,0,1,1,1,0,1,1,1,0,1,1,1}
								, {0,0,0,0,1,0,1,0,0,0,0,0,0,0,1}
								, {0,0,0,0,1,0,1,0,0,0,0,0,1,1,1}
								, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
				if(allpass[j][i]==1)
					theBricks[i][j] = new BlueBrick();
				else
				{
					theBricks[i][j] = new PlainBrick();
				}
				//theBricks[i][j] = new NoBrick();

				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}
		clickTOGoJLabel = new JLabel("按下滑鼠後開始執行，ALL PASS打不得！！");
		clickTOGoJLabel.setForeground(Color.white);
		clickTOGoJLabel.setFont(new Font("微軟正黑體", 2, 30));
		clickTOGoJLabel.setBounds(261-65,565-200,250,40);
		panel.add(clickTOGoJLabel);
	}

	public static void stage1()
	{
		clickTOGoJLabel.setText("===STAGE 1===");
		for(int i=0; i<theBricks.length; i++)
		{
			//清理殘磚塊
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].delete();
			}

			theBricks[i] = new Brick[15];

			for(int j=0; j<row; j++)
			{
				if((i*j)%2==0)
					theBricks[i][j] = new NoBrick();
				else
				{
					theBricks[i][j] = new PlainBrick();
					bCount++;
				}
				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}
	}

	public static void stage2()
	{
		clickTOGoJLabel.setText("===STAGE 2===");
		for(int i=0; i<theBricks.length; i++)
		{
			//清理殘磚塊
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].delete();
			}

			theBricks[i] = new Brick[15];
			for(int j=0; j<row; j++)
			{
				if( (i >= 5 && i<= 9)&& (j >= 5 && j<= 9) ){
					theBricks[i][j] = new BlueBrick();
				}
				else {
					theBricks[i][j] = new PlainBrick();
				}
			}
		}

		//設置無敵磚
		for(int i=0; i<theBricks.length; i++)
		{
			for(int j=0; j<row; j++)
			{
				if((i-j == 0) || (i+j == row -1)){
					theBricks[i][j]= new HardBrick();
				}
			}
		}

		//設置空磚
		for(int i=0; i<theBricks.length; i++)
		{
			for(int j=0; j<row; j++)
			{
				if ((i==0||i==14)||(j==0||j==14)){
					theBricks[i][j] = new NoBrick();
				}
			}
		}

		//將磚塊加入panel
		for(int i=0; i<theBricks.length; i++)
		{
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}
		bCount += 160;
	}

	public static void stage3()
	{
		clickTOGoJLabel.setText("===STAGE 3===");
		for(int i=0; i<theBricks.length; i++)
		{
			//清理殘磚塊
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].delete();
			}
			theBricks[i] = new Brick[15];
			for(int j=0; j<row; j++)
			{

				if(i==0||i==14||j==0)
					theBricks[i][j] = new NoBrick();
				else if(j==14 && i%2==0)
				{
					theBricks[i][j] = new BlueBrick();
					bCount+=2;
				}
				else if(j==13 && i%2==1)
					{
					theBricks[i][j] = new BlueBrick();
					bCount+=2;
					}
				else if(i==1&&j==3||i==1&&j==4||i==1&&j==5||i==2&&j==2||i==2&&j==6||i==3&&j==1||i==3&&j==7||i==4&&j==2||i==4&&j==6
						||i==5&&j==8||i==5&&j==9||i==5&&j==10||i==5&&j==12||i==6&&j==8||i==6&&j==10||i==6&&j==12||i==7&&j==2
						||i==7&&j==6||i==7&&j==8||i==7&&j==10||i==7&&j==11||i==7&&j==12||i==8&&j==2||i==8&&j==3||i==8&&j==4
						||i==8&&j==5||i==8&&j==6||i==9&&j==2||i==9&&j==6||i==11&&j==4||i==11&&j==5||i==11&&j==6||i==11&&j==7
						||i==11&&j==8||i==11&&j==9||i==11&&j==10||i==12&&j==4||i==12&&j==7||i==12&&j==10||i==13&&j==4
						||i==13&&j==7||i==13&&j==10)
					theBricks[i][j] = new HardBrick();
				else
				{
					theBricks[i][j] = new PlainBrick();
					bCount++;
				}
				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}


	}

	public static void stage4()
	{
		clickTOGoJLabel.setText("===STAGE 4===");
		for(int i=0; i<theBricks.length; i++)
		{
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].delete();
			}

			theBricks[i] = new Brick[15];

			for(int j=0; j<row; j++)
			{
				if((i+j)%10==0)
					theBricks[i][j] = new HardBrick();
				else if((i+j)%8==0)
					theBricks[i][j] = new NoBrick();
				else if((i+2*j)%2==0)
				{
					theBricks[i][j] = new PlainBrick();
					bCount++;
				}
				else
				{
					theBricks[i][j] = new BlueBrick();
					bCount+=2;
				}
				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}
	}

	public static void stage5()
	{
		clickTOGoJLabel.setText("===STAGE 5===");
		for(int i=0; i<theBricks.length; i++)
		{
			for(int j=0; j<row; j++)
			{
				theBricks[i][j].delete();
			}

			theBricks[i] = new Brick[15];

			for(int j=0; j<row; j++)
			{
				if((i+j)%7==0)
					theBricks[i][j] = new HardBrick();
				else if((i+j)%3==0)
					theBricks[i][j] = new NoBrick();
				else if((i+2*j)%2==0)
				{
					theBricks[i][j] = new PlainBrick();
					bCount++;
				}
				else
				{
					theBricks[i][j] = new BlueBrick();
					bCount+=2;
				}
				theBricks[i][j].setBounds(4+40*i, 6+16*j, 40, 16);
				panel.add(theBricks[i][j]);
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		theBall.setBounds(e.getX(), e.getY(), theBall.getWidth(), theBall.getHeight());
	}

	public void mouseMoved(MouseEvent e) {
		theStick.setBounds((theStick.getWidth()/2<=e.getX()) && (e.getX()<=(panel.getWidth()-theStick.getWidth()/2))?e.getX()-theStick.getWidth()/2:theStick.getX(), theStick.getY(), theStick.getWidth(), theStick.getHeight());
		if(paused)
			theBall.setBounds(theStick.getX()+theStick.getWidth()/2-theBall.getWidth()/2, theStick.getY()-theBall.getHeight(), theBall.getWidth(), theBall.getHeight());
	}

	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_P)
		{
			passBall = true;
			theBall.setIcon(new ImageIcon(MouseFun.class.getResource("/img/greenBall.png")));
		}
		if (k.getKeyCode() == KeyEvent.VK_F)
		{
			fireBall = true;
			theBall.setIcon(new ImageIcon(MouseFun.class.getResource("/img/redBall.png")));
		}
	}
	public void keyReleased(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_P)
		{
			passBall = false;
			theBall.setIcon(new ImageIcon(MouseFun.class.getResource("/img/aquaBall.png")));
		}
		if (k.getKeyCode() == KeyEvent.VK_F)
		{
			fireBall = false;
			theBall.setIcon(new ImageIcon(MouseFun.class.getResource("/img/aquaBall.png")));
		}
	}

	public void keyTyped(KeyEvent k) {

	}
}
