package com.itsol.mockup.services;

import com.itsol.mockup.web.dto.BaseDTO;
import com.itsol.mockup.web.dto.issue.IssueDTO;
import com.itsol.mockup.web.dto.request.IdRequestDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IssueService {
    @PreAuthorize("hasAnyRole('HR,MANAGER,TEAMLEAD')")
    BaseResultDTO findAllIssue(Integer pageSize, Integer page);
    BaseResultDTO addIssue(IssueDTO issueDTO);
    BaseResultDTO updateIssue(IssueDTO issueDTO);
    BaseResultDTO deleteIssue(Long id);
    BaseResultDTO getIssueById(Long id);
    ArrayResultDTO findIssueByUserId(Integer pageSize, Integer page, Long id);

}
