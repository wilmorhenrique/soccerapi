package com.whd.soccerclientapi.webclientsoccerapi.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeasonListPojo {
    private List<SeasonPojo> seasons;
    private String idLeague;

}
