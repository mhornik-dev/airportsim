package com.airportsim.manager;

public interface StationInterface {
    void setProvider(GroupProviderInterface provider);
    void startProcessing();
    void stopProcessing();
}
