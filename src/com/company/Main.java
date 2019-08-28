package com.company;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MainModel m = new MainModel();
                	MainView v = new MainView();
                    MainController C = new MainController(m, v);
                    C.initController();
                    v.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
