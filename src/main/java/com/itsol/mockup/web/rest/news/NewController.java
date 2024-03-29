package com.itsol.mockup.web.rest.news;

import com.itsol.mockup.services.NewService;
import com.itsol.mockup.web.dto.news.NewsDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Scope("request")
@CrossOrigin
public class NewController {
    @Autowired
    private NewService newService;

    @RequestMapping(value = "/new/list",method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> findAll(@RequestParam("pageSize") Integer PageSize,
                                                 @RequestParam("page") Integer page ) {
        BaseResultDTO result = newService.findAll(PageSize, page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResponseEntity<BaseResultDTO> addNew(@RequestBody NewsDTO newsDTO){
        BaseResultDTO result = newService.addNew(newsDTO);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/new",method = RequestMethod.PUT)
    public ResponseEntity<BaseResultDTO> updateNew(@RequestBody NewsDTO newsDTO){
        BaseResultDTO result = newService.updateNew(newsDTO);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }
    @RequestMapping(value = "/new",method = RequestMethod.DELETE)
    public ResponseEntity<BaseResultDTO> deleteNew(@RequestParam Long id){
        BaseResultDTO result = newService.deleteNew(id);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }
    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> fillOneNew(@RequestParam("id") Long id){
        BaseResultDTO result = newService.findOneById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/new/list/public",method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> findAllPublic(@RequestParam("pageSize") Integer PageSize,
                                                 @RequestParam("page") Integer page ) {
        BaseResultDTO result = newService.findNewPublic(PageSize, page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
