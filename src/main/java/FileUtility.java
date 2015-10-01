import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Monireh on 29/09/2015.
 *
 * This class provides the file utility methods for this application.
 */

public class FileUtility {


    //To read a text file and extract the data in a linked list
    public static LinkedList<String> readTextFile(String fileName) {

        LinkedList<String> productList = new LinkedList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));

            int lineNum = 1;
            String line = null;
            while ((line = reader.readLine()) != null) {

                String productName = StringUtility.removeLast(line);
                productList.add(productName);
            }
        }
        catch (IOException x) {
            System.out.println("Exception in reading " + fileName);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return productList;
    }


    //To write a given content to a given file
    public static void writeTextFile(String content, String fileName) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
        }
        catch (IOException e) {
            System.out.println("Exception in writing " + fileName);
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    //To convert the products in a map to json objects and write them into a json file
    public static void mapToJson(Map<String, Product> productMap, String fileName) {

        JsonObject jsonObject = null;
        JsonArray jsonArray = new JsonArray();;

        for (Map.Entry<String,Product> entry : productMap.entrySet())
        {
            jsonObject = new JsonObject();

            Product product = entry.getValue();

            jsonObject.addProperty("Name",product.getName());
            jsonObject.addProperty("EAN",product.getEAN());
            jsonObject.addProperty("Description",product.getDescription());

            jsonArray.add(jsonObject);
        }
        String jsonString = jsonArray.toString() ;

        writeTextFile(jsonString, fileName);
    }
}
