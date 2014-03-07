/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import java.io.BufferedReader;
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
    
    /**
     * This is a temporary method which will parse the first line in a text file assuming
     * the text file only has one line.  This will be used until I figure out how to properly
     * secure password storage.
     * @param txtfile text file containing single line to return
     * @return first line of text file
     */
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

