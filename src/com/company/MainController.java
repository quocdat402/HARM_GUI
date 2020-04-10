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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
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

	
	public MainController(MainModel m,MainView v) {
		
		view = v;
		model = m;
		
		initView();
		
	}
	
	public void initView() {
		
	}
	
	public void initController() {		
		
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter();
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter();
		MoveMouseAdapter moveMouseAdapter = new MoveMouseAdapter();
		DeleteArcMouseAdapter deleteArcMouseAdapter = new DeleteArcMouseAdapter();
		DeleteNodeMouseAdapter deleteNodeMouseAdapter = new DeleteNodeMouseAdapter();
		GetInfoMouseAdapter getInforMouseAdapter = new GetInfoMouseAdapter();
		NodeInfoMouseAdpater nodeInfoMouseAdpater = new NodeInfoMouseAdpater();
		ArcInfoMouseAdapter arcInfoMouseAdapter = new ArcInfoMouseAdapter();
		
		
		
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
		view.getBtnClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	view.getCenterPanel().removeAll();
            	view.getCenterPanel().validate();
            	view.getCenterPanel().repaint();
            	view.getArcs().clear();
            	view.getNodes().clear();
            	arcNumber = 0;
            	nodeNumber = 0;
            }
        });
		view.getMntmAttackgraph().addActionListener(e-> attackGraphAction());
		view.getBtnNode().addActionListener(e->activateNodeInt());
		view.getBtnArc().addActionListener(e->activateArcInt());
		view.getBtnMove().addActionListener(e -> activateMoveInt());
		view.getBtnDelete().addActionListener(e -> activateDeleteInt());
		view.getBtnGetinfo().addActionListener(e -> activateGetInfoInt());
		view.getNodeAttacker().addActionListener(e -> nodeAttacker());
		view.getNodeTarget().addActionListener(e -> nodeTarget());
		view.getCenterPanel().addMouseListener(new MouseAdapter() {
	        
			@Override
			public void mousePressed(MouseEvent e) {
	                		
	        	for(int i = 0; i < view.getNodes().size(); i++) {
	        		int x = view.getNodes().get(i).getX() + 12;
		       		int y = view.getNodes().get(i).getY() + 12;
		       		int radius = view.getNodes().get(i).getDiameter()/2;
		       			        		
		       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
		       			
		       		}
	        	}
	        }		
		});
		
		
		view.getBtnName().addActionListener(new ActionListener() {
            
			
			@Override
            public void actionPerformed(ActionEvent evt) {
				
				if(view.getTxtName() == null) {
					
				} else {
					
					view.getNodes().get(nodePropertyInt).setName(view.getTxtName().getText());
					view.getCenterPanel().repaint();
				}			
				
			}			
			
		});
		
		view.getBtnVul().addActionListener(new ActionListener() {
            
			
			@Override
            public void actionPerformed(ActionEvent evt) {
				
				if(view.getTxtVul() == null) {
					
				} else {
					
					view.getArcs().get(arcPropertyInt).setVulnerability(Double.valueOf(view.getTxtVul().getText()));
					view.getCenterPanel().repaint();
				}			
				
			}			
			
		});
		
	}
	
	private void nodeAttacker() {
		
		int AttackerSetting = 0;
		
		for(int i = 0; i < view.getNodes().size(); i++) {
			if(view.getNodes().get(i).isAttacker() == true) {
				JOptionPane.showMessageDialog(null, "Attacker is already set");
				AttackerSetting = 1;
			}
			
		}
		
		if(AttackerSetting == 1) {
			
		} else {
			view.getNodes().get(nodePropertyInt).setAttacker(true);
		}
	}
	
	private void nodeTarget() {
		
		int targetSetting = 0;
		
		for(int i = 0; i < view.getNodes().size(); i++) {
			if(view.getNodes().get(i).isTarget() == true) {
				JOptionPane.showMessageDialog(null, "Target is already set");
				targetSetting = 1;
			}
			
		}
		
		if(targetSetting == 1) {
			
		} else {
			view.getNodes().get(nodePropertyInt).setTarget(true);
		} 
		
	}
	
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
		
		String fromclient;
    	String toclient;
    	
    	try {
			ServerSocket server = new ServerSocket(5000);
			System.out.println("Waiting for client on port 5000");
			
//			String command = "python3 example3.py";
//			try {
//				Process p = Runtime.getRuntime().exec(command);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			List<String> toclientList = new ArrayList<String>();
			
			while(true) {
				Socket connected = server.accept();
				BufferedReader inFromUser = new BufferedReader(
						new InputStreamReader(System.in));
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader (connected.getInputStream()));
				PrintWriter outToClient = new PrintWriter(connected.getOutputStream(), true);
				
				
				
				toclient = Integer.toString(nodeNumber);
				outToClient.println(toclient);
				toclient = Integer.toString(arcNumber);
				outToClient.println(toclient);
				for(int i = 0; i < view.getArcs().size(); i++) {
					toclientList.add(String.valueOf(view.getArcs().get(i).getInitNode())
							+ String.valueOf(view.getArcs().get(i).getEndNode()));
				}
				
				for(int i = 0; i < view.getArcs().size(); i++) {
					outToClient.println(toclientList.get(i));
				}
				while((fromclient = inFromClient.readLine()) != null) {
					
					System.out.println(fromclient);
				}
				connected.close();
				
				
					
				
				
			server.close();
			}
			
			
			

			
			
		} catch (IOException e1) {
			//System.err.println("Invalid Server");
		}
		
		
		
	}
	
	private int activateNodeInt() {
		if (activateNode == 0) {
			if(activateArc == 1  || activateMove == 1 || activateDelete == 1 || activateGetInfo == 1) {
				
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
			if(activateNode == 1 || activateMove == 1 || activateDelete == 1 || activateGetInfo == 1) {
				
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
			if(activateNode == 1 || activateArc == 1 || activateDelete == 1 || activateGetInfo == 1) {
				
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
			if(activateNode == 1 || activateArc == 1 || activateMove == 1 || activateGetInfo == 1) {
				
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
			if(activateNode == 1 || activateArc == 1 || activateMove == 1 || activateDelete == 1) {
				
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
	
    private class ArcMouseAdapter extends MouseAdapter {
    	
    	private int initNode;
    	private int endNode;
    	private int x1, y1, x2, y2;

        @Override
        public void mousePressed(MouseEvent e) {
            
        	if(activateArc == 1) {
        		
        		if(e.getButton() == MouseEvent.BUTTON1) {      		
	        		for(int i = 0; i < view.getNodes().size(); i++) {
		        		int x = view.getNodes().get(i).getX() + 12;
			       		int y = view.getNodes().get(i).getY() + 12;
			       		int radius = view.getNodes().get(i).getDiameter()/2;
			       			        		
			       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
			       			
			       			x1 =  x;
			       			y1 =  y;		        	
			        		view.getCenterPanel().repaint();
			        		initNode = i;
			       		}
		        	}	
        		}
        	}
        }
        
    	@Override
        public void mouseReleased(MouseEvent e) {
    		if(activateArc == 1) {
        		
    			if(e.getButton() == MouseEvent.BUTTON1) {
    			
	        		for(int i = 0; i < view.getNodes().size(); i++) {
		        		int x = view.getNodes().get(i).getX() + 12;
			       		int y = view.getNodes().get(i).getY() + 12;
			       		int radius = view.getNodes().get(i).getDiameter()/2;
			       			        		
			       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
			       			
			       			x2 = x;
			       			if (x2 < x1) {
			       				
			       				x2 = x + 12;
			       				x1 = x1 - 12;
			       				
			       			} else if ( x2 > x1) {
			       				
			       				x2 = x - 12;
			       				x1 = x1 + 12;
			       				
			       			} else {
			       				
			       				x2 = x;
			       				
			       			}
			       			y2 = y;
			       			endNode = i;
			       			Arc arc = new Arc(x1, y1, x2, y2, Color.black, initNode, endNode,0,arcNumber);
			        		view.getArcs().add(arc);
			        		
			        		view.getCenterPanel().repaint();
			        		arcNumber++;
			        		
			       		}
		        	}	
    			}
        	}   		
        }  	
    	
    	
    }


	private class NodeMouseAdapter extends MouseAdapter {
				
		@Override
		public void mousePressed(MouseEvent e) {
			
			if (activateNode ==1) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					Node node = new Node(e.getX(), e.getY(), 24, Color.white, "node " + nodeNumber, nodeNumber, false, false);			
					view.getNodes().add(node);
					view.getCenterPanel().repaint();
					nodeNumber++;
					
				}
			}
		}
	}
	
	private class ArcInfoMouseAdapter extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			for (int i = 0; i< view.getArcs().size(); i++) {
				
				Arc arc = view.getArcs().get(i);
				
				if(distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(), e.getX(), e.getY()) < 
						distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {
					
					if(e.isPopupTrigger()) {
	    				view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
	    				arcPropertyInt = i;
					}	
					
					
				}
				
				
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
			for (int i = 0; i< view.getArcs().size(); i++) {
				
				Arc arc = view.getArcs().get(i);
				
				if(distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(), e.getX(), e.getY()) < 
						distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {
					
					if(e.isPopupTrigger()) {
	    				view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
	    				arcPropertyInt = i;
	    			}		
				}	
			}	
		}
	}
	
	private class NodeInfoMouseAdpater extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			
			for(int i = 0; i < view.getNodes().size(); i++) {
        		int x = view.getNodes().get(i).getX() + 12;
	       		int y = view.getNodes().get(i).getY() + 12;
	       		int radius = view.getNodes().get(i).getDiameter()/2;
	       			        		
	       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
	       			
	       			if(e.isPopupTrigger()) {
	    				view.getNodePopUp().show(e.getComponent(), e.getX(), e.getY());
	    				nodePropertyInt = i;
	       			
	       			}	
	       		}
        	}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
			
			for(int i = 0; i < view.getNodes().size(); i++) {
        		int x = view.getNodes().get(i).getX() + 12;
	       		int y = view.getNodes().get(i).getY() + 12;
	       		int radius = view.getNodes().get(i).getDiameter()/2;
	       			        		
	       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
	       			
	       			if(e.isPopupTrigger()) {
	    				view.getNodePopUp().show(e.getComponent(), e.getX(), e.getY());
	    				nodePropertyInt = i;
	       			}	
	       		}
        	}	
		}
	}
	
	
	private class DeleteArcMouseAdapter extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (activateDelete == 1) {
				
				if(e.getButton() == MouseEvent.BUTTON1) {
				
					//line detection
					for (int i = 0; i< view.getArcs().size(); i++) {
											
						Arc arc = view.getArcs().get(i);
						
						if(distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(), e.getX(), e.getY()) < 
								distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {
							
							view.getArcs().remove(i);
							view.getCenterPanel().repaint();
						}		
					}
				}
			}
		}
	}
	
	private class DeleteNodeMouseAdapter extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			ArrayList<Integer> arcArray = new ArrayList<Integer>();
			
			if (activateDelete == 1) {
			
				for(int i = 0; i < view.getNodes().size(); i++) {
	        		int x = view.getNodes().get(i).getX() + 12;
		       		int y = view.getNodes().get(i).getY() + 12;
		       		int radius = view.getNodes().get(i).getDiameter()/2;
		       			        		
		       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
		       				   
		       			
		       			int j = 0;
		       			while ( j < view.getArcs().size()) {
		       				
		       				if(view.getNodes().get(i).getNumber() == view.getArcs().get(j).getEndNode()) {
		       					
		       					view.getArcs().remove(j);
		       					view.getCenterPanel().repaint();
		       					
		       				} else {
		       					
		       					j++;
		       				}
		       				
		       				
		       			}
		       			
		       			int k = 0;
		       			while ( k < view.getArcs().size()) {
		       				
		       				if(view.getNodes().get(i).getNumber() == view.getArcs().get(k).getInitNode()) {
		       					
		       					view.getArcs().remove(k);
		       					view.getCenterPanel().repaint();
		       					
		       				} else {
		       					
		       					k++;
		       				}
		       				
		       				
		       			}
		       			
		       			
		       			view.getNodes().remove(i);
		       			
		        		view.getCenterPanel().repaint();
		        		
		       		}
	        	}
			}
		}
	}
	
	private class GetInfoMouseAdapter extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			if( activateGetInfo == 1) {
			
				for (int i = 0; i< view.getArcs().size(); i++) {
					
					Arc arc = view.getArcs().get(i);
					
					if(distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(), e.getX(), e.getY()) < 
							distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {
						
						System.out.println("X1 = " + arc.getX1() + ", Y1 = " + arc.getY1());
						System.out.println("X2 = " + arc.getX2() + ", Y2 = " + arc.getY2());
						System.out.println("InitNode = " + arc.getInitNode() + ", EndNode = " + arc.getEndNode());
						System.out.println("Vulnerability = " + arc.getVulnerability());
						System.out.println("Index = " + i);
					}
					
					
				}
				
				for(int i = 0; i < view.getNodes().size(); i++) {
		    		int x = view.getNodes().get(i).getX() + 12;
		       		int y = view.getNodes().get(i).getY() + 12;
		       		int radius = view.getNodes().get(i).getDiameter()/2;
		       		Node node = view.getNodes().get(i);	        		
		       		
		       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
		       			
		       			System.out.println("X = " +  node.getX() + ", Y = " + node.getY());
		       			System.out.println("Diameter = " +  node.getDiameter() + ", Name = " + node.getName());
		       			System.out.println("Number = " +  node.getNumber());
		       		}
				}
			}
		}
	}
	
	public double distance(int x1, int y1, int x2, int y2) {       
			    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
			}
	
	private class MoveMouseAdapter extends MouseAdapter {
		
		private int xLocation;
		private int yLocation;
		private int nodeIndex;
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			if (activateMove == 1) {
				for(int i = 0; i < view.getNodes().size(); i++) {
		    		int x = view.getNodes().get(i).getX() + 12;
		       		int y = view.getNodes().get(i).getY() + 12;
		       		int radius = view.getNodes().get(i).getDiameter()/2;
		       			        		
		       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
		       			
		       			nodeIndex = i;
		       			
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
				
				for(int i = 0; i < view.getArcs().size(); i++) {
					
					if(nodeIndex == view.getArcs().get(i).getInitNode()) {
						
						if(view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {
							
							view.getArcs().get(i).setX1(e.getX() + 24);
							
						} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {
							
							view.getArcs().get(i).setX1(e.getX());
							
						} else {
							
							view.getArcs().get(i).setX1(e.getX() + 12);
							
						}													
						view.getArcs().get(i).setY1(e.getY() + 12);
					}					
				}
				
				for(int i = 0; i < view.getArcs().size(); i++) {
					
					if(nodeIndex == view.getArcs().get(i).getEndNode()) {
						
						if(view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {
							
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
		public void mouseDragged(MouseEvent e) {
			
			if (activateMove == 1) {
				view.getNodes().get(nodeIndex).setX(e.getX());
				view.getNodes().get(nodeIndex).setY(e.getY());
				
				for(int i = 0; i < view.getArcs().size(); i++) {
					
					if(nodeIndex == view.getArcs().get(i).getInitNode()) {
						
						if(nodeIndex == view.getArcs().get(i).getInitNode()) {
							
							if(view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {
								
								view.getArcs().get(i).setX1(e.getX() + 24);
								
							} else if (view.getArcs().get(i).getX2() < view.getArcs().get(i).getX1()) {
								
								view.getArcs().get(i).setX1(e.getX());
								
							} else {
								
								view.getArcs().get(i).setX1(e.getX() - 12);
								
							}													
							view.getArcs().get(i).setY1(e.getY() + 12);
						}
						
						
					}
				}
				
				
				
				
				for(int i = 0; i < view.getArcs().size(); i++) {
					
					if(nodeIndex == view.getArcs().get(i).getEndNode()) {
						
						if(view.getArcs().get(i).getX2() > view.getArcs().get(i).getX1()) {
							
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
	}
	
	public boolean isInsideEllipse(Point point) {
		for(int i = 0; i < view.getNodes().size(); i++) {
    		int x = view.getNodes().get(i).getX() + 12;
       		int y = view.getNodes().get(i).getY() + 12;
       		int radius = view.getNodes().get(i).getDiameter()/2;
       			        		
       		if(Math.pow(x-point.getX(), 2) + Math.pow(y-point.getY(), 2) <= Math.pow(radius, 2)) {
       			
       			return true;
       		}
    	}
		return false;
	}
	
	
	private void printNodeInfo() {
		
	}
	
	//Get an info of node when each node is hovered
	private void getNodeInfo() {
		
		view.getCenterPanel().addMouseListener(new MouseAdapter() {
        @Override
			public void mouseMoved(MouseEvent e) {
        		int x = view.getNodes().get(0).getX();
        		int y = view.getNodes().get(0).getY();
        		int radius = view.getNodes().get(0).getDiameter()/2;
        		
        		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
        			
        		}
        	}
		});
		
	}
	
	
	
	
}