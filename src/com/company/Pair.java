package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

class Pair
{
    JLabel label1 ;
    JLabel label2 ;
    private Pair(){}
    public Pair(JLabel label1, JLabel label2)
    {
        this.label1 = label1;
        this.label2 = label2;
    }
    @Override
    public String toString()
    {
        return "{"+label1.getLocation()+","+label2.getLocation()+"}";
    }
    public int howToDraw()
    {
        Point point1 = label1.getLocation();
        Point point2 = label2.getLocation();
        if (point1.x > point2.x)
        {
            return 1;
        }
        else if (point1.x < point2.x)
        {
            return 2;
        }
        else if (point1.y > point2.y)
        {
            return 3;
        }
        else if (point1.y < point2.y)
        {
            return 4;
        }
        else
            return 5;
    }
    public JLabel getLabel1()
    {
        return label1;
    }
    public JLabel getLabel2()
    {
        return label2;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj instanceof Pair)
        {
            Pair temp = (Pair)obj;
            if ((temp.toString()).equalsIgnoreCase(this.toString()))
            {
                return true;
            }
        }
        return false;
    }
}

