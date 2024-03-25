public class Electronics extends Product {
    protected String brand;
    protected int warrantyPeriod;
    public Electronics(String productID, String productName, int stocks, double price, String brand, int warrantyPeriod) {
        super(productID, productName, stocks, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics\n\n" +
                "Brand = '" + brand + "'\n" +
                super.toString() +
                "WarrantyPeriod = " + warrantyPeriod;
    }
    public String toInfo() {
        return brand + "," + warrantyPeriod + " weeks warranty";
    }

    public String attribute1() {
        return brand;
    }

    public String attribute2() {
        return Integer.toString(warrantyPeriod);
    }

    @Override
    public String type() {
        return "Electronics";
    }

}
