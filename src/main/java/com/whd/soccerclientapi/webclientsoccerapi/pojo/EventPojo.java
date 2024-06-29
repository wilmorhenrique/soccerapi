package com.whd.soccerclientapi.webclientsoccerapi.pojo;

import lombok.Data;

@Data
public class EventPojo {

private String idEvent;
private String strEvent;
private String strFilename;
private String idLeague;
private String strLeague;
private String strSeason;
private int idHomeTeam;
private int idAwayTeam;
private int intHomeScore;
private int intRound;
private int intAwayScore;
private int intSpectators;
private String dateEvent;
private String strTime;

}
