import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // declaring the required variables for the program
    private static final Scanner scanner = new Scanner(System.in);
    public static ArrayList<Product> productList = new ArrayList<>();

    // main thread which is running the shop
    public static void main(String[] args) {

        // loading the product list from the saved file
        try {
            File myObj = new File("productData.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] productData = data.split("~");
                if(productData[0].equals("E")) {
                    Electronics e = new Electronics(productData[1],productData[2],Integer.parseInt(productData[3]),Double.parseDouble(productData[4]),productData[5],Integer.parseInt(productData[6]));
                    productList.add(e);
                } else if(productData[0].equals("C")) {
                    Clothing c = new Clothing(productData[1],productData[2],Integer.parseInt(productData[3]),Double.parseDouble(productData[4]),productData[5],productData[6]);
                    productList.add(c);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from file.\n");
        }
        while (true) {
            showMenu();
            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid entry\n");
                scanner.nextLine();
                continue;
            }
            switch (option) {
                case 1:
                    System.out.println("Opening Manager Instance..\n\n");
                    openManager();
                    break;
                case 2:
                    System.out.println("Opening Customer GUI Instance..\n\n");
                    openCustomer();
                    break;
                case 0:
                    System.out.println("Exiting..");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice\n\n");
                    break;
            }
        }
    }

    /**
     * Shopping menu method for the online shop
     */
    public static void showMenu() {
        String menuText = "Welcome to Westminster Shopping Manager\n\n" +
                "Choose the action:\n" +
                "1. Open Manager Instance\n" +
                "2. Open Customer GUI Instance\n" +
                "0. Exit\n\n" +
                "Enter your option here: ";
        System.out.print(menuText);
    }

    /**
     * Creating and opening an instance of the manager program
     */
    public static void openManager() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager(productList);
        while(true) {
            WestminsterShoppingManager.showMenu();
            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid entry\n");
                scanner.nextLine();
                continue;
            }
            manager.optionAction(option);
            if(option == 0) {
                break;
            }
        }
    }

    /**
     * Creating and opening an instance of the user instance of the shop
     */
    public static void openCustomer() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserGUI userGUI = new UserGUI();
    }
}
