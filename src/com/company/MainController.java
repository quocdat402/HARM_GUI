package com.company;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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



public class MainController {

	private MainView view;
	private MainModel model;
	private int counter = 0;//To know the number of routers pasted on center panel
	private int clickCount = 0;
	private ByteArrayOutputStream baos;
    private ByteArrayInputStream bais;
	
	public MainController(MainModel m,MainView v) {
		
		view = v;
		model = m;
		
		initView();
		
	}
	
	public void initView() {
		
	}
	
	public void initController() {
		
		
		
		view.getBtnClear().addActionListener(e -> clearAll());
		
		view.getButton().addActionListener(new ActionListener()
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
		
		
		
		
		
	}
	
	private void clearAll() {
		
		view.getBtnClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	view.getCenterPanel().removeAll();
            	view.getCenterPanel().validate();
            	view.getCenterPanel().repaint();
            	view.getListOfPairs().clear();
            	view.getMap().clear();
            	counter = 0;

            }
        });
		
	}
	
	private String[] showGUIForNodeSelection()
    {
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
    	
        if (clickCount == 1) {
            bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
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
    }
    
    class MyMouseAdapter extends MouseAdapter {
        private Point initialLoc;
        private Point initialLocOnScreen;

        @Override
        public void mousePressed(MouseEvent e) {
            Component comp = (Component) e.getSource();
            initialLoc = comp.getLocation();
            initialLocOnScreen = e.getLocationOnScreen();
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
            view.getMap().put(((JLabel)comp).getText(),new Point(x,y));
            view.getCenterPanel().repaint();
        }
        
        
    }
	
}
