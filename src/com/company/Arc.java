package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Arc {
	
	
	private int x1;
	private	int x2;
	private int y1;
	private int y2;
	private Color color;
	private final int ARR_SIZE = 4;
	private int initNode;
	private int endNode;
	private double vulnerability;
	private int number;
	
	public Arc(int x1, int y1, int x2, int y2, Color color, int initNode, int endNode, double vulnerability, int number) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		this.initNode = initNode;
		this.endNode = endNode;
		this.vulnerability = vulnerability;
		this.number = number;
	}
	
	public void drawLine(Graphics g) {
		g.setColor(color);
		drawArrow(g, x1, y1, x2, y2);	
		
		g.setColor(color.black);
		g.drawString(String.valueOf(vulnerability), (x1+x2)/2, (y1+y2)/2 + 25);
	}
	
	
	 void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
         Graphics2D g = (Graphics2D) g1.create();

         double dx = x2 - x1, dy = y2 - y1;
         double angle = Math.atan2(dy, dx);
         int len = (int) Math.sqrt(dx*dx + dy*dy);
         AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
         at.concatenate(AffineTransform.getRotateInstance(angle));
         g.transform(at);

         // Draw horizontal arrow starting in (0, 0)
         g.drawLine(0, 0, len, 0);
         g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                       new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
     }

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

	public double getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(double vulnerability) {
		this.vulnerability = vulnerability;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
