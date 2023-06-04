package peaksoft.springrestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrestproject.dto.ChangeRoleRequest;
import peaksoft.springrestproject.dto.StudentResponse;
import peaksoft.springrestproject.dto.TeacherRequest;
import peaksoft.springrestproject.dto.TeacherResponse;
import peaksoft.springrestproject.service.TeacherService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Teacher Auth", description = "We can create new Teacher")
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    @PostMapping
    @Operation(summary = "Create", description = "Admin can create new Teacher")
    public TeacherResponse create(@RequestBody TeacherRequest request){
        return teacherService.create(request);
    }
    @GetMapping("all")
    @Operation(summary = "Get all teachers", description = "Only Admin get all Teachers")
    public List<TeacherResponse> getAll(){
        return teacherService.getAll();
    }
    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin can get Teacher by id")
    public TeacherResponse getTeacher(@PathVariable("id")Long id){
        return teacherService.getTeacherById(id);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update", description = "Admin can update Teacher")
    public TeacherResponse update(@PathVariable("id")Long id, @RequestBody TeacherRequest request){
        return teacherService.updateTeacher(id, request);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete", description = "Admin can delete Teacher by id")
    public String delete(@PathVariable("id")Long id){
        teacherService.deleteTeacher(id);
        return "Successfully deleted Teacher with id: "+id;
    }
    @PutMapping("change-rol/{id}")
    public TeacherResponse changeRole(@PathVariable("id")Long id, @RequestBody ChangeRoleRequest request){
        return teacherService.changeRole(id, request);
    }
}
