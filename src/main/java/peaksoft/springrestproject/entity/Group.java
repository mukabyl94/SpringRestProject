package peaksoft.springrestproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<User> student;
}
