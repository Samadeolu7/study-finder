package com.tesa.studyfinder.service;

import com.tesa.studyfinder.model.StudyGroup;
import com.tesa.studyfinder.repo.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService {
    private final StudyGroupRepository repo;

    public StudyGroup createGroup(StudyGroup group) {
        return repo.save(group);
    }

    public List<StudyGroup> viewAllGroups() {
        return repo.findAll();
    }

    public List<StudyGroup> viewGroupsByCourse(String courseTitle) {
        return repo.findByCourseTitleIgnoreCase(courseTitle);
    }

    public List<StudyGroup> viewGroupsByUser(String userEmail) {
        return repo.findAll().stream()
                .filter(g -> g.getMembers().contains(userEmail))
                .toList();
    }

    public boolean joinGroup(String groupId, String userEmail) {
        return repo.findById(groupId).map(g -> {
            if (g.getMembers().size() >= g.getMaxSize()) return false;
            if (g.getMembers().contains(userEmail)) return false;
            g.getMembers().add(userEmail);
            repo.save(g);
            return true;
        }).orElse(false);
    }

    public boolean leaveGroup(String groupId, String userEmail) {
        return repo.findById(groupId).map(g -> {
            boolean removed = g.getMembers().remove(userEmail);
            if (removed) repo.save(g);
            return removed;
        }).orElse(false);
    }
}
