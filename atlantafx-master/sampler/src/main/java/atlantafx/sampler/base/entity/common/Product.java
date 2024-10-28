package atlantafx.sampler.base.entity.common;



public class Product {
  private int id;
  private String name;
  private double price;
  private String imageLink;
  private int categoryId;
  private int quantity;
  private Discount discount;

  public Product(int id, String name, double price, String imageLink, int categoryId, int quantity, Discount discount) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageLink = imageLink;
    this.categoryId = categoryId;
    this.quantity = quantity;
    this.discount = discount;
  }

  public Product() {
  }

  public Product(int id, String name, double price, String imageLink, int categoryId) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageLink = imageLink;
    this.categoryId = categoryId;
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

  public Discount getDiscount() {
    return discount;
  }

  public void setDiscount(Discount discount) {
    this.discount = discount;
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
            ", discount=" + discount +
            '}';
  }
}
