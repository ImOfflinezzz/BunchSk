package com.wh1lec0d3r_.bunchsk.core.server.packet;

import java.util.HashMap;
import java.util.Map;

public class PacketManager {

    private static Map<Short, Class<? extends YPacket>> packets = new HashMap<>();

    static {
    }

    public static YPacket getPacket(short id) {

        try {

            return packets.get(id).newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
