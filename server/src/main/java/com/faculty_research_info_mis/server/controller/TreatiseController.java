package com.faculty_research_info_mis.server.controller;

import com.faculty_research_info_mis.server.service.TreatiseBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-01-30
 * Time: 17:17
 */
@RestController
@RequestMapping("/treatise")
public class TreatiseController {

    @Autowired
    private TreatiseBasicInfoService service;


}
