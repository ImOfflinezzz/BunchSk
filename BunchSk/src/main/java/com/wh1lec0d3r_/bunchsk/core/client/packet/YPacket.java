package com.wh1lec0d3r_.bunchsk.core.client.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class YPacket {

    public abstract short getId();

    public abstract void read(DataInputStream dataInputStream) throws IOException;

    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public abstract void handle();
}
