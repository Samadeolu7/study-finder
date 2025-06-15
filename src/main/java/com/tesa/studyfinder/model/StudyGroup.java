package com.tesa.studyfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroup {
    private String id;          // e.g. "COMP-202-01"
    private String courseTitle; // e.g. "React Fundamentals"
    private String time;        // e.g. "Thursdays 3-5 PM"
    private String location;    // e.g. "Library Room B"
    private List<String> members = new ArrayList<>();
    private int maxSize = 6;
}
