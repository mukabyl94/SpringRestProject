package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.CourseRequest;
import peaksoft.springrestproject.dto.CourseResponse;
import peaksoft.springrestproject.entity.Company;
import peaksoft.springrestproject.entity.Course;
import peaksoft.springrestproject.repository.CompanyRepository;
import peaksoft.springrestproject.repository.CourseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;

    public CourseResponse create(CourseRequest courseRequest){
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setDurationMonth(courseRequest.getDurationMonth());
        course.setLocalDate(LocalDate.now());
        course.setIsActive(course.getIsActive());
        course.setIsDeleted(course.getIsDeleted());
        Company company = companyRepository.findById(courseRequest.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);
        return mapToResponse(course);
    }
    public CourseResponse mapToResponse(Course course){
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        courseResponse.setCompany(course.getCompany());
        courseResponse.setLocalDate(course.getLocalDate());
        courseResponse.setIsActive(course.getIsActive());
        courseResponse.setIsDelete(course.getIsDeleted());
        return courseResponse;
    }
    public List<CourseResponse> getAll(){
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courseResponses.add(mapToResponse(course));
        }
        return courseResponses;
    }
    public CourseResponse getCourseById(Long courseId){
        Course course = courseRepository.findById(courseId).get();
        return mapToResponse(course);
    }
    public CourseResponse updateCourse(Long id, CourseRequest request){
        Course course = courseRepository.findById(id).get();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        Company company = companyRepository.findById(request.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);
        return mapToResponse(course);
    }
    public void deleteCourse(Long courseId){
        courseRepository.deleteById(courseId);
    }
}
