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
	
	/**
	 * Create the frame.
	 */
	public ResultView() {
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
					System.out.println(index);
					
				}
				
				
			}
		});
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Risk", "Cost", "Mean of attack path lengths", "Mode of attack path lengths", "Shortest attack path length", "Return of Attack", "Probability of Attack", "All Results"};
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
