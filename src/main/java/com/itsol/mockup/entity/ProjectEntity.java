package com.itsol.mockup.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROJECT")
@Getter
@Setter
public class ProjectEntity {
    @Id
    @Column(name = "PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize = 1)
    private Long projectId;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DEADLINE")
    private Date deadline;

    @Column(name = "FINISH_DATE")
    private Date finishDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "project")
    private List<IssueEntity> issues = new ArrayList<>();

}
