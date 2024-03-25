import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomerGUI {
    private String selectedItem;
    private JComboBox<String> typeDropDown;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JLabel productDetailsLabel;
    private JLabel userNameLabel;

    private JButton addToCartButton;
    private JButton viewCartButton;
    private ArrayList<Product> productList;
    private String[] columnNames = {"<html><b>Product ID</b></html>", "<html><b>Name</b></html>",
                                    "<html><b>Category</b></html>", "<html><b>Price(£)</b></html>",
                                    "<html><b>Info</b></html>"};

    public CustomerGUI(ArrayList<Product> productList, String username, boolean newUser) {
        this.productList = productList;
        JFrame frame = new JFrame("Westminster Shopping System");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        ShoppingCart cart = new ShoppingCart(newUser);

        typeDropDown = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        tableModel = new DefaultTableModel(null, columnNames);
        productTable = new JTable(tableModel);
        int rowHeight = 30;
        productTable.setRowHeight(rowHeight);

        productDetailsLabel = new JLabel();
        userNameLabel = new JLabel("Welcome, " + username + "!");
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 180));
        userNameLabel.setFont(new Font("PLAIN", Font.BOLD, 14));

        addToCartButton = new JButton("Add to Cart");
        viewCartButton = new JButton("View Shopping Cart");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(userNameLabel);
        panel1.add(viewCartButton);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        typeDropDown.setPreferredSize(new Dimension(300, typeDropDown.getPreferredSize().height));
        panel2.add(typeDropDown);

        JPanel panel3 = new JPanel(new BorderLayout());
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        setColumnWidth(0, 100);  // Product Name column
        setColumnWidth(1, 150);  // Quantity column
        setColumnWidth(2, 100);
        setColumnWidth(3, 100);
        setColumnWidth(4, 250);
        tableScrollPane.setMaximumSize(new Dimension(700, 100));

        int padding = 30;
        panel3.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel3.add(tableScrollPane, BorderLayout.CENTER);

        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.setBorder(BorderFactory.createEmptyBorder(0, padding, padding, padding));
        panel4.add(productDetailsLabel);

        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel5.add(addToCartButton);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        updateTable();

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedItem != null) {
                    Product selectedProduct = null;
                    for (Product product : productList) {
                        if (product.getProductID().equals(selectedItem)) {
                            selectedProduct = product;
                            break;
                        }
                    }

                    if (selectedProduct != null) {
                        if (selectedProduct.getStocks() > 0) {
                            cart.addProduct(selectedProduct);
                            selectedProduct.setStocks(selectedProduct.getStocks() - 1);
                            updateTable();
                            updateItemLabel(selectedProduct);
                            JOptionPane.showMessageDialog(frame, "Product added to cart.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Sorry, this product is out of stock.");
                        }
                    }
                }
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    cart.openGUI();
            }
        });

        typeDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Object productId = productTable.getValueAt(selectedRow, 0);
                        Product clickedProduct = null;
                        for(Product product : productList) {
                            if(product.getProductID().equals(productId)) {
                                clickedProduct = product;
                            }
                        }
                        selectedItem = productId.toString();
                        updateItemLabel(clickedProduct);
                    }
                }
            }
        });


    }

    private void setColumnWidth(int columnIndex, int width) {
        TableColumn column = productTable.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setMinWidth(width);
    }

    private void updateItemLabel(Product selectedProduct) {
        String productDetails = "<html><b>Selected Product - Details:</b><br><br>" +
                "Product ID: " + selectedProduct.getProductID() + "<br>" +
                "Category: " + selectedProduct.type() + "<br>" +
                "Name: " + selectedProduct.getProductName() + "<br>";

        if (selectedProduct instanceof Electronics) {
            productDetails += "Size: " + ((Electronics) selectedProduct).attribute1() + "<br>" +
                    "Color: " + ((Electronics) selectedProduct).attribute2();
        } else {
            productDetails += "Brand: " + selectedProduct.attribute1() + "<br>" +
                    "Warranty Period: " + selectedProduct.attribute2();
        }

        productDetails += "<br>Available Items: " + selectedProduct.getStocks() + "</html>";
        productDetailsLabel.setText(productDetails);
        productDetailsLabel.setFont(new Font("PLAIN", Font.BOLD, 14));
    }


    private void updateTable() {
        String selectedCategory = (String) typeDropDown.getSelectedItem();
        ArrayList<Product> fillingArray = new ArrayList<>();
        tableModel.setRowCount(0);
        if (selectedCategory.equals("All")) {
            fillingArray.clear();
            fillingArray.addAll(productList);
        } else if(selectedCategory.equals("Electronics")) {
            fillingArray.clear();
            for(Product product : productList) {
                if(product instanceof Electronics) {
                    fillingArray.add(product);
                }
            }
        } else if(selectedCategory.equals("Clothing")) {
            fillingArray.clear();
            for(Product product : productList) {
                if(product instanceof Clothing) {
                    fillingArray.add(product);
                }
            }
        }
        // sorting the array
        Collections.sort(fillingArray, Comparator.comparing(Product::getProductID));

        for (Product product : fillingArray) {
            String productType;
            if(product instanceof Electronics) {
                productType = "Electronics";
            } else {
                productType = "Clothing";
            }
            Object[] data = {
                    product.getProductID(),
                    product.getProductName(),
                    productType,
                    product.getPrice(),
                    product.toInfo()
            };
            tableModel.addRow(data);
        }
    }
}