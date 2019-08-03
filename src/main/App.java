package main;

import java.awt.EventQueue;

public class App {
	
	public static void main(String[] args) {
		
		Model m = new Model();
		View v = new View();
		Controller c = new Controller(m, v);
		c.initController();
				
	}

}
