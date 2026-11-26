/**
 * A product whose final price is its base price reduced by a percentage
 * discount (for example, 15 means 15% off).
 *
 * @author Yusuf Almahoozi
 */
public class DiscountedProduct extends Product {

    private double discountedPercentage;

    public DiscountedProduct(String name, double price, int productId, double discountedPercentage) {
        super(name, price, productId);
        this.discountedPercentage = discountedPercentage;
    }

    public double getDiscountedPercentage() {
        return discountedPercentage;
    }

    /** Final price = base price - (base price * discount% / 100). */
    @Override
    public double getFinalPrice() {
        return getPrice() - (getPrice() * discountedPercentage / 100);
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Discount: " + discountedPercentage + "%";
    }
}
