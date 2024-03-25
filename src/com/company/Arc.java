package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Arc implements Serializable {

	/**
	 * add default serial number
	 */
	private static final long serialVersionUID = 10L;
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private Color color;
	private final int ARR_SIZE = 4;
	private int initNode;
	private int endNode;
	
	private int number;
	
	private int vulnerability;
	private double risk;
	private double cost;
	private double probability;
	private double impact;

	/**
	 * Initialize all the information of Arc
	 */
	public Arc(int x1, int y1, int x2, int y2, Color color, int initNode, int endNode, int number,
			int vulnerability, 
			double risk, double cost, double probability, double impact) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		this.initNode = initNode;
		this.endNode = endNode;
		this.number = number;
		this.vulnerability = vulnerability;
		this.risk = risk;
		this.cost = cost;
		this.probability = probability;
		this.impact = impact;
	}
	
	/**
	 * Draw the line on the centerpane.
	 */
	public void drawLine(Graphics g) {
		/*Draw the line between two points (x1, y1), (x2, y2)*/
		g.setColor(color);
		drawArrow(g, x1, y1, x2, y2);

		/*Show the value of vulnerability above the arc*/
		g.setColor(color.black);
		g.drawString("Arc" + String.valueOf(number), (x1 + x2) / 2, (y1 + y2) / 2 + 25);
	}

	/**
	 * Draw the arrow at the end of the line.
	 */
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		/*Draw horizontal arrow starting in (0, 0)*/
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
				4);
	}

	/*Getters and Setters*/
	public int getInitNode() {
		return initNode;
	}

	public void setInitNode(int initNode) {
		this.initNode = initNode;
	}

	public int getEndNode() {
		return endNode;
	}

	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
		
		if(probability > 1) {
			
			throw new IllegalArgumentException("Invalid Input Number");
			
		} else {
			
			this.probability = probability;
		}
	}

	public double getImpact() {
		return impact;
	}

	public void setImpact(double impact) {
		this.impact = impact;
	}
	

}