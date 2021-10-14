package services;

import board.Card;
import inventory.Inventory;
import inventory.PointsCalculator;
import inventory.Resources;
import model.StatPDO;
import player.PlayerWithInventory;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsHandler {

    private static final List<List<StatPDO>> statListOfAllGames = new ArrayList<>();

    private StatisticsHandler() {
    }

    public static void addStat(List<PlayerWithInventory> players) {
        List<StatPDO> statList = new ArrayList<>();
        Map<PlayerWithInventory, Integer> VP = PointsCalculator.calculVictoryPoints(players);
        for (PlayerWithInventory player : players) {
            StatPDO stat = getStat(player);
            stat.setTotalVictoryPoints(VP.get(player));
            statList.add(stat);
        }
        StatisticsHandler.statListOfAllGames.add(statList);
    }

    private static StatPDO getStat(PlayerWithInventory playerWithInventory) {
        StatPDO stat = new StatPDO();
        stat.setName(playerWithInventory.getPlayer().getName());

        Inventory inventory = playerWithInventory.getInventory();
        stat.setCardsPlayed(inventory.getCardsPlayed().stream().map(Card::getName).collect(Collectors.toList()));

        Map<String, Integer> resources = new HashMap<>();
        Set<Map.Entry<Resources, Integer>> entrySet = inventory.getResources().entrySet();
        for (Map.Entry<Resources, Integer> entry : entrySet) {
            resources.put(entry.getKey().name(), entry.getValue());
        }

        stat.setResources(resources);

        return stat;
    }

    public static void sendStat() {
        new ClientService().sendStats(StatisticsHandler.statListOfAllGames);
    }
}