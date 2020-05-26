package com.company;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import adapters.ArcInfoMouseAdapter;
import adapters.ArcMouseAdapter;
import adapters.DeleteArcMouseAdapter;
import adapters.DeleteNodeMouseAdapter;
import adapters.GetInfoMouseAdapter;
import adapters.MoveMouseAdapter;
import adapters.NodeInfoMouseAdapter;
import adapters.NodeMouseAdapter;
import undoredoStack.CommandStack;

public class MainController {

	private MainView view;
	private MainModel model;

	private int activateNode;
	private int activateArc;
	private int activateMove;
	private int activateDelete;
	private int activateGetInfo;

	private int nodeNumber;
	private int arcNumber;

	private int nodePropertyInt;
	private int arcPropertyInt;

	private List<String> lines;

	private CommandStack stack;

	/* DeleterArcMouseAdapater variables */
	private List<Arc> deleteArcs;
	private List<Arc> deleteArcsRedo;

	/* DeleteNodeMouseAdapater variables */
	private List<Node> deleteNodes;
	private List<Node> deleteNodesRedo;
	private List<Arc> deleteNodesArcs;
	private List<Arc> deleteNodesArcsRedo;
	private int deleteNodesArcsCounter;
	private int deleteNodesRedoCounter;
	private List<Integer> counterList;
	private List<Integer> counterRedoList;

	/* Move Undo Variables */
	private int moveCounter;
	private Map<Integer, List<Integer>> moveUndoMap;
	private Map<Integer, List<Integer>> moveRedoMap;
	private int moveRedoCounter;
	private int moveUndoArcCounter;
	private Arc deleteArc;
	private int portNumber;

	private ResultView resultView;
	private MetricsView metricsView;
	
	/**
	 * MainController handles all the interfaces in the GUI
	 */
	public MainController(MainModel m, MainView v) {

		view = v;
		model = m;
		initController();
	}

	/**
	 * Initialize all the interfaces
	 */
	public void initController() {
		
		resultView = new ResultView();
		

		stack = new CommandStack();
		view.getTxtVul().setText("0");
		view.getTxtCost().setText("0");
		view.getTxtRisk().setText("0");
		view.getTxtImpact().setText("0");
		view.getTxtProb().setText("0");
		deleteNodesArcsCounter = 0;
		deleteNodesRedoCounter = 0;
		moveCounter = 0;
		moveRedoCounter = 0;
		lines = new ArrayList<>();
		moveUndoMap = new HashMap<>();
		moveRedoMap = new HashMap<>();
		deleteArcs = new ArrayList<>();
		deleteArcsRedo = new ArrayList<>();
		deleteNodes = new ArrayList<>();
		deleteNodesRedo = new ArrayList<>();
		deleteNodesArcs = new ArrayList<>();
		deleteNodesArcsRedo = new ArrayList<>();
		counterList = new ArrayList<>();
		counterRedoList = new ArrayList<>();
		counterRedoList = new ArrayList<>();

		// Initialize all the mouse adapter
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter(model, view, this);
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, this);
		MoveMouseAdapter moveMouseAdapter = new MoveMouseAdapter(model, view, this);
		DeleteArcMouseAdapter deleteArcMouseAdapter = new DeleteArcMouseAdapter(model, view, this);
		DeleteNodeMouseAdapter deleteNodeMouseAdapter = new DeleteNodeMouseAdapter(model, view, this);
		GetInfoMouseAdapter getInforMouseAdapter = new GetInfoMouseAdapter(model, view, this);
		NodeInfoMouseAdapter nodeInfoMouseAdapter = new NodeInfoMouseAdapter(model, view, this);
		ArcInfoMouseAdapter arcInfoMouseAdapter = new ArcInfoMouseAdapter(model, view, this);

