package com.github.lil_minion.server.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * A container for storing interaction related data in memory only.
 */
public class InteractionVolatileData {

    // Todo implement cooldowns
    public static final Map<UUID, Long> INTERACTION_COOLDOWN_MAP = new HashMap<>();

}