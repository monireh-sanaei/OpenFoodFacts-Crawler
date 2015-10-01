/**
 * Created by Monireh on 30/09/2015.
 *
 * This class encapsulates the product used by this application.
 */

public class Product {

    private final String eAN;
    private final String name;
    private final String description;

    public Product(String eAN, String name, String description) {

        this.eAN = eAN;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getEAN() {
        return eAN;
    }

}
