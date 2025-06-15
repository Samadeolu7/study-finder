package com.tesa.studyfinder.service;

import com.tesa.studyfinder.model.StudyGroup;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {
    // In‑memory store of all groups
    private final Map<String, StudyGroup> groups = new HashMap<>();
    // Map of userEmail → set of group IDs they belong to
    private final Map<String, Set<String>> userGroups = new HashMap<>();

    public StudyGroup createGroup(StudyGroup group) {
        groups.put(group.getId(), group);
        return group;
    }

    public List<StudyGroup> viewAllGroups() {
        return new ArrayList<>(groups.values());
    }

    public List<StudyGroup> viewGroupsByCourse(String courseTitle) {
        return groups.values().stream()
                .filter(g -> g.getCourseTitle().equalsIgnoreCase(courseTitle))
                .collect(Collectors.toList());
    }

    public List<StudyGroup> viewGroupsByUser(String userEmail) {
        return userGroups.getOrDefault(userEmail, Collections.emptySet())
                .stream()
                .map(groups::get)
                .collect(Collectors.toList());
    }

    public boolean joinGroup(String groupId, String userEmail) {
        StudyGroup group = groups.get(groupId);
        if (group == null || group.getMembers().size() >= group.getMaxSize()) {
            return false;
        }
        if (group.getMembers().add(userEmail)) {
            userGroups.computeIfAbsent(userEmail, k -> new HashSet<>()).add(groupId);
            return true;
        }
        return false;
    }

    public boolean leaveGroup(String groupId, String userEmail) {
        StudyGroup group = groups.get(groupId);
        if (group == null) {
            return false;
        }
        if (group.getMembers().remove(userEmail)) {
            Set<String> set = userGroups.get(userEmail);
            if (set != null) set.remove(groupId);
            return true;
        }
        return false;
    }
}
