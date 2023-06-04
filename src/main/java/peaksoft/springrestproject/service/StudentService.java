package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.ChangeRoleRequest;
import peaksoft.springrestproject.dto.StudentRequest;
import peaksoft.springrestproject.dto.StudentResponse;
import peaksoft.springrestproject.dto.StudentResponseView;
import peaksoft.springrestproject.entity.Group;
import peaksoft.springrestproject.entity.Role;
import peaksoft.springrestproject.entity.StudyFormation;
import peaksoft.springrestproject.entity.User;
import peaksoft.springrestproject.repository.GroupRepository;
import peaksoft.springrestproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public StudentResponse create(StudentRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        user.setStudyFormation(StudyFormation.valueOf(request.getStudyFormationName()));
        Group group = groupRepository.findById(request.getGroupId()).get();
        user.setGroup(group);
        userRepository.save(user);
        return mapToResponse(user);
    }
    public StudentResponse mapToResponse(User user){
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setFirstName(user.getFirstName());
        studentResponse.setLastName(user.getLastName());
        studentResponse.setEmail(user.getEmail());
        studentResponse.setPassword(user.getPassword());
        studentResponse.setRoleName(user.getRole().name());
        studentResponse.setGroup(user.getGroup());
        return studentResponse;
    }
    public List<StudentResponse> getAll(){
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (User user : userRepository.findAll()){
            studentResponses.add(mapToResponse(user));
        }
        return studentResponses;
    }
    public StudentResponse getStudentById(Long studentId){
        User user = userRepository.findById(studentId).get();
        return mapToResponse(user);
    }
    public StudentResponse updateStudent(Long id, StudentRequest request){
        User user = userRepository.findById(id).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setStudyFormation(StudyFormation.valueOf(request.getStudyFormationName()));
        Group group = groupRepository.findById(request.getGroupId()).get();
        user.setGroup(group);
        userRepository.save(user);
        return mapToResponse(user);
    }
    public void deleteStudent(Long studentId){
        userRepository.deleteById(studentId);
    }
    public List<User> getAllStudents(){
        return userRepository.getAllStudents();
    }

    public StudentResponse changeRole(Long userId, ChangeRoleRequest request){
        User user = userRepository.findById(userId).get();
        user.setRole(Role.valueOf(request.getRolName()));
        userRepository.save(user);
        return mapToResponse(user);
    }
    public StudentResponseView searchAndPagination(String text, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        StudentResponseView studentResponseView = new StudentResponseView();
        studentResponseView.setStudentResponses(view(search(text, pageable)));
        return studentResponseView;
    }
    public List<StudentResponse> view (List<User> users){
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (User user : users) {
            studentResponses.add(mapToResponse(user));
        }
        return studentResponses;
    }
    public List<User> search(String text, Pageable pageable){
        String name= text == null?"": text;
        return userRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
