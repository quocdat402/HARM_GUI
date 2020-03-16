package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import com.company.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
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
    private JButton btnClear;
    private JButton btnArc;
    private JButton btnNode;
    private List<Node> nodes;
    private List<Arc> arcs;
    private JButton btnMove;
    private JButton btnDelete;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnGetinfo;
    private JPopupMenu nodePopUp;
    private JPopupMenu arcPopUp;
    
    private JMenuItem nodeProperties;
    private JMenuItem arcProperties;
    
    private JFrame nodeFrame;    
    private JFrame arcFrame;
    
    private JPanel arcPanel;
    private JPanel nodePanel;
    
    private JLabel lblVul;
    private JTextField txtVul;
    private JButton btnVul;
    
    private JLabel lblName;
    private JTextField txtName;
    private JButton btnName;
    
    private JMenuItem mntmOpen;
    private JMenuItem mntmSave;
    
    private JMenuItem mntmAttackgraph;
    
    private UndoManager undoManager = new UndoManager();

    public MainView() {
    	
        contentPane = new JPanel();             
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setTitle("HARMs Simulator Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        nodes = new ArrayList<Node>();
        arcs = new ArrayList<Arc>();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mnFile.add(mntmNew);

        mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);

        mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save As");
        mnFile.add(mntmSaveAs);

        JSeparator separator = new JSeparator();
        mnFile.add(separator);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        JMenu mnOptions = new JMenu("Options");
        menuBar.add(mnOptions);

        JMenu mnView = new JMenu("View");
        menuBar.add(mnView);

        JMenu mnTools = new JMenu("Tools");
        menuBar.add(mnTools);
        
        mntmAttackgraph = new JMenuItem("Attack Graph");
        mnTools.add(mntmAttackgraph);
        
        
        JMenu mnExtentions = new JMenu("Extentions");
        menuBar.add(mnExtentions);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        northPanel = new JPanel();
        
        btnUndo = new JButton("Undo");
        btnUndo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnUndo);
        
        btnRedo = new JButton("Redo");
        btnRedo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnRedo);
        
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
        
        btnGetinfo = new JButton("GetInfo");
        btnGetinfo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnGetinfo);
        northPanel.setBorder(BorderFactory.createTitledBorder("Selector"));
        contentPane.add(northPanel, BorderLayout.NORTH);
        centerPanel = new MyJPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Work Space", TitledBorder.CENTER, TitledBorder.TOP)); 
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(null);
        
        nodeFrame = new JFrame();
        nodeFrame.setSize(300, 300);
        nodeFrame.setTitle("Node");
        
        arcFrame = new JFrame();
        arcFrame.setSize(300, 300);
        arcFrame.setTitle("Arc");        
        //arcFrame.setLayout(null);
        
        
        
        nodePopUp = new JPopupMenu("Node pop-up");
        nodeProperties = new JMenuItem("Properties");
        nodePopUp.add(nodeProperties);
        
        arcPopUp = new JPopupMenu("Arc pop-up");
        arcProperties = new JMenuItem("Properties"); 
        arcPopUp.add(arcProperties);
        
        arcPanel = new JPanel();
        
        lblVul = new JLabel("Vulnerability");
        txtVul = new JTextField();
        txtVul.setPreferredSize(new Dimension(200, 24));
        btnVul = new JButton("Okay");
        
        
        arcPanel.add(lblVul);
        arcPanel.add(txtVul);
        arcPanel.add(btnVul);
        
        arcFrame.getContentPane().add(arcPanel);
        
        nodePanel = new JPanel();
        
        lblName = new JLabel("Name");
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 24));
        btnName = new JButton("Okay");
        
        nodePanel.add(lblName);
        nodePanel.add(txtName);
        nodePanel.add(btnName);
        
        nodeFrame.getContentPane().add(nodePanel);
        
        
    }
    
    private class MyJPanel extends JPanel implements StateEditable//Create your own JPanel and override paintComponentMethod.
    {
    	
    	UndoableEditSupport undoableEditSupport = new UndoableEditSupport(this);
    	
        
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


		@Override
		public void storeState(Hashtable state) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void restoreState(Hashtable state) {
			// TODO Auto-generated method stub
			
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
	
	
	public JButton getBtnClear() {
		return btnClear;
	}
	public void setBtnClear(JButton btnClear) {
		this.btnClear = btnClear;
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
	public JButton getBtnUndo() {
		return btnUndo;
	}
	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}
	public JButton getBtnRedo() {
		return btnRedo;
	}
	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}
	public JButton getBtnGetinfo() {
		return btnGetinfo;
	}
	public void setBtnGetinfo(JButton btnGetinfo) {
		this.btnGetinfo = btnGetinfo;
	}
	public JPopupMenu getNodePopUp() {
		return nodePopUp;
	}
	public void setNodePopUp(JPopupMenu nodePopUp) {
		this.nodePopUp = nodePopUp;
	}
	public JMenuItem getNodeProperties() {
		return nodeProperties;
	}
	public void setNodeProperties(JMenuItem nodeProperties) {
		this.nodeProperties = nodeProperties;
	}
	public JFrame getNodeFrame() {
		return nodeFrame;
	}
	public void setNodeFrame(JFrame nodeFrame) {
		this.nodeFrame = nodeFrame;
	}
	public JFrame getArcFrame() {
		return arcFrame;
	}
	public void setArcFrame(JFrame arcFrame) {
		this.arcFrame = arcFrame;
	}
	public JMenuItem getArcProperties() {
		return arcProperties;
	}
	public void setArcProperties(JMenuItem arcProperties) {
		this.arcProperties = arcProperties;
	}
	public JPopupMenu getArcPopUp() {
		return arcPopUp;
	}
	public void setArcPopUp(JPopupMenu arcPopUp) {
		this.arcPopUp = arcPopUp;
	}
	public JLabel getLblVul() {
		return lblVul;
	}
	public void setLblVul(JLabel lblVul) {
		this.lblVul = lblVul;
	}
	public JTextField getTxtVul() {
		return txtVul;
	}
	public void setTxtVul(JTextField txtVul) {
		this.txtVul = txtVul;
	}
	public JButton getBtnVul() {
		return btnVul;
	}
	public void setBtnVul(JButton btnVul) {
		this.btnVul = btnVul;
	}
	public JLabel getLblName() {
		return lblName;
	}
	public void setLblName(JLabel lblName) {
		this.lblName = lblName;
	}
	public JTextField getTxtName() {
		return txtName;
	}
	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}
	public JButton getBtnName() {
		return btnName;
	}
	public void setBtnName(JButton btnName) {
		this.btnName = btnName;
	}
	public JMenuItem getMntmOpen() {
		return mntmOpen;
	}
	public void setMntmOpen(JMenuItem mntmOpen) {
		this.mntmOpen = mntmOpen;
	}
	public JMenuItem getMntmSave() {
		return mntmSave;
	}
	public void setMntmSave(JMenuItem mntmSave) {
		this.mntmSave = mntmSave;
	}
	public JMenuItem getMntmAttackgraph() {
		return mntmAttackgraph;
	}
	public void setMntmAttackgraph(JMenuItem mntmAttackgraph) {
		this.mntmAttackgraph = mntmAttackgraph;
	}
	
}
