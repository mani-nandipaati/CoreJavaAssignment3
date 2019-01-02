package com.cts.serialization.service;

import com.cts.serialization.dao.SubjectDao;
import com.cts.serialization.pojo.Subject;

public class SubjectService {
	
	SubjectDao subjectDao = new SubjectDao();
	
	public void addSubject(Subject subject) {
		subjectDao.addSubject(subject);
	}
	
	public void deleteSubject(long id) {
		subjectDao.deleteSubject(id);
	}

	public void searchSubject(long id) {
		subjectDao.searchSubject(id);
	}
	
	public void sortSubjectBySubjectTitle() {
		subjectDao.sortSubjectBySubjectTitle();
	}

}
