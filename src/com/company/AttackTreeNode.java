package com.company;

import java.util.ArrayList;
import java.util.List;

public class AttackTreeNode {
    private String text;
    private List<AttackTreeNode> children;
    private AttackTreeNode parent;

    public AttackTreeNode(String text) {
        this.text = text;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public String getText() {
        return text;
    }

    public void addChild(AttackTreeNode child) {
        children.add(child);
        child.setParent(this);
    }

    public List<AttackTreeNode> getChildren() {
        return children;
    }

    public AttackTreeNode getParent() {
        return parent;
    }

    public void setParent(AttackTreeNode parent) {
        this.parent = parent;
    }
}
