package com.itsol.mockup.web.dto.project;

import com.itsol.mockup.entity.IssueEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProjectDTO {

    private Long projectId;

    private String projectName;

    private Date createdDate;

    private String createdBy;

    private Date deadline;

    private Date finishDate;

    private String description;

    private List<IssueEntity> issues = new ArrayList<>();

}
