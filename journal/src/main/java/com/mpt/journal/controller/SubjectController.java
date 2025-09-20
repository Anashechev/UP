package com.mpt.journal.controller;

import com.mpt.journal.model.SubjectModel;
import com.mpt.journal.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAllSubjects());
        return "subjectList";
    }

    @PostMapping("/subjects/add")
    public String addSubject(@RequestParam String name,
                            @RequestParam String code,
                            @RequestParam int credits,
                            @RequestParam String description) {
        SubjectModel newSubject = new SubjectModel(0, name, code, credits, description);
        subjectService.addSubject(newSubject);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/update")
    public String updateSubject(@RequestParam int id,
                               @RequestParam String name,
                               @RequestParam String code,
                               @RequestParam int credits,
                               @RequestParam String description) {
        SubjectModel updatedSubject = new SubjectModel(id, name, code, credits, description);
        subjectService.updateSubject(updatedSubject);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/delete")
    public String deleteSubject(@RequestParam int id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/logical-delete")
    public String logicalDeleteSubject(@RequestParam int id) {
        subjectService.logicalDeleteSubject(id);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/search")
    public String searchSubjects(@RequestParam String name, Model model) {
        model.addAttribute("subjects", subjectService.searchSubjectsByName(name));
        model.addAttribute("searchQuery", name);
        return "subjectList";
    }
}
