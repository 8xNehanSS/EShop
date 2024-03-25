import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    public Map<Product, Integer> cart = new HashMap<>();
    public static int[] categoryCount = {0, 0};
    private boolean newUser;
    ShoppingCart (boolean newUser) {
        this.newUser = newUser;
    }
    public void addProduct(Product product) {
        if(product.type().equals("Electronics")) {
            categoryCount[0]++;
        } else {
            categoryCount[1]++;
        }
        cart.put(product, cart.getOrDefault(product, 0) + 1);
    }
    public void openGUI() {
        ShoppingCartGUI cartGUI = new ShoppingCartGUI(cart, newUser);
    }
}
