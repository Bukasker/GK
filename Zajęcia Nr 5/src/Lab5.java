//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

class Lab5 extends GLJPanel implements GLEventListener, KeyListener {
	private int objectNumber = 1;
	private boolean useAnaglyph = false;
	private int rotateX = 0;
	private int rotateY = 0;
	private int rotateZ = 0;
	private GLUT glut = new GLUT();
	private GLU glu = new GLU();
	private double radius = 5.0D;
	private double angle = 0.0D;
	private float x = 0.0F;
	private float z = 0.0F;

	public static void main(String[] args) {
		JFrame window = new JFrame("Some Objects in 3D");
		Lab5 panel = new Lab5();
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setLocation(100, 50);
		window.setDefaultCloseOperation(3);
		window.setVisible(true);
	}

	public Lab5() {
		super(new GLCapabilities((GLProfile) null));
		this.setPreferredSize(new Dimension(700, 700));
		this.addGLEventListener(this);
		this.addKeyListener(this);
	}

	private void draw(GL2 gl2) {
		gl2.glRotatef((float) this.rotateZ, 0.0F, 0.0F, 1.0F);
		gl2.glRotatef((float) this.rotateY, 0.0F, 1.0F, 0.0F);
		gl2.glRotatef((float) this.rotateX, 1.0F, 0.0F, 0.0F);
		if (this.objectNumber == 1) {
			this.drawCorkscrew(gl2);
		} else if (this.objectNumber == 2) {
			this.drawPyramid(gl2);
		}
		// wybor obracanego obiektu
	}

	public void drawPyramid(GL2 gl2) {
		double radius = 3.0D;
		int corners = 15; // liczba scian
		float[] c1 = new float[]{1.0F, 0.0F, 0.0F}; // kolor bazowy piramidy
		float[] c2 = new float[]{0.8F, 0.0F, 0.0F}; // kolor cieniowania
		float topHeight = 3.0F; // wysokosc piramidy gĂłrna
		float bottomHeight = -4.0F; // wysokosc piramidy dolna
		gl2.glBegin(6);
		gl2.glVertex3f(0.0F, topHeight, 0.0F);
		gl2.glVertex3f(0.0F, bottomHeight, 0.0F);

		int i;
		for (i = 0; i < corners; ++i) {
			gl2.glVertex3f((float) (Math.cos((double) (i * 2) * Math.PI / (double) corners) * radius), bottomHeight, (float) (Math.sin((double) (i * 2) * Math.PI / (double) corners) * radius));
			gl2.glColor3fv(c1, 0);
			gl2.glVertex3f((float) (Math.cos((double) ((i + 1) * 2) * Math.PI / (double) corners) * radius), bottomHeight, (float) (Math.sin((double) ((i + 1) * 2) * Math.PI / (double) corners) * radius));
			gl2.glColor3fv(c2, 0);
		}

		gl2.glEnd();
		gl2.glBegin(9);
		gl2.glVertex3f(0.0F, bottomHeight, 0.0F);

		for (i = 0; i < corners; ++i) {
			gl2.glVertex3f((float) (Math.cos((double) (i * 2) * Math.PI / (double) corners) * radius), bottomHeight, (float) (Math.sin((double) (i * 2) * Math.PI / (double) corners) * radius));
			gl2.glColor3fv(c1, 0);
			gl2.glVertex3f((float) (Math.cos((double) ((i + 1) * 2) * Math.PI / (double) corners) * radius), bottomHeight, (float) (Math.sin((double) ((i + 1) * 2) * Math.PI / (double) corners) * radius));
			gl2.glColor3fv(c2, 0);
		}

		gl2.glEnd();
	}

	public void drawCorkscrew(GL2 gl2) {
		gl2.glEnable(2832); // jakosc renderowania
		gl2.glColor3f(1.0F, 0.0F, 0.0F); // kolor spirali
		float corners = 20.0F; // odleglosc miedzy kolejnym pierscieniem
		float radius = 1.5F; // szerokosc pierscienia
		float uplift = -5.0F; // punkt startu
		float pointSize = 4.0F; // grubosc punktow tworzacych spirale
		int numberOfSpirals = 12; // liczba "pierscieni"

		for (int j = 0; j < numberOfSpirals; ++j) {
			for (int i = 1; (float) i <= corners; ++i) {
				float x = (float) ((double) radius * Math.sin(Math.PI / (double) (corners / 2.0F) * (double) i));
				float y = (float) ((double) radius * Math.cos(Math.PI / (double) (corners / 2.0F) * (double) i));
				gl2.glPointSize(pointSize);
				gl2.glBegin(0);
				gl2.glVertex3f(x, uplift, y);
				gl2.glEnd();
				uplift = (float) ((double) uplift + 0.05D);
				pointSize = (float) ((double) pointSize + 0.04D);
			}
		}

	}


