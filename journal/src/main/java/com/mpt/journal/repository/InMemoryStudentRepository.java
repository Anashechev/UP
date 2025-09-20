package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository

//Репозиторий отвечает за хранение и управление данными студентов в памяти. Он предоставляет методы для выполнения операций(обычные CRUD действия с данными)
public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1); // Генерация уникального ID

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement()); // Установка уникального ID
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                student.updateTimestamp();
                students.set(i, student);
                return student;
            }
        }
        return null; // Студент не найден
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public List<StudentModel> findAllStudents() {
        return students.stream()
                .filter(student -> !student.isDeleted())
                .collect(java.util.stream.Collectors.toList());
    }

    public StudentModel findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id && !student.isDeleted())
                .findFirst()
                .orElse(null);
    }

    public void logicalDeleteStudent(int id) {
        students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .ifPresent(student -> student.setDeleted(true));
    }

    public List<StudentModel> searchStudentsByName(String name) {
        return students.stream()
                .filter(student -> !student.isDeleted() && 
                        (student.getName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getLastName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getMiddleName().toLowerCase().contains(name.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<StudentModel> findAllStudentsWithDeleted() {
        return new ArrayList<>(students);
    }

    public List<StudentModel> filterStudentsByAge(int minAge, int maxAge) {
        return students.stream()
                .filter(student -> !student.isDeleted() && 
                        student.getAge() >= minAge && student.getAge() <= maxAge)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<StudentModel> filterStudentsByEmail(String emailDomain) {
        return students.stream()
                .filter(student -> !student.isDeleted() && 
                        student.getEmail() != null && 
                        student.getEmail().toLowerCase().contains(emailDomain.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<StudentModel> filterStudentsByPhone(String phonePrefix) {
        return students.stream()
                .filter(student -> !student.isDeleted() && 
                        student.getPhone() != null && 
                        student.getPhone().startsWith(phonePrefix))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<StudentModel> filterStudentsByMultipleCriteria(String name, Integer minAge, String emailDomain) {
        return students.stream()
                .filter(student -> !student.isDeleted() && 
                        (name == null || name.isEmpty() || 
                         student.getName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getLastName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                         student.getMiddleName().toLowerCase().contains(name.toLowerCase())) &&
                        (minAge == null || student.getAge() >= minAge) &&
                        (emailDomain == null || emailDomain.isEmpty() || 
                         (student.getEmail() != null && student.getEmail().toLowerCase().contains(emailDomain.toLowerCase()))))
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteMultipleStudents(List<Integer> ids) {
        students.removeIf(student -> ids.contains(student.getId()));
    }

    public void logicalDeleteMultipleStudents(List<Integer> ids) {
        students.stream()
                .filter(student -> ids.contains(student.getId()))
                .forEach(student -> student.setDeleted(true));
    }

    public List<StudentModel> findStudentsWithPagination(int page, int size) {
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, students.size());
        
        return students.stream()
                .filter(student -> !student.isDeleted())
                .skip(startIndex)
                .limit(size)
                .collect(java.util.stream.Collectors.toList());
    }

    public long getTotalStudentsCount() {
        return students.stream()
                .filter(student -> !student.isDeleted())
                .count();
    }
}
