package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.ModLoaderMethods;
import net.neoforged.neoforge.network.PacketDistributor;

public class MethodUploaderNeoForge {

    public static void toCommonCodeForServer() {
        ModLoaderMethods.sendMessageToClientMethod = PacketDistributor::sendToPlayer;
    }

    public static void toCommonCodeForClient() {
        ModLoaderMethods.sendMessageToServerMethod = PacketDistributor::sendToServer;
    }

}
