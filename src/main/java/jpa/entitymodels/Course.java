package jpa.entitymodels;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

import java.util.Objects;

import static java.lang.System.out;

/**
 * @author mkemiche
 * @created 05/05/2021
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Course")
@NamedQuery(name = "getCourseById", query = "Select c from Course c where id = :id")
public class Course {

    @Id
    @Column(name = "id", columnDefinition = "int(11) UNSIGNED")
    private int cId;

    @Column(name = "name", length = 50, nullable = false)
    private String cName;
    @Column(name = "instructor", length = 50, nullable = false)
    private String cInstructorName;

    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    @Override
    public String toString() {
        return String.format("%-10s%-20S%-15s", getCId(), getCName(), getCInstructorName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return cId == course.cId && cName.equals(course.cName) && cInstructorName.equals(course.cInstructorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cInstructorName);
    }
}
