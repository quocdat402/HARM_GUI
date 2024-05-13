package com.company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class AttackTreeView extends JFrame {
    private JPanel attackTreePanel;
    private JTextArea textArea;
    private JButton btnGenerateTree;
    private MainController controller;

    public AttackTreeView(MainController controller) {
        setTitle("Create Attack Tree");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 850));

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the "File" menu
        JMenu fileMenu = new JMenu("File");
        // Add menu items to the "File" menu
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        
        // Create the "Help" menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToCreateMenuItem = new JMenuItem("How to Create Attack Tree");
        helpMenu.add(howToCreateMenuItem);

        // Add the menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Create the attack tree panel
        attackTreePanel = new JPanel();
        attackTreePanel.setLayout(null);
        mainPanel.add(attackTreePanel, BorderLayout.CENTER);

        // Create the text area and scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        // Add the text area to the top-right corner
        JPanel topRightPanel = new JPanel(new BorderLayout());
        topRightPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(topRightPanel, BorderLayout.NORTH);

        btnGenerateTree = new JButton("Generate Tree");
        btnGenerateTree.addActionListener(e -> {
            AttackTreeController viewController = new AttackTreeController(this);
            viewController.generateAttackTree();
        });

        // Add the "Generate Tree" button to the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnGenerateTree);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    // getters and setters for the components
    public JTextArea getTextArea() {
        return textArea;
    }

    public JButton getBtnGenerateTree() {
        return btnGenerateTree;
    }

    public JPanel getAttackTreePanel() {
        return attackTreePanel;
    }
}