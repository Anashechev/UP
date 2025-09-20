package com.mpt.journal.repository;

import com.mpt.journal.model.SubjectModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemorySubjectRepository {
    private List<SubjectModel> subjects = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public SubjectModel addSubject(SubjectModel subject) {
        subject.setId(idCounter.getAndIncrement());
        subjects.add(subject);
        return subject;
    }

    public SubjectModel updateSubject(SubjectModel subject) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getId() == subject.getId()) {
                subject.updateTimestamp();
                subjects.set(i, subject);
                return subject;
            }
        }
        return null;
    }

    public void deleteSubject(int id) {
        subjects.removeIf(subject -> subject.getId() == id);
    }

    public List<SubjectModel> findAllSubjects() {
        return subjects.stream()
                .filter(subject -> !subject.isDeleted())
                .collect(java.util.stream.Collectors.toList());
    }

    public SubjectModel findSubjectById(int id) {
        return subjects.stream()
                .filter(subject -> subject.getId() == id && !subject.isDeleted())
                .findFirst()
                .orElse(null);
    }

    public void logicalDeleteSubject(int id) {
        subjects.stream()
                .filter(subject -> subject.getId() == id)
                .findFirst()
                .ifPresent(subject -> subject.setDeleted(true));
    }

    public List<SubjectModel> searchSubjectsByName(String name) {
        return subjects.stream()
                .filter(subject -> !subject.isDeleted() && 
                        (subject.getName().toLowerCase().contains(name.toLowerCase()) ||
                         subject.getCode().toLowerCase().contains(name.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
    }
}
