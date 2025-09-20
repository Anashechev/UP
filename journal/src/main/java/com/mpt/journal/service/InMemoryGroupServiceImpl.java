package com.mpt.journal.service;

import com.mpt.journal.model.GroupModel;
import com.mpt.journal.repository.InMemoryGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryGroupServiceImpl implements GroupService {

    private final InMemoryGroupRepository groupRepository;

    public InMemoryGroupServiceImpl(InMemoryGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupModel> findAllGroups() {
        return groupRepository.findAllGroups();
    }

    @Override
    public GroupModel findGroupById(int id) {
        return groupRepository.findGroupById(id);
    }

    @Override
    public GroupModel addGroup(GroupModel group) {
        return groupRepository.addGroup(group);
    }

    @Override
    public GroupModel updateGroup(GroupModel group) {
        return groupRepository.updateGroup(group);
    }

    @Override
    public void deleteGroup(int id) {
        groupRepository.deleteGroup(id);
    }

    @Override
    public void logicalDeleteGroup(int id) {
        groupRepository.logicalDeleteGroup(id);
    }

    @Override
    public List<GroupModel> searchGroupsByName(String name) {
        return groupRepository.searchGroupsByName(name);
    }
}
