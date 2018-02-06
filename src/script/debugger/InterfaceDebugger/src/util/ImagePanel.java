package util;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel{

	private BufferedImage image;

	public ImagePanel(BufferedImage image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null) {
			g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
		}
	}

	public void setImage(BufferedImage image){
		this.image = image;
		if(image != null) {
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}else{
			setPreferredSize(new Dimension());
		}
	}
}