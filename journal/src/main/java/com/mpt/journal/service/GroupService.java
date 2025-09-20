package com.mpt.journal.service;

import com.mpt.journal.model.GroupModel;
import java.util.List;

public interface GroupService {
    List<GroupModel> findAllGroups();
    GroupModel findGroupById(int id);
    GroupModel addGroup(GroupModel group);
    GroupModel updateGroup(GroupModel group);
    void deleteGroup(int id);
    void logicalDeleteGroup(int id);
    List<GroupModel> searchGroupsByName(String name);
}
