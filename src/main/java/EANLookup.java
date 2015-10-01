import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;

/**
 * Created by Monireh on 29/09/2015.
 *
 * This interface provides the methods for a lookup application using http connection.
 */

public interface EANLookup {

    public HttpGet prepareGetRequest(String queryString);

    public HttpResponse executeGetRequest(HttpGet getRequest);

    public void parseResponse(HttpResponse httpResponse);
}
