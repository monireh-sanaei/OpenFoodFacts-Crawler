import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monireh on 30/09/2015.
 *
 * This class is used to parse the response to get requests from
 * the OpenFoodFacts database.
 */

public class DBParserOpenFoodFacts implements DBParser {

    //The class constants used for parsing of the responses
    public static final String NO_PRODUCTS = "No products";
    public static final String CODE = "code";
    public static final String TITLE = "title";
    public static final String PARAGRAPH = "<p>";

    @Override
    public Map<String, Product> parseResults(String inString, String productName) {

        Map<String, Product> results = new HashMap<String, Product>();

        if(!inString.contains(NO_PRODUCTS)) {

            //To parse number of products looked up for a given product name
            int nbProductsStart = inString.indexOf(PARAGRAPH) + PARAGRAPH.length();
            int nbProductsEnd = inString.indexOf(" ");

            String nbProductsString = inString.substring(nbProductsStart, nbProductsEnd);

            int nbProducts = 0;
            try {
                nbProducts = Integer.parseInt(nbProductsString);
            } catch(NumberFormatException e){
                System.out.println(e.getStackTrace());
            }
//            System.out.println("Number of products : " + nbProducts);

            int codeSearchIndex = 0;
            int codeStartIndex = 0;
            int codeEndIndex = 0;

            int titleSearchIndex = 0;
            int titleStartIndex = 0;
            int titleEndIndex = 0;

            int indexOfCode = -1;
            int indexOfTitle = -1;

            //To search for the occurrence of "code" and "title"
            while (indexOfCode != inString.lastIndexOf(CODE) && indexOfTitle != inString.lastIndexOf(TITLE)) {

                //To obtain the bar codes
                indexOfCode = inString.indexOf(CODE, codeSearchIndex);

                codeStartIndex = indexOfCode + CODE.length() + 1;
                codeEndIndex = inString.indexOf("\\", codeStartIndex);

                String barcode = inString.substring(codeStartIndex, codeEndIndex);

                //To update the "code" search index
                codeSearchIndex = codeEndIndex + 1;

                //To obtain the titles ( products' descriptions)
                indexOfTitle = inString.indexOf(TITLE, titleSearchIndex);

                titleStartIndex = indexOfTitle + TITLE.length() + 3;
                titleEndIndex = inString.indexOf("\\", titleStartIndex);

                String title = inString.substring(titleStartIndex, titleEndIndex);
                title = title.replace("&nbsp;", " ");


                //To put the product barcode and title in a synchronized map
                results.put(barcode, new Product(barcode, productName, title));

                //To update the "title" search index
                titleSearchIndex = titleEndIndex + 1;
            }
        }
        return results;
    }
}
