package peaksoft.springrestproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String durationMonth;
    @CreatedDate
    private LocalDate localDate;
    private Boolean isActive = true;
    private Boolean isDeleted = false;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private User teacher;
}
