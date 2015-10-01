import java.util.Map;

/**
 * Created by Monireh on 30/09/2015.
 *
 *This interface provides methods to parse the response of get requests to a
 * given database. It should be implemented for every particular database.
 */

public interface DBParser {

    public Map<String, Product> parseResults(String inString, String productName);
}
