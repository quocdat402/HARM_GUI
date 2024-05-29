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
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Color;


public class AttackTreeView extends JFrame {
    private JPanel attackTreePanel;
    private JTextArea textArea;
    private JButton btnGenerateTree;
    private MainController mainController;

    public AttackTreeView(MainController controller, MainController mainController) {

        this.mainController = mainController;
        setTitle("Create Attack Tree");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(900, 800));

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

        // Add action listeners to the menu items
        openMenuItem.addActionListener(e -> mainController.loadAction());
        saveMenuItem.addActionListener(e -> mainController.saveAction());
        saveAsMenuItem.addActionListener(e -> mainController.saveAsAction());
        howToCreateMenuItem.addActionListener(e -> mainController.showHowToUseAT());

        // Create the attack tree panel
        attackTreePanel = new JPanel();
        attackTreePanel.setLayout(null);
        mainPanel.add(attackTreePanel, BorderLayout.CENTER);

        // Create the text area and scroll pane
        textArea = new JTextArea();
        textArea.setFont(textArea.getFont().deriveFont(15f));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(280, 230));

        // Add the text area to the top-right corner
        JPanel topRightPanel = new JPanel(new BorderLayout());
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        btnGenerateTree = new JButton("Generate Tree");
        btnGenerateTree.setPreferredSize(new Dimension(280, 35));
        btnGenerateTree.setFont(btnGenerateTree.getFont().deriveFont(Font.BOLD, 15));
        btnGenerateTree.setBorder(BorderFactory.createLineBorder(new Color(132, 176, 227), 3, true));
        btnGenerateTree.addActionListener(e -> {
            AttackTreeController viewController = new AttackTreeController(this);
            viewController.generateAttackTree();
        });
        buttonPanel.add(btnGenerateTree); 

        textAreaPanel.add(buttonPanel, BorderLayout.SOUTH);
        topRightPanel.add(textAreaPanel, BorderLayout.EAST);
        mainPanel.add(topRightPanel, BorderLayout.NORTH);
        // Add the "Generate Tree" button to the bottom
        // JPanel bottomPanel = new JPanel();
        // bottomPanel.add(btnGenerateTree);
        // mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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