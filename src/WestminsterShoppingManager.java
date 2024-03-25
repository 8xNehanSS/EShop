import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Product> productList;

    public WestminsterShoppingManager(ArrayList<Product> productList) {
        WestminsterShoppingManager.productList = productList;
    }
    public static void showMenu() {
        String menuText = "Welcome to Westminster Shopping Manager\n\n" +
                "Choose the action:\n" +
                "1. Add a new product\n" +
                "2. Delete a product\n" +
                "3. Print the list of products\n" +
                "4. Save in a file\n" +
                "0. Exit\n\n" +
                "Enter your option here: ";
        System.out.print(menuText);
    }

    public void optionAction(int option) {
        switch (option) {
            case 1:
                addProduct();
                break;
            case 2:
                removeProduct();
                break;
            case 3:
                printProductList();
                break;
            case 4:
                saveToFile();
                break;
            case 0:
                System.out.println("Exiting..\n\n");
                return;
            default:
                System.out.println("Invalid Choice\n\n");
                break;
        }
    }

    public void addProduct() {
        if(productList.size() == 50) {
            System.out.println("\nProduct list is full\n\n");
            return;
        }
        System.out.print("Enter ProductID: ");
        String productID = scanner.nextLine();
        boolean exists = false;
        for(Product product : productList) {
            if (product.getProductID().equals(productID)) {
                exists = true;
                System.out.println("\nProduct already exists\nDo you want to edit the price and stocks?\n(Y/N): ");
                String valid = scanner.nextLine();
                if(valid.equalsIgnoreCase("Y")) {
                    editPriceStocks(product);
                } else {
                    break;
                }
            }
        }

        if(!exists) {
            addNewProduct(productID);
        }
    }

    private void editPriceStocks(Product product) {
        try {
            System.out.print("Enter new amount of Stocks: ");
            int stocks = scanner.nextInt();
            scanner.nextLine();
            product.setStocks(stocks);
            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            product.setPrice(price);
            System.out.println("Successfully changed Product Details\n\n" +product+"\n\n");
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid type\n\n");
        }
    }


    private static void addNewProduct(String productID) {
        System.out.print("Enter the product type\n1 - Electronics 2 - Clothing:");
        int productToAdd = scanner.nextInt();
        scanner.nextLine();
        if(productToAdd != 1 && productToAdd != 2) {
            System.out.println("Invalid Type\n\n");
            return;
        }
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        int stocks;
        double price;
        try {
            System.out.print("Enter amount of Stocks: ");
            stocks = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Price: ");
            price = scanner.nextDouble();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid entry\n\n");
            scanner.nextLine();
            return;
        }
        if(productToAdd==1) {
            System.out.print("Enter Brand: ");
            String brand = scanner.nextLine();
            int warrantyPeriod;
            try {
                System.out.print("Enter Warranty Period: ");
                warrantyPeriod = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid entry\n\n");
                scanner.nextLine();
                return;
            }
            Electronics newElectronic = new Electronics(productID,productName,stocks,price,brand,warrantyPeriod);
            productList.add(newElectronic);
            System.out.println(newElectronic.getProductName() + " added to the product list.\n\n");
        } else {
            System.out.print("Enter Size: ");
            String size = scanner.nextLine();
            System.out.print("Enter Color: ");
            String color = scanner.nextLine();
            Clothing newClothing = new Clothing(productID,productName,stocks,price,size,color);
            productList.add(newClothing);
            System.out.println(newClothing.getProductID() + " - " + newClothing.getProductName() +
                    " added to the product list.\n\n");
        }
    }

    public void removeProduct() {
        if(productList.isEmpty()) {
            System.out.println("\nProduct list is empty\n\n");
            return;
        }
        System.out.println("Enter ProductID: ");
        String productID = scanner.nextLine();
        boolean exists = false;
        for(Product product : productList) {
            if (product.getProductID().equals(productID)) {
                exists = true;
                productList.remove(product);
                System.out.println("Successfully removed Product\n" +product+"\n\n");
                break;
            }
        }

        if(!exists) {
            System.out.println("Product does not exist\n\n");
        }
    }

    public void printProductList() {
        ArrayList<Product> productSort = new ArrayList<>(productList);
        Collections.sort(productSort, Comparator.comparing(Product::getProductID));

        System.out.println("\nNumber of Products in System: " + productSort.size() + "\n");
        System.out.println("Sorted list of products\n\n");
        System.out.println(productSort);
    }



    public void saveToFile() {

        //prompt to check if user wants to store the data
        System.out.print("\nStoring will delete existing data. Are you sure? (Y): ");
        String valid = scanner.nextLine();
        if (!valid.equalsIgnoreCase("Y")) {
            System.out.println("\nExiting store process\n\n");
            return;
        }

        //defining writer object and writing the values to the text file
        try {
            FileWriter myWriter = new FileWriter("productData.txt");
            //writing the data
            for (Product product : productList) {
                if(product instanceof Electronics) {
                    Electronics e = (Electronics) product;
                    myWriter.write("E~"+
                            e.getProductID() + "~"+
                            e.getProductName() + "~"+
                            e.getStocks() + "~"+
                            e.getPrice() + "~"+
                            e.getBrand() + "~"+
                            e.getWarrantyPeriod() + "~\n"
                    );
                } else if(product instanceof Clothing) {
                    Clothing c = (Clothing) product;
                    myWriter.write("C~"+
                            c.getProductID() + "~"+
                            c.getProductName() + "~"+
                            c.getStocks() + "~"+
                            c.getPrice() + "~"+
                            c.getColor() + "~"+
                            c.getSize() + "~\n"
                    );
                }
            }
            myWriter.close();
            System.out.println("Successfully saved the Product List\n\n");
        } catch (IOException e) {
            System.out.println("Error occurred.\n\n");
        }
        System.out.println();
    }
}