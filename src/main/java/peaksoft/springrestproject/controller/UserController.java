package peaksoft.springrestproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrestproject.dto.ChangeRoleRequest;
import peaksoft.springrestproject.dto.UserRequest;
import peaksoft.springrestproject.dto.UserResponse;
import peaksoft.springrestproject.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping()
    public List<UserResponse> getAll(){
        return userService.getAll();
    }
    @GetMapping("{id}")
    public UserResponse getById(@PathVariable("id") Long userId){
        return userService.getUserById(userId);
    }
    @PostMapping
    public UserResponse create(@RequestBody UserRequest request){
        return userService.create(request);
    }
    @PutMapping("{id}")
    public UserResponse update(@PathVariable("id")Long userId,@RequestBody UserRequest request){
        return userService.update(userId,request);
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id")Long userId){
        return userService.delete(userId);
    }
    @PutMapping("change-role/{id}")
    public UserResponse changeRole(@PathVariable("id") Long id,@RequestBody ChangeRoleRequest request){
        return userService.changeRole(id,request);
    }

}
