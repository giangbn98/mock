package com.itsol.mockup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ISSUE")
@Getter
@Setter
public class IssueEntity {
    @Id
    @Column(name = "ISSUE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISSUE_SEQ")
    @SequenceGenerator(name = "ISSUE_SEQ", sequenceName = "ISSUE_SEQ", allocationSize = 1)
    private Long issueId;

    @Column(name = "ISSUE_NAME")
    private String issueName;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name="CONTENT")
    private String content;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "PROJECT_ID")
    private ProjectEntity project;
}
