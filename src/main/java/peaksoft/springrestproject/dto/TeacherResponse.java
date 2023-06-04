package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestproject.entity.Course;

@Getter
@Setter
public class TeacherResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Course course;
    private String roleName;
}
