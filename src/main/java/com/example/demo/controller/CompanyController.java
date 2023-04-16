package com.example.demo.controller;


import com.example.demo.model.Company;
import com.example.demo.model.Courier;
import com.example.demo.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyRepository companyRepository;
    @GetMapping
    public Iterable<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Company> getById(@PathVariable Integer id){
        return companyRepository.findById(id);
    }

    @PostMapping("/new")
    public Company addCompany(@RequestBody Company company){
        return companyRepository.save(company);
    }
}
