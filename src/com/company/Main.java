package com.company;

import java.awt.*;

import parser.APIcaller;

/**
 * Run GUI for HARMs
 */
public class Main {
	
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MainModel m = new MainModel();
                	MainView v = new MainView(m);
                    MainController C = new MainController(m, v);
                    v.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        APIcaller call = new APIcaller();
        
        call.get();
        
    }
}
