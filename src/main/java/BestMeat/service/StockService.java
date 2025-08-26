package BestMeat.service;

import BestMeat.model.dao.StockDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDao stockDao;
}
