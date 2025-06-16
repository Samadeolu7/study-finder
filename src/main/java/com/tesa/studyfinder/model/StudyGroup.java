// src/main/java/com/yourorg/studyfinder/model/StudyGroup.java
package com.tesa.studyfinder.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class StudyGroup {
    @Id
    private String id;  // e.g. "COMP-202-01"

    private String courseTitle;
    private String time;
    private String location;

    @ElementCollection
    @CollectionTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "member_email")
    @Builder.Default
    private List<String> members = new ArrayList<>();

    private int maxSize = 6;

    // Audit fields
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}
