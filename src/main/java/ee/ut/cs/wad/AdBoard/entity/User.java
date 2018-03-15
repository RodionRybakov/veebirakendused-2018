package ee.ut.cs.wad.AdBoard.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "\\b[A-ZÜÕÖÄa-zöäüõ -]+\\b")
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

    public User(){

    }
    public User(Long id,String name,int age,String gender,String about ){
        if(name.trim().equals("") || name.equals("") || id < 0 || age < 18)throw new RuntimeException("Wrong name");
    }
    public Long getId() {
        return id;
    }

    public void setWorks(List<Work> works) {

        this.works = works;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Work> getWorks() {

        return works;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getAbout() {
        return about;
    }

}
