import java.util.Locale;

/**
 * Base type for every item in the shop simulation.
 *
 * <p>Holds the data shared by all products (name, base price, and ID) and
 * declares {@link #getFinalPrice()} as abstract, so each product type decides
 * how its selling price is calculated.
 *
 * @author Yusuf Almahoozi
 */
public abstract class Product {

    private String name;
    private double price;
    private int productId;

    /**
     * @throws IllegalArgumentException if {@code price} is negative, NaN, or infinite
     */
    public Product(String name, double price, int productId) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative number, got: " + price);
        }
        this.name = name;
        this.price = price;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }

    /** Returns the base (list) price before any discount or promotion. */
    public double getPrice() {
        return price;
    }

    /** Returns the price the customer actually pays for this product. */
    public abstract double getFinalPrice();

    /**
     * Formats an amount as dollars with exactly two decimal places, e.g. {@code $60.00}.
     * Uses {@link Locale#US} so the decimal separator is always a period.
     */
    public static String formatCurrency(double amount) {
        return "$" + String.format(Locale.US, "%.2f", amount);
    }

    @Override
    public String toString() {
        return "Product ID: " + productId
                + ", Name: " + name
                + ", Price: " + formatCurrency(price);
    }
}
