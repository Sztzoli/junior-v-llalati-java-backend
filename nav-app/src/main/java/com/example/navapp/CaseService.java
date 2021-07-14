package com.example.navapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CaseService {

    private List<Case> cases = new ArrayList<>();

    public boolean hasCaseByCode(String code) {
        return cases.stream().anyMatch(c -> c.getCode().equals(code));
    }

    public void addToCase(Case caseType) {
        cases.add(caseType);
    }

    public List<Case> getCases() {
        return cases;
    }
}
