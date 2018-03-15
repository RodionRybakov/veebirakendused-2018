package ee.ut.cs.wad.AdBoard.entity;

import javax.persistence.*;
import java.util.List;

// example https://github.com/oronno/WebApplication-Project-Skeleton
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user_id")
    List<Work> works;

    @Column(name = "Name")
    String name;

    @Column(name = "Age")
    int age;

    @Column(name = "City")
    String city;

    @Column(name = "Gender")
    String gender;

    @Column(name = "About")//some text about me
    String about;


}
