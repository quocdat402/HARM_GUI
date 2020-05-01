package com.company;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.company.*;

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

	private Node deleteUndoNode;

	private List<Arc> deleteUndoArcs;

	private Node moveUndoNode;

	private List<Arc> moveUndoArcs;

	private int moveNodeX;
	private int moveNodeY;

	private int nodeIndex;

	private int moveCounter;

	private List<Node> moveUndoNodes;

	private List<Integer> moveUndoInteger;

	private Map<Integer, List<Integer>> moveUndoMap;

	private Map<Integer, List<Integer>> moveRedoMap;

	private int moveRedoCounter;

	private List<Integer> moveUndoArcInteger;

	private Map<Integer, List<Integer>> moveUndoArcMap;

	private int moveArcCounter;

	private int moveUndoArcCounter;

	public MainController(MainModel m, MainView v) {

		view = v;
		model = m;

		initView();

	}

	public void initView() {

	}

	// Initialize all the interfaces
	public void initController() {

		stack = new CommandStack();

		// initialize lines to send attack graph info to the engine

		lines = new ArrayList<>();
		moveUndoArcs = new ArrayList<>();

		deleteUndoArcs = new ArrayList<>();

		moveUndoNodes = new ArrayList<>();

		moveCounter = 0;

		moveRedoCounter = 0;

		moveArcCounter = 0;

		moveUndoMap = new HashMap<>();

		moveRedoMap = new HashMap<>();

		moveUndoArcMap = new HashMap<>();

		// Initialize all the mouse adapter
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter();
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter();
		MoveMouseAdapter moveMouseAdapter = new MoveMouseAdapter();
		DeleteArcMouseAdapter deleteArcMouseAdapter = new DeleteArcMouseAdapter();
		DeleteNodeMouseAdapter deleteNodeMouseAdapter = new DeleteNodeMouseAdapter();
		GetInfoMouseAdapter getInforMouseAdapter = new GetInfoMouseAdapter();
		NodeInfoMouseAdpater nodeInfoMouseAdpater = new NodeInfoMouseAdpater();
		ArcInfoMouseAdapter arcInfoMouseAdapter = new ArcInfoMouseAdapter();

		// Add mouse and action listener
		view.getBtnArc().addMouseListener(arcMouseAdapter);
		view.getCenterPanel().addMouseListener(nodeMouseAdapter);
		view.getCenterPanel().addMouseListener(arcMouseAdapter);
		view.getCenterPanel().addMouseListener(moveMouseAdapter);
		view.getCenterPanel().addMouseMotionListener(moveMouseAdapter);
		view.getCenterPanel().addMouseListener(deleteArcMouseAdapter);
		view.getCenterPanel().addMouseListener(deleteNodeMouseAdapter);
		view.getCenterPanel().addMouseListener(getInforMouseAdapter);
		view.getCenterPanel().addMouseListener(nodeInfoMouseAdpater);
		view.getCenterPanel().addMouseListener(arcInfoMouseAdapter);
		view.getMntmAttackgraph().addActionListener(e -> attackGraphAction());
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
		view.getBtnClear().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				view.getCenterPanel().removeAll();
				view.getCenterPanel().validate();
				view.getCenterPanel().repaint();
				view.getArcs().clear();
				view.getNodes().clear();
				moveUndoMap.clear();

				moveCounter = 0;

				moveArcCounter = 0;

				arcNumber = 0;
				nodeNumber = 0;
			}
		});
		view.getArcProperties().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				view.getArcFrame().setVisible(true);

			}
		});

		view.getNodeProperties().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				view.getNodeFrame().setVisible(true);

			}

		});
		view.getCenterPanel().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				for (int i = 0; i < view.getNodes().size(); i++) {
					int x = view.getNodes().get(i).getX() + 12;
					int y = view.getNodes().get(i).getY() + 12;
					int radius = view.getNodes().get(i).getDiameter() / 2;

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					}
				}
			}
		});

		view.getBtnName().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (view.getTxtName() == null) {

				} else {

					view.getNodes().get(nodePropertyInt).setName(view.getTxtName().getText());
					view.getCenterPanel().repaint();
				}

			}

		});

		view.getBtnVul().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (view.getTxtVul() == null) {

				} else {

					view.getArcs().get(arcPropertyInt).setVulnerability(Double.valueOf(view.getTxtVul().getText()));
					view.getCenterPanel().repaint();
				}

			}

		});

	}

	private void redoAction() {

		stack.redo();
		view.getCenterPanel().repaint();

	}

	private void undoAction() {

//		view.getArcs().clear();
//		for(Arc arc: view.getArcsUndo()) {
//			
//			view.getArcs().add(arc);
//		}

		stack.undo();

		view.getCenterPanel().repaint();

	}

	// Save the data
	private void saveAction() {

		try {

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

				out.writeObject(view.getArcs());
				out.writeObject(view.getNodes());

				out.close();
				file.close();

				System.out.println("Serialzation Done!!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Load saved file
	private void loadAction() {

		try {

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

				view.getArcs().clear();
				view.getNodes().clear();

				List<Arc> arcs = new ArrayList<>();
				arcs = (List<Arc>) in.readObject();

				List<Node> nodes = new ArrayList<>();
				nodes = (List<Node>) in.readObject();

				for (int i = 0; i < arcs.size(); i++) {

					view.getArcs().add(arcs.get(i));

				}

				for (int j = 0; j < nodes.size(); j++) {

					view.getNodes().add(nodes.get(j));

				}

//			Arc obj;
//			
//			for(int i = 0; i < SaveArcCounter; i++) {
//				obj =  (Arc)in.readObject();
//				view.getArcs().add(obj);
//				//System.out.println(obj.getInitNode());
//			}
//			
//			Node nodeObj;
//			for(int j = 0; j < SaveNodeCounter; j++) {
//				nodeObj = (Node) in.readObject();
//				view.getNodes().add(nodeObj);
//				
//			}

				in.close();
				file.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		view.getCenterPanel().repaint();

	}

	// Set up the attacker of the node
	private void nodeAttacker() {

		int AttackerSetting = 0;

		for (int i = 0; i < view.getNodes().size(); i++) {
			if (view.getNodes().get(i).isAttacker() == true) {
				JOptionPane.showMessageDialog(null, "Attacker is already set");
				AttackerSetting = 1;
			}

		}

		if (AttackerSetting == 1) {

		} else {
			view.getNodes().get(nodePropertyInt).setAttacker(true);
		}
	}

	// Set up the target of the node
	private void nodeTarget() {

		int targetSetting = 0;

		for (int i = 0; i < view.getNodes().size(); i++) {
			if (view.getNodes().get(i).isTarget() == true) {
				JOptionPane.showMessageDialog(null, "Target is already set");
				targetSetting = 1;
			}

		}

		if (targetSetting == 1) {

		} else {
			view.getNodes().get(nodePropertyInt).setTarget(true);
		}

	}

	// Analyze the result
	private void attackGraphAction() {

//		try {
//			FileWriter arcInputs = new FileWriter("ArcInput.txt");
//			FileWriter nodeInputs = new FileWriter("NodeInput.txt");
//			FileWriter numberOfNodes = new FileWriter("numberOfNodes.txt");
//			for(int i = 0; i < view.getArcs().size(); i++) {
//				arcInputs.write(String.valueOf(view.getArcs().get(i).getInitNode()));
//				arcInputs.write(String.valueOf(view.getArcs().get(i).getEndNode()) + "\n");
//			}
//			
//			
//			numberOfNodes.write(String.valueOf(nodeNumber));
//			
//			for(int i= 0; i< view.getNodes().size(); i++) {
//				
//				
//				if(view.getNodes().get(i).isAttacker() == true) {
//					
//					
//					nodeInputs.write("A");
//					nodeInputs.write(String.valueOf((view.getNodes().get(i).getNumber())));
//					nodeInputs.write(String.valueOf((view.getNodes().get(i).isAttacker())) + "\n");
//				}
//				if(view.getNodes().get(i).isTarget() == true) {
//					
//					nodeInputs.write("T");
//					nodeInputs.write(String.valueOf((view.getNodes().get(i).getNumber())));
//					nodeInputs.write(String.valueOf((view.getNodes().get(i).isTarget())) + "\n");
//					
//				}
//				
//			}
//			
//			numberOfNodes.close();
//			arcInputs.close();
//			nodeInputs.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
//		view.getResultFrame().setVisible(true);
//		String command = "python3 example3.py";
//		try {
//			Process p = Runtime.getRuntime().exec(command);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		try {
//			File Outputs = new File("example.txt");
//			Scanner myReader = new Scanner(Outputs);
//			while(myReader.hasNextLine()) {
//				String output = myReader.nextLine();
//				System.out.println(output);
//			}
//			myReader.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try (Socket socket = new Socket("localhost", 5000)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(nodeNumber);

			for (int i = 0; i < view.getNodes().size(); i++) {
				if (view.getNodes().get(i).isAttacker()) {

					writer.println(view.getNodes().get(i).getNumber());

				} else if (view.getNodes().get(i).isTarget()) {

					writer.println(view.getNodes().get(i).getNumber());

				}
			}

			for (int i = 0; i < view.getArcs().size(); i++) {
				writer.println(String.valueOf(view.getArcs().get(i).getInitNode()));

				writer.println(String.valueOf(view.getArcs().get(i).getEndNode()));
			}

			writer.println("c");

			InputStream input = socket.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String line;

			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				lines.add(line);

			}

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());

		}

//    	for(String lineTemp: lines) {
//    		
//			if(lineTemp.startsWith("Number")) {
//				break;
//			} else {			
//				lines.remove(lineTemp);
//			}
//		}

		for (Iterator<String> iter = lines.iterator(); iter.hasNext();) {

			String lineTemp = iter.next();
			if (lineTemp.startsWith("Number")) {
				break;
			} else {
				iter.remove();
			}

		}

		for (String lineTemp : lines) {

//    		view.getLblResults().setText(view.getLblResults().getText() + "\n" + lineTemp + "\n");
//    		view.getTxtResults().setText(view.getLblResults().getText() + "\n" + lineTemp + "\n");
			JLabel labelTemp = new JLabel(lineTemp);
			view.getResultPanel().add(labelTemp);

		}

		view.getResultFrame().setVisible(true);
//			String command = "python3 example3.py";
//			try {
//				Process p = Runtime.getRuntime().exec(command);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//			while(true) {
//				Socket connected = server.accept();
//				BufferedReader inFromUser = new BufferedReader(
//						new InputStreamReader(System.in));
//				BufferedReader inFromClient = new BufferedReader(
//						new InputStreamReader (connected.getInputStream()));
//				PrintWriter outToClient = new PrintWriter(connected.getOutputStream(), true);
//				
//				
//				
//				toclient = Integer.toString(nodeNumber);
//				outToClient.println(toclient);
//				toclient = Integer.toString(arcNumber);
//				outToClient.println(toclient);
//				for(int i = 0; i < view.getArcs().size(); i++) {
//					toclientList.add(String.valueOf(view.getArcs().get(i).getInitNode())
//							+ String.valueOf(view.getArcs().get(i).getEndNode()));
//				}
//				
//				for(int i = 0; i < view.getArcs().size(); i++) {
//					outToClient.println(toclientList.get(i));
//				}
//				while((fromclient = inFromClient.readLine()) != null) {
//					
//					System.out.println(fromclient);
//				}
//				connected.close();
//				
//				
//					
//				
//				
//			server.close();
//			}
//			
//			
//			
//
//			
//			
//		} catch (IOException e1) {
//			//System.err.println("Invalid Server");
//		}

	}

	private int activateNodeInt() {
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

	private int activateArcInt() {
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

	private int activateMoveInt() {
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

	private int activateDeleteInt() {

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

	private int activateGetInfoInt() {

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

	// Create an arc between nodes
	private class ArcMouseAdapter extends MouseAdapter implements Command {

		private int initNode;
		private int endNode;
		private int x1, y1, x2, y2;
		Arc ArcRedo = null;

		@Override
		public void mousePressed(MouseEvent e) {

			if (activateArc == 1) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					for (int i = 0; i < view.getNodes().size(); i++) {
						int x = view.getNodes().get(i).getX() + 12;
						int y = view.getNodes().get(i).getY() + 12;
						int radius = view.getNodes().get(i).getDiameter() / 2;

						if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

							x1 = x;
							y1 = y;
							view.getCenterPanel().repaint();
							initNode = i;
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (activateArc == 1) {

				if (e.getButton() == MouseEvent.BUTTON1) {

					for (int i = 0; i < view.getNodes().size(); i++) {
						int x = view.getNodes().get(i).getX() + 12;
						int y = view.getNodes().get(i).getY() + 12;
						int radius = view.getNodes().get(i).getDiameter() / 2;

						if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

							x2 = x;
							if (x2 < x1) {

								x2 = x + 12;
								x1 = x1 - 12;

							} else if (x2 > x1) {

								x2 = x - 12;
								x1 = x1 + 12;

							} else {

								x2 = x;

							}
							y2 = y;
							endNode = i;

							Arc arc = new Arc(x1, y1, x2, y2, Color.black, initNode, endNode, 0, arcNumber, 0, 0, 0, 0);
							view.getArcs().add(arc);

							view.getCenterPanel().repaint();
							arcNumber++;

							stack.doCommand(new ArcMouseAdapter());

						}
					}
				}
			}
		}

		@Override
		public void execute() {

			view.getArcs().add(ArcRedo);
			System.out.println("Add Arc! - Undo");

		}

		@Override
		public void undo() {
			int undoArcIndex = view.getArcs().size() - 1;
			ArcRedo = view.getArcs().get(undoArcIndex);
			view.getArcs().remove(undoArcIndex);
			System.out.println("Delete Arc! - Redo");
		}

	}

	// Create a node
	private class NodeMouseAdapter extends MouseAdapter implements Command {

		Node nodeRedo = null;

		@Override
		public void mousePressed(MouseEvent e) {

			if (activateNode == 1) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Node node = new Node(e.getX(), e.getY(), 24, Color.white, "node " + nodeNumber, nodeNumber, false,
							false);
					view.getNodes().add(node);
					view.getCenterPanel().repaint();
					nodeNumber++;

					stack.doCommand(new NodeMouseAdapter());

				}
			}
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			view.getNodes().add(nodeRedo);
			System.out.println("Add Node - Redo!");
		}

		@Override
		public void undo() {
			// TODO Auto-generated method stub

			int undoNodeIndex = view.getNodes().size() - 1;
			nodeRedo = view.getNodes().get(undoNodeIndex);
			view.getNodes().remove(undoNodeIndex);
			System.out.println("Remove Node - Undo!");
		}
	}

	// Open arc pop-up menu
	private class ArcInfoMouseAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			for (int i = 0; i < view.getArcs().size(); i++) {

				Arc arc = view.getArcs().get(i);

				if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
						e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

					if (e.isPopupTrigger()) {
						view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
						arcPropertyInt = i;
					}

				}

			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			for (int i = 0; i < view.getArcs().size(); i++) {

				Arc arc = view.getArcs().get(i);

				if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
						e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

					if (e.isPopupTrigger()) {
						view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
						arcPropertyInt = i;
					}
				}
			}
		}
	}

	// Open node pop-up menu
	private class NodeInfoMouseAdpater extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			for (int i = 0; i < view.getNodes().size(); i++) {
				int x = view.getNodes().get(i).getX() + 12;
				int y = view.getNodes().get(i).getY() + 12;
				int radius = view.getNodes().get(i).getDiameter() / 2;

				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					if (e.isPopupTrigger()) {
						view.getNodePopUp().show(e.getComponent(), e.getX(), e.getY());
						nodePropertyInt = i;

					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			for (int i = 0; i < view.getNodes().size(); i++) {

				int x = view.getNodes().get(i).getX() + 12;

				int y = view.getNodes().get(i).getY() + 12;

				int radius = view.getNodes().get(i).getDiameter() / 2;

				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					if (e.isPopupTrigger()) {

						view.getNodePopUp().show(e.getComponent(), e.getX(), e.getY());

						nodePropertyInt = i;
					}
				}
			}
		}
	}

	// Deleter Arc
	private class DeleteArcMouseAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			undoArc();

			if (activateDelete == 1) {

				if (e.getButton() == MouseEvent.BUTTON1) {

					// line detection
					for (int i = 0; i < view.getArcs().size(); i++) {

						Arc arc = view.getArcs().get(i);

						if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
								e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2())
										* 1.002) {

							view.getArcs().remove(i);
							view.getCenterPanel().repaint();
						}
					}
				}
			}
		}
	}

	// Delete Node
	private class DeleteNodeMouseAdapter extends MouseAdapter implements Command {

		@Override
		public void mousePressed(MouseEvent e) {

			ArrayList<Integer> arcArray = new ArrayList<Integer>();

			if (activateDelete == 1) {

				for (int i = 0; i < view.getNodes().size(); i++) {

					int x = view.getNodes().get(i).getX() + 12;

					int y = view.getNodes().get(i).getY() + 12;

					int radius = view.getNodes().get(i).getDiameter() / 2;

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						int j = 0;
						while (j < view.getArcs().size()) {

							if (view.getNodes().get(i).getNumber() == view.getArcs().get(j).getEndNode()) {

								deleteUndoArcs.add(view.getArcs().get(j));

								view.getArcs().remove(j);

								view.getCenterPanel().repaint();

							} else {

								j++;
							}

						}

						int k = 0;
						while (k < view.getArcs().size()) {

							if (view.getNodes().get(i).getNumber() == view.getArcs().get(k).getInitNode()) {

								deleteUndoArcs.add(view.getArcs().get(k));

								view.getArcs().remove(k);

								view.getCenterPanel().repaint();

							} else {

								k++;
							}

						}

						deleteUndoNode = view.getNodes().get(i);

						view.getNodes().remove(i);

						view.getCenterPanel().repaint();

						stack.doCommand(new DeleteNodeMouseAdapter());

					}
				}
			}
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub

			for (int i = 0; i < view.getNodes().size(); i++) {

				if (view.getNodes().get(i).getNumber() == deleteUndoNode.getNumber()) {

					view.getNodes().remove(view.getNodes().get(i));

				}

			}

			for (int i = 0; i < view.getArcs().size(); i++) {

				for (int j = 0; j < deleteUndoArcs.size(); j++) {

					if (view.getArcs().get(i).getNumber() == deleteUndoArcs.get(j).getNumber()) {

						view.getArcs().remove(view.getArcs().get(i));

					}

				}

			}

			System.out.println("Delete Node - Undo!");

		}

		@Override
		public void undo() {

			System.out.println("Add Node - Undo!");
			view.getNodes().add(deleteUndoNode.getNumber(), deleteUndoNode);
			for (Arc arc : deleteUndoArcs) {

				System.out.println(arc.getNumber());
				view.getArcs().add(arc);

			}

			view.getCenterPanel().repaint();

		}
	}

	private class GetInfoMouseAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			if (activateGetInfo == 1) {

				for (int i = 0; i < view.getArcs().size(); i++) {

					Arc arc = view.getArcs().get(i);

					if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY())
							+ distance(arc.getX2(), arc.getY2(), e.getX(),
									e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

						System.out.println("X1 = " + arc.getX1() + ", Y1 = " + arc.getY1());
						System.out.println("X2 = " + arc.getX2() + ", Y2 = " + arc.getY2());
						System.out.println("InitNode = " + arc.getInitNode() + ", EndNode = " + arc.getEndNode());
						System.out.println("Vulnerability = " + arc.getVulnerability());
						System.out.println("Index = " + i);
					}

				}

				for (int i = 0; i < view.getNodes().size(); i++) {
					int x = view.getNodes().get(i).getX() + 12;
					int y = view.getNodes().get(i).getY() + 12;
					int radius = view.getNodes().get(i).getDiameter() / 2;
					Node node = view.getNodes().get(i);

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						System.out.println("X = " + node.getX() + ", Y = " + node.getY());
						System.out.println("Diameter = " + node.getDiameter() + ", Name = " + node.getName());
						System.out.println("Number = " + node.getNumber());
					}
				}
			}
		}
	}

	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	// Move node
	private class MoveMouseAdapter extends MouseAdapter implements Command {

		@Override
		public void mousePressed(MouseEvent e) {

			if (activateMove == 1) {

				moveUndoArcCounter = 0;

				for (int i = 0; i < view.getNodes().size(); i++) {

					int x = view.getNodes().get(i).getX() + 12;

					int y = view.getNodes().get(i).getY() + 12;

					int radius = view.getNodes().get(i).getDiameter() / 2;

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						moveUndoInteger = new ArrayList<>();

						moveNodeX = view.getNodes().get(i).getX();
						moveNodeY = view.getNodes().get(i).getY();

						nodeIndex = i;

						moveUndoInteger.add(nodeIndex);
						moveUndoInteger.add(moveNodeX);
						moveUndoInteger.add(moveNodeY);

						moveUndoMap.put(moveCounter, moveUndoInteger);

						moveCounter++;

					}
				}

				view.getCenterPanel().repaint();

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (activateMove == 1) {

				view.getNodes().get(nodeIndex).setX(e.getX());

				view.getNodes().get(nodeIndex).setY(e.getY());

				for (int i = 0; i < view.getArcs().size(); i++) {

					if (nodeIndex == view.getArcs().get(i).getInitNode()) {

						if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX1(e.getX() + 24);

						} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX1(e.getX());

						} else {

							view.getArcs().get(i).setX1(e.getX() + 12);

						}
						view.getArcs().get(i).setY1(e.getY() + 12);
					}
				}

				for (int i = 0; i < view.getArcs().size(); i++) {

					if (nodeIndex == view.getArcs().get(i).getEndNode()) {

						if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX2(e.getX());

						} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX2(e.getX() + 24);

						} else {

							view.getArcs().get(i).setX2(e.getX() + 12);

						}
						view.getArcs().get(i).setY2(e.getY() + 12);
					}
				}

				view.getCenterPanel().repaint();

				stack.doCommand(new MoveMouseAdapter());

			}

		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if (activateMove == 1) {

				view.getNodes().get(nodeIndex).setX(e.getX());

				view.getNodes().get(nodeIndex).setY(e.getY());

				for (int i = 0; i < view.getArcs().size(); i++) {

					if (nodeIndex == view.getArcs().get(i).getInitNode()) {

						if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX1(e.getX() + 24);

						} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX1(e.getX());

						} else {

							view.getArcs().get(i).setX1(e.getX() - 12);

						}
						view.getArcs().get(i).setY1(e.getY() + 12);
					}

				}

				for (int i = 0; i < view.getArcs().size(); i++) {

					if (nodeIndex == view.getArcs().get(i).getEndNode()) {

						if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX2(e.getX());

						} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

							view.getArcs().get(i).setX2(e.getX() + 24);

						} else {

							view.getArcs().get(i).setX2(e.getX() + 12);

						}
						view.getArcs().get(i).setY2(e.getY() + 12);

					}
				}
				view.getCenterPanel().repaint();
			}
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub

			int nodeNumber;
			int xLocation;
			int yLocation;

			nodeNumber = moveRedoMap.get(moveRedoCounter - 1).get(0);
			xLocation = moveRedoMap.get(moveRedoCounter - 1).get(1);
			yLocation = moveRedoMap.get(moveRedoCounter - 1).get(2);

			// System.out.println(nodeNumber);

			// System.out.println(moveRedoMap);
			// System.out.println(moveRedoCounter);

			for (int i = 0; i < view.getNodes().size(); i++) {

				List<Integer> undoList = new ArrayList<>();

				if (view.getNodes().get(i).getNumber() == nodeNumber) {

					undoList.add(view.getNodes().get(i).getNumber());
					undoList.add(view.getNodes().get(i).getX());
					undoList.add(view.getNodes().get(i).getY());

					view.getNodes().get(i).setX(xLocation);
					view.getNodes().get(i).setY(yLocation);

					moveUndoMap.put(moveRedoCounter, undoList);

					moveRedoMap.remove(moveRedoCounter);

					moveCounter++;
					moveRedoCounter--;

				}

			}
			
			
			for (int i = 0; i < view.getArcs().size(); i++) {

				view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() + 12);
				view.getArcs().get(i).setY1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getY() + 12);
				view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 12);
				view.getArcs().get(i).setY2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getY() + 12);
				
				if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() + 24);

				} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX());

				} else {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() - 12);

				}
				
				if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX());

				} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 24);

				} else {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 12);

				}
				

			}

			System.out.println("Move - Redo!");

		}

		@Override
		public void undo() {

//			view.getNodes().get(nodeIndex).setX(moveNodeX);
//			view.getNodes().get(nodeIndex).setY(moveNodeY);

//			
//			System.out.println(moveNodeX);
//			
//			System.out.println(moveUndoNode.getX());

			// System.out.println(moveUndoMap);

			int nodeNumber;
			int xLocation;
			int yLocation;

			// System.out.println(moveCounter);

			nodeNumber = moveUndoMap.get(moveCounter - 1).get(0);
			xLocation = moveUndoMap.get(moveCounter - 1).get(1);
			yLocation = moveUndoMap.get(moveCounter - 1).get(2);

			for (int i = 0; i < view.getNodes().size(); i++) {

				List<Integer> redoList = new ArrayList<>();

				if (view.getNodes().get(i).getNumber() == nodeNumber) {

					redoList.add(view.getNodes().get(i).getNumber());
					redoList.add(view.getNodes().get(i).getX());
					redoList.add(view.getNodes().get(i).getY());

					view.getNodes().get(i).setX(xLocation);
					view.getNodes().get(i).setY(yLocation);

					moveRedoMap.put(moveRedoCounter, redoList);

					moveUndoMap.remove(moveCounter);
					moveRedoCounter++;
					moveCounter--;

				}

			}

			for (int i = 0; i < view.getArcs().size(); i++) {

				view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() + 12);
				view.getArcs().get(i).setY1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getY() + 12);
				view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 12);
				view.getArcs().get(i).setY2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getY() + 12);
				
				if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() + 24);

				} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX());

				} else {

					view.getArcs().get(i).setX1(view.getNodes().get(view.getArcs().get(i).getInitNode()).getX() - 12);

				}
				
				if (view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX());

				} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 24);

				} else {

					view.getArcs().get(i).setX2(view.getNodes().get(view.getArcs().get(i).getEndNode()).getX() + 12);

				}
				

			}
			System.out.println(moveUndoArcMap);
			System.out.println(moveArcCounter);
			System.out.println(moveUndoArcCounter);
			System.out.println("Move - Undo!");

		}
	}

	// Check the nodes are in the mouse pointer
	public boolean isInsideEllipse(Point point) {
		for (int i = 0; i < view.getNodes().size(); i++) {
			int x = view.getNodes().get(i).getX() + 12;
			int y = view.getNodes().get(i).getY() + 12;
			int radius = view.getNodes().get(i).getDiameter() / 2;

			if (Math.pow(x - point.getX(), 2) + Math.pow(y - point.getY(), 2) <= Math.pow(radius, 2)) {

				return true;
			}
		}
		return false;
	}

	private void undoArc() {

		view.getArcsUndo().clear();

		for (Arc arc : view.getArcs()) {

			view.getArcsUndo().add(arc);

		}

	}

	// Get an info of node when each node is hovered
	private void getNodeInfo() {

		view.getCenterPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = view.getNodes().get(0).getX();
				int y = view.getNodes().get(0).getY();
				int radius = view.getNodes().get(0).getDiameter() / 2;

				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

				}
			}
		});

	}

}