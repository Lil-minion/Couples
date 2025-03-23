package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.commands.ModCommandsFabric;
import com.github.lil_minion.couples.fabric.event.PlayerEventsFabric;
import com.github.lil_minion.couples.fabric.event.ServerEventsFabric;
import com.github.lil_minion.couples.fabric.items.ModCreativeTabFabric;
import com.github.lil_minion.couples.fabric.items.ModItemsFabric;
import com.github.lil_minion.couples.fabric.network.ModMessagesFabric;
import net.fabricmc.api.ModInitializer;

public final class CouplesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModCommandsFabric.register();
        ModItemsFabric.register();
        ModCreativeTabFabric.register();
        ModMessagesFabric.registerServer();
        MethodUploaderFabric.toCommonCodeForServer();
        PlayerEventsFabric.register();
        ServerEventsFabric.register();
    }

}
