package am.smartCode.jdbc.model;

public class User {

    private Long id;
    private String name;
    private String lastname;
    private double balance;
    private String email;
    private String password;
    private int age;

    public User() {

    }

    public User(String name, String lastname, double balance, String email, String password, int age) {
        this.name = name;
        this.lastname = lastname;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", lastname='" + lastname + '\'' +
               ", username='" + email + '\'' +
               ", password='" + password + '\'' +
               ", age=" + age +
               '}';
    }
}