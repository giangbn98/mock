package com.itsol.mockup.repository;

import com.itsol.mockup.entity.TimeSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TimesheetRepository extends JpaRepository<TimeSheetEntity,Long> {
    TimeSheetEntity getTimeSheetEntityByTimesheetId(Long id);
    long countByCreatedDateAndProjectId(Date date, Long projectId);
    long countByProjectIdAndCreatedDate(Long projectId,Date date);
}
