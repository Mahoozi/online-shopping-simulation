import java.util.List;

/**
 * A customer who earns loyalty points on purchases. The receipt matches a
 * regular user's receipt, plus the number of points earned for that order.
 *
 * @author Yusuf Almahoozi
 */
public class PremiumUser extends User {

    private int loyaltyPoints;

    public PremiumUser(int userId, String username) {
        super(userId, username);
        this.loyaltyPoints = 0;
    }

    /** Returns the points accumulated across every receipt printed for this user. */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    @Override
    public void printReceipt(List<Product> products) {
        System.out.println("--- Receipt for PremiumUser: " + getUsername() + " ---");
        double total = 0;
        for (Product product : products) {
            System.out.println("Product: " + product.getName() + " - " + Product.formatCurrency(product.getPrice())
                    + " → Final Price: " + Product.formatCurrency(product.getFinalPrice()));
            total += product.getFinalPrice();
        }
        System.out.println("Total: " + Product.formatCurrency(total));

        // 1 loyalty point per full $10 of the order total (partial amounts are dropped)
        int pointsEarned = (int) (total / 10);
        loyaltyPoints += pointsEarned;
        System.out.println("Points Earned: " + pointsEarned);
    }
}
