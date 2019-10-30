package com.itsol.mockup.services;

import com.itsol.mockup.entity.TimeSheetEntity;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.timesheet.TimesheetDTO;

import java.util.Date;

public interface TimesheetService {
    ArrayResultDTO<TimeSheetEntity> findAll();
    BaseResultDTO addTimesheet(TimesheetDTO timesheetDTO);
    BaseResultDTO updateTimesheet(TimesheetDTO timesheetDTO);
    BaseResultDTO deleteTimesheet(Long id);
    BaseResultDTO getTotalTimesheet(Long projectId, String date);
}
