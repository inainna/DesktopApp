package com.fortech;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.objects.NativeJSON;
import org.json.*;

import java.awt.*;
import java.awt.event.*;
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
    private JTextField licenseInput = new JTextField("", 5);
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
        validateBtn.addActionListener(new ValidateBtnEventListener());
        this.pack();

    }
    
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
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

    class ValidateBtnEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message="";

            Gson gson = new Gson();
            validationKey = gson.fromJson(cipher.decrypt(licenseInput.getText()), ValidationKey.class);
            System.out.println(validationKey);

            String key = cipher.decrypt(licenseInput.getText());

            System.out.println(key);

            if(isJSONValid(key)) {

                if(generatedKey.compare(validationKey))
                {

                    String start_date_string = validationKey.getStart_date();
                    String finish_date_string = validationKey.getFinish_date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    Date today = cal.getTime();

                    try {
                        Date start_date = sdf.parse(start_date_string);
                        Date finish_date = sdf.parse(finish_date_string);
                        if(today.after(start_date)&&today.before(finish_date)) {
                            message = "License accepted!";
                            String homeDir = System.getProperty("user.dir");
                            Path newFilePath = Paths.get(homeDir+"/license.txt");
                            LicenseFileUtilities licenseFileUtilities = new LicenseFileUtilities();
                            licenseFileUtilities.createFile(newFilePath, licenseInput.getText());
                        }
                        else message="License expired!";

                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                } else
                    message = "License not accepted!";
                }

            else message="License is incorrect";

                JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

}
