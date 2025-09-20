package com.mpt.journal.service;

import com.mpt.journal.model.SubjectModel;
import com.mpt.journal.repository.InMemorySubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemorySubjectServiceImpl implements SubjectService {

    private final InMemorySubjectRepository subjectRepository;

    public InMemorySubjectServiceImpl(InMemorySubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectModel> findAllSubjects() {
        return subjectRepository.findAllSubjects();
    }

    @Override
    public SubjectModel findSubjectById(int id) {
        return subjectRepository.findSubjectById(id);
    }

    @Override
    public SubjectModel addSubject(SubjectModel subject) {
        return subjectRepository.addSubject(subject);
    }

    @Override
    public SubjectModel updateSubject(SubjectModel subject) {
        return subjectRepository.updateSubject(subject);
    }

    @Override
    public void deleteSubject(int id) {
        subjectRepository.deleteSubject(id);
    }

    @Override
    public void logicalDeleteSubject(int id) {
        subjectRepository.logicalDeleteSubject(id);
    }

    @Override
    public List<SubjectModel> searchSubjectsByName(String name) {
        return subjectRepository.searchSubjectsByName(name);
    }
}
