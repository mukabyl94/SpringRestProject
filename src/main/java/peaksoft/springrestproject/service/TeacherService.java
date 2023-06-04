package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.ChangeRoleRequest;
import peaksoft.springrestproject.dto.StudentResponse;
import peaksoft.springrestproject.dto.TeacherRequest;
import peaksoft.springrestproject.dto.TeacherResponse;
import peaksoft.springrestproject.entity.Course;
import peaksoft.springrestproject.entity.Role;
import peaksoft.springrestproject.entity.User;
import peaksoft.springrestproject.repository.CourseRepository;
import peaksoft.springrestproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public TeacherResponse create(TeacherRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.INSTRUCTOR);
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setCourse(course);
        userRepository.save(user);
        return mapToResponse(user);
    }
    public TeacherResponse mapToResponse(User user){
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setFirstName(user.getFirstName());
        teacherResponse.setLastName(user.getLastName());
        teacherResponse.setEmail(user.getEmail());
        teacherResponse.setPassword(user.getPassword());
        teacherResponse.setRoleName(user.getRole().name());
        teacherResponse.setCourse(user.getCourse());
        return teacherResponse;
    }
    public List<TeacherResponse> getAll(){
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (User user : userRepository.findAll()){
            teacherResponses.add(mapToResponse(user));
        }
        return teacherResponses;
    }
    public TeacherResponse getTeacherById(Long teacherId){
        User user = userRepository.findById(teacherId).get();
        return mapToResponse(user);
    }
    public TeacherResponse updateTeacher(Long id, TeacherRequest request){
        User user = userRepository.findById(id).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setCourse(course);
        userRepository.save(user);
        return mapToResponse(user);
    }
    public void deleteTeacher(Long teacherId){
        userRepository.deleteById(teacherId);
    }
    public List<User> getAllTeachers(){
        return userRepository.getAllTeachers();
    }
    public TeacherResponse changeRole(Long userId, ChangeRoleRequest request) {
        User user = userRepository.findById(userId).get();
        user.setRole(Role.valueOf(request.getRolName()));
        userRepository.save(user);
        return mapToResponse(user);
    }
}
