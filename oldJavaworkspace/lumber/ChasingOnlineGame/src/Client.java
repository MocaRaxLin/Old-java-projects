import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
import javax.swing.JOptionPane;

public class Client extends JFrame {
	private Socket client; // socket to communicate with server
	private ObjectOutputStream output; // output stream to server
	private ObjectInputStream input; // input stream from server
	private String chatServer;
	private static final long serialVersionUID = 1;
	int defalutdx1 = 3, defalutdy1 = 3, defalutdx2 = 2, defalutdy2 = 2,dx1 = defalutdx1, dy1 = 0, dx2 = -defalutdx2, dy2 = 0;
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
	JLabel background = new JLabel(new ImageIcon(Chasing_double.class.getResource("/images/bgEmpty.jpg")),SwingConstants.CENTER);
	String recieve;
	int x1, y1, w1, h1, x2, y2, w2, h2;
	playSound sound = new playSound();

	public Client() throws IOException, ClassNotFoundException {
		ipcheck();
		drawGUI();
		runClient();
	}

	int serverip;
	String ip;
	private final JLabel db1 = new JLabel("Debug1");
	private final JLabel db2 = new JLabel("Debug2");

	public void ipcheck() {
		ip = JOptionPane.showInputDialog("請輸入主持人之ip位址");
		if (ip == null) {
			JOptionPane.showMessageDialog(null, "還是得開始XDD");
		} else {
			chatServer = ip;
		}

	}// end ipcheck

	public void runClient() throws ClassNotFoundException, IOException {
		try {
			connectToServer();
		} finally {
			//closeConnection();
		}
		
	}

	private void connectToServer() throws IOException {

		client = new Socket(InetAddress.getByName(chatServer), 12345);
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush();
		input = new ObjectInputStream(client.getInputStream());
		processConnection();
		} // end method connectToServer
	
		public void	processConnection(){
			final Timer timer1 = new Timer();
			TimerTask tt1 = new TimerTask() {
				public void run() {
					if(recieve.equals("go") && paused){
						paused = false;
						timerStart();
						((timerLabel) lblTime).start();
						db1.setText("Recieved Go command");
				}
			}
		};
		timer1.schedule(tt1, 5, 5);
	}

	private void closeConnection() {

		try {
			output.close(); // close output stream
			input.close(); // close input stream
			client.close(); // close socket
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method closeConnection

	private void sendData() {
		try {
			output.writeObject(key2);
			output.flush(); // flush output to client
			db2.setText("Sent: "+output);
		} // end try
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error writing object1");
		} // end catch
	} // end method sendData

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
		db1.setHorizontalAlignment(SwingConstants.RIGHT);
		db1.setForeground(Color.GREEN);
		db1.setBounds(6, 6, 628, 16);
		
		getContentPane().add(db1);
		db2.setHorizontalAlignment(SwingConstants.RIGHT);
		db2.setForeground(Color.GREEN);
		db2.setBounds(6, 22, 628, 16);
		
		getContentPane().add(db2);

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

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (paused) {
					paused = false;
					try {
						output.writeObject(5);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						output.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					timerStart();
					((timerLabel) lblTime).start();
				} else
					paused = true;
			}// end mouseClicked
		});// end addMouseListener

		KeyListener keyListener = new KeyListener() { // 1上2下3左4右
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					key2 = 1;
					key2Get = false;
					sendData();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					key2 = 2;
					key2Get = false;
					sendData();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					key2 = 3;
					key2Get = false;
					sendData();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					key2 = 4;
					key2Get = false;
					sendData();
				}
				
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if (paused) {
						paused = false;
						try {
							output.writeObject(5);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							output.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						timerStart();
						((timerLabel) lblTime).start();
					} /*else
						paused = true;*/
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
					try {
						recieve = (String)input.readObject();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					db1.setText("Recieved: "+recieve);
					String[] array = recieve.split(",");
					x1 = Integer.parseInt(array[0]);
					y1 = Integer.parseInt(array[1]);
					w1 = Integer.parseInt(array[2]);
					h1 = Integer.parseInt(array[3]);
					x2 = Integer.parseInt(array[4]);
					y2 = Integer.parseInt(array[5]);
					w2 = Integer.parseInt(array[6]);
					h2 = Integer.parseInt(array[7]);
					Ball1.setBounds(x1, y1, w1, h1);
					Ball2.setBounds(x2, y2, w2, h2);
					
					if (touched(Ball1, Ball2)) {
						paused = true;
						lblExplosion.setBounds((Ball1.getX()+Ball2.getX()-lblExplosion.getWidth())/2, (Ball1.getY()+Ball2.getY()-lblExplosion.getHeight())/2, lblExplosion.getWidth(), lblExplosion.getHeight());
						lblExplosion.setVisible(true);
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
						    	  sound.playBombSound();
						    	  Ball1.setVisible(true);
						    	  Ball2.setVisible(true);
						    	  Ball1.setBounds(-200, 0, Ball1.getWidth(), Ball1.getHeight());
						    	  Ball2.setBounds(-200, -200, Ball2.getWidth(), Ball2.getHeight());
						    	  try {
									input.readObject();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
						    	  sound.playReadySound();
						    	  this.cancel();
						      }
						};
						timer.schedule(tt,3000,3000);
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
	
}