		// Add mouse and action listener
		view.getBtnArc().addMouseListener(arcMouseAdapter);
		view.getCenterPanel().addMouseListener(nodeMouseAdapter);
		view.getCenterPanel().addMouseListener(arcMouseAdapter);
		view.getCenterPanel().addMouseMotionListener(arcMouseAdapter);
		view.getCenterPanel().addMouseListener(moveMouseAdapter);
		view.getCenterPanel().addMouseMotionListener(moveMouseAdapter);
		view.getCenterPanel().addMouseListener(deleteArcMouseAdapter);
		view.getCenterPanel().addMouseListener(deleteNodeMouseAdapter);
		view.getCenterPanel().addMouseListener(getInforMouseAdapter);
		view.getCenterPanel().addMouseListener(nodeInfoMouseAdapter);
		view.getCenterPanel().addMouseListener(arcInfoMouseAdapter);
		view.getMntmAttackgraph().addActionListener(e -> attackGraphAction());
		view.getMntmMetrics().addActionListener(e-> metricsAction());
		view.getBtnNode().addActionListener(e -> activateNodeInt());
		view.getBtnArc().addActionListener(e -> activateArcInt());
		view.getBtnMove().addActionListener(e -> activateMoveInt());
		view.getBtnDelete().addActionListener(e -> activateDeleteInt());
		view.getBtnGetinfo().addActionListener(e -> activateGetInfoInt());
		view.getNodeAttacker().addActionListener(e -> nodeAttacker());
		view.getNodeTarget().addActionListener(e -> nodeTarget());
		view.getMntmSave().addActionListener(e -> saveAction());
		view.getMntmOpen().addActionListener(e -> loadAction());
		view.getBtnUndo().addActionListener(e -> undoAction());
		view.getBtnRedo().addActionListener(e -> redoAction());
		view.getBtnClear().addActionListener(e -> clearAllInfo());
		view.getArcProperties().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				view.getArcFrame().setVisible(true);

			}
		});

		view.getNodeProperties().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				view.getTxtName().setText(model.getNodes().get(nodePropertyInt).getName());
				view.getTxtVul().setText(String.valueOf(model.getNodes().get(nodePropertyInt).getVulnerability()));
				view.getTxtCost().setText(String.valueOf(model.getNodes().get(nodePropertyInt).getCost()));
				view.getTxtRisk().setText(String.valueOf(model.getNodes().get(nodePropertyInt).getRisk()));
				view.getTxtImpact().setText(String.valueOf(model.getNodes().get(nodePropertyInt).getImpact()));
				view.getTxtProb().setText(String.valueOf(model.getNodes().get(nodePropertyInt).getProbability()));
				
				view.getNodeFrame().setVisible(true);

			}

		});

		view.getBtnName().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (view.getTxtName() == null) {

				} else {

					model.getNodes().get(nodePropertyInt).setName(view.getTxtName().getText());
					model.getNodes().get(nodePropertyInt).setVulnerability(Double.valueOf(view.getTxtVul().getText()));
					model.getNodes().get(nodePropertyInt).setRisk(Double.valueOf(view.getTxtRisk().getText()));
					model.getNodes().get(nodePropertyInt).setCost(Double.valueOf(view.getTxtCost().getText()));
					model.getNodes().get(nodePropertyInt).setImpact(Double.valueOf(view.getTxtImpact().getText()));
					model.getNodes().get(nodePropertyInt).setProbability(Double.valueOf(view.getTxtProb().getText()));
					view.getCenterPanel().repaint();
				}

			}

		});

		view.getBtnVul().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (view.getTxtVul() == null) {

				} else {

					view.getCenterPanel().repaint();
				}

			}

		});

	}

	/**
	 * Clear all the data
	 */
	public void clearAllInfo() {

		view.getCenterPanel().removeAll();
		view.getCenterPanel().validate();
		view.getCenterPanel().repaint();
		model.getArcs().clear();
		model.getNodes().clear();
		moveUndoMap.clear();
		moveCounter = 0;
		arcNumber = 0;
		nodeNumber = 0;
		view.getCenterPanel().repaint();
	}

	/**
	 * Call the execute command in the stack.
	 */
	public void redoAction() {
		stack.redo();
		view.getCenterPanel().repaint();
	}

	/**
	 * Call the undo command in the stack.
	 */
	public void undoAction() {
		stack.undo();
		view.getCenterPanel().repaint();
	}
	
	public void metricsAction() {
		
		metricsView = new MetricsView(model);
		metricsView.setVisible(true);
		
	}

	/**
	 * Save the data
	 */
	public void saveAction() {

		try {

			/*
			 * Set up the directory to save the file
			 */
			view.getFileChooser().setDialogTitle("save file");
			view.getFileChooser().setCurrentDirectory(new File(System.getProperty("user.dir")));
			int userSelection = view.getFileChooser().showSaveDialog(view.getSaveFrame());
			File fileToSave = null;
			
			if (userSelection == JFileChooser.APPROVE_OPTION) {

				fileToSave = view.getFileChooser().getSelectedFile();
			}
			
			if (fileToSave == null) {

			} else {

				FileOutputStream file = new FileOutputStream(fileToSave);
				ObjectOutputStream out = new ObjectOutputStream(file);

				/*
				 * Write objects into Outputstream
				 */
				out.writeObject(model.getArcs());
				out.writeObject(model.getNodes());

				out.close();
				file.close();

			}

		} catch (IOException e) {
	
			e.printStackTrace();
		
		}
	}

	/**
	 * Load saved file
	 */
	public void loadAction() {

		try {

			/*
			 * Set up the directory to load the file
			 */
			view.getFileChooser().setDialogTitle("Open file");
			view.getFileChooser().setCurrentDirectory(new File(System.getProperty("user.dir")));
			int userSelection = view.getFileChooser().showOpenDialog(view.getSaveFrame());
			File fileToSave = null;
			if (userSelection == JFileChooser.APPROVE_OPTION) {

				fileToSave = view.getFileChooser().getSelectedFile();
			}

			if (fileToSave == null) {

			} else {

				FileInputStream file = new FileInputStream(fileToSave);
				ObjectInputStream in = new ObjectInputStream(file);

				model.getArcs().clear();
				model.getNodes().clear();

				/*
				 * Read ojbects from the Inputstream
				 */
				List<Arc> arcs = new ArrayList<>();
				arcs = (List<Arc>) in.readObject();

				List<Node> nodes = new ArrayList<>();
				nodes = (List<Node>) in.readObject();

				/*
				 * Add loaded arcs and nodes into data of the program
				 */
				for (int i = 0; i < arcs.size(); i++) {

					model.getArcs().add(arcs.get(i));

				}

				for (int j = 0; j < nodes.size(); j++) {

					model.getNodes().add(nodes.get(j));

				}

				in.close();
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		view.getCenterPanel().repaint();

	}

	/**
	 * Set up the attacker of the node
	 */
	public void nodeAttacker() {

		int AttackerSetting = 0;

		for (int i = 0; i < model.getNodes().size(); i++) {
			if (model.getNodes().get(i).isAttacker() == true) {
				JOptionPane.showMessageDialog(null, "Attacker is already set");
				AttackerSetting = 1;
			}

		}

		if (AttackerSetting == 1) {

		} else {

			model.getNodes().get(nodePropertyInt).setAttacker(true);
			model.getNodes().get(nodePropertyInt).setName("Attacker");
			view.getCenterPanel().repaint();
		}
	}

	/**
	 * Set up the target of the node
	 */
	public void nodeTarget() {

		int targetSetting = 0;

		for (int i = 0; i < model.getNodes().size(); i++) {
			if (model.getNodes().get(i).isTarget() == true) {
				JOptionPane.showMessageDialog(null, "Target is already set");
				targetSetting = 1;
			}

		}

		if (targetSetting == 1) {

		} else {
			model.getNodes().get(nodePropertyInt).setTarget(true);
			model.getNodes().get(nodePropertyInt).setName("Target");
			view.getCenterPanel().repaint();
		}

	}

	/**
	 * Connect to the engine, send data and receive the result.
	 */
	private void attackGraphAction() {

		boolean isAttacker = false;
		boolean isTarget = false;
		
		ResultView.getTextPane().setText("");

		for (Node node : model.getNodes()) {

			if (node.isTarget()) {

				isTarget = true;

			}

			if (node.isAttacker()) {

				isAttacker = true;

			}

		}
		
//		try {
//			ServerSocket socket = new ServerSocket(0);
//			portNumber = socket.getLocalPort();
//			socket.close();
//		} catch (IOException e) {
//			
//			
//		}
		
		portNumber = 5016;
		
		if(isTarget && isAttacker) {
			
//			String command = "python3 example3.py " + String.valueOf(portNumber);
//			try {
//				Process p = Runtime.getRuntime().exec(command);
//				//TimeUnit.MILLISECONDS.sleep(500);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
		

		try (Socket socket = new Socket("localhost", 6666)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(nodeNumber);
			writer.println(arcNumber);

			for (int i = 0; i < model.getNodes().size(); i++) {
				
				if (model.getNodes().get(i).isAttacker()) {

					writer.println(model.getNodes().get(i).getNumber());

				} else if (model.getNodes().get(i).isTarget()) {

					writer.println(model.getNodes().get(i).getNumber());

				}
			}

			for (int i = 0; i < model.getArcs().size(); i++) {
				
				writer.println(String.valueOf(model.getArcs().get(i).getInitNode()));
				writer.println(String.valueOf(model.getArcs().get(i).getEndNode()));
			}
			
			
			
			for (int i = 0; i < model.getNodes().size(); i++) {
				
				writer.println(String.valueOf(model.getNodes().get(i).getRisk()));
				writer.println(String.valueOf(model.getNodes().get(i).getCost()));
				writer.println(String.valueOf(model.getNodes().get(i).getProbability()));
				writer.println(String.valueOf(model.getNodes().get(i).getImpact()));
				
			}

			writer.println("c");

			InputStream input = socket.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String line;

			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				lines.add(line);

			}
			
			
			socket.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());

		}

		

		for (String lineTemp : lines) {

			JLabel labelTemp = new JLabel(lineTemp);
			view.getResultPanel().add(labelTemp);
			
			
			ResultView.getTextPane().setText(ResultView.getTextPane().getText() + "\n" + lineTemp);

		}

		resultView.setVisible(true);
		
		} else {
			
			System.err.println("Attacker and target are not defined");
			JOptionPane.showMessageDialog(null, "Attacker and target are not defined");
		}
		

	}

	/**
	 * Prevents interfaces from running simultaneously
	 */
	public int activateNodeInt() {
		if (activateNode == 0) {
			if (activateArc == 1 || activateMove == 1 || activateDelete == 1 || activateGetInfo == 1) {

			} else {
				activateNode = 1;
				view.getBtnNode().setBackground(Color.yellow);
			}
		} else if (activateNode == 1) {
			activateNode = 0;
			view.getBtnNode().setBackground(Color.LIGHT_GRAY);
		}
		return activateNode;
	}

	public int activateArcInt() {
		if (activateArc == 0) {
			if (activateNode == 1 || activateMove == 1 || activateDelete == 1 || activateGetInfo == 1) {

			} else {
				activateArc = 1;
				view.getBtnArc().setBackground(Color.yellow);
			}

		} else if (activateArc == 1) {
			activateArc = 0;
			view.getBtnArc().setBackground(Color.LIGHT_GRAY);
		}
		return activateArc;
	}

	public int activateMoveInt() {
		if (activateMove == 0) {
			if (activateNode == 1 || activateArc == 1 || activateDelete == 1 || activateGetInfo == 1) {

			} else {
				activateMove = 1;
				view.getBtnMove().setBackground(Color.yellow);
			}

		} else if (activateMove == 1) {
			activateMove = 0;
			view.getBtnMove().setBackground(Color.LIGHT_GRAY);
		}
		return activateMove;

	}

	public int activateDeleteInt() {

		if (activateDelete == 0) {
			if (activateNode == 1 || activateArc == 1 || activateMove == 1 || activateGetInfo == 1) {

			} else {
				activateDelete = 1;
				view.getBtnDelete().setBackground(Color.yellow);
			}
		} else if (activateDelete == 1) {
			activateDelete = 0;
			view.getBtnDelete().setBackground(Color.LIGHT_GRAY);
		}
		return activateDelete;
	}

	public int activateGetInfoInt() {

		if (activateGetInfo == 0) {
			if (activateNode == 1 || activateArc == 1 || activateMove == 1 || activateDelete == 1) {

			} else {
				activateGetInfo = 1;
				view.getBtnGetinfo().setBackground(Color.yellow);
			}
		} else if (activateGetInfo == 1) {
			activateGetInfo = 0;
			view.getBtnGetinfo().setBackground(Color.LIGHT_GRAY);
		}
		return activateGetInfo;

	}
	
	
	
	/*Setters and Getters*/
	public int getActivateNode() {
		return activateNode;
	}

	public void setActivateNode(int activateNode) {
		this.activateNode = activateNode;
	}

	public int getActivateArc() {
		return activateArc;
	}

	public void setActivateArc(int activateArc) {
		this.activateArc = activateArc;
	}

	public int getActivateMove() {
		return activateMove;
	}

	public void setActivateMove(int activateMove) {
		this.activateMove = activateMove;
	}

	public int getActivateDelete() {
		return activateDelete;
	}

	public void setActivateDelete(int activateDelete) {
		this.activateDelete = activateDelete;
	}

	public int getActivateGetInfo() {
		return activateGetInfo;
	}

	public void setActivateGetInfo(int activateGetInfo) {
		this.activateGetInfo = activateGetInfo;
	}

	public int getNodePropertyInt() {
		return nodePropertyInt;
	}

	public void setNodePropertyInt(int nodePropertyInt) {
		this.nodePropertyInt = nodePropertyInt;
	}

	public MainView getView() {
		return view;
	}

	public void setView(MainView view) {
		this.view = view;
	}

	public MainModel getModel() {
		return model;
	}

	public void setModel(MainModel model) {
		this.model = model;
	}

	public int getArcNumber() {
		return arcNumber;
	}

	public void setArcNumber(int arcNumber) {
		this.arcNumber = arcNumber;
	}

	public CommandStack getStack() {
		return stack;
	}

	public void setStack(CommandStack stack) {
		this.stack = stack;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public int getArcPropertyInt() {
		return arcPropertyInt;
	}

	public void setArcPropertyInt(int arcPropertyInt) {
		this.arcPropertyInt = arcPropertyInt;
	}

	public Arc getDeleteArc() {
		return deleteArc;
	}

	public void setDeleteArc(Arc deleteArc) {
		this.deleteArc = deleteArc;
	}

	public List<Arc> getDeleteArcs() {
		return deleteArcs;
	}

	public void setDeleteArcs(List<Arc> deleteArcs) {
		this.deleteArcs = deleteArcs;
	}

	public List<Arc> getDeleteArcsRedo() {
		return deleteArcsRedo;
	}

	public void setDeleteArcsRedo(List<Arc> deleteArcsRedo) {
		this.deleteArcsRedo = deleteArcsRedo;
	}

	public List<Node> getDeleteNodes() {
		return deleteNodes;
	}

	public void setDeleteNodes(List<Node> deleteNodes) {
		this.deleteNodes = deleteNodes;
	}

	public List<Node> getDeleteNodesRedo() {
		return deleteNodesRedo;
	}

	public void setDeleteNodesRedo(List<Node> deleteNodesRedo) {
		this.deleteNodesRedo = deleteNodesRedo;
	}

	public List<Arc> getDeleteNodesArcs() {
		return deleteNodesArcs;
	}

	public void setDeleteNodesArcs(List<Arc> deleteNodesArcs) {
		this.deleteNodesArcs = deleteNodesArcs;
	}

	public List<Arc> getDeleteNodesArcsRedo() {
		return deleteNodesArcsRedo;
	}

	public void setDeleteNodesArcsRedo(List<Arc> deleteNodesArcsRedo) {
		this.deleteNodesArcsRedo = deleteNodesArcsRedo;
	}

	public int getDeleteNodesArcsCounter() {
		return deleteNodesArcsCounter;
	}

	public void setDeleteNodesArcsCounter(int deleteNodesArcsCounter) {
		this.deleteNodesArcsCounter = deleteNodesArcsCounter;
	}

	public int getDeleteNodesRedoCounter() {
		return deleteNodesRedoCounter;
	}

	public void setDeleteNodesRedoCounter(int deleteNodesRedoCounter) {
		this.deleteNodesRedoCounter = deleteNodesRedoCounter;
	}

	public List<Integer> getCounterList() {
		return counterList;
	}

	public void setCounterList(List<Integer> counterList) {
		this.counterList = counterList;
	}

	public List<Integer> getCounterRedoList() {
		return counterRedoList;
	}

	public void setCounterRedoList(List<Integer> counterRedoList) {
		this.counterRedoList = counterRedoList;
	}

	public int getMoveCounter() {
		return moveCounter;
	}

	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	public Map<Integer, List<Integer>> getMoveUndoMap() {
		return moveUndoMap;
	}

	public void setMoveUndoMap(Map<Integer, List<Integer>> moveUndoMap) {
		this.moveUndoMap = moveUndoMap;
	}

	public int getMoveUndoArcCounter() {
		return moveUndoArcCounter;
	}

	public void setMoveUndoArcCounter(int moveUndoArcCounter) {
		this.moveUndoArcCounter = moveUndoArcCounter;
	}

	public Map<Integer, List<Integer>> getMoveRedoMap() {
		return moveRedoMap;
	}

	public void setMoveRedoMap(Map<Integer, List<Integer>> moveRedoMap) {
		this.moveRedoMap = moveRedoMap;
	}

	public int getMoveRedoCounter() {
		return moveRedoCounter;
	}

	public void setMoveRedoCounter(int moveRedoCounter) {
		this.moveRedoCounter = moveRedoCounter;
	}

}