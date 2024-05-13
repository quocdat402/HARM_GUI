package com.company;

import java.util.ArrayList;
import java.util.List;

public class AttackTreeNode {
    private String text;
    private List<AttackTreeNode> children;

    public AttackTreeNode(String text) {
        this.text = text;
        this.children = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void addChild(AttackTreeNode child) {
        children.add(child);
    }

    public List<AttackTreeNode> getChildren() {
        return children;
    }
}
