import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Map;

/**
 * Created by Monireh on 29/09/2015.
 *
 * This class is implemented to create and send http connections to a given URL.
 */

public class HttpConnection implements EANLookup, Runnable{


    private final static String DATABASE_URL = "http://world.openfoodfacts.org/cgi/search.pl";
    private final String productName;
    private final Map<String, Product> productMap;
    private final DBParser parser = new DBParserOpenFoodFacts();


    public HttpConnection(String productName, Map<String, Product> productMap) {

        this.productName = productName;
        this.productMap = productMap;
    }

    @Override
    public void run() {

        //To build the request string to send
        RequestBuilder requestBuilder = new RequestBuilder(DATABASE_URL, productName);
        String query = requestBuilder.getRequest();

        HttpGet httpGet = prepareGetRequest(query);
        HttpResponse httpResponse = executeGetRequest(httpGet);
        parseResponse(httpResponse);

    }

    //To create an http connection
    @Override
    public HttpGet prepareGetRequest(String queryString) {

        HttpGet getRequest = new HttpGet(queryString);

        return getRequest;
    }

    //To execute the http connection and get the response
    @Override
    public HttpResponse executeGetRequest(HttpGet getRequest){

        HttpClient client = HttpClientBuilder.create().build();

        HttpResponse httpResponse = null;
        try{
            httpResponse = client.execute(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    //To parse the http response in order to extract the required data
    @Override
    public void parseResponse(HttpResponse httpResponse) {

        BufferedReader rd = null;
        if (httpResponse != null) try {
            rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            String line = "";
            StringBuffer responseBuffer = new StringBuffer();

            while ((line = rd.readLine()) != null) {
                responseBuffer.append(line);
            }

            Map<String, Product> results = parser.parseResults(responseBuffer.toString(), productName);
            productMap.putAll(results);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
