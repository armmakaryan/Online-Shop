package am.smartCode.jdbc.model;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    private long id;
    private String category;
    private String name;
    private long price;


}
