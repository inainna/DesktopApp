package com.fortech;

import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import javax.swing.*;


public class DesktopWebApplication extends JFrame {
    private JButton generateBtn = new JButton("Generate");
    private JTextArea json1Fld = new JTextArea("");
    private JLabel putLbl = new JLabel("Put your validation code here:");
    private JTextField licenseInput = new JTextField("", 5);
    private JButton validateBtn = new JButton("Validate");

    public LicenseOne licenseOne= new LicenseOne();
    public LicenseTwo licenseTwo = new LicenseTwo();


    public DesktopWebApplication() {

        super("Application");
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        //generateBtn
        generateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(generateBtn);
        generateBtn.addActionListener(new GenerateBtnEventListener());

        //json1Fld
        json1Fld.setEditable(true);
        json1Fld.setLineWrap(true);
        container.add(json1Fld);

        //putLbl
        putLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(putLbl);

        //licenseInput
        container.add(licenseInput);

        //validateBtn
        validateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(validateBtn);
        validateBtn.addActionListener(new validateBtnEventListener());
        this.pack();

    }


    class GenerateBtnEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            licenseOne.initization();
            json1Fld.setText(new Gson().toJson(licenseOne));
        }
    }

    class validateBtnEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message="";
            licenseTwo.initialize(licenseInput.getText());
            if(licenseOne.compare(licenseTwo))
                message = "License accepted";
                JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

}