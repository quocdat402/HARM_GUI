package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MainPanel extends JPanel {
	
	private List<Node> nodes = new ArrayList<Node>();
	
	public void addNode(Node node) {
		nodes.add(node);
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.drawOval(100, 100, 50, 50);
		
		for (Node n : nodes) {
			n.draw(g);
		}
		
	}

}
