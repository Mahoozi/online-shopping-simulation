/**
 * A product sold at a fixed promotional price instead of its base price.
 *
 * <p>The class is declared {@code final}, so it cannot be subclassed.
 *
 * @author Yusuf Almahoozi
 */
public final class FinalProduct extends Product {

    private double promotionalPrice;

    public FinalProduct(String name, double price, int productId, double promotionalPrice) {
        super(name, price, productId);
        this.promotionalPrice = promotionalPrice;
    }

    public double getPromotionalPrice() {
        return promotionalPrice;
    }

    /** The final price is the promotional price, regardless of the base price. */
    @Override
    public double getFinalPrice() {
        return promotionalPrice;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Promo Price: $" + promotionalPrice;
    }
}
