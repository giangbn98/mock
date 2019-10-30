package com.itsol.mockup.web.rest.team;

import com.itsol.mockup.services.TeamServices;
import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.team.TeamDTO;
import com.itsol.mockup.web.rest.BaseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Scope("request")
@RequestMapping(value = "/team")
public class TeamController extends BaseRest {

    @Autowired
    private TeamServices teamServices;

    @PostMapping(value = "/new-team")
    public ResponseEntity<BaseResultDTO> newTeam(@RequestBody TeamDTO teamDTO,
                                                 @RequestHeader HttpHeaders header){
        BaseResultDTO baseResultDTO = teamServices.addTeam(retrieveToken(header), teamDTO);
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/update-team")
    public ResponseEntity<BaseResultDTO> updateTeam(@RequestBody TeamDTO teamDTO,
                                                    @RequestHeader HttpHeaders header){
        BaseResultDTO  baseResultDTO = teamServices.updateTeam(retrieveToken(header), teamDTO);
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/search-teamId")
    public ResponseEntity<BaseResultDTO> searchTeamByTeamId(@RequestParam("id") Long id ){
        BaseResultDTO baseResultDTO = teamServices.findById(id);
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/search-all-team")
    public ResponseEntity<BaseResultDTO> searchAllTeam(@RequestBody BaseDTO request){
        BaseResultDTO baseResultDTO = teamServices.searchAllTeam(request);
        return new ResponseEntity<>(baseResultDTO, HttpStatus.OK);
    }






}
