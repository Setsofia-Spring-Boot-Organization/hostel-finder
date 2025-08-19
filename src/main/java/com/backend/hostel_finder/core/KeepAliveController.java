package com.backend.hostel_finder.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping(path = "/hf/api/v1")
public class KeepAliveController {


    @GetMapping(path = "/keep-alive")
    public ResponseEntity<String> keepAlive() {
        return new ResponseEntity<>("connected!", HttpStatus.OK);
    }


}


@Slf4j
@Service
class KeepAliveService {
    final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 500_000)
    public void keepAlive() {
        try {
            String response = restTemplate.getForObject("http://localhost:8080/hf/api/v1/keep-alive", String.class);
            log.info("Keep-alive ping sent: {}", response);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}