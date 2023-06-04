package peaksoft.springrestproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

    private String companyName;
    private String locatedCountry;
    private String directorName;
}
