package com.fortech;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        DesktopWebApplication app = new DesktopWebApplication();
        app.setVisible(true);

        String license = "";
        String homeDir = System.getProperty("user.dir");
        Path newFilePath = Paths.get(homeDir+"/license.txt");
        LicenseFileUtilities licenseFileUtilities = new LicenseFileUtilities();
        license = licenseFileUtilities.readFile(newFilePath);
        if(license!="") {
            if (licenseFileUtilities.isLicenseAvailable(license)) {
                JOptionPane.showMessageDialog(null, "License is available", "Output", JOptionPane.PLAIN_MESSAGE);
            }

            else {
                JOptionPane.showMessageDialog(null, "License is expired, please generate a new license!", "Output", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else JOptionPane.showMessageDialog(null, "Please generate a new license!", "Output", JOptionPane.PLAIN_MESSAGE);
    }
}
