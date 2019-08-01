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

public class View {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(20, 60, 780, 420);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
					
		JButton btnAddNodes = new JButton("Nodes");
		btnAddNodes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Image nodeImage = new ImageIcon(this.getClass().getResource("/node.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				JLabel nodeLabel = new JLabel();
				nodeLabel.setIcon(new ImageIcon(nodeImage));
				
				mainPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						nodeLabel.setBounds(e.getX(),e.getY(),50,30);
						mainPanel.add(nodeLabel);
						mainPanel.setBackground(new Color(240,240,240));
					}
				});
				
				//nodeLabel.setBounds(20,20,50,30);
				//mainPanel.add(nodeLabel);
				//mainPanel.setBackground(new Color(240,240,240));
				//System.out.println(e.getX() + ", " + e.getY());
			}
		});
		Image nodeImage = new ImageIcon(this.getClass().getResource("/node.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAddNodes.setIcon(new ImageIcon(nodeImage));
		btnAddNodes.setBackground(Color.LIGHT_GRAY);
		btnAddNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddNodes.setBounds(20, 10, 100, 30);
		frame.getContentPane().add(btnAddNodes);
		
		JButton btnAddArc = new JButton("Arc");
		Image arcImage = new ImageIcon(this.getClass().getResource("/arc.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAddArc.setIcon(new ImageIcon(arcImage));
		btnAddArc.setBackground(Color.LIGHT_GRAY);
		btnAddArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddArc.setBounds(140, 10, 100, 30);
		frame.getContentPane().add(btnAddArc);
		
		JButton btnMove = new JButton("Move");
		btnMove.setBackground(Color.LIGHT_GRAY);
		btnMove.setBounds(260, 10, 100, 30);
		frame.getContentPane().add(btnMove);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.LIGHT_GRAY);
		btnDelete.setBounds(380, 10, 100, 30);
		frame.getContentPane().add(btnDelete);
				
		frame.setBounds(100, 100, 840, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		
		JMenuItem about = new JMenuItem("About");
		help.add(about);
	}
}
