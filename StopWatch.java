//***********************************************************
//  StopWatch.java
//
//  Dustin Kaban T00663749
//  COMP 1231 Module 5 Assignment 5 Question 2
//  August 5th, 2020
//
//  This class is the main driver for the Stop Watch GUI.
//  It uses the StopWatchPanel class to implement panels into a frame.
//***********************************************************

import javax.swing.*;

public class StopWatch
{
    public static void main(String[] args)
    {
        //Setting up the frame
        JFrame frame = new JFrame("Stop Watch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StopWatchPanel());
        frame.pack();
        frame.setVisible(true);
    }
}