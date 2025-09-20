package com.mpt.journal.repository;

import com.mpt.journal.model.GroupModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryGroupRepository {
    private List<GroupModel> groups = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public GroupModel addGroup(GroupModel group) {
        group.setId(idCounter.getAndIncrement());
        groups.add(group);
        return group;
    }

    public GroupModel updateGroup(GroupModel group) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == group.getId()) {
                group.updateTimestamp();
                groups.set(i, group);
                return group;
            }
        }
        return null;
    }

    public void deleteGroup(int id) {
        groups.removeIf(group -> group.getId() == id);
    }

    public List<GroupModel> findAllGroups() {
        return groups.stream()
                .filter(group -> !group.isDeleted())
                .collect(java.util.stream.Collectors.toList());
    }

    public GroupModel findGroupById(int id) {
        return groups.stream()
                .filter(group -> group.getId() == id && !group.isDeleted())
                .findFirst()
                .orElse(null);
    }

    public void logicalDeleteGroup(int id) {
        groups.stream()
                .filter(group -> group.getId() == id)
                .findFirst()
                .ifPresent(group -> group.setDeleted(true));
    }

    public List<GroupModel> searchGroupsByName(String name) {
        return groups.stream()
                .filter(group -> !group.isDeleted() && 
                        group.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }
}
