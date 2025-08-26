package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;
}
