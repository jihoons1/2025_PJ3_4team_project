package BestMeat.service;

import BestMeat.model.dao.PointDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointDao pointDao;

} // class end