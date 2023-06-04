package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.GroupRequest;
import peaksoft.springrestproject.dto.GroupResponse;
import peaksoft.springrestproject.entity.Group;
import peaksoft.springrestproject.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    public GroupResponse create(GroupRequest request){
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setDateOfStart(request.getDateOfStart());
        group.setDateOfFinish(request.getDateOfFinish());
        groupRepository.save(group);
        return mapToResponse(group);
    }
    public GroupResponse mapToResponse(Group group){
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupName(group.getGroupName());
        groupResponse.setDateOfStart(group.getDateOfStart());
        groupResponse.setDateOfFinish(group.getDateOfFinish());
        return groupResponse;
    }
    public List<GroupResponse> getAllGroups(){
        List<GroupResponse> groupResponses = new ArrayList<>();
        for (Group group : groupRepository.findAll()){
            groupResponses.add(mapToResponse(group));
        }
        return groupResponses;
    }
    public GroupResponse getGroupById(Long groupId){
        Group group = groupRepository.findById(groupId).get();
        return mapToResponse(group);
    }
    public GroupResponse updateGroup(Long id, GroupRequest request){
        Group group = groupRepository.findById(id).get();
        group.setGroupName(request.getGroupName());
        group.setDateOfStart(request.getDateOfStart());
        group.setDateOfFinish(request.getDateOfFinish());
        groupRepository.save(group);
        return mapToResponse(group);
    }
    public void deleteGroup(Long groupId){
        groupRepository.deleteById(groupId);
    }
}
