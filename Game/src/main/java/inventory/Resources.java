package inventory;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum Resources {
    WOOD(ResourceType.RAW_MATERIALS),
    STONE(ResourceType.RAW_MATERIALS),
    ORE(ResourceType.RAW_MATERIALS),
    CLAY(ResourceType.RAW_MATERIALS),
    PAPYRUS(ResourceType.MANUFACTURED_GOODS),
    LOOM(ResourceType.MANUFACTURED_GOODS),
    GLASS(ResourceType.MANUFACTURED_GOODS),
    WRITING(ResourceType.SCIENTIFIC_SYMBOLS),
    PHYSICS(ResourceType.SCIENTIFIC_SYMBOLS),
    MATHEMATICS(ResourceType.SCIENTIFIC_SYMBOLS),
    VICTORY_POINT(ResourceType.OTHERS),
    MILITARY_POINT(ResourceType.OTHERS),
    MILITARY_VICTORY_POINT(ResourceType.OTHERS), //Uniquement utilisé pour le calcul des victoires militaire à la fin de chaque âge
    COINS(ResourceType.OTHERS);

    private final ResourceType type;

    Resources(ResourceType resourceType) {
        this.type = resourceType;
    }

    public ResourceType getType() {
        return type;
    }

    public List<Resources> getResourcesByType(ResourceType type) {
        return EnumSet.allOf(Resources.class).stream().filter(e -> e.type == type).collect(Collectors.toList());
    }

    public enum ResourceType {
        RAW_MATERIALS,
        MANUFACTURED_GOODS,
        SCIENTIFIC_SYMBOLS,
        OTHERS
    }
}
