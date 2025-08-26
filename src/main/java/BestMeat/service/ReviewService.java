package BestMeat.service;

import BestMeat.model.dao.ReviewDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;
}
