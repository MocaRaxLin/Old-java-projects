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

	//畫在這上面XD
	private GLCanvas glcanvas;
	//目前未知
	private GLProfile glprofile;
	
	//畫圖內容
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
        
        //設定雙重緩衝
        glprofile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glprofile);
        capabilities.setDoubleBuffered(true);
        
        glcanvas = new GLCanvas(composite, SWT.NO_BACKGROUND, capabilities, null, null);
        
        render = new Render();
        render.setTime(time);
        glcanvas.addGLEventListener(render);
        glcanvas.addMouseMoveListener(render);
        glcanvas.addMouseListener(render);
        
        //加入鍵盤滑鼠等控制
        //shell.addKeyListener(this);
        shell.addMouseWheelListener(render);
        
        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();
        
        // 計時器
        timer.schedule(new TimerTask(){
        	@Override
        	public void run(){
        		time.addSecond(8640 * 3);
        	}
        }, 0, 10 * 3);
         
        //視窗開啟
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
		//全螢幕以及取消
		if(is_fullscreen && (key.keyCode == SWT.ESC || key.character == 'f')){
			shell.setFullScreen(false);
			is_fullscreen = false;
		}
		else if(!is_fullscreen && key.character == 'f'){
			shell.setFullScreen(true);
			is_fullscreen = true;
		}
		if(key.character == '8'){//壓低
			render.pitchdown();
		}
		if(key.character == '5'){//拉高
			render.pitchup();
		}
		if(key.character == '4'){//左滾
			render.rowr();
		}
		if(key.character == '6'){//右滾
			render.rowl();
		}
		if(key.character == '7'){//左迴旋
			render.yawl();
		}
		if(key.character == '9'){//右迴旋
			render.yawr();
		}
		if(key.character == 'A'){//加速
			render.addspeed();
		}
		if(key.character == 'S'){//減速			
			render.minusspeed();
		}
		if(key.character == 'w'){//減速			
			render.watch();
		}
		if(key.character == ' '){//減速			
			render.beam();		
		}	
	}
	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub	
	}
}
