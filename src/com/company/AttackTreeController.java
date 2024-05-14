package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Stack;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class AttackTreeController {
    private AttackTreeView view;

    public AttackTreeController(AttackTreeView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnGenerateTree().addActionListener(e -> generateAttackTree());
    }
    
    private void generateAttackTreeVisual(AttackTreeNode node, int depth, int siblingIndex, int parentX, int parentY) {
        // Create a graphical component for the current node
        JPanel nodePanel = new JPanel();
        nodePanel.setLayout(new GridBagLayout());
        nodePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
        JLabel nodeLabel = new JLabel(node.getText());
        Font font = nodeLabel.getFont();
        Font largerFont = font.deriveFont(font.getSize() + 2f); // Increase font size by 2 points
        nodeLabel.setFont(largerFont);
        nodePanel.add(nodeLabel);
    
        // Calculate the position of the node based on depth and siblingIndex
        int x = parentX;
        int y = parentY + 80;
        if (depth > 0) {
            AttackTreeNode parent = node.getParent();
            int spacing = calculateSpacing(node);
            int parentWidth = spacing * parent.getChildren().size();
            int currentWidth = spacing;
            int currentIndex = siblingIndex;
            x = parentX - parentWidth / 2 + currentIndex * currentWidth + currentWidth / 2;
        }
        final int finalX = x;
        int nodeWidth = 180;
        int nodeHeight = 60;
        nodePanel.setBounds(x - nodeWidth / 2, y, nodeWidth, nodeHeight);
    
        // Add the node component to the attack tree panel
        view.getAttackTreePanel().add(nodePanel);
    
        // Create a custom panel to draw the lines
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));
                if (depth > 0) {
                    g2d.drawLine(parentX, parentY + nodeHeight / 2, finalX, y);
                }
            }
        };
        linePanel.setOpaque(false);
        linePanel.setBounds(0, 0, view.getAttackTreePanel().getWidth(), view.getAttackTreePanel().getHeight());
        view.getAttackTreePanel().add(linePanel);
    
        // Recursively generate the child nodes
        int childSiblingIndex = 0;
        for (AttackTreeNode child : node.getChildren()) {
            generateAttackTreeVisual(child, depth + 1, childSiblingIndex, x, y);
            childSiblingIndex++;
        }
    }

    private int calculateSpacing(AttackTreeNode node) {
        int descendants = countDescendants(node);
        return 200 + 100 * descendants;
    }
    
    private int countDescendants(AttackTreeNode node) {
        int count = node.getChildren().size();
        for (AttackTreeNode child : node.getChildren()) {
            count += countDescendants(child);
        }
        return count;
    }

    void generateAttackTree() {
        String treeText = view.getTextArea().getText();
        AttackTreeNode rootNode = parseAttackTree(treeText);
    
        // Clear the previous attack tree visual
        view.getAttackTreePanel().removeAll();
    
        // Set the preferred size of the attack tree panel based on the tree size
        int treeWidth = calculateTreeWidth(rootNode);
        int treeHeight = calculateTreeHeight(rootNode);
        view.getAttackTreePanel().setPreferredSize(new Dimension(treeWidth, treeHeight));
    
        // Generate the visual representation of the attack tree
        int centerX = treeWidth / 2;
        generateAttackTreeVisual(rootNode, 0, 0, centerX, 50);
    
        // Refresh the attack tree panel
        view.getAttackTreePanel().revalidate();
        view.getAttackTreePanel().repaint();
    }

    private int calculateTreeWidth(AttackTreeNode node) {
        if (node.getChildren().isEmpty()) {
            return 200;
        } else {
            int totalChildWidth = 0;
            for (AttackTreeNode child : node.getChildren()) {
                int childWidth = calculateTreeWidth(child);
                totalChildWidth += childWidth;
            }
            return Math.max(totalChildWidth, 200);
        }
    }
    
    private int calculateTreeHeight(AttackTreeNode node) {
        if (node.getChildren().isEmpty()) {
            return 100;
        } else {
            int maxChildHeight = 0;
            for (AttackTreeNode child : node.getChildren()) {
                int childHeight = calculateTreeHeight(child);
                maxChildHeight = Math.max(maxChildHeight, childHeight);
            }
            return 100 + maxChildHeight;
        }
    }

    private AttackTreeNode parseAttackTree(String treeText) {
        String[] lines = treeText.split("\\r?\\n");
        AttackTreeNode rootNode = null;
        Stack<AttackTreeNode> nodeStack = new Stack<>();
    
        for (String line : lines) {
            int level = countLeadingEquals(line);
            String nodeText = line.substring(level).trim();
    
            AttackTreeNode node = new AttackTreeNode(nodeText);
    
            if (level == 1) {
                rootNode = node;
            } else {
                while (nodeStack.size() > level - 1) {
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
