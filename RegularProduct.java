/**
 * A product sold at its base price with no discount or promotion.
 *
 * @author Yusuf Almahoozi
 */
public class RegularProduct extends Product {

    public RegularProduct(String name, double price, int productId) {
        super(name, price, productId);
    }

    /** The final price is simply the base price. */
    @Override
    public double getFinalPrice() {
        return getPrice();
    }
}
