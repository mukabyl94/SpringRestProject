package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long groupId;
    private String studyFormationName;
}
