import java.util.List;

/**
 * An administrator account. Admins do not make purchases, so their receipt
 * only prints a notice; instead they can view simple system statistics.
 *
 * @author Yusuf Almahoozi
 */
public class AdminUser extends User {

    public AdminUser(int userId, String username) {
        super(userId, username);
    }

    @Override
    public void printReceipt(List<Product> products) {
        System.out.println("--- Receipt for AdminUser: " + getUsername() + " ---");
        System.out.println("Admins do not have purchase receipts.");
    }

    /**
     * Prints product and user counts. Declared {@code final}, so subclasses of
     * AdminUser cannot override it.
     */
    public final void viewSystemStats(int totalProducts, int totalUsers) {
        System.out.println("Admin Stats: Total Products: " + totalProducts
                + " | Total Users: " + totalUsers);
    }
}
