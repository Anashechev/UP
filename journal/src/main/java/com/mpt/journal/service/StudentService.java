package com.mpt.journal.service;


import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;

import java.util.List;

public interface StudentService {
    public List<StudentModel> findAllStudent();
    public StudentModel findStudentById(int id);
    public StudentModel addStudent(StudentModel student);
    public StudentModel updateStudent(StudentModel student);
    public void deleteStudent(int id);
    public void logicalDeleteStudent(int id);
    public List<StudentModel> searchStudentsByName(String name);
    public List<StudentModel> findAllStudentsWithDeleted();
    public List<StudentModel> filterStudentsByAge(int minAge, int maxAge);
    public List<StudentModel> filterStudentsByEmail(String emailDomain);
    public List<StudentModel> filterStudentsByPhone(String phonePrefix);
    public List<StudentModel> filterStudentsByMultipleCriteria(String name, Integer minAge, String emailDomain);
    public void deleteMultipleStudents(List<Integer> ids);
    public void logicalDeleteMultipleStudents(List<Integer> ids);
    public List<StudentModel> findStudentsWithPagination(int page, int size);
    public long getTotalStudentsCount();
}
