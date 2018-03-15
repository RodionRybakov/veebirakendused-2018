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
    String typeOfwork;

    @Column(name = "Description")//some text about me
            String description;



    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {

        return id;
    }

    public User getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getTypeOfwork() {
        return typeOfwork;
    }

    public void setTypeOfwork(String typeOfwork) {

        this.typeOfwork = typeOfwork;
    }

    public String getDescription() {
        return description;
    }


}
