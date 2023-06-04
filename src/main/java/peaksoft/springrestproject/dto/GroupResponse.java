package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
}
