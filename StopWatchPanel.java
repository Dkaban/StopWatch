//***********************************************************
//  StopWatchPanel.java
//
//  Dustin Kaban
//  August 7th, 2020
//
//  This is the main class for the GUI that displays to the user a
//  functional stop watch.  The user can press start, stop, and reset buttons.
//  As well as using control commands A to start, D to stop, and R to reset.
//  This class handles all of the GUI functionality and the Timer functionality.
//***********************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class StopWatchPanel extends Panel
{
    //To keep track of starting, stopping, and pausing
    private long startTime, endTime, currentTime;

    //Variables, using a long for 64bit precision on timeElapsed
    private long timeElapsed = 0;
    private final int MILLISECONDS_TO_SECONDS = 1000;

    //Timer and formatting variables, and GUI elements
    Timer timer;
    private DecimalFormat df = new DecimalFormat("0.#");
    private JLabel timeLabel;
    private JButton startButton,stopButton,resetButton;

    public StopWatchPanel()
    {
        timer = new Timer(15,new InteractTimerButtonListener());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new BorderLayout());

        //Initialize Label
        timeLabel = new JLabel(df.format(timeElapsed) + " second(s)",SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial",Font.PLAIN,40));

        //Initialize Button
        startButton = new JButton("Start");
        startButton.addActionListener(new InteractTimerButtonListener());
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new InteractTimerButtonListener());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new InteractTimerButtonListener());

        //Add all of the buttons and labels to the correct panels in the correct locations
        buttonPanel.add(startButton,BorderLayout.WEST);
        buttonPanel.add(stopButton,BorderLayout.CENTER);
        buttonPanel.add(resetButton,BorderLayout.EAST);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Stop Watch Controls"));
        mainPanel.add(timeLabel,BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);

        //Create a border for the main panel with instructions and set its proper size
        mainPanel.setBorder(BorderFactory.createTitledBorder("Controls: Press A to Start, D to Stop, R to Reset."));
        mainPanel.setPreferredSize(new Dimension(400,150));

        //The buttons need key listeners or else they steal focus and keys don't work
        startButton.addKeyListener(new KeyCommandsListener());
        stopButton.addKeyListener(new KeyCommandsListener());
        resetButton.addKeyListener(new KeyCommandsListener());
        startButton.setFocusable(true);
        stopButton.setFocusable(true);
        resetButton.setFocusable(true);
        add(mainPanel);

        setBackground(Color.lightGray);
    }

    //This is used to listen for key presses and run the corresponding stopwatch commands
    public class KeyCommandsListener implements KeyListener
    {
        public void keyPressed(KeyEvent event)
        {
            switch(event.getKeyCode())
            {
                case KeyEvent.VK_A: //A Key
                    System.out.println("In here!");
                    startTimer();
                    break;

                case KeyEvent.VK_D: //D Key
                    stopTimer();
                    break;
                case KeyEvent.VK_R: //R Key
                    resetTimer();
                    break;

                default:
                    break;
            }
        }

        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }

    //Handles all the button presses and timer running method calls
    public class InteractTimerButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (timer.isRunning())
            {
                updateTimer();
            }

            if(event.getSource() == startButton)
            {
                startTimer();
            }
            else if(event.getSource() == stopButton)
            {
                stopTimer();
            }
            else if(event.getSource() == resetButton)
            {
                resetTimer();
            }
        }
    }

    //Updates the timer variables to keep track of current and elapsed time and displays
    private void updateTimer()
    {
        currentTime = System.currentTimeMillis();
        timeElapsed = currentTime - startTime;
        timeLabel.setText(df.format(((double)timeElapsed/MILLISECONDS_TO_SECONDS)) + " second(s)");
        repaint();
    }

    //In charge of starting the timer either from 0 or when paused
    private void startTimer()
    {
        if(!timer.isRunning())
        {
            timer.start();
            if (startTime <= 0)
            {
                startTime = System.currentTimeMillis();
            }
            else
            {
                startTime += (System.currentTimeMillis() - endTime);
            }
        }
    }

    //stops the timer completely and tracks the time
    private void stopTimer()
    {
        timer.stop();
        endTime = System.currentTimeMillis();
    }

    //Resets everything so the timer is fresh to start again
    private void resetTimer()
    {
        timer.stop();
        timeElapsed = 0;
        startTime = 0;
        endTime = 0;
        currentTime = 0;
        timeLabel.setText(0 + " second(s)");
        repaint();
    }
}
