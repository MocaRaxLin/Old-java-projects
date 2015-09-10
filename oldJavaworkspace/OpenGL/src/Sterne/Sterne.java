package Sterne;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FillLayout;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.swt.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

public class Sterne implements KeyListener{
	
	private Display display;
	private Shell shell;
	private Composite composite;
	private boolean is_fullscreen = false; 

	//�e�b�o�W��XD
	private GLCanvas glcanvas;
	//�ثe����
	private GLProfile glprofile;
	
	//�e�Ϥ��e
	private Render render;
	
	private Time time = new Time();
	private Timer timer = new Timer();
	
	public void start(){
		
		display = new Display();
		shell = new Shell(display);
		shell.setText("Sterne");
        shell.setLayout(new FillLayout());
        shell.setSize(1024, 768);
        
        
        composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new FillLayout());
        
        //�]�w�����w��
        glprofile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glprofile);
        capabilities.setDoubleBuffered(true);
        
        glcanvas = new GLCanvas(composite, SWT.NO_BACKGROUND, capabilities, null, null);
        
        render = new Render();
        render.setTime(time);
        glcanvas.addGLEventListener(render);
        glcanvas.addMouseMoveListener(render);
        glcanvas.addMouseListener(render);
        
        //�[�J��L�ƹ�������
        //shell.addKeyListener(this);
        shell.addMouseWheelListener(render);
        
        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();
        
        // �p�ɾ�
        timer.schedule(new TimerTask(){
        	@Override
        	public void run(){
        		time.addSecond(8640 * 3);
        	}
        }, 0, 10 * 3);
         
        //�����}��
        shell.open();

        while(!shell.isDisposed()){
            if(!display.readAndDispatch())
                display.sleep();
        }

        timer.cancel();
        timer.purge();
        animator.stop();
        display.dispose();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		//���ù��H�Ψ���
		if(is_fullscreen && (key.keyCode == SWT.ESC || key.character == 'f')){
			shell.setFullScreen(false);
			is_fullscreen = false;
		}
		else if(!is_fullscreen && key.character == 'f'){
			shell.setFullScreen(true);
			is_fullscreen = true;
		}
		if(key.character == '8'){//���C
			render.pitchdown();
		}
		if(key.character == '5'){//�԰�
			render.pitchup();
		}
		if(key.character == '4'){//���u
			render.rowr();
		}
		if(key.character == '6'){//�k�u
			render.rowl();
		}
		if(key.character == '7'){//���j��
			render.yawl();
		}
		if(key.character == '9'){//�k�j��
			render.yawr();
		}
		if(key.character == 'A'){//�[�t
			render.addspeed();
		}
		if(key.character == 'S'){//��t			
			render.minusspeed();
		}
		if(key.character == 'w'){//��t			
			render.watch();
		}
		if(key.character == ' '){//��t			
			render.beam();		
		}	
	}
	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub	
	}
}
