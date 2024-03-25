import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ShoppingCartGUI {
    private JFrame frame;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel, firstPurchaseDiscountLabel, categoryDiscountLabel, finalTotalLabel;
    private boolean newUser;
    private Map<Product, Integer> cart;

    public ShoppingCartGUI(Map<Product, Integer> cart, boolean newUser) {
        this.newUser = newUser;
        this.cart = cart;

        frame = new JFrame("Shopping Cart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        String[] columnNames = {"<html><b>Product Name</html></b>", "<html><b>Quantity</html></b>",
                                "<html><b>Price</html></b>"};
        tableModel = new DefaultTableModel(null, columnNames);

        cartTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(cartTable);

        totalLabel = new JLabel("Total  :  0.00 £");
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount  :  0.00 £");
        categoryDiscountLabel = new JLabel("Category Discount  :  0.00 £");
        finalTotalLabel = new JLabel("Final Total  :  0.00 £");

        totalLabel.setFont(new Font("PLAIN", Font.BOLD, 14));
        firstPurchaseDiscountLabel.setFont(new Font("PLAIN", Font.BOLD, 14));
        categoryDiscountLabel.setFont(new Font("PLAIN", Font.BOLD, 14));
        finalTotalLabel.setFont(new Font("PLAIN", Font.BOLD, 14));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel1.add(tableScrollPane);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel3.add(totalLabel);
        panel3.add(firstPurchaseDiscountLabel);
        panel3.add(categoryDiscountLabel);
        panel3.add(finalTotalLabel);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        panel2.add(panel3);

        panel.add(panel1);
        panel.add(panel2);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        updateCartTable();
        updateTotalLabels();
    }

    private void updateCartTable() {
        tableModel.setRowCount(0);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            Object[] rowData = {product.getProductName(), quantity, product.getPrice()+" £"};
            tableModel.addRow(rowData);
        }
    }

    private void updateTotalLabels() {
        double total = calculateTotal();
        double firstPurchaseDiscount = calculateFirstPurchaseDiscount();
        double categoryDiscount = calculateCategoryDiscount();
        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;

        totalLabel.setText("Total: " + String.format("%.2f", total)+" £");
        firstPurchaseDiscountLabel.setText("First Purchase Discount: " + String.format("%.2f", firstPurchaseDiscount)+" £");
        categoryDiscountLabel.setText("Category Discount: " + String.format("%.2f", categoryDiscount)+" £");
        finalTotalLabel.setText("Final Total: " + String.format("%.2f", finalTotal)+" £");
    }

    private double calculateCategoryDiscount() {
        if(ShoppingCart.categoryCount[0]>2) {
            return 0.2 * calculateTotal();
        }
        else if(ShoppingCart.categoryCount[1]>2) {
            return 0.2 * calculateTotal();
        } else {
            return 0;
        }
    }

    private double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += quantity * product.getPrice();
        }
        return total;
    }

    private double calculateFirstPurchaseDiscount() {
        if(newUser) {
            return 0.1 * calculateTotal();
        }
        return 0;
    }
}
