package com.fortech;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LicenseFileUtilities {

    public void createFile(Path path, String text) {
        try {
            Files.createFile(path);
            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path.toString()), "utf-8"));
                writer.write(text);
            } catch (IOException ex) {
                // Report
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {/*ignore*/}
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String readFile(Path path) {
        String line = null;
        String fileName = String.valueOf(path.getFileName());
        String response = "";

        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                response+=line;
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return response;
    }

    public boolean isLicenseAvailable(String license) {
        boolean state = false;
        Cipher cipher = new Cipher();
        Gson gson = new Gson();
        ValidationKey validationKey = new ValidationKey();
        String decryptedLicense = cipher.decrypt(license);
        validationKey = gson.fromJson(decryptedLicense, ValidationKey.class);
        String finish_date_string = validationKey.getFinish_date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date finish_date = sdf.parse(finish_date_string);
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            if (today.before(finish_date))
                state = true;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return state;
    }
}

