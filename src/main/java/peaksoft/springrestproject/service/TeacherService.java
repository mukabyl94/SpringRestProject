package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.*;
import peaksoft.springrestproject.entity.Course;
import peaksoft.springrestproject.entity.Role;
import peaksoft.springrestproject.entity.User;
import peaksoft.springrestproject.repository.CourseRepository;
import peaksoft.springrestproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public TeacherResponse create(TeacherRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setCourse(course);
        userRepository.save(user);
        return mapToResponse(user);
    }
    public TeacherResponse mapToResponse(User user){
        return TeacherResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleName(user.getRole().name())
                .course(user.getCourse()).build();
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
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
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
    public TeacherResponseView searchAndPagination(String text, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        TeacherResponseView teacherResponseView = new TeacherResponseView();
        teacherResponseView.setTeacherResponses(view(search(text, pageable)));
        return teacherResponseView;
    }
    public List<TeacherResponse> view (List<User> users){
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (User user : users) {
            teacherResponses.add(mapToResponse(user));
        }
        return teacherResponses;
    }
    public List<User> search(String text, Pageable pageable){
        String name= text == null?"": text;
        return userRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
