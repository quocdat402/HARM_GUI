package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Node implements Serializable{

	/**
	 * add default serial number
	 */
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int diameter;
	private Color color;
	private String name;
	private int number;
	private boolean attacker;
	private boolean target;
	
	private int vulnerability;
	private double risk;
	private double cost;
	private double probability;
	private double impact;
	
	/**
	 * Initialize all the information of Node
	 */
	public Node(int x, int y, int diameter, Color color, String name, int number, boolean attacker,
			boolean target, int vulnerability, 
			double risk, double cost, double probability, double impact) {
		super();
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
		this.name = name;
		this.number = number;
		this.attacker = attacker;
		this.target = target;
		this.vulnerability = vulnerability;
		this.risk = risk;
		this.cost = cost;
		this.probability = probability;
		this.impact = impact;
	}
	
	/**
	 * Draw a node on the centrepane
	 */
	public void draw(Graphics g) {
		
		/*
		 * Draw a node
		 */
		g.setColor(color.black);
		g.fillOval(x - 2, y - 2, diameter+4, diameter+4);
		g.setColor(color);
		g.fillOval(x, y, diameter, diameter);
		
		/*
		 * Show the name of the node below the node
		 */
		g.setColor(color.black);
		g.drawString(name, x - 4, y + 40);
		
	}

	/*Getters and Setters*/
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isAttacker() {
		return attacker;
	}

	public void setAttacker(boolean attacker) {
		this.attacker = attacker;
	}

	public boolean isTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}

	public int getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(int vulnerability) {
		this.vulnerability = vulnerability;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getImpact() {
		return impact;
	}

	public void setImpact(double impact) {
		this.impact = impact;
	}

	
	
}
