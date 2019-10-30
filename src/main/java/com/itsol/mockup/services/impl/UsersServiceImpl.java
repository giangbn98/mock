package com.itsol.mockup.services.impl;

import com.itsol.mockup.entity.UsersEntity;
import com.itsol.mockup.repository.UsersRepository;
import com.itsol.mockup.repository.UsersRepositoryCustom;
import com.itsol.mockup.services.EmailService;
import com.itsol.mockup.services.UsersService;
import com.itsol.mockup.utils.Constants;
import com.itsol.mockup.utils.TokenUtils;
import com.itsol.mockup.web.dto.request.IdRequestDTO;
import com.itsol.mockup.web.dto.request.SearchUsersRequestDTO;
import com.itsol.mockup.web.dto.request.auth.AuthRequestDTO;
import com.itsol.mockup.web.dto.response.*;
import com.itsol.mockup.web.dto.response.auth.AuthResponseDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsersServiceImpl extends BaseService implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersRepositoryCustom usersRepositoryCustom;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenUtils tokenUtils;


    @Override
    public BaseResultDTO findAllUsers(SearchUsersRequestDTO request) {
        logger.info("=== START FIND ALL USERS::");
        ArrayResultDTO<UsersDTO> responseResultDTO = new ArrayResultDTO<>();
        List<UsersDTO> lstUsers = new ArrayList<>();
        try {
            Page<UsersEntity> rawDatas = usersRepository.findAll(PageRequest.of(request.getPage(), request.getPageSize()));
            if (rawDatas != null) {
                if (rawDatas.getContent().size() > 0) {
                    rawDatas.getContent().forEach(user -> {
                        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
                        lstUsers.add(usersDTO);
                    });
                }
                responseResultDTO.setSuccess(lstUsers, rawDatas.getTotalElements(), rawDatas.getTotalPages());
                logger.info("=== FIND ALL USERS RESPONSE::" + responseResultDTO.getErrorCode());
            }
        } catch (Exception ex) {
            responseResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return responseResultDTO;
    }

    @Override
    public BaseResultDTO findUsersByFullNameAndUserName(SearchUsersRequestDTO requestDTO) {
        logger.info("=== START FIND ALL USERS BY FULL_NAME AND USER_NAME::");
        SingleResultDTO<UsersDTO> respoonseSingleResultDTO = new SingleResultDTO<>();
        try {
            Page<UsersDTO> rawDatas = usersRepositoryCustom.findUsersByFullNameAndUserName(requestDTO);
            if (rawDatas.getContent().size() > 0) {
                respoonseSingleResultDTO.setSuccess((UsersDTO) rawDatas.getContent());
            }
            logger.info("=== FIND ALL USERS BY FULL_NAME AND USER_NAME RESPONSE::" + respoonseSingleResultDTO.getErrorCode());
        } catch (Exception ex) {
            respoonseSingleResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return respoonseSingleResultDTO;
    }


    @Override
    public BaseResultDTO findAllUsersNotListId(IdRequestDTO requestDTO) {
        logger.info("=== SRART FIND ALL USER NOT LIST ID");
        ArrayResultDTO<UsersDTO> arrayResultDTO = new ArrayResultDTO<>();
        try {
            Page<UsersDTO> rawDatas = usersRepositoryCustom.findUserNotRequest(requestDTO);
//            if (rawDatas.getContent().size() > 0){
//                if (rawDatas.getContent().size() > 0){
//                    rawDatas.getContent().forEach(user -> {
//                        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
//                        lstUser.add(usersDTO);
//                    });
//                }
            arrayResultDTO.setSuccess(rawDatas.getContent(), rawDatas.getTotalElements(), rawDatas.getTotalPages());
//            }
            logger.info("=== FIND ALL USERS NOT LIST USER_ID: " + arrayResultDTO.getErrorCode());

        } catch (Exception e) {
            arrayResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO addUser(UsersDTO requestDTO) {
        logger.info("=== START ADD NEW USER::");
        BaseResultDTO responseResultDTO = new BaseResultDTO();
//        String generatedString = RandomStringUtils.randomAlphabetic(10);
        try {
            UsersEntity usersEntity = usersRepository.findUsersEntityByUserName(requestDTO.getUserName());
            if (usersEntity != null) {
                responseResultDTO.setFail("người dùng này đã tồn tại !!!!");
                logger.info("=== ADD NEW USER STOP RESPONES: " + responseResultDTO.getErrorCode());
                throw new BadCredentialsException("người dùng này đã tồn tại !!!!");
            }
//            String rdPassWord = RandomStringUtils.randomAlphabetic(10);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            requestDTO.setPassWord(passwordEncoder.encode(requestDTO.getPassWord()));
            UsersEntity user = modelMapper.map(requestDTO, UsersEntity.class);
            user = usersRepository.save(user);
            if (user != null) {
                logger.info("new user" + user.getUserId());
                responseResultDTO.setSuccess();
            }
            logger.info("=== ADD NEW USER RESPONSE::" + responseResultDTO.getErrorCode());
        } catch (Exception ex) {
            responseResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return responseResultDTO;
    }

    @Override
    public BaseResultDTO updateUser(UsersDTO usersDTO) {
        logger.info("=== START UPDATE USER::" + usersDTO.getUserId());
        BaseResultDTO baseResultDTO = new BaseResultDTO();
        try {
            UsersEntity user = usersRepository.getUsersEntitiesByUserId(usersDTO.getUserId());
            if (user.getUserId() != null) {
                user.setUserName(usersDTO.getUserName());
                user.setFullName(usersDTO.getFullName());
                user.setPassWord(usersDTO.getPassWord());
                usersRepository.save(user);
                baseResultDTO.setSuccess();
            }
            logger.info("=== UPDATE USER RESPONSE::" + baseResultDTO.getErrorCode());
        } catch (Exception ex) {
            baseResultDTO.setFail(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return baseResultDTO;
    }

    @Override
    public BaseResultDTO findUserEntityByUserName(String token) {
        logger.info("=== START SEARCH USER NAME");
        SingleResultDTO<UsersEntity> resultDTO = new SingleResultDTO<>();
        try {
            UsersEntity user = usersRepository.findUsersEntityByUserName(tokenUtils.getUsernameFromToken(token));
            if (user != null) {
                resultDTO.setSuccess(user);
            }
            logger.info("=== SEARCH USERNAME RESPONSE:" + resultDTO.getErrorCode());
        } catch (Exception e) {
            resultDTO.setFail(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return resultDTO;
    }


    @Override
    public BaseResultDTO findAll() {
        ArrayResultDTO<UsersEntity> response = new ArrayResultDTO<>();
        try {
            ArrayList<UsersEntity> ls = (ArrayList<UsersEntity>) usersRepository.findAll();
            if (ls.size() > 0) {
                response.setSuccess(ls);
            }
        } catch (Exception e) {
            response.setFail(e.getMessage());
        }
        return response;
    }

    // ====== START SERVICES FOR AUTHENTICATION ======
    @Override
    public AuthResponseDTO generateToken(AuthRequestDTO userForAuthentication) {
        logger.info("=== START GENERATE TOKEN::");
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        try{
            if (isRequestDataValid(userForAuthentication)) {
                UserDetails springSecurityUser = userDetailsService.loadUserByUsername(userForAuthentication.getUsername());
                if (springSecurityUser != null && (
                        springSecurityUser.getUsername().equals(userForAuthentication.getUsername()) &&
                                new BCryptPasswordEncoder().matches(userForAuthentication.getPassword(), springSecurityUser.getPassword())
                 | springSecurityUser.getPassword().equals(userForAuthentication.getPassword())
                )) {
                    responseDTO.setToken(tokenUtils.generateToken(springSecurityUser));
                    responseDTO.setUsername(springSecurityUser.getUsername());
                    responseDTO.setPassword(springSecurityUser.getPassword());
                    responseDTO.setRole(springSecurityUser.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.joining(",")));
                    responseDTO.setErrorCode(Constants.SUCCESS);
                    return responseDTO;
                }
                responseDTO.setErrorCode(Constants.ERROR_401);
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        }catch (Exception ex){
            responseDTO.setErrorCode(Constants.ERROR);
            logger.error(ex.getMessage(), ex);
        }
        logger.info("=== GENERATE TOKEN RESPONSE::" + responseDTO.getErrorCode());
        return responseDTO;
    }

    @Override
    public UsersDTO findUserByEmail(String email) {
        SingleResultDTO singleResultDTO = new SingleResultDTO();
         UsersEntity usersEntity = usersRepository.findUsersEntityByEmail(email);
         UsersDTO usersDTO = modelMapper.map(usersEntity, UsersDTO.class);
        return usersDTO;
    }

    private boolean isRequestDataValid(AuthRequestDTO userForAuthentication) {
        return userForAuthentication != null &&
                userForAuthentication.getUsername() != null &&
                userForAuthentication.getPassword() != null &&
                !userForAuthentication.getUsername().isEmpty() &&
                !userForAuthentication.getPassword().isEmpty();
    }
    // ====== END ======


}
