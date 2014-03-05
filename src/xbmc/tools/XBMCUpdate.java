package xbmc.tools;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


/*
 * XBMCUpdate.java
 */ 

/**
 *
 * @author Jens Bodal
 */
public class XBMCUpdate {
    private String username = "xbmc";
    private String password = "xbmc";
    private String XBMC_Host = "10.0.0.151";
    private String port = "8080";
    private String URL; 

    //HttpClient httpclient = HttpClientBuilder.create().build();    
    
    /**
     * Passes default values to constructor
     */
        
    public XBMCUpdate() {
        setURL();
    }
    
    /**
     *
     * @param username username in XBMC
     * @param password password in XBMC
     * @param host host IP address of machine running XBMC
     * @param port port set in XBMC settings
     */
    public XBMCUpdate(String username, String password, 
            String host, String port) {
        this.username = username;
        this.password = password;
        this.XBMC_Host = host;
        this.port = port;
        setURL();
    }
    
    /**
     * sends JSON update request to XBMC machine to update the video 
     * library 
     */
    public void sendUpdateRequest() {
        String updateString = 
                "{\"jsonrpc\": \"2.0\", \"method\": \"VideoLibrary.Scan\"}" ;
        StringEntity entity = new StringEntity(updateString, Consts.UTF_8);
        HttpPost httpPost = new HttpPost(getURL());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        
        try (CloseableHttpClient client = HttpClientBuilder.create().build()){
            HttpResponse response = client.execute(httpPost);
            System.out.println(response.getStatusLine()); // move to log
            
        }
        catch (IOException e) {
            e.printStackTrace(); // move to log
        }
    }
    
    
    private void setURL() {
        this.URL = buildURL(username, password, XBMC_Host, port);

    }
    
    /**
     * Returns the URL which will be posted to JSON
     * @return URL which will be posted to JSON request
     */
    public String getURL() {
        return this.URL;
    }
    
    private String buildURL(String username, String password, 
            String host, String port) {
        StringBuilder returnURL = new StringBuilder();
        returnURL.append("http://");
        returnURL.append(username);
        returnURL.append(":");
        returnURL.append(password);
        returnURL.append("@");
        returnURL.append(host);
        returnURL.append(":");
        returnURL.append(port);
        returnURL.append("/jsonrpc");
        return returnURL.toString();
    }
    
    
   

}
