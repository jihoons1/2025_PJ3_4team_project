package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeDao noticeDao;


} // class end