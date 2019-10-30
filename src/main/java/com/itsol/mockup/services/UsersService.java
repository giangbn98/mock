package com.itsol.mockup.services;

import com.itsol.mockup.entity.UsersEntity;
import com.itsol.mockup.web.dto.request.IdRequestDTO;
import com.itsol.mockup.web.dto.request.SearchUsersRequestDTO;
import com.itsol.mockup.web.dto.request.auth.AuthRequestDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.auth.AuthResponseDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;

/**
 * @author anhvd_itsol
 */

public interface UsersService {

    BaseResultDTO findAllUsers(SearchUsersRequestDTO requestDTO);
    BaseResultDTO findUsersByFullNameAndUserName(SearchUsersRequestDTO requestDTO);
    BaseResultDTO addUser(UsersDTO usersDTO);
    BaseResultDTO updateUser(UsersDTO usersDTO);
    BaseResultDTO findUserEntityByUserName(String token);

    BaseResultDTO findAllUsersNotListId(IdRequestDTO requestDTO);

//    @PreAuthorize("hasAuthority('MANAGER')")
    BaseResultDTO findAll();


    // ====== START SERVICES FOR AUTHENTICATION ======
    AuthResponseDTO generateToken(AuthRequestDTO userForAuthentication);

    // ====== END ======

    UsersDTO findUserByEmail(String email);


}
