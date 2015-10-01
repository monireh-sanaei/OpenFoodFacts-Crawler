import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monireh on 29/09/2015.
 *
 * This class is used to build the query string to send by http connection.
 */

public class RequestBuilder {

    //The class constants to be used in the query string
    public static final String SEARCH_TERMS = "search_terms";
    public static final String PAGE_SIZE = "page_size";
    public static final String SEARCH_SIMPLE = "search_simple";
    public static final String JQM = "jqm";
    public static final String UTF_8 = "utf-8";

    private String productName;
    private String dataBaseURL;
    private List<NameValuePair> params;


    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getDataBaseURL() {
        return dataBaseURL;
    }


    public void setDataBaseURL(String dataBaseURL) {
        this.dataBaseURL = dataBaseURL;
    }

    //To add the parameters to the query string
    public void addParams() {

        params.add(new BasicNameValuePair(SEARCH_TERMS, productName));
        params.add(new BasicNameValuePair(PAGE_SIZE, 1000 + ""));
        params.add(new BasicNameValuePair(SEARCH_SIMPLE, 1 + ""));
        params.add(new BasicNameValuePair(JQM, 1 + ""));
    }


    public String getRequest() {

        StringBuffer requestUrl = new StringBuffer(dataBaseURL);

        addParams();
        String queryString = URLEncodedUtils.format(params, UTF_8);
        requestUrl.append("?");
        requestUrl.append(queryString);

        return requestUrl.toString();
    }


    public RequestBuilder(String dataBaseURL, String productName) {

        this.dataBaseURL = dataBaseURL;
        this.productName = productName;

        params = new ArrayList<NameValuePair>();
    }
}
