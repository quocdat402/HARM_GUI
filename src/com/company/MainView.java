package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainView extends JFrame {
    private JPanel contentPane;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JPanel westPanel;
    private ArrayList<Pair> listOfPairs;//Added for storing pairs of nodes
    private Map<String, Point> map;//Stores Jlabel added on center panel and its location.
    
    private JButton button;
    private JLabel lblNode;
    private JButton btnClear;

    public MainView() {
    	
        contentPane = new JPanel();
        listOfPairs = new ArrayList<Pair>();
        map = new LinkedHashMap<String,Point>();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setTitle("HARMs Simulator Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mnFile.add(mntmNew);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save As");
        mnFile.add(mntmSaveAs);

        JSeparator separator = new JSeparator();
        mnFile.add(separator);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);

        JMenuItem mntmCopy = new JMenuItem("Copy");
        mnEdit.add(mntmCopy);

        JMenuItem mntmPaste = new JMenuItem("Paste");
        mnEdit.add(mntmPaste);

        JSeparator separator_1 = new JSeparator();
        mnEdit.add(separator_1);

        JMenuItem mntmNewMenuItem = new JMenuItem("Undo");
        mnEdit.add(mntmNewMenuItem);

        JMenuItem mntmRedo = new JMenuItem("Redo");
        mnEdit.add(mntmRedo);

        JMenu mnOptions = new JMenu("Options");
        menuBar.add(mnOptions);

        JMenu mnView = new JMenu("View");
        menuBar.add(mnView);

        JMenu mnTools = new JMenu("Tools");
        menuBar.add(mnTools);

        JMenu mnExtentions = new JMenu("Extentions");
        menuBar.add(mnExtentions);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        southPanel = new JPanel();
        lblNode = new JLabel("Node Label");
        lblNode.setBorder(BorderFactory.createLineBorder(Color.black,1));
        southPanel.add(lblNode);       
        southPanel.setBorder(BorderFactory.createTitledBorder("Selector"));
        contentPane.add(southPanel, BorderLayout.SOUTH);
        centerPanel = new MyJPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Work Space", TitledBorder.CENTER, TitledBorder.TOP)); 
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(null);

        westPanel = new JPanel();
        contentPane.add(westPanel, BorderLayout.WEST);
        westPanel.setBorder(BorderFactory.createTitledBorder("Option"));
        btnClear = new JButton("Clear");
        
        westPanel.add(btnClear);
        button  = new JButton("DrawArc");
        
        westPanel.add(button);
    }
    
    
    
    private class MyJPanel extends JPanel//Create your own JPanel and override paintComponentMethod.
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for (Pair pair : listOfPairs )
            {
                JLabel label1 = pair.getLabel1();
                JLabel label2 = pair.getLabel2();
                Point point1 = label1.getLocation();
                Point point2 = label2.getLocation();
                int i = pair.howToDraw();
                if ( i == 1)
                {
                    g.drawLine(point1.x  , point1.y + label1.getHeight() / 2 , point2.x + label2.getWidth() , point2.y  +  label2.getHeight() / 2);
                }
                else if (i == 2)
                {
                    g.drawLine(point2.x , point2.y + label2.getHeight() / 2 , point1.x + label1.getWidth() , point1.y  +  label1.getHeight() / 2);
                }
                else if (i == 3)
                {
                    g.drawLine(point1.x + label1.getWidth() / 2 , point1.y , point2.x + label2.getWidth() / 2, point2.y + label2.getHeight());
                }
                else if (i == 4)
                {
                    g.drawLine(point2.x + label2.getWidth() / 2 , point2.y , point1.x + label1.getWidth() / 2, point1.y + label1.getHeight());
                }
            }
        }
    }
    
	public JPanel getSouthPanel() {
		return southPanel;
	}
	public void setSouthPanel(JPanel southPanel) {
		this.southPanel = southPanel;
	}
	public JPanel getCenterPanel() {
		return centerPanel;
	}
	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	public JPanel getWestPanel() {
		return westPanel;
	}
	public void setWestPanel(JPanel westPanel) {
		this.westPanel = westPanel;
	}
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}
	public JLabel getLblNode() {
		return lblNode;
	}
	public void setLblNode(JLabel lblNode) {
		this.lblNode = lblNode;
	}
	public JButton getBtnClear() {
		return btnClear;
	}
	public void setBtnClear(JButton btnClear) {
		this.btnClear = btnClear;
	}
	
	public ArrayList<Pair> getListOfPairs() {
		return listOfPairs;
	}
	public void setListOfPairs(ArrayList<Pair> listOfPairs) {
		this.listOfPairs = listOfPairs;
	}
	public Map<String, Point> getMap() {
		return map;
	}
	public void setMap(Map<String, Point> map) {
		this.map = map;
	}
	
	
	
}
