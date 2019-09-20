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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.company.*;



public class MainController {

	private MainView view;
	private MainModel model;
	private int counter = 0;//To know the number of routers pasted on center panel
	private int clickCount = 0;
	private ByteArrayOutputStream baos;
    private ByteArrayInputStream bais;
    private ObjectInputStream ois;
    private int activateNode;
    private int activateArc;
    private int activateMove;
    private int activateDelete;
    private int activateGetInfo;
    
    private int nodeNumber;
    private boolean dragging = false;
    private Point last;
	private int width, height;
	
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
		
		view.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                String[] nodes = showGUIForNodeSelection();
                int i = 0;
                System.out.println(nodes[1]);
                System.out.println(view.getMap().keySet());
                System.out.println(view.getMap().get("Node " + i).x);
                System.out.println(view.getMap().get("Node 0").y);
                if (nodes == null || nodes[0]==null || nodes.length == 0 ) {
                	
                }
                else if (!nodes[0].equals(nodes[1])) {
                    String split[] = nodes[0].split(",");                    
                    Point p1 = new Point(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
                    split = nodes[1].split(",");
                    Point p2 = new Point(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
                    JLabel label1 = (JLabel)view.getCenterPanel().getComponentAt(p1);
                    JLabel label2 = (JLabel)view.getCenterPanel().getComponentAt(p2);
                                       
                    Pair pair = new Pair(label1,label2);
                    view.getListOfPairs().add(pair);
                    view.getCenterPanel().repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Nodes can't be same","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });	
		
		view.getLblNode().addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                clickCount = 1;
                try {
                    copy((JLabel) e.getSource());
                } catch (Exception ex) {
                }
            }
        });		
		
		view.getCenterPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickCount == 1) {
                    try {
                        pasteLabel(e.getX(), e.getY());
                    } catch (Exception ex) {
                    }
                }
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
            	
            	nodeNumber = 0;
            }
        });
		view.getBtnNode().addActionListener(e->activateNodeInt());
		view.getBtnArc().addActionListener(e->activateArcInt());
		view.getBtnMove().addActionListener(e -> activateMoveInt());
		view.getBtnDelete().addActionListener(e -> activateDeleteInt());
		view.getBtnGetinfo().addActionListener(e -> activateGetInfoInt());
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
	
	private void deleteNodesArcs() {
		
	}
	
	private void clearAll() {
		
		view.getBtnClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	view.getCenterPanel().removeAll();
            	view.getCenterPanel().validate();
            	view.getCenterPanel().repaint();
            	view.getArcs().clear();
            	view.getNodes().clear();
            	
            	nodeNumber = 0;
            }
        });
	}
	
	private String[] showGUIForNodeSelection() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(view.getMap().size(),2));
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        final String nodes[] = new String[2];
        Set<String> keySet = view.getMap().keySet();
        for (String name : keySet)
        {
        	JRadioButton rButton = new JRadioButton(name);
            rButton.setActionCommand(view.getMap().get(name).x+","+view.getMap().get(name).y);
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
            rButton1.setActionCommand(view.getMap().get(name).x+","+view.getMap().get(name).y);
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
	
	public void copy(JLabel label) throws Exception {
        baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(label);
        oos.close();
    }

    public void pasteLabel(int x, int y) throws Exception {
    	
        
        bais = new ByteArrayInputStream(baos.toByteArray());
        ois = new ObjectInputStream(bais);
        JLabel obj = (JLabel) ois.readObject();
                
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        
        obj.addMouseListener(myMouseAdapter);
        obj.addMouseMotionListener(myMouseAdapter);
        view.getCenterPanel().add(obj);
        obj.setText("Node "+counter);
        //obj.setBounds(x, y, obj.getWidth(), obj.getHeight());
        obj.setLocation(x,y);
        clickCount = 0;
        ois.close();
        view.getMap().put("Node "+counter , obj.getLocation());
        counter++;
    }
    
    private class MyMouseAdapter extends MouseAdapter {
        private Point initialLoc;
        private Point initialLocOnScreen;

        @Override
        public void mousePressed(MouseEvent e) {
            Component comp = (Component) e.getSource();
            initialLoc = comp.getLocation();
            initialLocOnScreen = e.getLocationOnScreen();
            
            System.out.println(initialLoc);
            System.out.println(comp);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Component comp = (Component) e.getSource();
            Point locOnScreen = e.getLocationOnScreen();

            int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
            int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
            comp.setLocation(x, y);
            
            view.getMap().put(((JLabel)comp).getText(),new Point(x,y));
            view.getCenterPanel().repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component comp = (Component) e.getSource();
            Point locOnScreen = e.getLocationOnScreen();
            int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
            int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
            comp.setLocation(x, y);
            view.getNodes();
            view.getMap().put(((JLabel)comp).getText(),new Point(x,y));
            view.getCenterPanel().repaint();
        }
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
        		//x2 = e.getX();
        		//y2 = e.getY();
        		
        		//Arc arc = new Arc(x1, y1, x2, y2, Color.black);
        		//view.getArcs().add(arc);
        		
        		//view.getCenterPanel().repaint();
        		
    			if(e.getButton() == MouseEvent.BUTTON1) {
    			
	        		for(int i = 0; i < view.getNodes().size(); i++) {
		        		int x = view.getNodes().get(i).getX() + 12;
			       		int y = view.getNodes().get(i).getY() + 12;
			       		int radius = view.getNodes().get(i).getDiameter()/2;
			       			        		
			       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
			       			
			       			x2 = x;
			       			if (x2 < x1) {
			       				
			       				x2 = x + 12;
			       				
			       			} else if ( x2 > x1) {
			       				
			       				x2 = x - 12;
			       				
			       			} else {
			       				
			       				x2 = x;
			       				
			       			}
			       			y2 = y;
			       			endNode = i;
			       			Arc arc = new Arc(x1, y1, x2, y2, Color.black, initNode, endNode,0);
			        		view.getArcs().add(arc);
			        		
			        		view.getCenterPanel().repaint();
			        		
			        		
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
					Node node = new Node(e.getX(), e.getY(), 24, Color.white, "node " + nodeNumber, nodeNumber);			
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
			
			if (activateDelete == 1) {
			
				for(int i = 0; i < view.getNodes().size(); i++) {
	        		int x = view.getNodes().get(i).getX() + 12;
		       		int y = view.getNodes().get(i).getY() + 12;
		       		int radius = view.getNodes().get(i).getDiameter()/2;
		       			        		
		       		if(Math.pow(x-e.getX(), 2) + Math.pow(y-e.getY(), 2) <= Math.pow(radius, 2)) {
		       				   
		       			
		       			for (int j = 0; j< view.getArcs().size(); j++) {
							
							
							/*
							Arc arc = view.getArcs().get(j);
							
							if(view.getNodes().get(i).getNumber() == arc.getInitNode() ||
									view.getNodes().get(i).getNumber() == arc.getEndNode()) {
								
								view.getArcs().remove(j);
							}
							*/
		       				
		       				if(view.getNodes().get(i).getNumber() == view.getArcs().get(j).getInitNode()) {
		       					
		       					System.out.println(j);
		       					view.getArcs().remove(j);
		       					
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
						
						view.getArcs().get(i).setX1(e.getX() + 12);
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
						
						view.getArcs().get(i).setX1(e.getX() + 12);
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