package com.MoneyPlant.service;

import com.MoneyPlant.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Service
@Slf4j
public class CardService {

    private boolean isFirstExecution = true;
    private final CardRepository cardRepository;
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void executeCardCrawlerOnStartup() {
        if (isFirstExecution) {
            executeCardCrawler();
            isFirstExecution = false;
        }
    }

    @Scheduled(cron = "0 0 4 * * ?", zone = "Asia/Seoul") // 매일 새벽 4시에 실행, 1분마다 실행은 cron = "0 */1 * * * ?"
    public void executeCardCrawlerScheduled() {
        executeCardCrawler();
    }

    private void executeCardCrawler() {
        try {
            cardRepository.deleteAll(); // card_list 모든 데이터 삭제
            String pythonScriptPath = "src/main/resources/python/CardCrolling.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("CardCrolling.py 실행이 성공했습니다.");
            } else {
                log.error("CardCrolling.py 실행이 실패했습니다. 종료 코드: " + exitCode);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
