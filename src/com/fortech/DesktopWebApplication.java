package com.fortech;

import com.google.gson.*;
import org.json.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;


public class DesktopWebApplication extends JFrame {
    private JButton generateBtn = new JButton("Generate");
    private JTextArea json1Fld = new JTextArea("");
    private JLabel putLbl = new JLabel("Put your validation code here:");
    private JButton pasteValidationKey = new JButton("Paste");
    private JTextArea licenseInput = new JTextArea();
    private JButton validateBtn = new JButton("Validate");

    public GeneratedKey generatedKey = new GeneratedKey();
    public ValidationKey validationKey = new ValidationKey();
    public Cipher cipher = new Cipher();


    public DesktopWebApplication() {

        super("Application");
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        Color containerColor = Color.decode("#5B9092");
        container.setBackground(containerColor);

        //generateBtn
        generateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(generateBtn);
        generateBtn.addActionListener(new GenerateBtnEventListener());

        //json1Fld
        json1Fld.setEditable(true);
        json1Fld.setLineWrap(true);
        Color fieldColor = Color.decode("#d0e1e2");
        json1Fld.setBackground(fieldColor);
        container.add(json1Fld);

        //putLbl
        putLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(putLbl);

        //pasteValidationKey
        pasteValidationKey.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(pasteValidationKey);
        pasteValidationKey.addActionListener(new PasteValidationKeyEventListener());

        //licenseInput
        licenseInput.setBackground(fieldColor);
        licenseInput.setEditable(true);
        licenseInput.setLineWrap(true);
        container.add(licenseInput);

        //validateBtn
        validateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.pack();
        container.add(validateBtn);
        validateBtn.addActionListener(new ValidateBtnEventListener());
        this.pack();

    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    class GenerateBtnEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            licenseInput.setText("");

            generatedKey.initization();

            JsonParser parser = new JsonParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement el = parser.parse((new Gson().toJson(generatedKey)).toString());
            System.out.println(gson.toJson(el).toString());
            json1Fld.setText(cipher.encrypt(gson.toJson(el)));
        }
    }

    class PasteValidationKeyEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String dataFromClipboard = "";
            try {
                dataFromClipboard = (String) Toolkit.getDefaultToolkit()
                        .getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println(dataFromClipboard);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            if (dataFromClipboard != "") {
                licenseInput.setText(dataFromClipboard);
            } else JOptionPane.showMessageDialog(null, "Clipboard is empty", "Output", JOptionPane.PLAIN_MESSAGE);
        }

    }

    class ValidateBtnEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            boolean nullFields = true;

            if (!licenseInput.getText().equals("")) {
                Gson gson = new Gson();
                String encryptedValidationKey = licenseInput.getText();
                String decryptedJsonValidationKey = cipher.decrypt(encryptedValidationKey);

                try {
                    validationKey = gson.fromJson(decryptedJsonValidationKey, ValidationKey.class);
                    nullFields = validationKey.checkNullFields();
                } catch (Exception ex) {
                }
                String key = cipher.decrypt(licenseInput.getText());
                if (isJSONValid(key) && nullFields == false) {

                    if (generatedKey.compare(validationKey)) {

                        String start_date_string = validationKey.getStart_date();
                        String finish_date_string = validationKey.getFinish_date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Calendar cal = Calendar.getInstance();
                        Date today = cal.getTime();

                        try {
                            Date start_date = sdf.parse(start_date_string);
                            Date finish_date = sdf.parse(finish_date_string);
                            if (today.after(start_date) && today.before(finish_date)) {
                                message = "License accepted!";
                                String homeDir = System.getProperty("user.dir");

                                Path newFilePath = Paths.get(homeDir + "/license.txt");
                                try {
                                    Files.deleteIfExists(newFilePath);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                LicenseFileUtilities licenseFileUtilities = new LicenseFileUtilities();
                                licenseFileUtilities.createFile(newFilePath, licenseInput.getText());
                            } else message = "License expired!";
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else
                        message = "License not accepted!";
                } else message = "License is incorrect";
            } else message = "Empty field";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
