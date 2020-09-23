package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;

@JsonView(View.FilterFloodStations.class)
public class InfoByStation {
    private int station;
    private List<InfoByAddress> listInfo;

    public InfoByStation(List<InfoByAddress> listInfoByAddresses, int station) {
        this.listInfo = listInfoByAddresses;
        this.station = station;
    }

    public List<InfoByAddress> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<InfoByAddress> listInfo) {
        this.listInfo = listInfo;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "InfoByStation{" +
                "listInfoByAddresses=" + listInfo +
                ", station=" + station +
                '}';
    }
}
