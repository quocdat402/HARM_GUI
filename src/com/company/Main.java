package com.company;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MainModel m = new MainModel();
                	SimulatorGUI frame = new SimulatorGUI();
                    MainController C = new MainController(m, frame);
                    C.initController();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
