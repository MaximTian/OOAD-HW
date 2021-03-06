package serverConnector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ljkis_000 on 2015/9/30.
 */
public class DataOperationPost extends DataOperation {
    private static int timeOut = 10000;
    private static String codeMode = "utf-8";
    private String json; // json package
    private String url;
    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    private void processUrl() {
        if (getQuery() != null)
            setUrl(getUrl()+getQuery());
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String Do() {
        String result = "";
        BufferedReader reader = null;
        try {
            // initial connection
            processUrl();
            URL url = new URL(this.getUrl());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("connection", "Keep-Alive");

            // message to back
            connection.setDoOutput(true);
            connection.setRequestProperty("content-type", "application/json");
//            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//            String str = URLEncoder.encode(this.getJson(), codeMode);
            out.write(this.getJson());
            out.flush();
            out.close();

            // connect
            connection.setConnectTimeout(timeOut);
            connection.connect();

            // message to front
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while((line = reader.readLine()) != null) {
                result += line;
            }
            // encoding to utf-8 make it difficult (to decode image encoded in base64)
            // result = URLDecoder.decode(result, codeMode);
            result = result.replace("\n", "");
        } catch (Exception e) {
            System.out.println("Send Post Fail!");
            result = "{\"status\": false, \"message\": \"Send Post Fail!\"}";
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
