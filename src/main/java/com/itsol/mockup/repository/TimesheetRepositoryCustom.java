package com.itsol.mockup.repository;

import com.itsol.mockup.web.dto.request.ReportDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;

import java.util.Date;

public interface TimesheetRepositoryCustom {
    ReportDTO getToTalMemberNotHaveTimeSheet(Long projectId, String date);
}
