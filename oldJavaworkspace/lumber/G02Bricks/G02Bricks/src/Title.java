import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Title extends JPanel
{
	private JButton start_button;
	private JButton stage_button;
	private JButton about_button;
	private JButton exit_button;

	public Title()
	{

		setBounds(2, 2, 606, 587);
		setLayout(null);
		Font buttonFont = new Font("Arial", Font.BOLD, 20);
		start_button = new JButton("START");
		start_button.setBounds(250, 210, 122, 25);
		start_button.setFont(buttonFont);
		start_button.setBackground(new Color(127, 205, 233) );
		StartButtonHandler startButtonHandler = new StartButtonHandler();
		start_button.addActionListener(startButtonHandler);

		stage_button = new JButton("STAGE");
		stage_button.setBounds(250, 250, 122, 25);
		stage_button.setFont(buttonFont);
		stage_button.setBackground(new Color(127, 205, 233) );
		StageButtonHandler stageButtonHandler = new StageButtonHandler();
		stage_button.addActionListener(stageButtonHandler);

		about_button = new JButton("ABOUT");
		about_button.setBounds(250, 290, 122, 25);
		about_button.setFont(buttonFont);
		about_button.setBackground(new Color(127, 205, 233) );
		AboutButtonHandler aboutButtonHandler = new AboutButtonHandler();
		about_button.addActionListener(aboutButtonHandler);

		exit_button = new JButton("EXIT");
		exit_button.setBounds(250, 330, 122, 25);
		exit_button.setFont(buttonFont);
		exit_button.setBackground(new Color(127, 205, 233) );
		ExitButtonHandler exitButtonHandler = new ExitButtonHandler();
		exit_button.addActionListener(exitButtonHandler);

		add(start_button);
		add(stage_button);
		add(about_button);
		add(exit_button);

		setVisible(true);
	}

	public void paintComponent(Graphics g)
	{
		ImageIcon icon=new ImageIcon(ClassLoader.getSystemResource("img/TitleBG.png") );
		g.drawImage(icon.getImage(), 0, 0, null);
	}

	private class StartButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//MouseFun.bCount=0;
			MouseFun.stage=0;
			//MouseFun.stage0();
			//MouseFun.stage1();
			//System.out.println("stage值："+MouseFun.stage);
			//System.out.println("bcount值："+MouseFun.bCount);
			setVisible(false);
			MouseFun.panel.setVisible(true);
		}
	}

	private class StageButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object[] options = {"Stage1", "Stage2", "Stage3", "Stage4", "Stage5"};
			try
			{
				Object choice = JOptionPane.showInputDialog(null, "請選擇關卡", "STAGE", JOptionPane.QUESTION_MESSAGE,
														 null,options, options[0]);
				if(choice.equals(options[0]))
				{
					MouseFun.bCount=-1;
					MouseFun.stage = 1;
					MouseFun.stage1();
					setVisible(false);
					MouseFun.panel.setVisible(true);
				}

				if(choice.equals(options[1]))
				{
					MouseFun.bCount=0;
					MouseFun.stage=2;
					MouseFun.stage2();
					setVisible(false);
					MouseFun.panel.setVisible(true);
				}

				if(choice.equals(options[2]))
				{
					MouseFun.bCount=0;
					MouseFun.stage=3;
					MouseFun.stage3();
					setVisible(false);
					MouseFun.panel.setVisible(true);
				}

				if(choice.equals(options[3]))
				{
					MouseFun.bCount=0;
					MouseFun.stage=4;
					MouseFun.stage4();
					setVisible(false);
					MouseFun.panel.setVisible(true);
				}

				if(choice.equals(options[4]))
				{
					MouseFun.bCount=0;
					MouseFun.stage=5;
					MouseFun.stage5();
					setVisible(false);
					MouseFun.panel.setVisible(true);
				}
			}
			catch(java.lang.NullPointerException e)
			{

			}
		}
	}

	private class AboutButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JOptionPane.showMessageDialog(null, "大神工作室\n打磚塊v1.7", "關於", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
}
