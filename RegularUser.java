import java.util.List;

/**
 * A standard customer. Their receipt lists each product's base and final
 * price, followed by the total of the final prices.
 *
 * @author Yusuf Almahoozi
 */
public class RegularUser extends User {

    public RegularUser(int userId, String username) {
        super(userId, username);
    }

    @Override
    public void printReceipt(List<Product> products) {
        System.out.println("--- Receipt for RegularUser: " + getUsername() + " ---");
        double total = 0;
        for (Product product : products) {
            System.out.println("Product: " + product.getName() + " - $" + product.getPrice()
                    + " → Final Price: $" + product.getFinalPrice());
            total += product.getFinalPrice();
        }
        System.out.println("Total: $" + total);
    }
}
