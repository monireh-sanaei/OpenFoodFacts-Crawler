import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Monireh on 29/09/2015.
 *
 * This class initializes a thread pool with a fixed size in order to
 * execute http connections to communicate with a given database.
 */

public class LookupClient {


    public static final int POOL_SIZE = 100;
    private final ExecutorService pool;


    //The primary product list including products' names
    private LinkedList<String> productList;

    //The final product map including <barcode, product> pairs
    private Map<String, Product> productMap;

    private String groceryData = "resources//grocery.txt";

    //The json file holding data looked up in the database
    private String lookupData = "resources//lookupData.json";


    public LookupClient(int poolSize) {

        productList = FileUtility.readTextFile(groceryData);
        productMap = Collections.synchronizedMap(new HashMap<String, Product>());

        pool = Executors.newFixedThreadPool(poolSize);
    }


    public static void main(String[] args) {

        LookupClient concurrentLookup = new LookupClient(POOL_SIZE);

        while(concurrentLookup.productList.size() > 0){

            String productName = concurrentLookup.productList.removeFirst();

            //To create an http connection
            HttpConnection httpConnection = new HttpConnection(productName, concurrentLookup.productMap);

            //To submit the pool to execute the http connection
            concurrentLookup.pool.submit(httpConnection);
        }
        concurrentLookup.pool.shutdown();

        while (!concurrentLookup.pool.isTerminated()) {
        }

        System.out.print("Number of looked up products : " + concurrentLookup.productMap.size());

        //To convert the product map to json format and write it to a json file
        FileUtility.mapToJson(concurrentLookup.productMap, concurrentLookup.lookupData);
    }
}
