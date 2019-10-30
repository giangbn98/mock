package com.itsol.mockup.web.rest.users;

import com.itsol.mockup.entity.UsersEntity;
import com.itsol.mockup.services.EmailService;
import com.itsol.mockup.services.UsersService;
import com.itsol.mockup.utils.TokenUtils;
import com.itsol.mockup.web.dto.request.IdRequestDTO;
import com.itsol.mockup.web.dto.request.SearchUsersRequestDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import com.itsol.mockup.web.rest.BaseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@Scope("request")
@CrossOrigin
public class UsersController extends BaseRest {
    @Autowired
    UsersService usersService;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> users(@RequestBody SearchUsersRequestDTO requestDTO) {
        BaseResultDTO result = usersService.findAllUsers(requestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    public ResponseEntity<BaseResultDTO> addUser(@RequestBody UsersDTO requestDTO) {
        BaseResultDTO result = usersService.addUser(requestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUsers", method = RequestMethod.PUT)
    public ResponseEntity<BaseResultDTO> updateUser(@RequestBody UsersDTO requestDTO) {
        BaseResultDTO result = usersService.updateUser(requestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/find-by-fullname-username", method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> findByFullNameAndUserName (@RequestBody SearchUsersRequestDTO requestDTO){
        BaseResultDTO result = usersService.findUsersByFullNameAndUserName(requestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<BaseResultDTO> findAllUser(){
        BaseResultDTO resultDTO = usersService.findAll();
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/get-allUser-not-listId")
    public ResponseEntity<BaseResultDTO> searchAllUserNotIds(@RequestBody IdRequestDTO idRequestDTO){
        BaseResultDTO baseResultDTO = usersService.findAllUsersNotListId(idRequestDTO);
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/info")
    public ResponseEntity<BaseResultDTO> UserInfo(@RequestHeader HttpHeaders header){
        BaseResultDTO baseResultDTO  = usersService.findUserEntityByUserName(retrieveToken(header));;
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<BaseResultDTO> resetPassword(@RequestParam("email") String email) {
        BaseResultDTO baseResultDTO = new BaseResultDTO();
        UsersDTO usersDTO = usersService.findUserByEmail(email);
        if(usersDTO != null){
            String token = UUID.randomUUID().toString();
            usersDTO.setPassWord(token);
            usersService.updateUser(usersDTO);
            try {
                emailService.sendEmail("wintermmo97@gmail.com","Reset",token);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(baseResultDTO,HttpStatus.OK);
    }
}
