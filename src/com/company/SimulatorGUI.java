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

public class SimulatorGUI extends JFrame {
    private JPanel contentPane;
    JPanel southPanel;
    JPanel centerPanel;
    JPanel westPanel;
    int clickCount = 0;
    ArrayList<Pair> listOfPairs;//Added for storing pairs of nodes
    Map<String, Point> map;//Stores Jlabel added on center panel and its location.
    ByteArrayOutputStream baos;
    ByteArrayInputStream bais;
    int counter = 0;//To know the number of routers pasted on center panel

    public SimulatorGUI() {
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
        JLabel lblNode = new JLabel("Node Label");
        lblNode.setBorder(BorderFactory.createLineBorder(Color.black,1));
        southPanel.add(lblNode);
        class MyMouseAdapter extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount = 1;
                try {
                    copy((JLabel) e.getSource());
                } catch (Exception ex) {
                }
            }
        }
        lblNode.addMouseListener(new MyMouseAdapter());
        southPanel.setBorder(BorderFactory.createTitledBorder("Selector"));
        contentPane.add(southPanel, BorderLayout.SOUTH);

        //centerPanel = new JPanel();
        centerPanel = new MyJPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Work Space", TitledBorder.CENTER, TitledBorder.TOP));
        centerPanel.addMouseListener(new MouseAdapter() {
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
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(null);

        westPanel = new JPanel();
        contentPane.add(westPanel, BorderLayout.WEST);
        westPanel.setBorder(BorderFactory.createTitledBorder("Option"));
        JButton btnClear = new JButton("Clear");
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanel.removeAll();
                centerPanel.validate();
                centerPanel.repaint();
                listOfPairs.clear();map.clear();
                counter = 0;
            }
        });
        westPanel.add(btnClear);
        JButton button  = new JButton("DrawArc");
        button.addActionListener(new ActionListener()
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
                    JLabel label1 = (JLabel)centerPanel.getComponentAt(p1);
                    JLabel label2 = (JLabel)centerPanel.getComponentAt(p2);
                    Pair pair = new Pair(label1,label2);
                    listOfPairs.add(pair);
                    centerPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(SimulatorGUI.this,"Nodes can't be same","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        westPanel.add(button);
    }
    private String[] showGUIForNodeSelection()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(map.size(),2));
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        final String nodes[] = new String[2];
        Set<String> keySet = map.keySet();
        for (String name : keySet)
        {
            JRadioButton rButton = new JRadioButton(name);
            rButton.setActionCommand(map.get(name).x+","+map.get(name).y);
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
            rButton1.setActionCommand(map.get(name).x+","+map.get(name).y);
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
        JOptionPane.showMessageDialog(SimulatorGUI.this,panel,"Choose the nodes pair",JOptionPane.INFORMATION_MESSAGE);
        return nodes;
    }
    private class MyJPanel extends JPanel//Creater your own JPanel and override paintComponentMethod.
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
            centerPanel.add(obj);
            obj.setText("Node "+counter);
            //obj.setBounds(x, y, obj.getWidth(), obj.getHeight());
            obj.setLocation(x,y);
            clickCount = 0;
            ois.close();
            map.put("Node "+counter , obj.getLocation());
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
            map.put(((JLabel)comp).getText(),new Point(x,y));
            centerPanel.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component comp = (Component) e.getSource();
            Point locOnScreen = e.getLocationOnScreen();

            int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
            int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
            comp.setLocation(x, y);
            map.put(((JLabel)comp).getText(),new Point(x,y));
            centerPanel.repaint();
        }
    }
}
