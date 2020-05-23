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
    
    private JLabel lblArc;
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
    
    private JMenuItem mntmMetrics;
    private JMenuItem mntmAttackgraph;
    
 
    
    private JFrame saveFrame;
    private JFileChooser fileChooser;
    
    private MainModel model;
    
    
    /**
     * Implements any representation of information of HARMs GUI.
     */
    public MainView(MainModel m) {
    	
    	this.model = m;
    	
    	/*Open & Save frame*/
    	saveFrame = new JFrame();
    	fileChooser = new JFileChooser();
    	
    	/*Centre Panel that user can draw nodes and arcs*/
        contentPane = new JPanel();             
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setTitle("HARMs Simulator Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        /*Menu navigation at the top*/
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
        mntmMetrics = new JMenuItem("Metrics");
        mntmAttackgraph = new JMenuItem("Analysis");
        
        mnTools.add(mntmMetrics);
        mnTools.add(mntmAttackgraph); 
        
        JMenu mnExtentions = new JMenu("Extentions");
        menuBar.add(mnExtentions);
        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        /*North Panel that contains buttons*/
        northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createTitledBorder("Selector"));
        contentPane.add(northPanel, BorderLayout.NORTH);
        centerPanel = new MyJPanel();
        centerPanel.setName("centerPanel");
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Work Space", TitledBorder.CENTER, TitledBorder.TOP)); 
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(null);
        
        /*Undo button*/
        btnUndo = new JButton("Undo");
        btnUndo.setName("UndoButton");
        btnUndo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnUndo);
        
        /*Redo button*/
        btnRedo = new JButton("Redo");
        btnRedo.setName("RedoButton");
        btnRedo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnRedo);
        
        /*Node button*/
        btnNode = new JButton("Node");
        btnNode.setName("NodeButton");
        btnNode.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnNode);
        
        /*Arc button*/
        btnArc = new JButton("Arc");
        btnArc.setName("ArcButton");
        btnArc.setBackground(Color.LIGHT_GRAY);        
        northPanel.add(btnArc);
        
        /*Move button*/
        btnMove = new JButton("Move");
        btnMove.setName("MoveButton");
        btnMove.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnMove);
        
        /*Delete button*/
        btnDelete = new JButton("Delete");
        btnDelete.setName("DeleteButton");
        btnDelete.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnDelete);
        
        /*Clear button*/
        btnClear = new JButton("Clear");
        btnClear.setName("ClearButton");
        btnClear.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnClear);
        
        /*GetInfo button*/
        btnGetinfo = new JButton("GetInfo");
        btnGetinfo.setBackground(Color.LIGHT_GRAY);
        northPanel.add(btnGetinfo);
        
        /*Node properties Frame*/
        nodeFrame = new JFrame();
        nodeFrame.setSize(300, 300);
        nodeFrame.setTitle("Node");
        
        /*Arc properties Frame*/
        arcFrame = new JFrame();
        arcFrame.setSize(200, 300);
        arcFrame.setTitle("Arc");      
        
        /*Analysis of attack graph Frame*/
        resultFrame = new JFrame();
        resultFrame.setSize(300, 300);
        resultFrame.setTitle("result");
        
        /*Node pop up menu*/
        nodePopUp = new JPopupMenu("Node pop-up");
        nodeProperties = new JMenuItem("Properties");
        nodeAttacker = new JMenuItem("Set as the Attacker");
        nodeTarget = new JMenuItem("Set as the Target");
        nodePopUp.add(nodeProperties);
        nodePopUp.add(nodeAttacker);
        nodePopUp.add(nodeTarget);
        
        /*Arc pop up menu*/
        arcPopUp = new JPopupMenu("Arc pop-up");
        arcProperties = new JMenuItem("Properties"); 
        arcPopUp.add(arcProperties);
        arcPanel = new JPanel();
        arcPanel.setLayout(null);
        lblArc = new JLabel("Arc");
        lblArc.setBounds(30, 20, 150, 24);
        lblVul = new JLabel("Vulnerability");
        lblVul.setBounds(20, 50, 80, 24);
        txtVul = new JTextField();
        txtVul.setBounds(100, 50, 40, 24);
        lblRisk = new JLabel("Risk");
        lblRisk.setBounds(20, 80, 80, 24);
        txtRisk = new JTextField();
        txtRisk.setBounds(100, 80, 40, 24);
        lblCost = new JLabel("Cost");
        lblCost.setBounds(20, 110, 80, 24);
        txtCost = new JTextField();
        txtCost.setBounds(100, 110, 40, 24);
        lblProb = new JLabel("Prob");
        lblProb.setBounds(20, 140, 80, 24);
        txtProb = new JTextField();
        txtProb.setBounds(100, 140, 40, 24);
        lblImpact = new JLabel("Impact");
        lblImpact.setBounds(20, 170, 80 , 24);
        txtImpact = new JTextField();
        txtImpact.setBounds(100, 170, 40, 24);
        btnVul = new JButton("Okay");
        btnVul.setBounds(30, 200, 70, 24);
        
        arcPanel.add(lblArc);
        arcPanel.add(lblVul);
        arcPanel.add(txtVul);
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
    
    /**
     * Create a JPanel and override paintComponentMethod.
     */
    @SuppressWarnings("serial")
	public class MyJPanel extends JPanel{

    	/**
    	 * Draw node or arc when they are created.
    	 */
    	@Override
    	public void paintComponent(Graphics g) {

    		super.paintComponent(g);

    		for (Node n : model.getNodes()) {
    			n.draw(g);
    		}

    		for (Arc a : model.getArcs()) {
    			a.drawLine(g);
    		}
    	}

    }
    
    
    /*Setters and getters*/
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
	public JButton getBtnNode() {
		return btnNode;
	}
	public void setBtnNode(JButton btnNode) {
		this.btnNode = btnNode;
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
	public JTextField getTxtImpact() {
		return txtImpact;
	}
	public void setTxtImpact(JTextField txtImpact) {
		this.txtImpact = txtImpact;
	}
	public JMenuItem getMntmMetrics() {
		return mntmMetrics;
	}
	public void setMntmMetrics(JMenuItem mntmMetrics) {
		this.mntmMetrics = mntmMetrics;
	}
	public JLabel getLblArc() {
		return lblArc;
	}
	public void setLblArc(JLabel lblArc) {
		this.lblArc = lblArc;
	}
}
