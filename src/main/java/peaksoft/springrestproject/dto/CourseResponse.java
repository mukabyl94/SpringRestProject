package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springrestproject.entity.Company;

import java.time.LocalDate;
@Getter
@Setter
public class CourseResponse {
    private String courseName;
    private String durationMonth;
    private LocalDate localDate;
    private Boolean isActive;
    private Boolean isDelete;
    private Company company;
}
