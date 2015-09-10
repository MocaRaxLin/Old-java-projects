import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Chasing_double extends JFrame {
	private static final long serialVersionUID = 1L;
	int defalutdx1 = 3, defalutdy1 = 3, defalutdx2 = 2, defalutdy2 = 2, dx1 = defalutdx1, dy1 = 0,
			dx2 = -defalutdx2, dy2 = 0;
	int key1 = 0, key2 = 0; // 1上2下3左4右
	boolean key1Get = true, key2Get = true;
	boolean paused = true;
	int screenWidth = 640, screenHeight = 480, windowBar = 25;
	static JLabel Ball1 = new JLabel();
	static JLabel Ball2 = new JLabel();
	static JLabel lblTime = new timerLabel();
	static JLabel lblTms = new JLabel(".00");
	static Font timerfont = new Font("微軟正黑體", Font.PLAIN, 70);
	Color defaultColor = new Color(20, 80, 165);
	JLabel lblExplosion = new JLabel();
	JLabel background = new JLabel(new ImageIcon(
			Chasing_double.class.getResource("/images/bgEmpty.jpg")),
			SwingConstants.CENTER);
	playSound sound = new playSound();

	public Chasing_double() {
		drawGUI();
	}

	public void drawGUI() {
		getContentPane().setBackground(Color.BLACK);
		setSize(screenWidth, screenHeight);
		setTitle("Chasing");
		setLocationRelativeTo(null);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
		getContentPane().setLayout(null);

		Ball1.setIcon(new ImageIcon(Chasing_double.class
				.getResource("/images/square1.png")));
		Ball1.setBounds(6, 6, 32, 32);
		getContentPane().add(Ball1);

		Ball2.setIcon(new ImageIcon(Chasing_double.class
				.getResource("/images/square2.png")));
		Ball2.setBounds(602, 420, 32, 32);
		getContentPane().add(Ball2);
		
		lblTms.setBounds(318, 213, 187, 55);
		lblTms.setFont(timerfont);
		lblTms.setForeground(new Color(20, 80, 165));
		getContentPane().add(lblTms);
		
		lblTime.setBounds(161, 6, 156, 446);
		getContentPane().add(lblTime);
		lblExplosion.setVisible(false);
		
		lblExplosion.setIcon(new ImageIcon(Chasing_double.class.getResource("/images/explode.gif")));
		lblExplosion.setBounds(205, 136, 256, 192);
		getContentPane().add(lblExplosion);

		background.setBounds(0, 0, 640, 480);
		getContentPane().add(background);
		setVisible(true);
		sound.playReadySound();

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (paused) {
					paused = false;
					timerStart();
					((timerLabel) lblTime).start();
				} else
					end();
			}// end mouseClicked
		});// end addMouseListener

		KeyListener keyListener = new KeyListener() { // 1上2下3左4右
			public void keyPressed(KeyEvent e) {
				sound.playKeySound();
				if (e.getKeyCode() == KeyEvent.VK_W) {
					key1 = 1;
					key1Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					key1 = 2;
					key1Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					key1 = 3;
					key1Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					key1 = 4;
					key1Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					key2 = 1;
					key2Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					key2 = 2;
					key2Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					key2 = 3;
					key2Get = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					key2 = 4;
					key2Get = false;
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if (paused) {
						paused = false;
						timerStart();
						((timerLabel) lblTime).start();
					} else
						paused = true;
				}
			}

			public void keyTyped(KeyEvent e	) {
				
			}
		};
		addKeyListener(keyListener);
	}

	public void timerStart() {
		((timerLabel) lblTime).reset();
		lblTms.setForeground(defaultColor);
		final Timer timer = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
				if (paused) {
					lblTms.setForeground(Color.WHITE);
					Ball1.setBounds(6, 6, 32, 32);
					Ball2.setBounds(602, 420, 32, 32);
					((timerLabel) lblTime).stop();
					this.cancel();
				} else {
					lblTms.setText('.'+String.format("%02d", ((timerLabel) lblTime).getTenMs()));
					// 動作
					if (!key1Get) {
						if (key1 == 1) {
							dy1 = -defalutdy1;
							dx1 = 0;
						}
						if (key1 == 2) {
							dy1 = defalutdy1;
							dx1 = 0;
						}
						if (key1 == 3) {
							dy1 = 0;
							dx1 = -defalutdx1;
						}
						if (key1 == 4) {
							dy1 = 0;
							dx1 = defalutdx1;
						}
						key1Get = true;
					}
					if (!key2Get) {
						if (key2 == 1) {
							dy2 = -defalutdy2;
							dx2 = 0;
						}
						if (key2 == 2) {
							dy2 = defalutdy2;
							dx2 = 0;
						}
						if (key2 == 3) {
							dy2 = 0;
							dx2 = -defalutdx2;
						}
						if (key2 == 4) {
							dy2 = 0;
							dx2 = defalutdx2;
						}
						key2Get = true;
					}

					// 反彈
					if (ballOutX(Ball1))
					{
						sound.playHitSound();
						dx1 = -dx1;
					}
					if (ballOutY(Ball1))
					{
						sound.playHitSound();
						dy1 = -dy1;
					}
					
					// 穿越
					if (Ball2.getX()<=-Ball2.getWidth())
					{
						sound.playPassSound();
						Ball2.setBounds(screenWidth, Ball2.getY(), Ball2.getWidth(), Ball2.getHeight());
					}
					else if (Ball2.getX()>=screenWidth)
					{
						sound.playPassSound();
						Ball2.setBounds(-Ball2.getWidth(), Ball2.getY(), Ball2.getWidth(), Ball2.getHeight());
					}
					if (Ball2.getY()<=-Ball2.getHeight())
					{
						sound.playPassSound();
						Ball2.setBounds(Ball2.getX(), screenHeight-windowBar, Ball2.getWidth(), Ball2.getHeight());
					}
					else if (Ball2.getY()>=screenHeight-windowBar)
					{
						sound.playPassSound();
						Ball2.setBounds(Ball2.getX(), -Ball2.getHeight(), Ball2.getWidth(), Ball2.getHeight());
					}

					Ball1.setBounds(Ball1.getX() + dx1, Ball1.getY() + dy1,
							Ball1.getWidth(), Ball1.getHeight());
					Ball2.setBounds(Ball2.getX() + dx2, Ball2.getY() + dy2,
							Ball2.getWidth(), Ball2.getHeight());

					if (touched(Ball1, Ball2)) {
						end();
					} 
				}
			}// end run
		};

		timer.schedule(tt, 5, 5);
	}// end timerStart

	private boolean touched(JLabel Ball1, JLabel Ball2) {
		return (Ball1.getY() - Ball2.getY() <= Ball2.getHeight()
				&& Ball2.getY() - Ball1.getY() <= Ball1.getHeight()
				&& Ball1.getX() - Ball2.getX() <= Ball2.getWidth() && Ball2
				.getX() - Ball1.getX() <= Ball1.getWidth());
	}

	private boolean ballOutX(JLabel ball) {
		return (ball.getX() <= 0 || ball.getX() >= screenWidth
				- ball.getWidth());
	}

	private boolean ballOutY(JLabel ball) {
		return (ball.getY() <= 0 || ball.getY() >= screenHeight
				- ball.getHeight() - windowBar);
	}
	

	private void end() {
		paused = true;
		lblExplosion.setBounds((Ball1.getX()+Ball2.getX()-lblExplosion.getWidth())/2, (Ball1.getY()+Ball2.getY()-lblExplosion.getHeight())/2, lblExplosion.getWidth(), lblExplosion.getHeight());
		lblExplosion.setVisible(true);
		sound.playBombSound();
		background.setVisible(false);
		Ball1.setVisible(false);
		Ball2.setVisible(false);
		Timer timer = new Timer();
		TimerTask tt = new TimerTask()
		{
		      public void run()
		      {
		    	  lblExplosion.setVisible(false);
		    	  background.setVisible(true);
		    	  Ball1.setVisible(true);
		    	  Ball2.setVisible(true);
		    	  sound.playReadySound();
		    	  this.cancel();
		      }
		};
		timer.schedule(tt,3000);
	}
}// end class
