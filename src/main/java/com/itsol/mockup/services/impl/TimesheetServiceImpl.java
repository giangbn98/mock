package com.itsol.mockup.services.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.itsol.mockup.entity.CommentsEntity;
import com.itsol.mockup.entity.TimeSheetEntity;
import com.itsol.mockup.repository.TimesheetRepository;
import com.itsol.mockup.repository.TimesheetRepositoryCustom;
import com.itsol.mockup.services.TimesheetService;
import com.itsol.mockup.utils.Constants;
import com.itsol.mockup.web.dto.comment.CommentDTO;
import com.itsol.mockup.web.dto.request.ReportDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.SingleResultDTO;
import com.itsol.mockup.web.dto.timesheet.TimesheetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TimesheetServiceImpl extends BaseService implements TimesheetService {
    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    TimesheetRepositoryCustom timesheetRepositoryCustom;
    @Override
    public ArrayResultDTO<TimeSheetEntity> findAll() {
        logger.info("=== START FIND ALL ISSUE::");

        ArrayResultDTO arrayResultDTO = new ArrayResultDTO<>();
        List<TimesheetDTO> lstResult = new ArrayList<>();
        List<TimeSheetEntity> rawDatas = timesheetRepository.findAll();
        if(rawDatas != null) {
            rawDatas.forEach(i -> {
                TimesheetDTO dto = modelMapper.map(i, TimesheetDTO.class);
                lstResult.add(dto);
            });
        }
        arrayResultDTO.setSuccess(lstResult,1L,2);
        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO addTimesheet(TimesheetDTO timesheetDTO) {
       logger.info("ADD NEW TIMESHEET");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            TimeSheetEntity timeSheetEntity = modelMapper.map(timesheetDTO,TimeSheetEntity.class);
            timeSheetEntity = timesheetRepository.save(timeSheetEntity);
            singleResultDTO.setSuccess(timeSheetEntity);

        }catch (Exception e){
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO updateTimesheet(TimesheetDTO timesheetDTO) {
        logger.info("UPDATE TIMESHEET");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            TimeSheetEntity timeSheetEntity = timesheetRepository.getTimeSheetEntityByTimesheetId(timesheetDTO.getTimesheetId());
            if(timeSheetEntity.getTimesheetId() != null){
                timeSheetEntity = modelMapper.map(timesheetDTO, TimeSheetEntity.class);
                timesheetRepository.save(timeSheetEntity);
                singleResultDTO.setSuccess(timeSheetEntity);
            }
            logger.info("UPDATE TIMESHEET RESPONSE: "+ singleResultDTO.getErrorCode());
        }catch (Exception e){
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO deleteTimesheet(Long id) {
        logger.info("DELETE TIMESHEET");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            if(id != null){
                timesheetRepository.deleteById(id);
                singleResultDTO.setSuccess();
            }
        }catch (Exception e){
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return singleResultDTO;
    }

    @Override
    public BaseResultDTO getTotalTimesheet(Long projectId, String date) {
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        ReportDTO reportDTO = timesheetRepositoryCustom.getToTalMemberNotHaveTimeSheet(projectId, date);
        singleResultDTO.setSuccess(reportDTO);
        return singleResultDTO;
    }
}
