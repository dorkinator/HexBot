package util;

import javax.swing.*;
import java.awt.*;

public class NodeIcon implements Icon {

	private static final int SIZE = 9;

	private char type;

	public NodeIcon(char type) {
		this.type = type;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		switch (type) {
			case '+':
			case '-':
				g.setColor(UIManager.getColor("Tree.background"));
				g.fillRect(x, y, SIZE - 1, SIZE - 1);

				g.setColor(UIManager.getColor("Tree.hash").darker());
				g.drawRect(x, y, SIZE - 1, SIZE - 1);

				g.setColor(UIManager.getColor("Tree.foreground"));
				break;
		}
		switch (type){
			case '+':
				g.drawLine(x + SIZE / 2, y + 2, x + SIZE / 2, y + SIZE - 3);
			case '-':
				g.drawLine(x + 2, y + SIZE / 2, x + SIZE - 3, y + SIZE / 2);
			case ' ':
				break;
			default:
				break;
		}
	}

	public int getIconWidth() {
		return SIZE;
	}

	public int getIconHeight() {
		return SIZE;
	}
}
