package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
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
	
	String Risk = new String();
	String Cost = new String();
	String Mean = new String();
	String Mode = new String();
	String Shortest = new String();
	String Standard = new String();
	String Prob = new String();
	
	
	/**
	 * Create the Resut frame.
	 */
	@SuppressWarnings("unchecked")
	public ResultView(MainModel m, MainController c) {
		
		this.model = m;
		this.controller = c;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		list = new JList<>();

		list.setCellRenderer(new CustomListCellRenderer());
		list.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 5));
		list.setBackground(new Color(240, 240, 240));
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JList list = (JList)e.getSource();
				if(e.getClickCount() == 2) {
					
					int index = list.locationToIndex(e.getPoint());
					
					//Divide out put lines from the HARMs engine by Items(risk, cost, mean, mode...)
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
							
						} else if(lineTemp.startsWith("Probability")) {
							
							Prob = lineTemp.split(": ")[1];
							
						}
					}
					
					listClickAction(index);
					
				}
				
				
			}
		});
		//Add menus in the list

		list.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"Risk", "Cost", "Attack Lengths", "Probability", "All Results"};
		
			public int getSize() {
				return values.length;
			}
		
			public String getElementAt(int index) {
				return values[index];
			}
		});
		
		list.setBounds(12, 41, 175, 210);
		contentPane.add(list);
		
		//JLabel lblOutput = new JLabel("Output");
		//lblOutput.setBounds(372, 16, 57, 15);
		//contentPane.add(lblOutput);

		JScrollPane listScrollPane = new JScrollPane(list);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(199, 41, 373, 210);
		contentPane.add(textPane);

		JScrollPane textScrollPane = new JScrollPane(textPane);
		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, textScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200); 

		contentPane.add(splitPane, BorderLayout.CENTER);

		JLabel lblOutput = new JLabel("Report Information");
		lblOutput.setBounds(372, 16, 57, 15);
    	contentPane.add(lblOutput, BorderLayout.NORTH);
	}

	//When the user double click item in the list, the textPane shows related outputs.
	public void listClickAction(int index) {
		switch(index) {					
		case 0:
			textPane.setText("");		
			for(Arc arc: model.getArcs()) {	
				textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Risk: " + String.format("%.2f", arc.getRisk()));		
			}		
			textPane.setText(textPane.getText() + "\n" + "-----------------------" + "\n" + "Total Risk: " + String.format("%.2f", Double.parseDouble(Risk)));
			break;					
		case 1:		
			textPane.setText("");					
			for(Arc arc: model.getArcs()) {
				textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Cost: " + String.format("%.2f", arc.getCost()));				
			}		
			textPane.setText(textPane.getText() + "\n" + "-----------------------" + "\n" + "Total Cost: " + String.format("%.2f", Double.parseDouble(Cost)));
			
			break;
		case 2:			
			textPane.setText("");
			textPane.setText(textPane.getText() + "\n" + "Mean of attack path lengths: " + Mean);
			textPane.setText(textPane.getText() + "\n" + "-----------------------");
			textPane.setText(textPane.getText() + "\n" + "Mode of attack path lengths: " + Mode);
			textPane.setText(textPane.getText() + "\n" + "-----------------------");
			textPane.setText(textPane.getText() + "\n" + "Shortest attack path length: " + Shortest);
			textPane.setText(textPane.getText() + "\n" + "-----------------------");
			textPane.setText(textPane.getText() + "\n" + "Standard Deviaton of attack path length: " + Standard);			
			break;			
	
		case 3:
			textPane.setText("");
			for (Arc arc : model.getArcs()) {
				textPane.setText(textPane.getText() + "\n" + "Node " + arc.getNumber() + " Probability: " + String.format("%.2f", arc.getProbability()));
			}
			textPane.setText(textPane.getText() + "\n" + "-----------------------" + "\n" + "Probability of Attack Success: " + String.format("%.2f", Double.parseDouble(Prob)));
			break;
				
		case 4:		
			textPane.setText("");			
			for (String lineTemp : controller.getLines()) {			
				ResultView.getTextPane().setText(ResultView.getTextPane().getText() + "\n" + lineTemp);
			}			
			break;
		}
		
		
	}
	
	/*
	 * Getters and Setters
	 */
	public static JTextPane getTextPane() {
		return textPane;
	}

	public static void setTextPane(JTextPane textPane) {
		ResultView.textPane = textPane;
	}
}
