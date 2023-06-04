package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.CompanyRequest;
import peaksoft.springrestproject.dto.CompanyResponse;
import peaksoft.springrestproject.dto.CompanyResponseView;
import peaksoft.springrestproject.entity.Company;
import peaksoft.springrestproject.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<CompanyResponse> getAllCompanies(){
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companyRepository.findAll()) {
            companyResponses.add(mapToResponse(company));
        }
        return companyResponses;
    }
    public CompanyResponse getCompanyById(Long companyId){
        Company company = companyRepository.findById(companyId).get();
        return mapToResponse(company);
    }
    public CompanyResponse saveCompany(CompanyRequest request){
        Company company = mapToEntity(request);
        companyRepository.save(company);
        return mapToResponse(company);
    }
    public CompanyResponse updateCompany(Long id,CompanyRequest request){
        Company company = companyRepository.findById(id).get();
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());
        company.setDirectorName(request.getDirectorName());
        companyRepository.save(company);
        return mapToResponse(company);
    }
    public void deleteCompany(Long companyId){
        companyRepository.deleteById(companyId);
    }

    public Company mapToEntity(CompanyRequest request){
        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());
        company.setDirectorName(request.getDirectorName());
        company.setLocalDate(LocalDate.now());
        return company;
    }
    public CompanyResponse mapToResponse(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setLocatedCountry(company.getLocatedCountry());
        companyResponse.setDirectorName(company.getDirectorName());
        companyResponse.setLocalDate(company.getLocalDate());
        return companyResponse;
    }
    public CompanyResponseView searchAndPagination(String text, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        CompanyResponseView companyResponseView = new CompanyResponseView();
        companyResponseView.setCompanyResponses(view(search(text, pageable)));
        return companyResponseView;

    }
    public List<CompanyResponse> view(List<Company> companies){
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companies) {
            companyResponses.add(mapToResponse(company));
        }
        return companyResponses;
    }
    private List<Company> search(String text, Pageable pageable){
        String name = text == null?"": text;
        return companyRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
