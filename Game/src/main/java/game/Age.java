package game;

public enum Age {
    AGE_1(1, true),
    AGE_2(2, false),
    AGE_3(3, true);

    private final int currentAge;
    private final boolean sens;

    Age(int currentAge, boolean sens) {
        this.currentAge = currentAge;
        this.sens = sens;
    }

    public int getAge() {
        return currentAge;
    }

    public boolean getSens() {
        return sens;
    }

}
