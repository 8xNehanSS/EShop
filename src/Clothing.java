public class Clothing extends Product {
    protected String size;
    protected String color;
    public Clothing(String productID, String productName, int stocks, double price, String size, String color) {
        super(productID, productName, stocks, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Clothing\n\n" +
                super.toString() +
                "Size = '" + size  + "'\n" +
                "Color = '" + color + "'\n";
    }

    public String toInfo() {
        return size + "," + color;
    }

    public String attribute1() {
        return size;
    }

    public String attribute2() {
        return color;
    }

    @Override
    public String type() {
        return "Clothing";
    }
}