	public void display(GLAutoDrawable drawable) {

		GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.

		if (useAnaglyph) {
			gl2.glDisable(GL2.GL_COLOR_MATERIAL); // in anaglyph mode, everything is drawn in white
			gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{1,1,1,1}, 0);
		}
		else {
			gl2.glEnable(GL2.GL_COLOR_MATERIAL);  // in non-anaglyph mode, glColor* is respected
		}
		gl2.glNormal3f(0,0,1); // (Make sure normal vector is correct for object 1.)

		gl2.glClearColor( 0, 0, 0, 1 ); // Background color (black).
		gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );


		if (useAnaglyph == false) {
			gl2.glLoadIdentity(); // Make sure we start with no transformation!
			gl2.glTranslated(0,0,-15);  // Move object away from viewer (at (0,0,0)).
			draw(gl2);
		}
		else {
			gl2.glLoadIdentity(); // Make sure we start with no transformation!
			gl2.glColorMask(true, false, false, true);
			gl2.glRotatef(4,0,1,0);
			gl2.glTranslated(1,0,-15);
			draw(gl2);  // draw the current object!
			gl2.glColorMask(true, false, false, true);
			gl2.glClear(GL2.GL_DEPTH_BUFFER_BIT);
			gl2.glLoadIdentity();
			gl2.glRotatef(-4,0,1,0);
			gl2.glTranslated(-1,0,-15);
			gl2.glColorMask(false, true, true, true);
			draw(gl2);
			gl2.glColorMask(true, true, true, true);
		}

	} // end display()
	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glFrustum(-3.5, 3.5, -3.5, 3.5, 5, 25);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[]{0.7f, 0.7f, 0.7f}, 0);
		gl2.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glLineWidth(3);  // make wide lines for the stellated dodecahedron.
	}

	public void dispose(GLAutoDrawable drawable) {
		// called when the panel is being disposed
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// called when user resizes the window
	}

	// ----------------  Methods from the KeyListener interface --------------

	/**
	 * Responds to keypressed events.  The four arrow keys control the rotations
	 * about the x- and y-axes.  The PageUp and PageDown keys control the rotation
	 * about the z-axis.  The Home key resets all rotations to zero.  The number
	 * keys 1, 2, 3, 4, 5, and 6 select the current object number.  Pressing the space
	 * bar toggles anaglyph stereo on and off.  The panel is redrawn to reflect the
	 * change.
	 */
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		boolean repaint = true;
		if (key == KeyEvent.VK_LEFT)
			rotateY -= 6;
		else if (key == KeyEvent.VK_RIGHT)
			rotateY += 6;
		else if (key == KeyEvent.VK_DOWN)
			rotateX += 6;
		else if (key == KeyEvent.VK_UP)
			rotateX -= 6;
		else if (key == KeyEvent.VK_PAGE_UP)
			rotateZ += 6;
		else if (key == KeyEvent.VK_PAGE_DOWN)
			rotateZ -= 6;
		else if (key == KeyEvent.VK_HOME)
			rotateX = rotateY = rotateZ = 0;
		else if (key == KeyEvent.VK_1)
			objectNumber = 1;
		else if (key == KeyEvent.VK_2)
			objectNumber = 2;
		else if (key == KeyEvent.VK_3)
			objectNumber = 3;
		else if (key == KeyEvent.VK_4)
			objectNumber = 4;
		else if (key == KeyEvent.VK_5)
			objectNumber = 5;
		else if (key == KeyEvent.VK_6)
			objectNumber = 6;
		else if (key == KeyEvent.VK_SPACE)
			useAnaglyph = !useAnaglyph;
		else
			repaint = false;
		if (repaint)
			repaint();
	}

	public void keyReleased(KeyEvent evt) {
	}

	public void keyTyped(KeyEvent evt) {
	}
}
