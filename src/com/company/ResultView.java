package com.company;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.AbstractListModel;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResultView extends JFrame {

	private static final long serialVersionUID = 22L;
	private JPanel contentPane;

	private JList list;
	private static JTextPane textPane;
	private MainModel model;
	private MainController controller;
	
	/**
	 * Create the frame.
	 */
	public ResultView(MainModel m, MainController c) {
		
		this.model = m;
		this.controller = c;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JList list = (JList)e.getSource();
				if(e.getClickCount() == 2) {
					
					int index = list.locationToIndex(e.getPoint());
					String Risk = new String();
					String Cost = new String();
					String Mean = new String();
					String Mode = new String();
					String Shortest = new String();
					String Standard = new String();
					String Prob = new String();
					
					for (String lineTemp : controller.getLines()) {
						if(lineTemp.startsWith("Risk")) {
							
							Risk = lineTemp.split(": ")[1];
							
						} else if(lineTemp.startsWith("Cost")) {
							
							Cost = lineTemp.split(": ")[1];
							
						} else if(lineTemp.startsWith("Mean")) {
							
							Mean = lineTemp.split(": ")[1];
							
						} else if(lineTemp.startsWith("Mode")) {
							
							Mode = lineTemp.split(": ")[1];
							
						} else if(lineTemp.startsWith("Shortest")) {
							
							Shortest = lineTemp.split(": ")[1];
							
						} else if(lineTemp.startsWith("Standard")) {
							
							Standard = lineTemp.split(": ")[1];
							
						}
					}
					
					switch(index) {
					
					
					case 0:
						
						textPane.setText("");
						
						for(Arc arc: model.getArcs()) {
							
							textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Risk: " + arc.getRisk());
							
						}
						
						textPane.setText(textPane.getText() + "\n" + "Total Risk: " + Risk);
						
						
						break;					
					case 1:
						
						textPane.setText("");
						
						
						
						for(Arc arc: model.getArcs()) {
							
							textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Cost: " + arc.getCost());
							
						}
						
						textPane.setText(textPane.getText() + "\n" + "Total Cost: " + Cost);
						
						break;
					case 2:
						
						textPane.setText("");
						textPane.setText(textPane.getText() + "\n" + "Mean of attack path lengths: " + Mean);
						textPane.setText(textPane.getText() + "\n" + "Mode of attack path lengths: " + Mode);
						textPane.setText(textPane.getText() + "\n" + "Shortest attack path length: " + Shortest);
						textPane.setText(textPane.getText() + "\n" + "Standard Deviaton of attack path length: " + Standard);
						
						break;			
				
					case 3:
						
						textPane.setText("");
						
						for(Arc arc: model.getArcs()) {
							
							textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Probability: " + arc.getProbability());
							
						}
						
						textPane.setText(textPane.getText() + "\n" + "Probability of Attack Success: " + Prob);
						
						
						break;
							
					case 4:
						
						textPane.setText("");
						for (String lineTemp : controller.getLines()) {

						
							ResultView.getTextPane().setText(ResultView.getTextPane().getText() + "\n" + lineTemp);

						}
						break;
					}
					
				}
				
				
			}
		});
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Risk", "Cost", "Attack lengths", "Probability", "All Results"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(12, 41, 175, 210);
		contentPane.add(list);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(372, 16, 57, 15);
		contentPane.add(lblOutput);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(199, 41, 373, 210);
		contentPane.add(textPane);
	}

	public static JTextPane getTextPane() {
		return textPane;
	}

	public static void setTextPane(JTextPane textPane) {
		ResultView.textPane = textPane;
	}
}
