package org.kanelucky.simpletpa;

import org.allaymc.api.entity.interfaces.EntityPlayer;

public class TPARequest {
    private final EntityPlayer requester;
    private final EntityPlayer target;
    private final TpaType type;
    private final long timestamp;

    public TPARequest(EntityPlayer requester, EntityPlayer target, TpaType type) {
        this.requester = requester;
        this.target = target;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }

    public EntityPlayer getRequester() {
        return requester;
    }

    public EntityPlayer getTarget() {
        return target;
    }

    public TpaType getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isExpired(long timeoutSeconds) {
        long elapsed = (System.currentTimeMillis() - timestamp) / 1000;
        return elapsed > timeoutSeconds;
    }

    public enum TpaType {
        TPA,
        TPAHERE
    }
}