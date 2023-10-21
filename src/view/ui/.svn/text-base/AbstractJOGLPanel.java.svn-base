package view.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;


/**
 * algemeine Klasse für ein Panel mit einem OpenGL Canvas 
 * Grundlage für Panels, die OpenGL Grafiken darstellen sollen
 *
 */
public abstract class AbstractJOGLPanel extends AbstractPanel implements
		GLEventListener {

	public GLCanvas canvas;
	protected Animator animator;
	
	protected int panelWidth=0;
	protected int panelHeight=0;
	
	/**
	 * Breite des Panels auslesen
	 * @return Breite des Panels
	 */
	public int getPanelWidth() {
		return panelWidth;
	}
	
	/**
	 * Breite des Panels setzen
	 * @return Breite des Panels
	 */
	public int getPanelHeight() {
		return panelHeight;
	}

	/**
	 * Initialisierung des JOGL Canvas
	 * Den Animator starten, damit regelmässig die display Methode aufgerufen wird
	 */
	public AbstractJOGLPanel() {
		setLayout(new java.awt.BorderLayout());
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		animator = new Animator(canvas);
		animator.start();
		this.add(canvas);
	}


	@Override
	/**
	 * Hier beginnt das Zeichnen der OpenGL Elemente
	 * Aufruf der abstrakten Methode Draw 
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glLoadIdentity(); // Reset The View
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	
		this.Draw(drawable);

		gl.glFlush();
	}

	@Override
	/**
	 * Anpassungen bei Größenänderung des Fensters
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		GLU glu = new GLU();
		GL gl = drawable.getGL();
		
		this.panelHeight=h;
		this.panelWidth=w;

		if (h <= 0) // avoid a divide by zero error!
			h = 1;
		float a = (float) w / (float) h;
		//float a = (float) h / (float) w;
		//float a=1.0f;
		gl.glViewport(0, 0, w, h);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, a, 1.0, 20.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity(); // Reset The ModalView Matrix

	}

	@Override
	/**
	 * JOGL initialisieren 
	 */
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

		GL gl = drawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL.GL_NORMALIZE | GL.GL_DEPTH_TEST); // Enables Depth
		// Testing
		gl.glDepthFunc(GL.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		gl.glEnable(GL.GL_ALPHA_TEST);
		gl.glAlphaFunc(GL.GL_GREATER, 0.1f);

	}

	@Override	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	/** 
	 * Abstrakte Methode für übergeordnenten Klasse für das Zeichnen
	 */
	public abstract void Draw(GLAutoDrawable drawable);

	/**
	 * OpenGL Darstellung anhalten
	 */
	public void stopAnimator() {
		this.animator.stop();
	}

	/**
	 * OpenGL Darstellung fortsetzen
	 */
	public void startAnimator() {
		this.animator.start();
	}
	
	/**
	 * einen erweiterbaren Float Buffer anlegen
	 * @param array erweiterbarer Float Buffer 
	 * @return
	 */
	protected FloatBuffer makeDirectFloatBuffer(float[] array) {
		int len = array.length * (Float.SIZE / 8);
		ByteBuffer storage = ByteBuffer.allocateDirect(len);
		storage.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = storage.asFloatBuffer();
		buffer.put(array);
		buffer.position(0);
		return buffer;
	}
}
