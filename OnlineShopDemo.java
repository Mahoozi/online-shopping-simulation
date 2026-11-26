import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for the online shopping simulation.
 *
 * <p>Builds a small catalog of products and a set of users, then prints each
 * user's receipt, the product details, discount information found through
 * safe downcasting, and an admin statistics summary.
 *
 * @author Yusuf Almahoozi
 */
public class OnlineShopDemo {

    public static void main(String[] args) {

        // Each concrete product is stored through its Product supertype (upcasting)
        List<Product> products = new ArrayList<>();
        products.add(new DiscountedProduct("iPhone", 999.0, 1, 15));
        products.add(new RegularProduct("Jeans", 60.0, 2));
        products.add(new FinalProduct("T-Shirt", 25.0, 3, 9.99));

        // Each user role is stored through its User supertype (upcasting)
        List<User> users = new ArrayList<>();
        users.add(new PremiumUser(101, "alice123"));
        users.add(new RegularUser(102, "bob456"));

        // printReceipt() is resolved at runtime from each user's actual class
        for (User user : users) {
            user.printReceipt(products);
            System.out.println();
        }

        // toString() is resolved at runtime from each product's actual class
        System.out.println("--- Product Details (late binding) ---");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        // instanceof check before downcasting to reach a subclass-only method
        System.out.println("--- Special Behavior (safe downcasting) ---");
        for (Product product : products) {
            if (product instanceof DiscountedProduct) {
                DiscountedProduct dp = (DiscountedProduct) product;
                System.out.println(dp.getName() + " has a discount of "
                        + dp.getDiscountedPercentage() + "%");
            }
        }
        System.out.println();

        // AdminUser.viewSystemStats() is a final method
        AdminUser admin = new AdminUser(1, "admin");
        admin.viewSystemStats(products.size(), users.size());
    }
}
