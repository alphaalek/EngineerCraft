package me.alek.mechanics;

import me.alek.exceptions.NoSuchProfile;
import me.alek.mechanics.profiles.*;

import java.util.*;

public class UnitLibrary {

    public enum UnitType {
        MINER(1, new MinerProfile()),
        SMELTER(2, new SmelterProfile()),
        CONSTRUCTOR(3, new ConstructorProfile()),
        CONVEYOR(4, new ConveyorProfile()),
        CONVEYOR_POLE(5, new ConveyorPoleProfile());

        private final UnitProfile<? extends Unit> profile;
        private final int id;

        UnitType(int id, UnitProfile<? extends Unit> profile) {
            this. id = id;
            this.profile = profile;
        }

        public UnitProfile<? extends Unit> getProfile() {
            return profile;
        }

        public int getId() {
            return id;
        }
    }

    private static final Set<Integer> ids = new HashSet<>();
    private static final Map<Integer, UnitProfile<? extends Unit>> unitProfiles = new HashMap<>();

    public static void setup() {
        for (UnitType type : UnitType.values()) {
            ids.add(type.getId());
            unitProfiles.put(type.getId(), type.getProfile());
        }
    }

    public static <U extends Unit> UnitProfile<U> getProfileById(int id) throws NoSuchProfile {
        if (ids.isEmpty()) throw new NoSuchProfile();
        if (!ids.contains(id)) throw new NoSuchProfile();

        return (UnitProfile<U>) unitProfiles.get(id);
    }

    public static <U extends Unit> UnitProfile<U> getProfileByName(String name) throws NoSuchProfile {
        for (UnitProfile<? extends Unit> unitProfile : unitProfiles.values()) {
            if (unitProfile.getName().replaceAll(" ", "_").equalsIgnoreCase(name)) {
                return (UnitProfile<U>) unitProfile;
            }
        }
        throw new NoSuchProfile();
    }

    public static Collection<UnitProfile<? extends Unit>> getUnitProfiles() {
        return unitProfiles.values();
    }

    public static <M extends Mechanic> List<UnitProfile<M>> getMechanicProfiles() {
        final List<UnitProfile<M>> mechanicProfiles = new ArrayList<>();
        for (UnitProfile<? extends Unit> unitProfile : getUnitProfiles()) {
            if (unitProfile.isMechanic()) {
                mechanicProfiles.add((UnitProfile<M>) unitProfile);
            }
        }
        return mechanicProfiles;
    }
}
