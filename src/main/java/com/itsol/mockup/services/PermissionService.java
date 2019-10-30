package com.itsol.mockup.services;

import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.permisson.PermissionDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.auth.AuthResponseDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PermissionService {
    BaseResultDTO findAllPermission(BaseDTO request);
    BaseResultDTO addPermission(PermissionDTO permissionDTO);
    BaseResultDTO updatePermission(PermissionDTO permissionDTO);
    BaseResultDTO deletePermission(Long id);
    BaseResultDTO findPermissionByUserName(String userName);

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    BaseResultDTO findPermissionByStatus();


    BaseResultDTO findAllPermissionByStatus(Integer status,Integer page,Integer pageSize);
}
