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
    
    private List<Arc> arcsUndo;
    private List<Node> nodesUndo;
    
    
    
    private JButton btnMove;
    private JButton btnDelete;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnGetinfo;
    private JPopupMenu nodePopUp;
    private JPopupMenu arcPopUp;
    
    private JMenuItem nodeProperties;
    private JMenuItem nodeAttacker;
    private JMenuItem nodeTarget;
    private JMenuItem arcProperties;
    
    private JFrame nodeFrame;    
    private JFrame arcFrame;
    private JFrame resultFrame;
    
    private JPanel arcPanel;
    private JPanel nodePanel;
    private JPanel resultPanel;
    
    private JLabel lblMetrics;
    private JLabel lblResults;
    private JTextField txtResults;
    
    private JLabel lblVul;
    private JLabel lblRisk;
    private JLabel lblCost;
    private JLabel lblProb;
    private JLabel lblImpact;
    
    private JTextField txtVul;
    private JTextField txtRisk;
    private JTextField txtCost;
    private JTextField txtProb;
    private JTextField txtImpact;
    
    
    private JButton btnVul;
    
    private JLabel lblName;
    private JTextField txtName;
    private JButton btnName;
    
    private JMenuItem mntmOpen;
    private JMenuItem mntmSave;
    
    private JMenuItem mntmAttackgraph;
    
    private UndoManager undoManager = new UndoManager();
    
    private JFrame saveFrame;
    private JFileChooser fileChooser;
    
    
    /*
     * Implements any representation of information of HARMs GUI.
     */
    public MainView() {
    	
    	
    	//Open & Save frame
    	saveFrame = new JFrame();
    	fileChooser = new JFileChooser();
    	
    	//Main Panel
        contentPane = new JPanel();             
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setTitle("HARMs Simulator Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        //Initiate node and arc array
        nodes = new ArrayList<Node>();
        arcs = new ArrayList<Arc>();

        arcsUndo = new ArrayList<Arc>();
        nodesUndo = new ArrayList<Node>();
        
        //Menu navigation at the top
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

        //Menu Incons at the top.
        northPanel = new JPanel();
        
        btnUndo = new JButton("Undo");
        btnUndo.setBackground(Color.LIGHT_GRAY);
        //btnUndo.setVisible(false);
        northPanel.add(btnUndo);
        
        btnRedo = new JButton("Redo");
        btnRedo.setBackground(Color.LIGHT_GRAY);
        //btnRedo.setVisible(false);
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
                
        //Node properties frame
        nodeFrame = new JFrame();
        nodeFrame.setSize(300, 300);
        nodeFrame.setTitle("Node");
        
        //Arc properties frame
        arcFrame = new JFrame();
        arcFrame.setSize(300, 300);
        arcFrame.setTitle("Arc");      
        
        //Analysis of attack graph frame
        resultFrame = new JFrame();
        resultFrame.setSize(300, 300);
        resultFrame.setTitle("result");
        
        //Node pop up menu
        nodePopUp = new JPopupMenu("Node pop-up");
        nodeProperties = new JMenuItem("Properties");
        nodeAttacker = new JMenuItem("Set as the Attacker");
        nodeTarget = new JMenuItem("Set as the Target");
        nodePopUp.add(nodeProperties);
        nodePopUp.add(nodeAttacker);
        nodePopUp.add(nodeTarget);
        
        //Arc pop up menu
        arcPopUp = new JPopupMenu("Arc pop-up");
        arcProperties = new JMenuItem("Properties"); 
        arcPopUp.add(arcProperties);
        arcPanel = new JPanel();
        lblVul = new JLabel("Vulnerability");
        txtVul = new JTextField();
        txtVul.setPreferredSize(new Dimension(40, 24));
        lblRisk = new JLabel("Risk");
        txtRisk = new JTextField();
        txtRisk.setPreferredSize(new Dimension(40, 24));
        lblCost = new JLabel("Cost");
        txtCost = new JTextField();
        txtCost.setPreferredSize(new Dimension(40, 24));
        lblProb = new JLabel("Prob");
        txtProb = new JTextField();
        txtProb.setPreferredSize(new Dimension(40, 24));
        lblImpact = new JLabel("Impact");
        txtImpact = new JTextField();
        txtImpact.setPreferredSize(new Dimension(40, 24));
        btnVul = new JButton("Okay");
        
        //arcPanel.add(lblVul);
        //arcPanel.add(txtVul);
        arcPanel.add(lblRisk);
        arcPanel.add(txtRisk);
        arcPanel.add(lblCost);
        arcPanel.add(txtCost);
        arcPanel.add(lblProb);
        arcPanel.add(txtProb);
        arcPanel.add(lblImpact);
        arcPanel.add(txtImpact);
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
        
        
        resultPanel = new JPanel();
        
        lblMetrics = new JLabel("---------------Analysis---------------\n");
        //lblResults = new JLabel();
        //txtResults = new JTextField();
        
        
        resultPanel.add(lblMetrics);
        //resultPanel.add(lblResults);
        //resultPanel.add(txtResults);
        
        resultFrame.getContentPane().add(resultPanel);
        
        
    }
    
  //Create a JPanel and override paintComponentMethod.
    private class MyJPanel extends JPanel implements StateEditable
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
    
    //Setters and getters
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
	public JFrame getResultFrame() {
		return resultFrame;
	}
	public void setResultFrame(JFrame resultFrame) {
		this.resultFrame = resultFrame;
	}
	public JPanel getResultPanel() {
		return resultPanel;
	}
	public void setResultPanel(JPanel resultPanel) {
		this.resultPanel = resultPanel;
	}
	public JMenuItem getNodeAttacker() {
		return nodeAttacker;
	}
	public void setNodeAttacker(JMenuItem nodeAttacker) {
		this.nodeAttacker = nodeAttacker;
	}
	public JMenuItem getNodeTarget() {
		return nodeTarget;
	}
	public void setNodeTarget(JMenuItem nodeTarget) {
		this.nodeTarget = nodeTarget;
	}
	public JLabel getLblRisk() {
		return lblRisk;
	}
	public void setLblRisk(JLabel lblRisk) {
		this.lblRisk = lblRisk;
	}
	public JLabel getLblCost() {
		return lblCost;
	}
	public void setLblCost(JLabel lblCost) {
		this.lblCost = lblCost;
	}
	public JLabel getLblProb() {
		return lblProb;
	}
	public void setLblProb(JLabel lblProb) {
		this.lblProb = lblProb;
	}
	public JLabel getLblImpact() {
		return lblImpact;
	}
	public void setLblImpact(JLabel lblImpact) {
		this.lblImpact = lblImpact;
	}
	public JTextField getTxtRisk() {
		return txtRisk;
	}
	public void setTxtRisk(JTextField txtRisk) {
		this.txtRisk = txtRisk;
	}
	public JTextField getTxtCost() {
		return txtCost;
	}
	public void setTxtCost(JTextField txtCost) {
		this.txtCost = txtCost;
	}
	public JTextField getTxtProb() {
		return txtProb;
	}
	public void setTxtProb(JTextField txtProb) {
		this.txtProb = txtProb;
	}
	public JLabel getLblMetrics() {
		return lblMetrics;
	}
	public void setLblMetrics(JLabel lblMetrics) {
		this.lblMetrics = lblMetrics;
	}
	public JLabel getLblResults() {
		return lblResults;
	}
	public void setLblResults(JLabel lblResults) {
		this.lblResults = lblResults;
	}
	public JTextField getTxtResults() {
		return txtResults;
	}
	public void setTxtResults(JTextField txtResults) {
		this.txtResults = txtResults;
	}
	public JFrame getSaveFrame() {
		return saveFrame;
	}
	public void setSaveFrame(JFrame saveFrame) {
		this.saveFrame = saveFrame;
	}
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	public void setFileChooser(JFileChooser filseChooser) {
		this.fileChooser = filseChooser;
	}
	public List<Arc> getArcsUndo() {
		return arcsUndo;
	}
	public void setArcsUndo(List<Arc> arcsUndo) {
		this.arcsUndo = arcsUndo;
	}
	public List<Node> getNodesUndo() {
		return nodesUndo;
	}
	public void setNodesUndo(List<Node> nodesUndo) {
		this.nodesUndo = nodesUndo;
	}

}
