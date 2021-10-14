package fr.unice.server;

import model.StatPDO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class StatService {

    private static final Log log = LogFactory.getLog(StatService.class);

    public void createStatCsv(List<List<StatPDO>> statPDOPartiesList) throws IOException {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("Stats/" + (System.currentTimeMillis() / 1000) + ".csv"), CSVFormat.EXCEL.withDelimiter(';'))) {
            csvPrinter.printRecord("name","Total Victory Points", "Victory Points from blue", "Military Points", "Gold", "Wood", "Stone", "Ore", "Clay", "Papyrus", "Loom", "Glass", "Writing", "Physics", "Mathematics", "Cards Played");
            for (List<StatPDO> statPDOParty : statPDOPartiesList) {
                for (StatPDO statPlayer : statPDOParty) {
                    csvPrinter.printRecord(
                            statPlayer.getName(),
                            statPlayer.getTotalVictoryPoints(),
                            statPlayer.getResources().get("VICTORY_POINT"),
                            statPlayer.getResources().get("MILITARY_POINT"),
                            statPlayer.getResources().get("COINS"),
                            statPlayer.getResources().get("WOOD"),
                            statPlayer.getResources().get("STONE"),
                            statPlayer.getResources().get("ORE"),
                            statPlayer.getResources().get("CLAY"),
                            statPlayer.getResources().get("PAPYRUS"),
                            statPlayer.getResources().get("LOOM"),
                            statPlayer.getResources().get("GLASS"),
                            statPlayer.getResources().get("WRITING"),
                            statPlayer.getResources().get("PHYSICS"),
                            statPlayer.getResources().get("MATHEMATICS"),
                            Arrays.toString(statPlayer.getCardsPlayed().toArray())
                    );
                }
            }
        } catch (IOException e) {
            log.error("createStatCsv(...) - Echec de la création du fichier : " + e.getMessage());
            throw e;
        }
    }
}
