package com.bolshakovk.favthingsservice.service;

import com.bolshakovk.favthingsservice.dto.LogDto;
import com.bolshakovk.favthingsservice.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogMapper logMapper;

    public void addLogInDB(LogDto logDto) {
        logMapper.addLogsInDB(logDto);
    }
}
