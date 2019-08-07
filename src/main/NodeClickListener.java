package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NodeClickListener extends MouseAdapter {
	
	private MainPanel panel;
	
	public NodeClickListener(MainPanel panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX());
		panel.addNode(new Node(e.getX(), e.getY(), 24, Color.blue));
	}
}
