package main;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class View {

	private JFrame frame;
	private JPanel mainPanel;
	private JButton btnAddNodes, btnAddArc, btnMove, btnDelete;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem exit, about;
	private Image nodeImage, arcImage;
	private MainPanel MainPanel;
	//private List<Node> nodes = new ArrayList<Node>();
	
	public View() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		MainPanel = new MainPanel();
		MainPanel.setBounds(20, 60, 780, 420);
		frame.getContentPane().add(MainPanel);
		MainPanel.setLayout(null);
		MainPanel.addMouseListener(new NodeClickListener(MainPanel));
		
		//mainPanel = new JPanel();
		//mainPanel.setBounds(20, 60, 780, 420);
		//frame.getContentPane().add(mainPanel);
		//mainPanel.setLayout(null);		
					
		btnAddNodes = new JButton("Nodes");
		nodeImage = new ImageIcon(this.getClass().getResource("/node.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAddNodes.setIcon(new ImageIcon(nodeImage));
		btnAddNodes.setBackground(Color.LIGHT_GRAY);
		btnAddNodes.setBounds(20, 10, 100, 30);
		frame.getContentPane().add(btnAddNodes);
		
		btnAddArc = new JButton("Arc");
		arcImage = new ImageIcon(this.getClass().getResource("/arc.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAddArc.setIcon(new ImageIcon(arcImage));
		btnAddArc.setBackground(Color.LIGHT_GRAY);
		btnAddArc.setBounds(140, 10, 100, 30);
		frame.getContentPane().add(btnAddArc);
		
		btnMove = new JButton("Move");
		btnMove.setBackground(Color.LIGHT_GRAY);
		btnMove.setBounds(260, 10, 100, 30);
		frame.getContentPane().add(btnMove);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.LIGHT_GRAY);
		btnDelete.setBounds(380, 10, 100, 30);
		frame.getContentPane().add(btnDelete);
				
		frame.setBounds(100, 100, 840, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		file = new JMenu("File");
		menuBar.add(file);
		
		exit = new JMenuItem("Exit");
		file.add(exit);
		
		help = new JMenu("Help");
		menuBar.add(help);
		
		about = new JMenuItem("About");
		help.add(about);
		
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JButton getBtnAddNodes() {
		return btnAddNodes;
	}

	public void setBtnAddNodes(JButton btnAddNodes) {
		this.btnAddNodes = btnAddNodes;
	}

	public JButton getBtnAddArc() {
		return btnAddArc;
	}

	public void setBtnAddArc(JButton btnAddArc) {
		this.btnAddArc = btnAddArc;
	}

	public JButton getBtnMove() {
		return btnMove;
	}

	public void setBtnMove(JButton btnMove) {
		this.btnMove = btnMove;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getFile() {
		return file;
	}

	public void setFile(JMenu file) {
		this.file = file;
	}

	public JMenu getHelp() {
		return help;
	}

	public void setHelp(JMenu help) {
		this.help = help;
	}

	public JMenuItem getExit() {
		return exit;
	}

	public void setExit(JMenuItem exit) {
		this.exit = exit;
	}

	public JMenuItem getAbout() {
		return about;
	}

	public void setAbout(JMenuItem about) {
		this.about = about;
	}

	public Image getNodeImage() {
		return nodeImage;
	}

	public void setNodeImage(Image nodeImage) {
		this.nodeImage = nodeImage;
	}

	public Image getArcImage() {
		return arcImage;
	}

	public void setArcImage(Image arcImage) {
		this.arcImage = arcImage;
	}
	
	
	
	
	
	
}
