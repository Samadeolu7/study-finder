// src/main/java/com/yourorg/studyfinder/repo/StudyGroupRepository.java
package com.tesa.studyfinder.repo;

import com.tesa.studyfinder.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, String> {
    List<StudyGroup> findByCourseTitleIgnoreCase(String courseTitle);
}
