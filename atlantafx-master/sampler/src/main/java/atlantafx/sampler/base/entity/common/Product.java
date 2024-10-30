package atlantafx.sampler.base.entity.common;



public class Product {
  private int id;
  private String name;
  private double price;
  private String imageLink;
  private int categoryId;
  private int quantity;
  private double  discountPercentage;



  public Product() {
  }



  public Product(int id, String name, double price, int categoryId, String imageLink) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.imageLink = imageLink;
  }



  public Product(int id, String name, double price, int i) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.categoryId = i;
  }

    public Product(int id, String imageLink, String name, double price, double discountedPrice, int categoryId) {
        this.id = id;
        this.imageLink = imageLink;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(int id, String imageLink, String name, double price, int categoryId) {
        this.id = id;
        this.imageLink = imageLink;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

  public Product(String name, double price, String imagePath) {
    this.name = name;
    this.price = price;
    this.imageLink = imagePath;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(double discountPercentage) {
    this.discountPercentage = discountPercentage;
  }
  public double getDiscountedPrice() {
    return price - (price * (discountPercentage / 100));
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", imageLink='" + imageLink + '\'' +
            ", categoryId=" + categoryId +
            ", quantity=" + quantity +
            ", discountPercentage=" + discountPercentage +
            '}';
  }
}
