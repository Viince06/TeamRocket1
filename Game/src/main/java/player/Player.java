package player;

import strategy.IStrategy;
import strategy.MultipleStrategies;
import strategy.StrategyFactory;
import strategy.StrategyPriority;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;

    public boolean isAmbitious() {
        return ambitious;
    }

    public void setAmbitious(boolean ambitious) {
        this.ambitious = ambitious;
    }

    private boolean ambitious = false;
    private IStrategy strategy;
    private List<StrategyPriority> strategyList;
    private final StrategyFactory strategyFactory = new StrategyFactory();

    public Player(String name, StrategyFactory.DIFFICULTY_LEVEL difficultyLevel) throws Exception {
        this.name = name;
        setPlayStrategy(difficultyLevel);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyFactory.DIFFICULTY_LEVEL difficultyLevel) throws Exception {
        this.strategy = strategyFactory.setStrategy(difficultyLevel);
    }

    public void setStrategy(MultipleStrategies multipleStrategies) {
        this.strategy = multipleStrategies;
    }

    public void setPlayStrategy(StrategyFactory.DIFFICULTY_LEVEL difficultyLevel) throws Exception {
        this.strategyList = new ArrayList<>();
        this.strategy = strategyFactory.setStrategy(difficultyLevel);
    }

    public void setStrategyList(List<StrategyPriority> strategyList) {
        this.strategyList = strategyList;
    }

    public List<StrategyPriority> getStrategyList() {
        return strategyList;
    }

}