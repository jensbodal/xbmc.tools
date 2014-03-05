package xbmc.tools;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
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
//C:\cURL\curl.exe -i 
//-H "Content-Type: application/json" 
//-X POST -d "{\"jsonrpc\": \"2.0\", \"method\": \"VideoLibrary.Scan\"}" 
    //http://xbmc:xbmc@10.0.0.151:8080/jsonrpc
    private String username = "xbmc";
    private String password = "xbmc";
    private String XBMC_Host = "10.0.0.151";
    private String port = "8080";
    private String URL; 

    //HttpClient httpclient = HttpClientBuilder.create().build();    
    
    public XBMCUpdate() {
        setURL();
    }
    
    public XBMCUpdate(String username, String password, 
            String host, String port) {
        this.username = username;
        this.password = password;
        this.XBMC_Host = host;
        this.port = port;
        setURL();
    }
    
    public void sendUpdateRequest() {
        String updateString = 
                "{\"jsonrpc\": \"2.0\", \"method\": \"VideoLibrary.Scan\"}" ;
        StringEntity entity = new StringEntity(updateString, Consts.UTF_8);
        HttpPost httpPost = new HttpPost(getURL());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(httpPost);
            System.out.println(response.getStatusLine());
            client.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
        }
    }
    
    private void setURL() {
        this.URL = buildURL(username, password, XBMC_Host, port);

    }
    
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
