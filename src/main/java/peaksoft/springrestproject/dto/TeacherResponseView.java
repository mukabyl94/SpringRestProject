package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherResponseView {
    private List<TeacherResponse> teacherResponses;
}
