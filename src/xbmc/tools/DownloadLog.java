/*
 * DownloadLog.java
 */ 

package xbmc.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jens Bodal
 */
public class DownloadLog {
    private String label;
    private String title;
    
    public DownloadLog(String label, String title) {
        setLabel(label);
        setTitle(title);
                
    }
    
    private void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    private void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    @Override
    public String toString() {
        return String.format("%s %s\\%s", getDate(), getLabel(), getTitle())
                .replace("\"", "");
    }
}
