package com.itsol.mockup.services;

import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.project.ProjectDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;

public interface ProjectService {
    BaseResultDTO findAll(Integer pageSize, Integer page);
    BaseResultDTO addProject(ProjectDTO projectDTO);
    BaseResultDTO updateProject(ProjectDTO projectDTO);
    BaseResultDTO deleteProject(Long id);
    BaseResultDTO findProjectById(Long id);
    BaseResultDTO findProjectAndTeamLeadByProjectId(Long projectId);
    BaseResultDTO getTotalMember(Long projectId);


}
