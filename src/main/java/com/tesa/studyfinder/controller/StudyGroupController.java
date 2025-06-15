package com.tesa.studyfinder.controller;

import com.tesa.studyfinder.model.StudyGroup;
import com.tesa.studyfinder.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class StudyGroupController {
    private final StudyGroupService service;

    // Create
    @PostMapping("/create")
    public ResponseEntity<StudyGroup> createGroup(@RequestBody StudyGroup group) {
        return ResponseEntity.ok(service.createGroup(group));
    }

    // View all
    @GetMapping
    public ResponseEntity<List<StudyGroup>> viewAll() {
        return ResponseEntity.ok(service.viewAllGroups());
    }

    // View by course title
    @GetMapping("/by-course")
    public ResponseEntity<List<StudyGroup>> viewByCourse(@RequestParam String title) {
        return ResponseEntity.ok(service.viewGroupsByCourse(title));
    }

    // View my groups
    @GetMapping("/my")
    public ResponseEntity<List<StudyGroup>> viewMy(@RequestParam String userEmail) {
        return ResponseEntity.ok(service.viewGroupsByUser(userEmail));
    }

    // Join
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestParam String groupId, @RequestParam String userEmail) {
        return service.joinGroup(groupId, userEmail)
                ? ResponseEntity.ok("Joined")
                : ResponseEntity.badRequest().body("Cannot join");
    }

    // Leave
    @PostMapping("/leave")
    public ResponseEntity<String> leave(@RequestParam String groupId, @RequestParam String userEmail) {
        return service.leaveGroup(groupId, userEmail)
                ? ResponseEntity.ok("Left")
                : ResponseEntity.badRequest().body("Cannot leave");
    }
}
