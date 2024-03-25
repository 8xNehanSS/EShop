public abstract class Product {
    private String productID;
    private String productName;
    private int stocks;
    private double price;

    public Product(String productID, String productName, int stocks, double price) {
        this.productID = productID;
        this.productName = productName;
        this.stocks = stocks;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID = '" + productID + "'\n" +
                "Product Name = '" + productName + "'\n" +
                "Stocks = " + stocks + "\n" +
                "Price = " + price + "\n";
    }

    public abstract String toInfo();

    public abstract String attribute1();
    public abstract String attribute2();
    public abstract String type();

}
