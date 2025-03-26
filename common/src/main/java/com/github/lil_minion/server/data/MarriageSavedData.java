package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.model.relationship.marriage.MarriageInteraction;
import com.github.lil_minion.model.relationship.marriage.MarriageInteractionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Class that handles the saved data for marriages in the game.
 */
public class MarriageSavedData extends SavedData {

    public static final Map<UUID, Marriage> MARRIAGE_MAP = new HashMap<>();

    private static final Set<Marriage> MARRIAGE_SET = new HashSet<>();

    public static SavedData INSTANCE;

    /**
     * Creates the server state for marriage data.
     *
     * @param server The Minecraft server instance.
     */
    public static void createServerState(MinecraftServer server) {
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(MarriageSavedData::create, MarriageSavedData::load,
                        DataFixTypes.LEVEL), Couples.MOD_ID + ".marriage_data");
        INSTANCE.setDirty();
    }

    /**
     * Creates a new instance of MarriageSavedData.
     *
     * @return A new MarriageSavedData instance.
     */
    public static MarriageSavedData create() {
        return new MarriageSavedData();
    }

    /**
     * Loads marriage data from the given CompoundTag.
     *
     * @param tag            The {@link CompoundTag} containing the marriage data.
     * @param registryLookup The HolderLookup provider for registries.
     * @return A new instance of MarriageSavedData.
     */
    public static MarriageSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        CompoundTag compoundTag = tag.getCompound("couples.marriage_data");

        for (String key : compoundTag.getAllKeys()) {
            CompoundTag marriageCompound = compoundTag.getCompound(key);
            Marriage marriage = loadMarriage(marriageCompound);
            MARRIAGE_SET.add(marriage);
        }

        for (Marriage marriage : MARRIAGE_SET) {
            MARRIAGE_MAP.put(marriage.getPlayer1(), marriage);
            MARRIAGE_MAP.put(marriage.getPlayer2(), marriage);
        }
        MARRIAGE_SET.clear();

        return new MarriageSavedData();
    }

    /**
     * Loads a marriage from the given CompoundTag.
     *
     * @param marriageCompound The CompoundTag containing the marriage data.
     * @return The loaded Marriage object.
     */
    private static Marriage loadMarriage(CompoundTag marriageCompound) {
        UUID player1 = marriageCompound.getUUID("player1");
        UUID player2 = marriageCompound.getUUID("player2");
        long timeOfMarriage = marriageCompound.getLong("timeOfMarriage");
        Marriage marriage = new Marriage(player1, player2, timeOfMarriage);

        marriage.setHearths(marriageCompound.getInt("hearths"));
        marriage.setTimesKissed(marriageCompound.getInt("timesKissed"));
        marriage.setTimesFlirted(marriageCompound.getInt("timesFlirted"));
        marriage.setHearthsEarned(marriageCompound.getInt("hearthsEarned"));
        marriage.setHeartsLost(marriageCompound.getInt("heartsLost"));
        marriage.setTimesSleptTogether(marriageCompound.getInt("timesSleptTogether"));
        marriage.setTimesSleptApart(marriageCompound.getInt("timesSleptApart"));
        marriage.setGiftsGiven(marriageCompound.getInt("giftsGiven"));
        marriage.setRumorCount(marriageCompound.getInt("rumorCount"));

        List<MarriageInteraction> interactionList = loadInteractions(marriageCompound.getCompound("marriageInteractions"));
        marriage.getInteractionsList().addAll(interactionList);

        return marriage;
    }

    /**
     * Loads interactions from the given CompoundTag.
     *
     * @param marriageInteractions The CompoundTag containing the interactions data.
     * @return A list of MarriageInteraction objects.
     */
    private static List<MarriageInteraction> loadInteractions(CompoundTag marriageInteractions) {
        List<MarriageInteraction> interactionList = new ArrayList<>();
        for (String interactionId : marriageInteractions.getAllKeys()) {
            CompoundTag interaction = marriageInteractions.getCompound(interactionId);
            MarriageInteractionType type = MarriageInteractionType.fromString(interaction.getString("type"));
            long timeOfInteraction = interaction.getLong("timeOfInteraction");
            int hearthsChanged = interaction.getInt("hearthsChanged");
            interactionList.add(new MarriageInteraction(type, timeOfInteraction, hearthsChanged));
        }
        return interactionList;
    }


    @Override
    /*
     * Saves the current state of marriage data to the given CompoundTag.
     *
     * @param tag The {@link CompoundTag} to save data into.
     * @param registries The HolderLookup provider for registries.
     * @return The updated CompoundTag containing the saved marriage data.
     */
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        MARRIAGE_SET.addAll(MARRIAGE_MAP.values());
        CompoundTag compoundTag = new CompoundTag();

        for (Marriage marriage : MARRIAGE_SET) {
            CompoundTag marriageTag = saveMarriage(marriage);
            compoundTag.put(marriage.getPlayer1().toString(), marriageTag);
        }

        MARRIAGE_SET.clear();
        tag.put("couples.marriage_data", compoundTag);
        return tag;
    }

    /**
     * Saves the given Marriage object to a CompoundTag.
     *
     * @param marriage The Marriage object to save.
     * @return A CompoundTag containing the saved marriage data.
     */
    private CompoundTag saveMarriage(Marriage marriage) {
        CompoundTag marriageTag = new CompoundTag();
        marriageTag.putUUID("player1", marriage.getPlayer1());
        marriageTag.putUUID("player2", marriage.getPlayer2());
        marriageTag.putLong("timeOfMarriage", marriage.getTimeOfMarriage());
        marriageTag.putInt("timesKissed", marriage.getTimesKissed());
        marriageTag.putInt("timesFlirted", marriage.getTimesFlirted());
        marriageTag.putInt("hearths", marriage.getHearths());
        marriageTag.putInt("hearthsEarned", marriage.getHearthsEarned());
        marriageTag.putInt("heartsLost", marriage.getHeartsLost());
        marriageTag.putInt("timesSleptTogether", marriage.getTimesSleptTogether());
        marriageTag.putInt("timesSleptApart", marriage.getTimesSleptApart());
        marriageTag.putInt("giftsGiven", marriage.getGiftsGiven());
        marriageTag.putInt("rumorCount", marriage.getRumorCount());

        CompoundTag marriageInteractions = saveInteractions(marriage.getInteractionsList());
        marriageTag.put("marriageInteractions", marriageInteractions);
        return marriageTag;
    }

    /**
     * Saves a list of MarriageInteraction objects to a CompoundTag.
     *
     * @param interactions The list of MarriageInteraction objects to save.
     * @return A {@link CompoundTag} containing the saved interaction's data.
     */
    private CompoundTag saveInteractions(List<MarriageInteraction> interactions) {
        CompoundTag marriageInteractions = new CompoundTag();
        int i = 0;
        for (MarriageInteraction interaction : interactions) {
            CompoundTag marriageInteraction = new CompoundTag();
            marriageInteraction.putString("type", interaction.getInteractionType().getType());
            marriageInteraction.putLong("timeOfInteraction", interaction.getTimeOfInteraction());
            marriageInteraction.putInt("hearthsChanged", interaction.getHearthsChanged());

            marriageInteractions.put(String.valueOf(i), marriageInteraction);
            i++;
        }
        return marriageInteractions;
    }

}
