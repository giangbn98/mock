package com.itsol.mockup.web.dto.permisson;

import com.itsol.mockup.entity.UsersEntity;
import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class PermissionDTO extends BaseDTO {
    private Long permissionId;

    private Date absenceDate;

    private String reason;

    private Integer status;

    private UsersDTO user;

}
