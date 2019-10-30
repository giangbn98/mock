package com.itsol.mockup.repository;

import com.itsol.mockup.entity.PermissionEntity;
import com.itsol.mockup.web.dto.permisson.PermissionDTO;

import java.util.List;

public interface PermissionRepositoryCustom {
    PermissionEntity findPermissionByUserId(Long userId);

}
