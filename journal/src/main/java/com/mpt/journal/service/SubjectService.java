package com.mpt.journal.service;

import com.mpt.journal.model.SubjectModel;
import java.util.List;

public interface SubjectService {
    List<SubjectModel> findAllSubjects();
    SubjectModel findSubjectById(int id);
    SubjectModel addSubject(SubjectModel subject);
    SubjectModel updateSubject(SubjectModel subject);
    void deleteSubject(int id);
    void logicalDeleteSubject(int id);
    List<SubjectModel> searchSubjectsByName(String name);
}
