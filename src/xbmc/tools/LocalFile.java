/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author jensbodal
 * @version 1.0
 * 
 * Compiler Java 1.7 OS: Windows 7, OSX Hardware: PC
 * 
 * Date Day, Year Initials Completed v#
 */

public class LocalFile {
    
    public static String getString(String txtfile) {
        String returnString = null;
        String userHome = System.getProperty("user.home");
        StringBuilder fileLocation = new StringBuilder();
        fileLocation.append(userHome);
        fileLocation.append(txtfile);
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileLocation.toString()))) {
            returnString = (reader.readLine());
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return returnString;
    }

}

