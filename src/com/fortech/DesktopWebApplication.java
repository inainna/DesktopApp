package com.fortech;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import javax.swing.*;


public class DesktopWebApplication extends JFrame {
    private JButton button = new JButton("Generate");
    private JTextArea field = new JTextArea("");
    private JLabel label2 = new JLabel("Put your validation code here:");
    private JTextField input = new JTextField("", 5);
    private JButton button2 = new JButton("Validate");



    public DesktopWebApplication() {
        super("Simple example");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();


        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2, 2));
        container.add(button);
        button.addActionListener(new ButtonEventListener());
        field.setEditable(true);
        field.setLineWrap(true);
        container.add(field);
        container.add(label2);
        container.add(input);
        container.add(button2);
        button2.addActionListener(new Button2EventListener());

    }


    class ButtonEventListener implements ActionListener{
        public void actionPerformed (ActionEvent e){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            field.setText("Host Name:"+SystemInfo.getSystemName()+" Host IP:"+SystemInfo.getIPAddress()+" Host Address:"+SystemInfo.getMAC()+" "+timestamp.getTime());
        }
    }

    class Button2EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "License accepted";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

}