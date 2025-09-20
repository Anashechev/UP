package com.mpt.journal.service;

import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


//Сервисный слой отвечает за бизнес-логику приложения. Он использует репозиторий для выполнения операций с данными и может включать дополнительные проверки или преобразования данных
//так же мы тут можем настроить инкапсуляцию
//А если простыми словами тут происходит разделенние запросов от контроллера к сервису
@Service
public class InMemoryStudentServiceImpl implements StudentService {

    private final InMemoryStudentRepository studentRepository;

    public InMemoryStudentServiceImpl(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent() {
        return studentRepository.findAllStudents();
    }

    @Override
    public StudentModel findStudentById(int id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        return studentRepository.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    @Override
    public void logicalDeleteStudent(int id) {
        studentRepository.logicalDeleteStudent(id);
    }

    @Override
    public List<StudentModel> searchStudentsByName(String name) {
        return studentRepository.searchStudentsByName(name);
    }

    @Override
    public List<StudentModel> findAllStudentsWithDeleted() {
        return studentRepository.findAllStudentsWithDeleted();
    }

    @Override
    public List<StudentModel> filterStudentsByAge(int minAge, int maxAge) {
        return studentRepository.filterStudentsByAge(minAge, maxAge);
    }

    @Override
    public List<StudentModel> filterStudentsByEmail(String emailDomain) {
        return studentRepository.filterStudentsByEmail(emailDomain);
    }

    @Override
    public List<StudentModel> filterStudentsByPhone(String phonePrefix) {
        return studentRepository.filterStudentsByPhone(phonePrefix);
    }

    @Override
    public List<StudentModel> filterStudentsByMultipleCriteria(String name, Integer minAge, String emailDomain) {
        return studentRepository.filterStudentsByMultipleCriteria(name, minAge, emailDomain);
    }

    @Override
    public void deleteMultipleStudents(List<Integer> ids) {
        studentRepository.deleteMultipleStudents(ids);
    }

    @Override
    public void logicalDeleteMultipleStudents(List<Integer> ids) {
        studentRepository.logicalDeleteMultipleStudents(ids);
    }

    @Override
    public List<StudentModel> findStudentsWithPagination(int page, int size) {
        return studentRepository.findStudentsWithPagination(page, size);
    }

    @Override
    public long getTotalStudentsCount() {
        return studentRepository.getTotalStudentsCount();
    }
}
