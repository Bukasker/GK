import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Transforms2D extends JPanel {

	private class Display extends JPanel {
	        protected void paintComponent(Graphics g) {
				int whichTransform = transformSelect.getSelectedIndex();
	        	super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g;
	            int centerX = getWidth() / 2;
	            int centerY = getHeight() / 2;
	            int radius = 150;
	            int sides = 12;
	            int[] xPoints = new int[sides];
	            int[] yPoints = new int[sides];
	            double angle = 2 * Math.PI / sides;
	            for (int i = 0; i < sides; i++) {
	                xPoints[i] = (int) (centerX + radius * Math.cos(i * angle));
	                yPoints[i] = (int) (centerY + radius * Math.sin(i * angle));
	            }

	            switch (whichTransform) {
                case 2: // obrót o 45 stopni
                    g2.rotate(Math.toRadians(45), centerX, centerY);
                    break;
                case 3: // obrót o 180 stopni w poziomie
                    g2.rotate(Math.PI, centerX, centerY);
                    break;
                case 4: // pochylenie o 45 stopni
                    g2.translate(-radius *2, 0);
                    g2.shear(1.0, 0.0);
                    break;
                case 5: // zmniejszenie wysokości o połowę
                	 g2.translate(0 , -radius/2);
                    g2.scale(1.0, 0.5);
                    break;
                case 6: // pochylenie o 45 stopni i obrót o 90 stopni
                    g2.translate(-radius *2, 0);
                    g2.shear(1.0, 0.0);
                    g2.rotate(Math.toRadians(90), centerX, centerY);
                    break;
                case 7: // obrót o 180 stopni w poziomie i pionie
                    g2.translate(-radius *4, -radius *4);
                    g2.rotate(Math.PI, centerX, centerY);
                    g2.scale(-1.0, -1.0);
                    break;
                case 8: // pochylenie o 45 stopni i zmniejszenie wysokości o połowę
                    g2.translate(-radius, 300);
                    g2.shear(1.0, 0.0);
                    g2.scale(1.0, 0.5);
                    break;
                case 9: // pochylenie o 45 stopni i obrót o 180 stopni w poziomie i pionie
                	g2.translate(-radius *2*5, -radius *4);
                    g2.shear(1.0, 0.0);
                    g2.rotate(Math.PI, centerX, centerY);
                    g2.scale(-1.0, -1.0);
                    break;
                    
                default: // brak transformacji
                    break;
                    
            }

	        g2.drawPolygon(xPoints, yPoints, sides);
		}
	}

	private Display display;
	private BufferedImage pic;
	private JComboBox<String> transformSelect;

	public Transforms2D() throws IOException {
		pic = ImageIO.read(getClass().getClassLoader().getResource("shuttle.jpg"));
		display = new Display();
		display.setBackground(Color.YELLOW);
		display.setPreferredSize(new Dimension(600,600));
		transformSelect = new JComboBox<String>();
		transformSelect.addItem("None");
		for (int i = 1; i < 10; i++) {
			transformSelect.addItem("No. " + i);
		}
		transformSelect.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.repaint();
			}
		});
		setLayout(new BorderLayout(3,3));
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY,10));
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		top.add(new JLabel("Transform: "));
		top.add(transformSelect);
		add(display,BorderLayout.CENTER);
		add(top,BorderLayout.NORTH);
	}


	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("2D Transforms");
		window.setContentPane(new Transforms2D());
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation( (screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2 );
		window.setVisible(true);
	}

}