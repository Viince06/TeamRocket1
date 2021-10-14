package strategy;

import game.Age;

public class StrategyPriority {
    private Age age;
    private StrategyFactory.DIFFICULTY_LEVEL difficulty_level;

    public StrategyPriority(Age age, StrategyFactory.DIFFICULTY_LEVEL difficulty_level) {
        this.age = age;
        this.difficulty_level = difficulty_level;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public StrategyFactory.DIFFICULTY_LEVEL getDifficulty() {
        return difficulty_level;
    }

    public void setDifficulty(StrategyFactory.DIFFICULTY_LEVEL difficulty) {
        this.difficulty_level = difficulty;
    }
}
