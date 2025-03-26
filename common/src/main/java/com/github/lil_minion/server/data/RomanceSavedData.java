package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.utils.relationship.RomanceUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Class that handles the saved data for romances in the game.
 */
public class RomanceSavedData extends SavedData {

    public static final Map<UUID, List<Romance>> ROMANCE_MAP = new HashMap<>();

    private static final Set<Romance> ROMANCE_SET = new HashSet<>();

    public static SavedData INSTANCE;

    /**
     * Creates the server state for romance data.
     *
     * @param server The Minecraft server instance.
     */
    public static void createServerState(MinecraftServer server) {
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(RomanceSavedData::create, RomanceSavedData::load,
                        DataFixTypes.LEVEL), Couples.MOD_ID);
        INSTANCE.setDirty();
    }

    /**
     * Creates a new instance of RomanceSavedData.
     *
     * @return A new RomanceSavedData instance.
     */
    public static RomanceSavedData create() {
        return new RomanceSavedData();
    }

    /**
     * Loads romance data from the given CompoundTag.
     *
     * @param tag            The {@link CompoundTag} containing the romance data.
     * @param registryLookup The HolderLookup provider for registries.
     * @return A new instance of RomanceSavedData.
     */
    public static RomanceSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        ROMANCE_MAP.clear();
        CompoundTag compoundTag = tag.getCompound("couples.romance_data");
        loadRomances(compoundTag);
        return new RomanceSavedData();
    }

    /**
     * Loads romances from the given CompoundTag.
     *
     * @param compoundTag The CompoundTag containing the romances data.
     */
    private static void loadRomances(CompoundTag compoundTag) {
        for (String key : compoundTag.getAllKeys()) {
            CompoundTag romanceCompound = compoundTag.getCompound(key);
            Romance romance = loadRomance(romanceCompound);
            ROMANCE_SET.add(romance);
        }

        ROMANCE_SET.forEach(RomanceUtil::loadRomance);
        ROMANCE_SET.clear();
    }

    /**
     * Loads a romance from the given CompoundTag.
     *
     * @param romanceCompound The CompoundTag containing the romance data.
     * @return The loaded Romance object.
     */
    private static Romance loadRomance(CompoundTag romanceCompound) {
        UUID player1 = romanceCompound.getUUID("player1");
        UUID player2 = romanceCompound.getUUID("player2");
        int timesKissed = romanceCompound.getInt("timesKissed");
        int timesFlirt = romanceCompound.getInt("timesFlirt");
        int hearths = romanceCompound.getInt("hearths");

        Romance romance = new Romance(player1, player2);
        romance.setTimesKissed(timesKissed);
        romance.setTimesFlirted(timesFlirt);
        romance.setHearths(hearths);
        return romance;
    }

    /**
     * Saves the current state of romance data to the given CompoundTag.
     *
     * @param tag        The {@link CompoundTag} to save data into.
     * @param registries The HolderLookup provider for registries.
     * @return The updated CompoundTag containing the saved romance data.
     */
    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ROMANCE_MAP.values().forEach(ROMANCE_SET::addAll);
        CompoundTag romancesTag = new CompoundTag();
        saveRomances(romancesTag);
        tag.put("couples.romance_data", romancesTag);
        return tag;
    }

    /**
     * Saves a collection of romances to the given CompoundTag.
     *
     * @param romancesTag The CompoundTag to save the romances into.
     */
    private void saveRomances(CompoundTag romancesTag) {
        int i = 0;
        for (Romance romance : ROMANCE_SET) {
            CompoundTag romanceTag = saveRomance(romance);
            romancesTag.put("" + i, romanceTag);
            i++;
        }
        ROMANCE_SET.clear();
    }

    /**
     * Saves the given Romance object to a CompoundTag.
     *
     * @param romance The Romance object to save.
     * @return A CompoundTag containing the saved romance data.
     */
    private CompoundTag saveRomance(Romance romance) {
        CompoundTag romanceTag = new CompoundTag();
        romanceTag.putUUID("player1", romance.getPlayer1());
        romanceTag.putUUID("player2", romance.getPlayer2());
        romanceTag.putInt("timesKissed", romance.getTimesKissed());
        romanceTag.putInt("timesFlirt", romance.getTimesFlirted());
        romanceTag.putInt("hearths", romance.getHearths());
        return romanceTag;
    }
}
