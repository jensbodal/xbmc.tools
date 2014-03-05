/*
 * XBMCUpdateTest.java
 */ 

package xbmc.tools.tests;

import xbmc.tools.XBMCUpdate;


/**
 *
 * @author Jens Bodal
 * @version
 *
 * Compiler Java 1.7
 * OS: Windows 7, OSX
 * Hardware: PC
 *
 * Date, JB completed vX.x
 */
public class XBMCUpdateTest {

    /**
     *
     * @param args no arguments used
     */
    public static void main(String[] args) {
        XBMCUpdate update = new XBMCUpdate();
        System.out.println(update.getURL());
        update.sendUpdateRequest();
    }

}
