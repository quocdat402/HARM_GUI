package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Controller {
	
	private View view;
	private Model model;
	
	public Controller(Model m, View v) {
		
		model = m;
		view = v;
		initView();
	}
	
	public void initView() {
		
	}
	
	public void initController() {
		view.getBtnAddNodes().addActionListener(e -> addNodes());
		
	}
	
	private void addNodes() {
		Image nodeImage = new ImageIcon(this.getClass().getResource("/node.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		JLabel nodeLabel = new JLabel();
		nodeLabel.setIcon(new ImageIcon(nodeImage));
		
		view.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nodeLabel.setBounds(e.getX(),e.getY(),20,20);
				view.getMainPanel().add(nodeLabel);
				view.getMainPanel().setBackground(new Color(240,240,240));
			}
		});
	}

}
