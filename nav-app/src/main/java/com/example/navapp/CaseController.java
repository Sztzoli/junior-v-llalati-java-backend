package com.example.navapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/types")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @GetMapping()
    public List<Case> getCases() {
        return caseService.getCases();
    }
}
