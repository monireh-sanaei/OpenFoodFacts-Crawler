/**
 * Created by Monireh on 29/09/2015.
 *
 * This class provides String utility methods for this application.
 */

public class StringUtility {


    public static String[] toArray(String inString) {

        return inString.split(" ");
    }


    public static String removeLast(String inString) {

        String[] strArr = StringUtility.toArray(inString);

        StringBuffer modifiedStr = new StringBuffer();

        for (int i = 0; i < strArr.length - 1; i++) {
            modifiedStr.append(strArr[i]);
            if (i != strArr.length - 2)
                modifiedStr.append(" ");
        }
        return modifiedStr.toString();
    }

}
