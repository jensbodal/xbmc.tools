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

* C:\cURL\curl.exe -i -H "Content-Type: application/json" -X POST -d 
* "{\"jsonrpc\": \"2.0\", \"method\": \"VideoLibrary.Scan\"}" http://xbmc:xbmc@10.0.0.151:8080/jsonrpc
     */
    public static void main(String[] args) {
        XBMCUpdate update = new XBMCUpdate();
        System.out.println(update.getURL());
        update.sendUpdateRequest();
    }

}
