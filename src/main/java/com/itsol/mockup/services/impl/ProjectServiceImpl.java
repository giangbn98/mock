package com.itsol.mockup.services.impl;

import com.itsol.mockup.entity.ProjectEntity;
import com.itsol.mockup.repository.ProjectRepository;
import com.itsol.mockup.repository.ProjectRepositoryCustom;
import com.itsol.mockup.services.ProjectService;
import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.project.ProjectDTO;
import com.itsol.mockup.web.dto.request.ReportDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.SingleResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRepositoryCustom projectRepositoryCustom;

    @Override
    public ArrayResultDTO<ProjectEntity> findAll(Integer pageSize, Integer page) {
       logger.info("=== START FIND ALL PROJECT");
       ArrayResultDTO arrayResultDTO = new ArrayResultDTO();
        List<ProjectDTO> lists = new ArrayList<>();
        try{
            Page<ProjectEntity> datas = projectRepository.findAll(PageRequest.of(page - 1, pageSize));
            if(datas != null) {
                if (datas.getContent().size() >0){
                    for (ProjectEntity projectEntity : datas.getContent()){
                        ProjectDTO projectDTO = modelMapper.map(projectEntity, ProjectDTO.class);
                        lists.add(projectDTO);
                    }
                    arrayResultDTO.setSuccess(lists,datas.getTotalElements(),datas.getTotalPages());
                    logger.info("=== FIND ALL PROJECT WITH RESPONSE: "+arrayResultDTO.getErrorCode());
                }
            }
        }catch (Exception e){
            arrayResultDTO.setFail("Fail");
            logger.error(e.getMessage());
        }
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO addProject(ProjectDTO projectDTO) {
        logger.info("===START ADD NEW PROJECT");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try{
            ProjectEntity projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);
            projectEntity.setCreatedDate(getCurTimestamp());
            projectEntity = projectRepository.save(projectEntity);
            if(projectEntity.getProjectId() != null){
                singleResultDTO.setSuccess();
            }else{
                singleResultDTO.setFail("Fail");
            }
            logger.info("=== ADD NEW PROJECT RESPONSE: "+ singleResultDTO.getErrorCode());
        }catch (Exception e){
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage());
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO updateProject(ProjectDTO projectDTO) {
        logger.info("=== START UPDATE PROJECT: ");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try{
            ProjectEntity projectEntity = projectRepository.getProjectEntityByProjectId(projectDTO.getProjectId());
            if(projectEntity.getProjectId() != null){
                projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);
                projectEntity = projectRepository.save(projectEntity);
                singleResultDTO.setSuccess();
            }else {
                singleResultDTO.setFail("Fail");
            }
            logger.info("=== UPDATE PROJECT RESPONSE: "+ singleResultDTO.getErrorCode());
        }catch (Exception e) {
                singleResultDTO.setFail(e.getMessage());
                logger.error(e.getMessage());
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO deleteProject(Long id) {
        logger.info("=== START DELETE PROJECT");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            if (id != null) {
                projectRepository.deleteById(id);
                singleResultDTO.setSuccess();
            } else {
                singleResultDTO.setFail("Fail");
            }
            logger.info("=== DELETE PROJECT RESPONE: "+ singleResultDTO.getErrorCode());
        }catch (Exception e){
            singleResultDTO.setFail(e.getMessage());
            logger.info(e.getMessage());
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO findProjectById(Long id) {
        logger.info("START FIND PROJECT BY ID");
        SingleResultDTO<ProjectEntity> resultDTO = new SingleResultDTO<>();
        try {
            ProjectEntity projectEntity = projectRepository.getProjectEntityByProjectId(id);
            if (projectEntity != null){
                resultDTO.setSuccess(projectEntity);
            }
            logger.info(" FIND PROJECT BY ID RESPONSE: "+ resultDTO.getErrorCode());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            resultDTO.setFail(e.getMessage());
        }
        return resultDTO;
    }

    @Override
    public BaseResultDTO findProjectAndTeamLeadByProjectId(Long projectId) {
        logger.info("START FIND PROJECT AND TEAMLEAD BY PROJECT ID: ");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            List list = projectRepositoryCustom.findProjectNameAndTeamLead(projectId);
            if (list != null){
                singleResultDTO.setSuccess(list.get(0));
            }
            logger.info("FIND PROJECT AND TEAMLEAD BY PROJECT ID RESPONSE::"+ singleResultDTO.getErrorCode());
        }catch (Exception e){
            singleResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO getTotalMember(Long projectId) {
        logger.info("START FIND TOTAL MEMBER BY PROJECT ID");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            ReportDTO reportDTO = projectRepositoryCustom.getTotalMember(projectId);
            if (reportDTO != null){
                singleResultDTO.setSuccess(reportDTO);
            }
            logger.info("FIND TOTAL MEMBER BY PROJECT ID RESPONSE::"+ singleResultDTO.getErrorCode());
        }catch (Exception e){
            singleResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }
        return singleResultDTO;
    }
}
