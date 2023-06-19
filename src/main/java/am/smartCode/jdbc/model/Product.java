package am.smartCode.jdbc.model;

import java.util.Objects;

public class Product {
    private long id;
    private String category;
    private String name;
    private String publishedDate;
    private long price;

    public Product(long id, String category, String name, String publishedYear, long price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.publishedDate = publishedYear;
        this.price = price;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", category='" + category + '\'' +
               ", name='" + name + '\'' +
               ", publishedDate=" + publishedDate +
               ", price=" + price +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && Objects.equals(category, product.category) && Objects.equals(name, product.name) && Objects.equals(publishedDate, product.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, publishedDate, price);
    }
}