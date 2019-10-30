package com.itsol.mockup.services.impl;

import com.itsol.mockup.entity.PermissionEntity;
import com.itsol.mockup.entity.UsersEntity;
import com.itsol.mockup.repository.PermissionRepository;
import com.itsol.mockup.repository.PermissionRepositoryCustom;
import com.itsol.mockup.repository.UsersRepository;
import com.itsol.mockup.services.PermissionService;
import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.permisson.PermissionDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.SingleResultDTO;
import com.itsol.mockup.web.dto.response.auth.AuthResponseDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends BaseService implements PermissionService {


    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PermissionRepositoryCustom permissionRepositoryCustom;



    @Override
    public BaseResultDTO findAllPermission(BaseDTO request) {
        logger.info("=== START FIND ALL PERMISSION::");
        ArrayResultDTO arrayResultDTO = new ArrayResultDTO<>();
        List<PermissionDTO> listPermission = new ArrayList<>();
        try{
            Page<PermissionEntity> datas = permissionRepository.findAll(PageRequest.of(request.getPage(), request.getPageSize()));
            if (datas != null) {
                if(datas.getContent().size() > 0){
                    for (PermissionEntity data: datas){
                        PermissionDTO permissionDTO = modelMapper.map(data,PermissionDTO.class);
                        listPermission.add(permissionDTO);
                    }
                }
                arrayResultDTO.setSuccess(listPermission,datas.getTotalElements(),datas.getTotalPages());
                logger.info("FIND ALL PERMISSION WITH RESULT"+ arrayResultDTO.getErrorCode());
            }
        }catch (Exception ex){
            arrayResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);

        }
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO addPermission(PermissionDTO permissionDTO) {
        logger.info("START ADD NEW PERMISSION");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try{
            PermissionEntity permissionEntity = modelMapper.map(permissionDTO, PermissionEntity.class);
            permissionEntity.setAbsenceDate(new Timestamp(System.currentTimeMillis()));
            permissionEntity = permissionRepository.save(permissionEntity);
            if(permissionEntity.getPermissionId() != null) {
                singleResultDTO.setSuccess(permissionEntity);
            }
            logger.info("ADD NEW PERMISSION RESPONSE:: "+singleResultDTO.getErrorCode());
        }catch (Exception ex) {
            singleResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO updatePermission(PermissionDTO permissionDTO) {
        logger.info("START UPDATE PERMISSION");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try{
            PermissionEntity permissionEntity = permissionRepository.getPermissionEntityByPermissionId(permissionDTO.getPermissionId());
            if(permissionEntity.getPermissionId() != null){
                /*permissionEntity.setAbsenceDate(permissionDTO.getAbsenceDate());
                permissionEntity.setReason(permissionDTO.getReason());
                permissionEntity.setStatus(permissionDTO.getStatus());*/
                permissionEntity = modelMapper.map(permissionDTO, PermissionEntity.class);
                permissionEntity = permissionRepository.save(permissionEntity);
                singleResultDTO.setSuccess(permissionEntity);
            }
            logger.info("UPDATE PERMISSION RESPONSE:: "+singleResultDTO.getErrorCode());
        }catch (Exception ex) {
            singleResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO deletePermission(Long id) {
        logger.info("START DELTE PERMISSION");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try{
            if (id != null){
                permissionRepository.deleteById(id);
                singleResultDTO.setSuccess();
            }
            logger.info("DELETE PERMISSION RESPONSE:: "+singleResultDTO.getErrorCode());
        }catch (Exception ex){
            singleResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO findPermissionByUserName(String userName) {
        logger.info("START FIND PERMISSION BY USERNAME:: ");
        ArrayResultDTO<PermissionEntity> arrayResultDTO = new ArrayResultDTO();
        List<PermissionDTO> listDTO = new ArrayList<>();
        try{
            List<PermissionEntity> lists = permissionRepository.getPermissionEntityByUser_UserName(userName);
            arrayResultDTO.setSuccess(lists);
        }catch (Exception e){
            arrayResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO findPermissionByStatus() {
        logger.info("START FIND PERMISSION BY STATUS:  ");
        ArrayResultDTO arrayResultDTO = new ArrayResultDTO();
        List<PermissionDTO> listPermission = new ArrayList<>();
        try{
            List<PermissionEntity> lists = permissionRepository.getPermissionEntityByStatus(0);
            if (lists != null) {
                if(lists.size() > 0){
                    for (PermissionEntity data: lists){
                        PermissionDTO permissionDTO = modelMapper.map(data,PermissionDTO.class);
                        listPermission.add(permissionDTO);
                    }
                }
                arrayResultDTO.setSuccess(listPermission,1L,2);
                logger.info("FIND ALL PERMISSION BY STATUS WITH RESULT"+ arrayResultDTO.getErrorCode());
            }
        }catch (Exception e){
            arrayResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO findAllPermissionByStatus(Integer status,Integer page,Integer pageSize) {
        logger.info("START FIND ALL PERMISSION BY STATUS::");
        ArrayResultDTO arrayResultDTO = new ArrayResultDTO();
        List<PermissionDTO> list = new ArrayList<>();
        try {
            Page<PermissionEntity> data = permissionRepository.findPermissionEntitiesByStatus(status, PageRequest.of(page, pageSize));
            if (data != null) {
                for (PermissionEntity permissionEntity : data.getContent()) {
                    PermissionDTO permissionDTO = modelMapper.map(permissionEntity, PermissionDTO.class);
                    list.add(permissionDTO);
                }
                arrayResultDTO.setSuccess(list, data.getTotalElements(), data.getTotalPages());
                logger.info("FIND ALL PERMISSION BY STATUS RESPONSE::" + arrayResultDTO.getErrorCode());
            }
        }catch (Exception e){
            arrayResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }
        return arrayResultDTO;
    }
}
