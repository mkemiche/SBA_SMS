package jpa.entitymodels;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author mkemiche
 * @created 05/05/2021
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Student")
@NamedQueries({
        @NamedQuery(name = "getStudentsByEmail", query = "select s from Student s where email = :email"),
        @NamedQuery(name = "getStudentCoursesByEmail2", query = "select course from Student s where email = :email"),
})
public class Student {

    @Id
    @Column(name = "email", length = 50, columnDefinition = "varchar(50)")
    private String sEmail;


    @Column(name = "name", length = 50, nullable = false)
    private String sName;

    @Column(name = "password", length = 50, nullable = false)
    private String sPass;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Course> course;

    @Override
    public String toString() {
        return "Student{" +
                "sEmail='" + sEmail + '\'' +
                ", sName='" + sName + '\'' +
                ", sPass='" + sPass + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(sEmail, student.sEmail) && Objects.equals(sName, student.sName) && Objects.equals(sPass, student.sPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sEmail, sName, sPass);
    }
}
