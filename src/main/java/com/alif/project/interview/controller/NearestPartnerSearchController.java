package com.alif.project.interview.controller;

import com.alif.project.interview.dto.request.nearest_partner_search.GetResultRequestDto;
import com.alif.project.interview.exceptions.TaskException;
import com.alif.project.interview.service.NearestPartnerSearchService;
import com.alif.project.interview.util.ApiResponseTemplate;
import com.alif.project.interview.util.ApiTemplateMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nearestPartnerSearch")
public class NearestPartnerSearchController {

    private final ApiTemplateMessage templateMessage;

    private final NearestPartnerSearchService nearestPartnerSearchService;

    public NearestPartnerSearchController(ApiTemplateMessage templateMessage,
                                          NearestPartnerSearchService nearestPartnerSearchService
                                          ) {
        this.templateMessage = templateMessage;
        this.nearestPartnerSearchService = nearestPartnerSearchService;
    }

    @PostMapping(value = "/getResult", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getResult(@Valid @RequestBody GetResultRequestDto request) throws TaskException {
        List<int[]> result = nearestPartnerSearchService.getResult(request.getInput());
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }
}
