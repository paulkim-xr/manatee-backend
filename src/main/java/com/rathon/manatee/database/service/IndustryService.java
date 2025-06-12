package com.rathon.manatee.database.service;

import com.rathon.manatee.database.mapper.IndustryMapper;
import com.rathon.manatee.database.model.Industry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {
    private final IndustryMapper mapper;

    public IndustryService(IndustryMapper mapper) {
        this.mapper = mapper;
    }

    public Industry getIndustryById(Long id) {
        return this.mapper.findById(id);
    }

    public List<Industry> getAll() {
        return this.mapper.findAll();
    }

    public void createIndustry(Industry u) {
        mapper.insert(u);
    }

    public void updateIndustry(Industry u) {
        mapper.update(u);
    }

    public void deleteIndustry(Long id) {
        mapper.delete(id);
    }
}
