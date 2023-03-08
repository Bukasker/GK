import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LetterZ extends JPanel {
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);

    // draw first square
    g.fillRect(-60, 0, 300, 10);

    // draw second square
    Graphics2D g2d = (Graphics2D) g;
    AffineTransform originalTransform = g2d.getTransform(); // save the original transform
    g2d.rotate(Math.toRadians(-45), 25*3,125*3); // rotate by 45 degrees around (125, 125)
    g2d.fillRect(25*3, 75*3, 425, 10);
    g2d.setTransform(originalTransform); // restore the original transform

    // draw third square
    g.fillRect(-50, 250, 300, 10);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Letter Z");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new LetterZ());
    frame.setSize(270, 300);
    frame.setVisible(true);
  }
}