package peaksoft.springrestproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestproject.entity.Group;

@Getter
@Setter
@Builder
public class StudentResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Group group;
    private String roleName;
    private String studyFormationName;
}
