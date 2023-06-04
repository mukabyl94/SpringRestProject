package peaksoft.springrestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrestproject.dto.GroupRequest;
import peaksoft.springrestproject.dto.GroupResponse;
import peaksoft.springrestproject.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Group Auth", description = "We can create new Group")
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "Create", description = "Admin can create new Group")
    public GroupResponse create(@RequestBody GroupRequest request){
        return groupService.create(request);
    }
    @GetMapping("all")
    @Operation(summary = "Get all groups", description = "Only Admin get all Groups")
    public List<GroupResponse> getAll(){
        return groupService.getAllGroups();
    }
    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin can get Group by id")
    public GroupResponse getGroup(@PathVariable("id")Long id){
        return groupService.getGroupById(id);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update", description = "Admin can update Group")
    public GroupResponse update(@PathVariable("id")Long id, @RequestBody GroupRequest request){
        return groupService.updateGroup(id, request);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete", description = "Admin can delete Group by id")
    public String delete(@PathVariable("id")Long id){
        groupService.deleteGroup(id);
        return "Successfully deleted Group with id: "+id;
    }
}
