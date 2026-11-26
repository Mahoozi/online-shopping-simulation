import java.util.List;

/**
 * Base type for every account in the shop simulation.
 *
 * <p>Holds the shared user data (ID and username) and declares
 * {@link #printReceipt(List)} as abstract, so each user role decides what its
 * receipt looks like.
 *
 * @author Yusuf Almahoozi
 */
public abstract class User {

    private int userId;
    private String username;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    /** Prints this user's receipt for the given list of products to standard output. */
    public abstract void printReceipt(List<Product> products);

    @Override
    public String toString() {
        return "User ID: " + userId + ", Username: " + username;
    }
}
