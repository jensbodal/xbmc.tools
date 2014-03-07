/*
 * DownloadLog.java
 */ 

package xbmc.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Download log is a class which generates a String that can be output. The 
 * String is meant for uTorrent which will append the date, time, label, and
 * file name of a downloaded torrent to a string
 * @author Jens Bodal
 */
public class DownloadLog {
    private String label;
    private String title;
    
    /**
     * Only constructor for this class.  Sets the label and title
     * 
     * @param label This is the label of the file.  This should be passed as a
     * parameter from uTorrent
     * @param title this is the title of the file.  This should be passed as a
     * parameter from uTorrent
     */
    public DownloadLog(String label, String title) {
        setLabel(label);
        setTitle(title);
    }
    
    private void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * 
     * @return the label for the torrent file
     */
    public String getLabel() {
        return this.label;
    }
    
    private void setTitle(String title) {
        this.title = title;
    }
    
    /**
     *
     * @return the title for the torrent file
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     *
     * @return the date and time in the specified format e.g.
     * Thursday 03/06/2014 21:33:00
     */
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     *
     * @return the string that should be written to a log file e.g.
     * Thursday 03/06/2014 21:33:00 HDMovies\moviename.mkv
     */
    @Override
    public String toString() {
        return String.format("%s %s\\%s", getDate(), getLabel(), getTitle())
                .replace("\"", "");
    }
}
