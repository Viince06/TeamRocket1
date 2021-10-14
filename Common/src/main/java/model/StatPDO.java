package model;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatPDO {
    private String name;
    private List<String> cardsPlayed;
    private Map<String, Integer> resources = new HashMap<>();
    private int totalVictoryPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalVictoryPoints(int vP){
        this.totalVictoryPoints = vP;
    }

    public int getTotalVictoryPoints(){
        return totalVictoryPoints;
    }

    public List<String> getCardsPlayed() {
        return cardsPlayed;
    }

    public void setCardsPlayed(List<String> cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
    }

    public Map<String, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<String, Integer> resources) {
        this.resources = resources;
    }

    public static String toJSON(StatPDO statPDO) {
        return new Gson().toJson(statPDO);
    }

    public static String toJSON(List<List<StatPDO>> statList) {
        return new Gson().toJson(statList);
    }

    public static StatPDO toStatPDO(String s) {
        return new Gson().fromJson(s, StatPDO.class);
    }
}
