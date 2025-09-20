package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//Основная бизнес-логика нашего проекта
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        List<StudentModel> students = studentService.findStudentsWithPagination(page, size);
        long totalCount = studentService.getTotalStudentsCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", size);
        return "studentList";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false, defaultValue = "0") int age) {
        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName, email, phone, age);
        studentService.addStudent(newStudent);
        return "redirect:/students";
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false, defaultValue = "0") int age) {
        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName, email, phone, age);
        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id); // Ссылаемся на наш сервис для удаления по id
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/logical-delete")
    public String logicalDeleteStudent(@RequestParam int id) {
        studentService.logicalDeleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String name, Model model) {
        model.addAttribute("students", studentService.searchStudentsByName(name));
        model.addAttribute("searchQuery", name);
        return "studentList";
    }

    @GetMapping("/students/filter")
    public String filterStudents(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer minAge,
                                @RequestParam(required = false) String emailDomain,
                                Model model) {
        List<StudentModel> students = studentService.filterStudentsByMultipleCriteria(name, minAge, emailDomain);
        model.addAttribute("students", students);
        model.addAttribute("filterName", name);
        model.addAttribute("filterMinAge", minAge);
        model.addAttribute("filterEmailDomain", emailDomain);
        return "studentList";
    }

    @PostMapping("/students/delete-multiple")
    public String deleteMultipleStudents(@RequestParam String studentIds) {
        List<Integer> ids = Arrays.stream(studentIds.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.deleteMultipleStudents(ids);
        return "redirect:/students";
    }

    @PostMapping("/students/logical-delete-multiple")
    public String logicalDeleteMultipleStudents(@RequestParam String studentIds) {
        List<Integer> ids = Arrays.stream(studentIds.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.logicalDeleteMultipleStudents(ids);
        return "redirect:/students";
    }
}
