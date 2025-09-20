package com.mpt.journal.controller;

import com.mpt.journal.model.GroupModel;
import com.mpt.journal.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.findAllGroups());
        return "groupList";
    }

    @PostMapping("/groups/add")
    public String addGroup(@RequestParam String name,
                          @RequestParam String description) {
        GroupModel newGroup = new GroupModel(0, name, description);
        groupService.addGroup(newGroup);
        return "redirect:/groups";
    }

    @PostMapping("/groups/update")
    public String updateGroup(@RequestParam int id,
                             @RequestParam String name,
                             @RequestParam String description) {
        GroupModel updatedGroup = new GroupModel(id, name, description);
        groupService.updateGroup(updatedGroup);
        return "redirect:/groups";
    }

    @PostMapping("/groups/delete")
    public String deleteGroup(@RequestParam int id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }

    @PostMapping("/groups/logical-delete")
    public String logicalDeleteGroup(@RequestParam int id) {
        groupService.logicalDeleteGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/groups/search")
    public String searchGroups(@RequestParam String name, Model model) {
        model.addAttribute("groups", groupService.searchGroupsByName(name));
        model.addAttribute("searchQuery", name);
        return "groupList";
    }
}
