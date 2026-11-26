/**
 * A product sold at a fixed promotional price instead of its base price.
 *
 * <p>The class is declared {@code final}, so it cannot be subclassed.
 *
 * @author Yusuf Almahoozi
 */
public final class FinalProduct extends Product {

    private double promotionalPrice;

    /**
     * @throws IllegalArgumentException if the price is invalid or
     *         {@code promotionalPrice} is negative or above the base price
     */
    public FinalProduct(String name, double price, int productId, double promotionalPrice) {
        super(name, price, productId);
        if (!(promotionalPrice >= 0 && promotionalPrice <= price)) {
            throw new IllegalArgumentException("Promotional price must be between 0 and the base price ("
                    + formatCurrency(price) + "), got: " + promotionalPrice);
        }
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
                + ", Promo Price: " + formatCurrency(promotionalPrice);
    }
}
