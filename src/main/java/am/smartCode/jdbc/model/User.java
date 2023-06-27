package am.smartCode.jdbc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class User {

    private Long id;
    private String name;
    private String lastname;
    private double balance;
    private String email;
    private String password;
    private int age;

    public User(String name, String lastname, double balance, String email, String password, int age) {
        this.name = name;
        this.lastname = lastname;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}