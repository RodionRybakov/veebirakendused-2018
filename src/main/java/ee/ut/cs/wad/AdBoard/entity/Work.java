package ee.ut.cs.wad.AdBoard.entity;

import javax.persistence.*;

@Entity
@Table (name = "Work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false)
    User user_id; //foreign key

    @Column(name = "Name")
    String name;

    @Column(name = "City")
    String city;

    @Column(name = "TypeOfWork")
    String gender;

    @Column(name = "Description")//some text about me
            String description;


}
