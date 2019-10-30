package com.itsol.mockup.web.rest.timesheet;

import com.itsol.mockup.entity.TimeSheetEntity;
import com.itsol.mockup.services.TimesheetService;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.timesheet.TimesheetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
@Scope("request")
@CrossOrigin
public class TimesheetController {
    @Autowired
    TimesheetService timesheetService;
    @RequestMapping("/timesheet/list")
    public ResponseEntity<BaseResultDTO> findAll(){
        BaseResultDTO result = timesheetService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/timesheet",method = RequestMethod.POST)
    public ResponseEntity<BaseResultDTO> addTimesheet(@RequestBody TimesheetDTO timesheetDTO){
        BaseResultDTO result = timesheetService.addTimesheet(timesheetDTO);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }
    @RequestMapping(value = "/timesheet",method = RequestMethod.PUT)
    public ResponseEntity<BaseResultDTO> updateTimesheet(@RequestBody TimesheetDTO timesheetDTO){
        BaseResultDTO result = timesheetService.updateTimesheet(timesheetDTO);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @RequestMapping(value = "/timesheet",method = RequestMethod.DELETE)
    public ResponseEntity<BaseResultDTO> deleteTimesheet(Long id){
        BaseResultDTO result = timesheetService.deleteTimesheet(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/timesheet/getTotal",method = RequestMethod.GET)
    public ResponseEntity<BaseResultDTO> getTotelTimeSheet(@RequestParam("id") Long id,
                                                            @RequestParam("date") String date) throws ParseException {
        BaseResultDTO result = timesheetService.getTotalTimesheet(id,date);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
