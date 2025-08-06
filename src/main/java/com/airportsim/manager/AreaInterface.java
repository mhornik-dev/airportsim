package com.airportsim.manager;

public interface AreaInterface {
    void setProvider(GroupProviderInterface provider);
    void startForwarding();
    void stopForwarding();
}