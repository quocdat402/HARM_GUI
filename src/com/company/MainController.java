package com.company;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.company.SimulatorGUI.MyMouseAdapter;

public class MainController {

	private SimulatorGUI simulatorGUI;
	private MainModel model;
	private int counter = 0;//To know the number of routers pasted on center panel
	private int clickCount = 0;
	
	public MainController(MainModel m,SimulatorGUI sGUI) {
		
		simulatorGUI = sGUI;
		model = m;
		
		initView();
		
	}
	
	public void initView() {
		
	}
	
	public void initController() {
		
		
		
		simulatorGUI.getBtnClear().addActionListener(e -> clearAll());
		
		simulatorGUI.getButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                String[] nodes = showGUIForNodeSelection();
                if (nodes == null || nodes[0]==null || nodes.length == 0 )
                {}
                else if (!nodes[0].equals(nodes[1]))
                {
                    String split[] = nodes[0].split(",");
                    Point p1 = new Point(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
                    split = nodes[1].split(",");
                    Point p2 = new Point(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
                    JLabel label1 = (JLabel)simulatorGUI.getCenterPanel().getComponentAt(p1);
                    JLabel label2 = (JLabel)simulatorGUI.getCenterPanel().getComponentAt(p2);
                    Pair pair = new Pair(label1,label2);
                    simulatorGUI.getListOfPairs().add(pair);
                    simulatorGUI.getCenterPanel().repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Nodes can't be same","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
		
		
		
	}
	
	private void clearAll() {
		
		simulatorGUI.getBtnClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	simulatorGUI.getCenterPanel().removeAll();
            	simulatorGUI.getCenterPanel().validate();
            	simulatorGUI.getCenterPanel().repaint();
            	simulatorGUI.getListOfPairs().clear();
            	simulatorGUI.getMap().clear();
            	simulatorGUI.setCounter(0);
            }
        });
		
	}
	
	private String[] showGUIForNodeSelection()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(simulatorGUI.getMap().size(),2));
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        final String nodes[] = new String[2];
        Set<String> keySet = simulatorGUI.getMap().keySet();
        for (String name : keySet)
        {
            JRadioButton rButton = new JRadioButton(name);
            rButton.setActionCommand(simulatorGUI.getMap().get(name).x+","+simulatorGUI.getMap().get(name).y);
            rButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    nodes[0] = ((JRadioButton)evt.getSource()).getActionCommand();
                }
            });
            group1.add(rButton);
            panel.add(rButton);
            JRadioButton rButton1 = new JRadioButton(name);
            rButton1.setActionCommand(simulatorGUI.getMap().get(name).x+","+simulatorGUI.getMap().get(name).y);
            rButton1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    nodes[1] = ((JRadioButton)evt.getSource()).getActionCommand();
                }
            });
            group2.add(rButton1);
            panel.add(rButton1);
        }
        JOptionPane.showMessageDialog(null,panel,"Choose the nodes pair",JOptionPane.INFORMATION_MESSAGE);
        return nodes;
    }
	
	
}
