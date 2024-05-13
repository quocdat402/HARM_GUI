package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Stack;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttackTreeController {
    private AttackTreeView view;

    public AttackTreeController(AttackTreeView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnGenerateTree().addActionListener(e -> generateAttackTree());
    }

    private void generateAttackTreeVisual(AttackTreeNode node, int depth, int siblingIndex) {
        // Create a graphical component for the current node
        JPanel nodePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw lines to child nodes
                if (!node.getChildren().isEmpty()) {
                    Point parentCenter = new Point(getWidth() / 2, getHeight());
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(Color.BLACK);

                    for (int i = 0; i < node.getChildren().size(); i++) {
                        AttackTreeNode child = node.getChildren().get(i);
                        JPanel childPanel = (JPanel) view.getAttackTreePanel().getComponent(depth * node.getChildren().size() + i);
                        Point childCenter = new Point(childPanel.getX() + childPanel.getWidth() / 2, childPanel.getY());
                        g2d.drawLine(parentCenter.x, parentCenter.y, childCenter.x, childCenter.y - 25);
                    }
                }
            }
        };

        nodePanel.setLayout(new BorderLayout());
        nodePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nodeLabel = new JLabel(node.getText());
        nodePanel.add(nodeLabel, BorderLayout.CENTER);

        // Calculate the position of the node based on depth and siblingIndex
        int x = siblingIndex * 200;
        int y = depth * 100;
        nodePanel.setBounds(x, y, 150, 50);

        // Add the node component to the attack tree panel
        view.getAttackTreePanel().add(nodePanel);

        // Recursively generate the child nodes
        for (int i = 0; i < node.getChildren().size(); i++) {
            generateAttackTreeVisual(node.getChildren().get(i), depth + 1, i);
        }
    }

    void generateAttackTree() {
        String treeText = view.getTextArea().getText();
        AttackTreeNode rootNode = parseAttackTree(treeText);

        view.getAttackTreePanel().removeAll();

        // Generate the visual representation of the attack tree
        generateAttackTreeVisual(rootNode, 0, 0);

        // Refresh the attack tree panel
        view.getAttackTreePanel().revalidate();
        view.getAttackTreePanel().repaint();
    }

    private AttackTreeNode parseAttackTree(String treeText) {
        String[] lines = treeText.split("\\r?\\n");
        AttackTreeNode rootNode = null;
        Stack<AttackTreeNode> nodeStack = new Stack<>();

        for (String line : lines) {
            int level = countLeadingEquals(line);
            String nodeText = line.substring(level).trim();

            AttackTreeNode node = new AttackTreeNode(nodeText);

            if (level == 0) {
                rootNode = node;
            } else {
                while (nodeStack.size() > level) {
                    nodeStack.pop();
                }
                nodeStack.peek().addChild(node);
            }

            nodeStack.push(node);
        }

        return rootNode;
    }

    private int countLeadingEquals(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == '=') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
