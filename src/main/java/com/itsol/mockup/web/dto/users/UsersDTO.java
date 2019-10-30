package com.itsol.mockup.web.dto.users;

import com.itsol.mockup.entity.RoleEntity;
import com.itsol.mockup.entity.TeamEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class UsersDTO {
    private Long userId;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;
    private String phone;
    private String skypeName;
    private String facebook_url;
    private String address;
    private String education;
    private String university;
    private String faculty;
    private Date graduation_date;
    private Integer active;
    private String reason;
    private Integer imageId;
    private Integer levelId;

    private List<RoleEntity> roles = new ArrayList<>();

    private List<TeamEntity> teams = new ArrayList<>();

}
