package fr.unice.server;

import model.StatPDO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private StatService statService;

    private static final Log log = LogFactory.getLog(Controller.class);


    @PostMapping(value = "/stats/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStatistics(@RequestBody List<List<StatPDO>> stats) {
        try {
            this.statService.createStatCsv(stats);
            return new ResponseEntity<>("OK", HttpStatus.valueOf(200));
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("KO", HttpStatus.valueOf(500));
        }
    }
}
