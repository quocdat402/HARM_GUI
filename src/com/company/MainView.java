package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.company.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MainView extends JFrame {
    private JPanel contentPane;
    private JPanel northPanel;
    private MyJPanel centerPanel;
    private ArrayList<Pair> listOfPairs;//Added for storing pairs of nodes
    private ArrayList<JLabel> nodeArray;
    private Map<String, Point> map;//Stores Jlabel added on center panel and its location.
    
    private JButton button;
    private JLabel lblNode;
    private JButton btnClear;
    private JButton btnArc;
    private JButton btnNode;
    private List<Node> nodes;
    private Map<Node, Point> nodesInfo;
    private List<Arc> arcs;
    private JButton btnMove;
    private JButton btnDelete;

    public MainView() {
    	
        contentPane = new JPanel();
        
        listOfPairs = new ArrayList<Pair>();
        nodeArray = new ArrayList<JLabel>();
        map = new LinkedHashMap<String,Point>();
        nodes = new ArrayList<Node>();
        arcs = new ArrayList<Arc>();
        nodesInfo = new LinkedHashMap<Node, Point>();
        
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

        northPanel = new JPanel();
        
        btnNode = new JButton("Node");
        btnNode.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnNode);
        
        btnArc = new JButton("Arc");
        btnArc.setBackground(Color.LIGHT_GRAY);
        
        northPanel.add(btnArc);
        
        btnMove = new JButton("Move");
        btnMove.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnMove);
        
        btnDelete = new JButton("Delete");
        btnDelete.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnDelete);
        btnClear = new JButton("Clear");
        btnClear.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnClear);
        lblNode = new JLabel("Node Label");
        lblNode.setBorder(BorderFactory.createLineBorder(Color.black,1));
        northPanel.add(lblNode);       
        northPanel.setBorder(BorderFactory.createTitledBorder("Selector"));
        contentPane.add(northPanel, BorderLayout.NORTH);
        button  = new JButton("DrawArc");
        northPanel.add(button);
        centerPanel = new MyJPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Work Space", TitledBorder.CENTER, TitledBorder.TOP)); 
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(null);
    }
    
    private class MyJPanel extends JPanel//Create your own JPanel and override paintComponentMethod.
    {
        
    	/*
    	@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for (Pair pair : listOfPairs ) {
                JLabel label1 = pair.getLabel1();
                JLabel label2 = pair.getLabel2();
                Point point1 = label1.getLocation();
                Point point2 = label2.getLocation();
                g.drawLine(point1.x, point1.y, point2.x, point2.y);
            }
        }
        */
    	
    	
         
    	
    	
    	@Override
    	public void paintComponent(Graphics g) {
    				
    		super.paintComponent(g);
    		
    		for (Node n : nodes) {
    			n.draw(g);
    		}
    		
    		for (Arc a : arcs) {
    			a.drawLine(g);
    		}
    	}
    }
    
	public JPanel getNorthPanel() {
		return northPanel;
	}
	public void setNorthPanel(JPanel southPanel) {
		this.northPanel = southPanel;
	}
	public JPanel getCenterPanel() {
		return centerPanel;
	}
	public void setCenterPanel(MyJPanel centerPanel) {
		this.centerPanel = centerPanel;
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
	public JButton getBtnArc() {
		return btnArc;
	}
	public void setBtnArc(JButton btnArc) {
		this.btnArc = btnArc;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public JButton getBtnNode() {
		return btnNode;
	}
	public void setBtnNode(JButton btnNode) {
		this.btnNode = btnNode;
	}
	public List<Arc> getArcs() {
		return arcs;
	}
	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
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
	
	
	
}
