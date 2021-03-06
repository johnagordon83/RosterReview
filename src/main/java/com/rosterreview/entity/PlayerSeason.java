package com.rosterreview.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An {@link Entity} class describing the statistics generated during a single season
 * of a professional football player's career.
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="player_season")
public class PlayerSeason implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="player_id")
    protected String playerId;

    @Id
    @Column(name="franchise_id")
    protected String franchiseId;

    @Id
    @Column(name="season")
    protected Integer season;

    @OneToOne()
    @Fetch(FetchMode.JOIN)
    @JoinColumns({
        @JoinColumn(name="franchise_id", referencedColumnName="franchise_id", insertable=false, updatable=false),
        @JoinColumn(name="season", referencedColumnName="season", insertable=false, updatable=false)
        })
    protected Team team;

    @Column(name="age")
    protected Integer age;

    @Column(name="position")
    @Enumerated(EnumType.STRING)
    protected Position position;

    @Column(name="jersey_number")
    protected Integer jerseyNumber;

    @Column(name="games_played", nullable=false)
    protected Integer gamesPlayed = 0;

    @Column(name="games_started", nullable=false)
    protected Integer gamesStarted = 0;

    @Column(name="pass_comp", nullable=false)
    protected Integer passComp = 0;

    @Column(name="pass_att", nullable=false)
    protected Integer passAtt = 0;

    @Column(name="pass_yds", nullable=false)
    protected Integer passYds = 0;

    @Column(name="pass_tds", nullable=false)
    protected Integer passTds = 0;

    @Column(name="pass_ints", nullable=false)
    protected Integer passInts = 0;

    @Column(name="pass_long", nullable=false)
    protected Integer passLong = 0;

    @Column(name="passer_rating", nullable=false)
    protected Double passerRating = 0.0;

    @Column(name="sacked", nullable=false)
    protected Integer sacked = 0;

    @Column(name="sacked_yds", nullable=false)
    protected Integer sackedYds = 0;

    @Column(name="rush_att", nullable=false)
    protected Integer rushAtt = 0;

    @Column(name="rush_yds", nullable=false)
    protected Integer rushYds = 0;

    @Column(name="rush_tds", nullable=false)
    protected Integer rushTds = 0;

    @Column(name="rush_long")
    protected Integer rushLong = 0;

    @Column(name="targets", nullable=false)
    protected Integer targets = 0;

    @Column(name="rec", nullable=false)
    protected Integer receptions = 0;

    @Column(name="rec_yds", nullable=false)
    protected Integer recYds = 0;

    @Column(name="rec_tds", nullable=false)
    protected Integer recTds = 0;

    @Column(name="rec_long", nullable=false)
    protected Integer recLong = 0;

    @Column(name="fumbles", nullable=false)
    protected Integer fumbles = 0;

    @Column(name="interceptions", nullable=false)
    protected Integer interceptions = 0;

    @Column(name="int_yds", nullable=false)
    protected Integer intYds = 0;

    @Column(name="int_tds", nullable=false)
    protected Integer intTds = 0;

    @Column(name="int_long", nullable=false)
    protected Integer intLong = 0;

    @Column(name="pass_def", nullable=false)
    protected Integer passDef = 0;

    @Column(name="fum_forced", nullable=false)
    protected Integer fumForced = 0;

    @Column(name="fum_rec", nullable=false)
    protected Integer fumRec = 0;

    @Column(name="fum_rec_yds", nullable=false)
    protected Integer fumRecYds = 0;

    @Column(name="fum_rec_tds", nullable=false)
    protected Integer fumRecTds = 0;

    @Column(name="sacks", nullable=false)
    protected Double sacks = 0.0;

    @Column(name="tackles", nullable=false)
    protected Integer tackles = 0;

    @Column(name="assists", nullable=false)
    protected Integer assists = 0;

    @Column(name="safeties", nullable=false)
    protected Integer safeties = 0;

    @Column(name="fga_teens", nullable=false)
    protected Integer fgaTeens = 0;

    @Column(name="fgm_teens", nullable=false)
    protected Integer fgmTeens = 0;

    @Column(name="fga_twenties", nullable=false)
    protected Integer fgaTwenties = 0;

    @Column(name="fgm_twenties", nullable=false)
    protected Integer fgmTwenties = 0;

    @Column(name="fga_thirties", nullable=false)
    protected Integer fgaThirties = 0;

    @Column(name="fgm_thirties", nullable=false)
    protected Integer fgmThirties = 0;

    @Column(name="fga_fourties", nullable=false)
    protected Integer fgaFourties = 0;

    @Column(name="fgm_fourties", nullable=false)
    protected Integer fgmFourties = 0;

    @Column(name="fga_fifty_plus", nullable=false)
    protected Integer fgaFiftyPlus = 0;

    @Column(name="fgm_fifty_plus", nullable=false)
    protected Integer fgmFiftyPlus = 0;

    @Column(name="fga_total", nullable=false)
    protected Integer fgaTotal = 0;

    @Column(name="fgm_total", nullable=false)
    protected Integer fgmTotal = 0;

    @Column(name="fg_long", nullable=false)
    protected Integer fgLong = 0;

    @Column(name="xpa", nullable=false)
    protected Integer xpa = 0;

    @Column(name="xpm", nullable=false)
    protected Integer xpm = 0;

    @Column(name="punts", nullable=false)
    protected Integer punts = 0;

    @Column(name="punt_yds", nullable=false)
    protected Integer puntYds = 0;

    @Column(name="punt_long", nullable=false)
    protected Integer puntLong = 0;

    @Column(name="punt_blocked", nullable=false)
    protected Integer puntBlocked = 0;

    @Column(name="punt_ret", nullable=false)
    protected Integer puntRet = 0;

    @Column(name="punt_ret_yds", nullable=false)
    protected Integer puntRetYds = 0;

    @Column(name="punt_ret_tds", nullable=false)
    protected Integer puntRetTds = 0;

    @Column(name="punt_ret_long", nullable=false)
    protected Integer puntRetLong = 0;

    @Column(name="kick_ret", nullable=false)
    protected Integer kickRet = 0;

    @Column(name="kick_ret_yds", nullable=false)
    protected Integer kickRetYds = 0;

    @Column(name="kick_ret_tds", nullable=false)
    protected Integer kickRetTds = 0;

    @Column(name="kick_ret_long", nullable=false)
    protected Integer kickRetLong = 0;

    @Column(name="other_tds", nullable=false)
    protected Integer otherTds = 0;

    @Column(name="all_tds", nullable=false)
    protected Integer allTds = 0;

    @Column(name="two_point_conv", nullable=false)
    protected Integer twoPointConv = 0;

    @Column(name="postseason_games_played", nullable=false)
    protected Integer postseasonGamesPlayed = 0;

    @Column(name="postseason_games_started", nullable=false)
    protected Integer postseasonGamesStarted = 0;

    @Column(name="postseason_pass_comp", nullable=false)
    protected Integer postseasonPassComp = 0;

    @Column(name="postseason_pass_att", nullable=false)
    protected Integer postseasonPassAtt = 0;

    @Column(name="postseason_pass_yds", nullable=false)
    protected Integer postseasonPassYds = 0;

    @Column(name="postseason_pass_tds", nullable=false)
    protected Integer postseasonPassTds = 0;

    @Column(name="postseason_pass_ints", nullable=false)
    protected Integer postseasonPassInts = 0;

    @Column(name="postseason_pass_long", nullable=false)
    protected Integer postseasonPassLong = 0;

    @Column(name="postseason_passer_rating", nullable=false)
    protected Double postseasonPasserRating = 0.0;

    @Column(name="postseason_sacked", nullable=false)
    protected Integer postseasonSacked = 0;

    @Column(name="postseason_sacked_yds", nullable=false)
    protected Integer postseasonSackedYds = 0;

    @Column(name="postseason_rush_att", nullable=false)
    protected Integer postseasonRushAtt = 0;

    @Column(name="postseason_rush_yds", nullable=false)
    protected Integer postseasonRushYds = 0;

    @Column(name="postseason_rush_tds", nullable=false)
    protected Integer postseasonRushTds = 0;

    @Column(name="postseason_rush_long", nullable=false)
    protected Integer postseasonRushLong = 0;

    @Column(name="postseason_targets", nullable=false)
    protected Integer postseasonTargets = 0;

    @Column(name="postseason_rec", nullable=false)
    protected Integer postseasonReceptions = 0;

    @Column(name="postseason_rec_yds", nullable=false)
    protected Integer postseasonRecYds = 0;

    @Column(name="postseason_rec_tds", nullable=false)
    protected Integer postseasonRecTds = 0;

    @Column(name="postseason_rec_long", nullable=false)
    protected Integer postseasonRecLong = 0;

    @Column(name="postseason_fumbles", nullable=false)
    protected Integer postseasonFumbles = 0;

    @Column(name="postseason_interceptions", nullable=false)
    protected Integer postseasonInterceptions = 0;

    @Column(name="postseason_int_yds", nullable=false)
    protected Integer postseasonIntYds = 0;

    @Column(name="postseason_int_tds", nullable=false)
    protected Integer postseasonIntTds = 0;

    @Column(name="postseason_int_long", nullable=false)
    protected Integer postseasonIntLong = 0;

    @Column(name="postseason_pass_def", nullable=false)
    protected Integer postseasonPassDef = 0;

    @Column(name="postseason_fum_forced", nullable=false)
    protected Integer postseasonFumForced = 0;

    @Column(name="postseason_fum_rec", nullable=false)
    protected Integer postseasonFumRec = 0;

    @Column(name="postseason_fum_rec_yds", nullable=false)
    protected Integer postseasonFumRecYds = 0;

    @Column(name="postseason_fum_rec_tds", nullable=false)
    protected Integer postseasonFumRecTds = 0;

    @Column(name="postseason_sacks", nullable=false)
    protected Double postseasonSacks = 0.0;

    @Column(name="postseason_tackles", nullable=false)
    protected Integer postseasonTackles = 0;

    @Column(name="postseason_assists", nullable=false)
    protected Integer postseasonAssists = 0;

    @Column(name="postseason_safeties", nullable=false)
    protected Integer postseasonSafeties = 0;

    @Column(name="postseason_fga_teens", nullable=false)
    protected Integer postseasonFgaTeens = 0;

    @Column(name="postseason_fgm_teens", nullable=false)
    protected Integer postseasonFgmTeens = 0;

    @Column(name="postseason_fga_twenties", nullable=false)
    protected Integer postseasonFgaTwenties = 0;

    @Column(name="postseason_fgm_twenties", nullable=false)
    protected Integer postseasonFgmTwenties = 0;

    @Column(name="postseason_fga_thirties", nullable=false)
    protected Integer postseasonFgaThirties = 0;

    @Column(name="postseason_fgm_thirties", nullable=false)
    protected Integer postseasonFgmThirties = 0;

    @Column(name="postseason_fga_fourties", nullable=false)
    protected Integer postseasonFgaFourties = 0;

    @Column(name="postseason_fgm_fourties", nullable=false)
    protected Integer postseasonFgmFourties = 0;

    @Column(name="postseason_fga_fifty_plus", nullable=false)
    protected Integer postseasonFgaFiftyPlus = 0;

    @Column(name="postseason_fgm_fifty_plus", nullable=false)
    protected Integer postseasonFgmFiftyPlus = 0;

    @Column(name="postseason_fga_total", nullable=false)
    protected Integer postseasonFgaTotal = 0;

    @Column(name="postseason_fgm_total", nullable=false)
    protected Integer postseasonFgmTotal = 0;

    @Column(name="postseason_fg_long", nullable=false)
    protected Integer postseasonFgLong = 0;

    @Column(name="postseason_xpa", nullable=false)
    protected Integer postseasonXpa = 0;

    @Column(name="postseason_xpm", nullable=false)
    protected Integer postseasonXpm = 0;

    @Column(name="postseason_punts", nullable=false)
    protected Integer postseasonPunts = 0;

    @Column(name="postseason_punt_yds", nullable=false)
    protected Integer postseasonPuntYds = 0;

    @Column(name="postseason_punt_long", nullable=false)
    protected Integer postseasonPuntLong = 0;

    @Column(name="postseason_punt_blocked", nullable=false)
    protected Integer postseasonPuntBlocked = 0;

    @Column(name="postseason_punt_ret", nullable=false)
    protected Integer postseasonPuntRet = 0;

    @Column(name="postseason_punt_ret_yds", nullable=false)
    protected Integer postseasonPuntRetYds = 0;

    @Column(name="postseason_punt_ret_tds", nullable=false)
    protected Integer postseasonPuntRetTds = 0;

    @Column(name="postseason_punt_ret_long", nullable=false)
    protected Integer postseasonPuntRetLong = 0;

    @Column(name="postseason_kick_ret", nullable=false)
    protected Integer postseasonKickRet = 0;

    @Column(name="postseason_kick_ret_yds", nullable=false)
    protected Integer postseasonKickRetYds = 0;

    @Column(name="postseason_kick_ret_tds", nullable=false)
    protected Integer postseasonKickRetTds = 0;

    @Column(name="postseason_kick_ret_long", nullable=false)
    protected Integer postseasonKickRetLong = 0;

    @Column(name="postseason_other_tds", nullable=false)
    protected Integer postseasonOtherTds = 0;

    @Column(name="postseason_all_tds", nullable=false)
    protected Integer postseasonAllTds = 0;

    @Column(name="postseason_two_point_conv", nullable=false)
    protected Integer postseasonTwoPointConv = 0;

    @Column(name="probowl")
    protected Boolean probowl;

    @Column(name="all_pro")
    protected Boolean allPro;

    @Column(name="avg_value", nullable=false)
    protected Integer avgValue = 0;

    public PlayerSeason() {}

    public PlayerSeason(String playerId, String franchiseId, Integer season, Integer age, Position position, Integer jerseyNumber,
            Integer gamesPlayed, Integer gamesStarted, Integer passComp, Integer passAtt, Integer passYds, Integer passTds, Integer passInts,
            Integer passLong, Double passerRating, Integer sacked, Integer sackedYds, Integer rushAtt, Integer rushYds, Integer rushTds,
            Integer rushLong, Integer targets, Integer receptions, Integer recYds, Integer recTds, Integer recLong, Integer fumbles,
            Integer interceptions, Integer intYds, Integer intTds, Integer intLong, Integer passDef, Integer fumForced, Integer fumRec, Integer fumRecYds,
            Integer fumRecTds, Double sacks, Integer tackles, Integer assists, Integer safeties, Integer fgaTeens, Integer fgmTeens, Integer fgaTwenties,
            Integer fgmTwenties, Integer fgaThirties, Integer fgmThirties, Integer fgaFourties, Integer fgmFourties, Integer fgaFiftyPlus,
            Integer fgmFiftyPlus, Integer fgaTotal, Integer fgmTotal, Integer fgLong, Integer xpa, Integer xpm, Integer punts, Integer puntYds,
            Integer puntLong, Integer puntBlocked, Integer puntRet, Integer puntRetYds, Integer puntRetTds, Integer puntRetLong, Integer kickRet,
            Integer kickRetYds, Integer kickRetTds, Integer kickRetLong, Integer otherTds, Integer allTds, Integer twoPointConv,
            Integer postseasonGamesPlayed, Integer postseasonGamesStarted, Integer postseasonPassComp, Integer postseasonPassAtt, Integer postseasonPassYds,
            Integer postseasonPassTds, Integer postseasonPassInts, Integer postseasonPassLong, Double postseasonPasserRating, Integer postseasonSacked,
            Integer postseasonSackedYds, Integer postseasonRushAtt, Integer postseasonRushYds, Integer postseasonRushTds, Integer postseasonRushLong,
            Integer postseasonTargets, Integer postseasonReceptions, Integer postseasonRecYds, Integer postseasonRecTds, Integer postseasonRecLong,
            Integer postseasonFumbles, Integer postseasonInterceptions, Integer postseasonIntYds, Integer postseasonIntTds, Integer postseasonIntLong,
            Integer postseasonPassDef, Integer postseasonFumForced, Integer postseasonFumRec, Integer postseasonFumRecYds, Integer postseasonFumRecTds,
            Double postseasonSacks, Integer postseasonTackles, Integer postseasonAssists, Integer postseasonSafeties, Integer postseasonFgaTeens,
            Integer postseasonFgmTeens, Integer postseasonFgaTwenties, Integer postseasonFgmTwenties, Integer postseasonFgaThirties, Integer postseasonFgmThirties,
            Integer postseasonFgaFourties, Integer postseasonFgmFourties, Integer postseasonFgaFiftyPlus, Integer postseasonFgmFiftyPlus,
            Integer postseasonFgaTotal, Integer postseasonFgmTotal, Integer postseasonFgLong, Integer postseasonXpa, Integer postseasonXpm, Integer postseasonPunts,
            Integer postseasonPuntYds, Integer postseasonPuntLong, Integer postseasonPuntBlocked, Integer postseasonPuntRet, Integer postseasonPuntRetYds,
            Integer postseasonPuntRetTds, Integer postseasonPuntRetLong, Integer postseasonKickRet, Integer postseasonKickRetYds, Integer postseasonKickRetTds,
            Integer postseasonKickRetLong, Integer postseasonOtherTds, Integer postseasonAllTds, Integer postseasonTwoPointConv, Boolean probowl,
            Boolean allPro, Integer avgValue) {
        this.playerId = playerId;
        this.franchiseId = franchiseId;
        this.season = season;
        this.age = age;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.gamesPlayed = gamesPlayed;
        this.gamesStarted = gamesStarted;
        this.passComp = passComp;
        this.passAtt = passAtt;
        this.passYds = passYds;
        this.passTds = passTds;
        this.passInts = passInts;
        this.passLong = passLong;
        this.passerRating = passerRating;
        this.sacked = sacked;
        this.sackedYds = sackedYds;
        this.rushAtt = rushAtt;
        this.rushYds = rushYds;
        this.rushTds = rushTds;
        this.rushLong = rushLong;
        this.targets = targets;
        this.receptions = receptions;
        this.recYds = recYds;
        this.recTds = recTds;
        this.recLong = recLong;
        this.fumbles = fumbles;
        this.interceptions = interceptions;
        this.intYds = intYds;
        this.intTds = intTds;
        this.intLong = intLong;
        this.passDef = passDef;
        this.fumForced = fumForced;
        this.fumRec = fumRec;
        this.fumRecYds = fumRecYds;
        this.fumRecTds = fumRecTds;
        this.sacks = sacks;
        this.tackles = tackles;
        this.assists = assists;
        this.safeties = safeties;
        this.fgaTeens = fgaTeens;
        this.fgmTeens = fgmTeens;
        this.fgaTwenties = fgaTwenties;
        this.fgmTwenties = fgmTwenties;
        this.fgaThirties = fgaThirties;
        this.fgmThirties = fgmThirties;
        this.fgaFourties = fgaFourties;
        this.fgmFourties = fgmFourties;
        this.fgaFiftyPlus = fgaFiftyPlus;
        this.fgmFiftyPlus = fgmFiftyPlus;
        this.fgaTotal = fgaTotal;
        this.fgmTotal = fgmTotal;
        this.fgLong = fgLong;
        this.xpa = xpa;
        this.xpm = xpm;
        this.punts = punts;
        this.puntYds = puntYds;
        this.puntLong = puntLong;
        this.puntBlocked = puntBlocked;
        this.puntRet = puntRet;
        this.puntRetYds = puntRetYds;
        this.puntRetTds = puntRetTds;
        this.puntRetLong = puntRetLong;
        this.kickRet = kickRet;
        this.kickRetYds = kickRetYds;
        this.kickRetTds = kickRetTds;
        this.kickRetLong = kickRetLong;
        this.otherTds = otherTds;
        this.allTds = allTds;
        this.twoPointConv = twoPointConv;
        this.postseasonGamesPlayed = postseasonGamesPlayed;
        this.postseasonGamesStarted = postseasonGamesStarted;
        this.postseasonPassComp = postseasonPassComp;
        this.postseasonPassAtt = postseasonPassAtt;
        this.postseasonPassYds = postseasonPassYds;
        this.postseasonPassTds = postseasonPassTds;
        this.postseasonPassInts = postseasonPassInts;
        this.postseasonPassLong = postseasonPassLong;
        this.postseasonPasserRating = postseasonPasserRating;
        this.postseasonSacked = postseasonSacked;
        this.postseasonSackedYds = postseasonSackedYds;
        this.postseasonRushAtt = postseasonRushAtt;
        this.postseasonRushYds = postseasonRushYds;
        this.postseasonRushTds = postseasonRushTds;
        this.postseasonRushLong = postseasonRushLong;
        this.postseasonTargets = postseasonTargets;
        this.postseasonReceptions = postseasonReceptions;
        this.postseasonRecYds = postseasonRecYds;
        this.postseasonRecTds = postseasonRecTds;
        this.postseasonRecLong = postseasonRecLong;
        this.postseasonFumbles = postseasonFumbles;
        this.postseasonInterceptions = postseasonInterceptions;
        this.postseasonIntYds = postseasonIntYds;
        this.postseasonIntTds = postseasonIntTds;
        this.postseasonIntLong = postseasonIntLong;
        this.postseasonPassDef = postseasonPassDef;
        this.postseasonFumForced = postseasonFumForced;
        this.postseasonFumRec = postseasonFumRec;
        this.postseasonFumRecYds = postseasonFumRecYds;
        this.postseasonFumRecTds = postseasonFumRecTds;
        this.postseasonSacks = postseasonSacks;
        this.postseasonTackles = postseasonTackles;
        this.postseasonAssists = postseasonAssists;
        this.postseasonSafeties = postseasonSafeties;
        this.postseasonFgaTeens = postseasonFgaTeens;
        this.postseasonFgmTeens = postseasonFgmTeens;
        this.postseasonFgaTwenties = postseasonFgaTwenties;
        this.postseasonFgmTwenties = postseasonFgmTwenties;
        this.postseasonFgaThirties = postseasonFgaThirties;
        this.postseasonFgmThirties = postseasonFgmThirties;
        this.postseasonFgaFourties = postseasonFgaFourties;
        this.postseasonFgmFourties = postseasonFgmFourties;
        this.postseasonFgaFiftyPlus = postseasonFgaFiftyPlus;
        this.postseasonFgmFiftyPlus = postseasonFgmFiftyPlus;
        this.postseasonFgaTotal = postseasonFgaTotal;
        this.postseasonFgmTotal = postseasonFgmTotal;
        this.postseasonFgLong = postseasonFgLong;
        this.postseasonXpa = postseasonXpa;
        this.postseasonXpm = postseasonXpm;
        this.postseasonPunts = postseasonPunts;
        this.postseasonPuntYds = postseasonPuntYds;
        this.postseasonPuntLong = postseasonPuntLong;
        this.postseasonPuntBlocked = postseasonPuntBlocked;
        this.postseasonPuntRet = postseasonPuntRet;
        this.postseasonPuntRetYds = postseasonPuntRetYds;
        this.postseasonPuntRetTds = postseasonPuntRetTds;
        this.postseasonPuntRetLong = postseasonPuntRetLong;
        this.postseasonKickRet = postseasonKickRet;
        this.postseasonKickRetYds = postseasonKickRetYds;
        this.postseasonKickRetTds = postseasonKickRetTds;
        this.postseasonKickRetLong = postseasonKickRetLong;
        this.postseasonOtherTds = postseasonOtherTds;
        this.postseasonAllTds = postseasonAllTds;
        this.postseasonTwoPointConv = postseasonTwoPointConv;
        this.probowl = probowl;
        this.allPro = allPro;
        this.avgValue = avgValue;
    }

    /**
     * @return The unique identifier of the player.
     */
    @JsonIgnore
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId  The unique identifier of the player.
     */
    @JsonProperty
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return The unique identifier of the football team the player played
     *         for this season.
     */
    public String getFranchiseId() {
        return franchiseId;
    }

    /**
     * @param teamId  The unique identifier of the football team the player played
     *                for this season.
     */
    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    /**
     * @return The season (year) these statistics were generated.
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * @param season  The season (year) these statistics were generated.
     */
    public void setSeason(Integer season) {
        this.season = season;
    }

    /**
     * @return The team the player played for this season.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team  The team the player played for this season.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * @return The age of the player during this season.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age  The age of the player during this season.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return The primary position the player played this season.
     */
     public Position getPosition() {
        return position;
    }

    /**
     * @param position  The primary position the player played this season.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return The jersey number worn by the player this season.
     */
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    /**
     * @param jerseyNumber  The jersey number worn by the player this season.
     */
    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    /**
     * @return The number of games the player appeared in this season.
     */
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @param gamesPlayed  The number of games the player appeared in this season.
     */
    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * @return The number of games the player started this season.
     */
    public Integer getGamesStarted() {
        return gamesStarted;
    }

    /**
     * @param gamesStarted  The number of games the player started this season.
     */
    public void setGamesStarted(Integer gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    /**
     * @return The number of passes completed by the player this season.
     */
    public Integer getPassComp() {
        return passComp;
    }

    /**
     * @param passComp  The number of passes completed by the player this season.
     */
    public void setPassComp(Integer passComp) {
        this.passComp = passComp;
    }

    /**
     * @return The number of passes attempted by the player this season.
     */
    public Integer getPassAtt() {
        return passAtt;
    }

    /**
     * @param passAtt  The number of passes attempted by the player this season.
     */
    public void setPassAtt(Integer passAtt) {
        this.passAtt = passAtt;
    }

    /**
     * @return The number of passing yards thrown by the player this season.
     */
    public Integer getPassYds() {
        return passYds;
    }

    /**
     * @param passYds  The number of passing yards thrown by the player this season.
     */
    public void setPassYds(Integer passYds) {
        this.passYds = passYds;
    }

    /**
     * @return The number of passing touchdowns thrown by the player this season.
     */
    public Integer getPassTds() {
        return passTds;
    }

    /**
     * @param passTds  The number of passing touchdowns thrown by the player this season.
     */
    public void setPassTds(Integer passTds) {
        this.passTds = passTds;
    }

    /**
     * @return The number of interceptions thrown by the player this season.
     */
    public Integer getPassInts() {
        return passInts;
    }

    /**
     * @param passInts  The number of interceptions thrown by the player this season.
     */
    public void setPassInts(Integer passInts) {
        this.passInts = passInts;
    }

    /**
     * @return The longest pass (yds) thrown by the player this season.
     */
    public Integer getPassLong() {
        return passLong;
    }

    /**
     * @param passLong  The longest pass (yds) thrown by the player this season.
     */
    public void setPassLong(Integer passLong) {
        this.passLong = passLong;
    }

    /**
     * @return The player's passer rating for the season.
     */
    public Double getPasserRating() {
        return passerRating;
    }

    /**
     * @param passerRating  The player's passer rating for this season.
     */
    public void setPassRating(Double passerRating) {
        this.passerRating = passerRating;
    }

    /**
     * @return The number of times the player was sacked this season.
     */
    public Integer getSacked() {
        return sacked;
    }

    /**
     * @param sacked  The number of times the player was sacked this season.
     */
    public void setSacked(Integer sacked) {
        this.sacked = sacked;
    }

    /**
     * @return The number yards lost when the player was sacked this season.
     */
    public Integer getSackedYds() {
        return sackedYds;
    }

    /**
     * @param sacked  The number yards lost when the player was sacked this season.
     */
    public void setSackedYds(Integer sackedYds) {
        this.sackedYds = sackedYds;
    }

    /**
     * @return The number of rushes attempted by the player this season.
     */
    public Integer getRushAtt() {
        return rushAtt;
    }

    /**
     * @param rushAtt  The number of rushes attempted by the player this season.
     */
    public void setRushAtt(Integer rushAtt) {
        this.rushAtt = rushAtt;
    }

    /**
     * @return The number of rushing yards gained by the player this season.
     */
    public Integer getRushYds() {
        return rushYds;
    }

    /**
     * @param rushYds  The number of rushing yards gained by the player this season.
     */
    public void setRushYds(Integer rushYds) {
        this.rushYds = rushYds;
    }

    /**
     * @return The number of rushing touchdowns scored by the player this season.
     */
    public Integer getRushTds() {
        return rushTds;
    }

    /**
     * @param rushTds  The number of rushing touchdowns scored by the player this season.
     */
    public void setRushTds(Integer rushTds) {
        this.rushTds = rushTds;
    }

    /**
     * @return The longest rush made by the player this season.
     */
    public Integer getRushLong() {
        return rushLong;
    }

    /**
     * @param rushLong  The longest rush made by the player this season.
     */
    public void setRushLong(Integer rushLong) {
        this.rushLong = rushLong;
    }

    /**
     * @return The number of passes thrown to the player this season.
     */
    public Integer getTargets() {
        return targets;
    }

    /**
     * @param targets  The number of passes thrown to the player this season.
     */
    public void setTargets(Integer targets) {
        this.targets = targets;
    }

    /**
     * @return The number of passes caught by the player this season.
     */
    public Integer getReceptions() {
        return receptions;
    }

    /**
     * @param receptions  The number of passes caught by the player this season.
     */
    public void setReceptions(Integer receptions) {
        this.receptions = receptions;
    }

    /**
     * @return The number of receiving yards gained by the player this season.
     */
    public Integer getRecYds() {
        return recYds;
    }

    /**
     * @param recYds  The number of receiving yards gained by the player this season.
     */
    public void setRecYds(Integer recYds) {
        this.recYds = recYds;
    }

    /**
     * @return The number of receiving touchdowns scored by the player this season.
     */
    public Integer getRecTds() {
        return recTds;
    }

    /**
     * @param recTds  The number of receiving touchdowns scored by the player this season.
     */
    public void setRecTds(Integer recTds) {
        this.recTds = recTds;
    }

    /**
     * @return The longest reception (yds) made by the player this season.
     */
    public Integer getRecLong() {
        return recLong;
    }

    /**
     * @param recLong  The longest reception (yds) made by the player this season.
     */
    public void setRecLong(Integer recLong) {
        this.recLong = recLong;
    }

    /**
     * @return The number of times the player has fumbled this season.
     */
    public Integer getFumbles() {
        return fumbles;
    }

    /**
     * @param fumbles  The number of times the player has fumbled this season.
     */
    public void setFumbles(Integer fumbles) {
        this.fumbles = fumbles;
    }

    /**
     * @return The number of passes intercepted by the player this season.
     */
    public Integer getInterceptions() {
        return interceptions;
    }

    /**
     * @param interceptions  The number of passes intercepted by the player this season.
     */
    public void setInterceptions(Integer interceptions) {
        this.interceptions = interceptions;
    }

    /**
     * @return The number of interception return yards gained by the player this season.
     */
    public Integer getIntYds() {
        return intYds;
    }

    /**
     * @param intYds  The number of interception return yards gained by the player this
     *                season.
     */
    public void setIntYds(Integer intYds) {
        this.intYds = intYds;
    }

    /**
     * @return The number of touchdowns scored by the player on interception returns
     *         this season.
     */
    public Integer getIntTds() {
        return intTds;
    }

    /**
     * @param intTds  The number of touchdowns scored by the player on interception returns
     *                this season.
     */
    public void setIntTds(Integer intTds) {
        this.intTds = intTds;
    }

    /**
     * @return The longest return (yds) of an interception made by the player this season.
     */
    public Integer getIntLong() {
        return intLong;
    }

    /**
     * @param intLong  The longest return (yds) of an interception made by the player
     *                 this season.
     */
    public void setIntLong(Integer intLong) {
        this.intLong = intLong;
    }

    /**
     * @return The number of passes defended by the player this season.
     */
    public Integer getPassDef() {
        return passDef;
    }

    /**
     * @param passDef  The number of passes defended by the player this season.
     */
    public void setPassDef(Integer passDef) {
        this.passDef = passDef;
    }

    /**
     * @return The number of fumbles forced by the player this season.
     */
    public Integer getFumForced() {
        return fumForced;
    }

    /**
     * @param fumForced  The number of fumbles forced by the player this season.
     */
    public void setFumForced(Integer fumForced) {
        this.fumForced = fumForced;
    }

    /**
     * @return The number of fumbles recovered by the player this season.
     */
    public Integer getFumRec() {
        return fumRec;
    }

    /**
     * @param fumRec  The number of fumbles recovered by the player this season.
     */
    public void setFumRec(Integer fumRec) {
        this.fumRec = fumRec;
    }

    /**
     * @return The number of fumble return yards gained by the player this season.
     */
    public Integer getFumRecYds() {
        return fumRecYds;
    }

    /**
     * @param fumRecYds  The number of fumble return yards gained by the player
     *                   this season.
     */
    public void setFumRecYds(Integer fumRecYds) {
        this.fumRecYds = fumRecYds;
    }

    /**
     * @return The number of touchdowns scored by the player on fumble returns
     *         this season.
     */
    public Integer getFumRecTds() {
        return fumRecTds;
    }

    /**
     * @param fumRecTds  The number of touchdowns scored by the player on fumble
     *                   returns this season.
     */
    public void setFumRecTds(Integer fumRecTds) {
        this.fumRecTds = fumRecTds;
    }

    /**
     * @return The number of quarterback sacks made by the player this season.
     */
    public Double getSacks() {
        return sacks;
    }

    /**
     * @param sacks  The number of quarterback sacks made by the player this season.
     */
    public void setSacks(Double sacks) {
        this.sacks = sacks;
    }

    /**
     * @return The number of solo tackles made by the player this season.
     */
    public Integer getTackles() {
        return tackles;
    }

    /**
     * @param tackles  The number of solo tackles made by the player this season.
     */
    public void setTackles(Integer tackles) {
        this.tackles = tackles;
    }

    /**
     * @return The number of tackle assists made by the player this season.
     */
    public Integer getAssists() {
        return assists;
    }

    /**
     * @param assists  The number of tackle assists made by the player this season.
     */
    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    /**
     * @return The number of safeties made by the player this season.
     */
    public Integer getSafeties() {
        return safeties;
    }

    /**
     * @param safeties  The number of safeties made by the player this season.
     */
    public void setSafeties(Integer safeties) {
        this.safeties = safeties;
    }

    /**
     * @return The number of 17-19 yard field goals attempted by the player this season.
     */
    public Integer getFgaTeens() {
        return fgaTeens;
    }

    /**
     * @param fgaTeens  The number of 17-19 yard field goals attempted by the player
     *                  this season.
     */
    public void setFgaTeens(Integer fgaTeens) {
        this.fgaTeens = fgaTeens;
    }

    /**
     * @return The number of successful 17-19 yard field goal attempts made by the
     *         player this season.
     */
    public Integer getFgmTeens() {
        return fgmTeens;
    }

    /**
     * @param fgmTeens  The number of successful 17-19 yard field goal attempts made
     *                  by the player this season.
     */
    public void setFgmTeens(Integer fgmTeens) {
        this.fgmTeens = fgmTeens;
    }

    /**
     * @return The number of 20-29 yard field goals attempted by the player this season.
     */
    public Integer getFgaTwenties() {
        return fgaTwenties;
    }

    /**
     * @param fgaTwenties  The number of 20-29 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaTwenties(Integer fgaTwenties) {
        this.fgaTwenties = fgaTwenties;
    }

    /**
     * @return The number of successful 20-29 yard field goal attempts made by the player
     *         this season.
     */
    public Integer getFgmTwenties() {
        return fgmTwenties;
    }

    /**
     * @param fgmTwenties  The number of successful 20-29 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmTwenties(Integer fgmTwenties) {
        this.fgmTwenties = fgmTwenties;
    }

    /**
     * @return The number of 30-39 yard field goals attempted by the player this season.
     */
    public Integer getFgaThirties() {
        return fgaThirties;
    }

    /**
     * @param fgaThirties  The number of 30-39 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaThirties(Integer fgaThirties) {
        this.fgaThirties = fgaThirties;
    }

    /**
     * @return The number of successful 30-39 yard field goal attempts made by the player
     *         this season.
     */
    public Integer getFgmThirties() {
        return fgmThirties;
    }

    /**
     * @param fgmThirties  The number of successful 30-39 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmThirties(Integer fgmThirties) {
        this.fgmThirties = fgmThirties;
    }

    /**
     * @return The number of 40-49 yard field goals attempted by the player this season.
     */
    public Integer getFgaFourties() {
        return fgaFourties;
    }

    /**
     * @param fgaFourties  The number of 40-49 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaFourties(Integer fgaFourties) {
        this.fgaFourties = fgaFourties;
    }

    /**
     * @return The number of successful 40-49 yard field goal attempts made by the player
     *         this season.
     */
    public Integer getFgmFourties() {
        return fgmFourties;
    }

    /**
     * @param fgmFourties  The number of successful 40-49 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmFourties(Integer fgmFourties) {
        this.fgmFourties = fgmFourties;
    }

    /**
     * @return The number of 50+ yard field goals attempted by the player this season.
     */
    public Integer getFgaFiftyPlus() {
        return fgaFiftyPlus;
    }

    /**
     * @param fgaFiftyPlus  The number of 50+ yard field goals attempted by the player
     *                      this season.
     */
    public void setFgaFiftyPlus(Integer fgaFiftyPlus) {
        this.fgaFiftyPlus = fgaFiftyPlus;
    }

    /**
     * @return The number of successful 50+ yard field goal attempts made by the player
     *         this season.
     */
    public Integer getFgmFiftyPlus() {
        return fgmFiftyPlus;
    }

    /**
     * @param fgmFiftyPlus  The number of successful 50+ yard field goal attempts made
     *                      by the player this season.
     */
    public void setFgmFiftyPlus(Integer fgmFiftyPlus) {
        this.fgmFiftyPlus = fgmFiftyPlus;
    }

    /**
     * @return The total number of field goals attempted by the player this season.
     */
    public Integer getFgaTotal() {
        return fgaTotal;
    }

    /**
     * @param fgaTotal  The total number of field goals attempted by the player this
     *                  season.
     */
    public void setFgaTotal(Integer fgaTotal) {
        this.fgaTotal = fgaTotal;
    }

    /**
     * @return The total number of successful field goal attempts made by the player
     *         this season.
     */
    public Integer getFgmTotal() {
        return fgmTotal;
    }

    /**
     * @param fgmTotal  The total number of successful field goal attempts made by the
     *                  player this season.
     */
    public void setFgmTotal(Integer fgmTotal) {
        this.fgmTotal = fgmTotal;
    }

    /**
     * @return The longest successful field goal (yds) attempt made by the player
     *         this season.
     */
    public Integer getFgLong() {
        return fgLong;
    }

    /**
     * @param fgLong  The longest successful field goal (yds) attempt made by the
     *                player this season.
     */
    public void setFgLong(Integer fgLong) {
        this.fgLong = fgLong;
    }

    /**
     * @return The total number of extra points attempted by the player this season.
     */
    public Integer getXpa() {
        return xpa;
    }

    /**
     * @param xpa  The total number of extra points attempted by the player this season.
     */
    public void setXpa(Integer xpa) {
        this.xpa = xpa;
    }

    /**
     * @return The total number of successful extra point attempts made by the player
     *         this season.
     */
    public Integer getXpm() {
        return xpm;
    }

    /**
     * @param xpm  The total number of successful extra point attempts made by the player
     *             this season.
     */
    public void setXpm(Integer xpm) {
        this.xpm = xpm;
    }

    /**
     * @return The total number of punts made by the player this season.
     */
    public Integer getPunts() {
        return punts;
    }

    /**
     * @param punts  The total number of punts made by the player this season.
     */
    public void setPunts(Integer punts) {
        this.punts = punts;
    }

    /**
     * @return The total number of punt yards made by the player this season.
     */
    public Integer getPuntYds() {
        return puntYds;
    }

    /**
     * @param puntYds  The total number of punt yards made by the player this season.
     */
    public void setPuntYds(Integer puntYds) {
        this.puntYds = puntYds;
    }

    /**
     * @return The longest punt (yds) made by the player this season.
     */
    public Integer getPuntLong() {
        return puntLong;
    }

    /**
     * @param puntLong  The longest punt (yds) made by the player this season.
     */
    public void setPuntLong(Integer puntLong) {
        this.puntLong = puntLong;
    }

    /**
     * @return The number of the player's punts that were blocked this season.
     */
    public Integer getPuntBlocked() {
        return puntBlocked;
    }

    /**
     * @param puntBlocked  The number of the player's punts that were blocked this season.
     */
    public void setPuntBlocked(Integer puntBlocked) {
        this.puntBlocked = puntBlocked;
    }

    /**
     * @return The number of punts returned by the player this season.
     */
    public Integer getPuntRet() {
        return puntRet;
    }

    /**
     * @param puntRet  The number of punts returned by the player this season.
     */
    public void setPuntRet(Integer puntRet) {
        this.puntRet = puntRet;
    }

    /**
     * @return The number of punt return yards gained by the player this season.
     */
    public Integer getPuntRetYds() {
        return puntRetYds;
    }

    /**
     * @param puntRetYds  The number of punt return yards gained by the player this season.
     */
    public void setPuntRetYds(Integer puntRetYds) {
        this.puntRetYds = puntRetYds;
    }

    /**
     * @return The number of punts returned for touchdowns by the player this season.
     */
    public Integer getPuntRetTds() {
        return puntRetTds;
    }

    /**
     * @param puntRetTds  The number of punts returned for touchdowns by the player this season.
     */
    public void setPuntRetTds(Integer puntRetTds) {
        this.puntRetTds = puntRetTds;
    }

    /**
     * @return The longest punt return (yds) made by the player this season.
     */
    public Integer getPuntRetLong() {
        return puntRetLong;
    }

    /**
     * @param puntRetLong  The longest punt return (yds) made by the player this season.
     */
    public void setPuntRetLong(Integer puntRetLong) {
        this.puntRetLong = puntRetLong;
    }

    /**
     * @return The number of kickoffs returned by the player this season.
     */
    public Integer getKickRet() {
        return kickRet;
    }

    /**
     * @param kickRet  The number of kickoffs returned by the player this season.
     */
    public void setKickRet(Integer kickRet) {
        this.kickRet = kickRet;
    }

    /**
     * @return The number of kickoff return yards gained by the player this season.
     */
    public Integer getKickRetYds() {
        return kickRetYds;
    }

    /**
     * @param kickRetYds  The number of kickoff return yards gained by the player this season.
     */
    public void setKickRetYds(Integer kickRetYds) {
        this.kickRetYds = kickRetYds;
    }

    /**
     * @return The number of kickoffs returned for touchdowns by the player this season.
     */
    public Integer getKickRetTds() {
        return kickRetTds;
    }

    /**
     * @param kickRetTds  The number of kickoffs returned for touchdowns by the player
     *                    this season.
     */
    public void setKickRetTds(Integer kickRetTds) {
        this.kickRetTds = kickRetTds;
    }

    /**
     * @return The longest kick return (yds) made by the player this season.
     */
    public Integer getKickRetLong() {
        return kickRetLong;
    }

    /**
     * @param kickRetLong  The longest kick return (yds) made by the player this season.
     */
    public void setKickRetLong(Integer kickRetLong) {
        this.kickRetLong = kickRetLong;
    }

    /**
     * @return The total number of touchdowns scored via other means by the player this season.
     */
    public Integer getOtherTds() {
        return otherTds;
    }

    /**
     * @param otherTds  The total number of touchdowns scored via other means by the player
     *                  this season.
     */
    public void setOtherTds(Integer otherTds) {
        this.otherTds = otherTds;
    }

    /**
     * @return The total number of touchdowns scored by the player this season.
     */
    public Integer getAllTds() {
        return allTds;
    }

    /**
     * @param allTds  The total number of touchdowns scored by the player this season.
     */
    public void setAllTds(Integer allTds) {
        this.allTds = allTds;
    }

    /**
     * @return The total number of two point conversions scored by the player this season.
     */
    public Integer getTwoPointConv() {
        return twoPointConv;
    }

    /**
     * @param twoPointConv  The total number of two point conversions scored by the player
     *                      this season.
     */
    public void setTwoPointConv(Integer twoPointConv) {
        this.twoPointConv = twoPointConv;
    }

    /**
     * @return The number of games the player appeared in this postseason.
     */
    public Integer getPostseasonGamesPlayed() {
        return postseasonGamesPlayed;
    }

    /**
     * @param gamesPlayed  The number of games the player appeared in this postseason.
     */
    public void setPostseasonGamesPlayed(Integer postseasonGamesPlayed) {
        this.postseasonGamesPlayed = postseasonGamesPlayed;
    }

    /**
     * @return The number of games the player started this postseason.
     */
    public Integer getPostseasonGamesStarted() {
        return postseasonGamesStarted;
    }

    /**
     * @param postseasonGamesStarted  The number of games the player started this
     *                                postseason.
     */
    public void setPostseasonGamesStarted(Integer postseasonGamesStarted) {
        this.postseasonGamesStarted = postseasonGamesStarted;
    }

    /**
     * @return The number of passes completed by the player this postseason.
     */
    public Integer getPostseasonPassComp() {
        return postseasonPassComp;
    }

    /**
     * @param postseasonPassComp  The number of passes completed by the player this
     *                            postseason.
     */
    public void setPostseasonPassComp(Integer postseasonPassComp) {
        this.postseasonPassComp = postseasonPassComp;
    }

    /**
     * @return The number of passes attempted by the player this postseason.
     */
    public Integer getPostseasonPassAtt() {
        return postseasonPassAtt;
    }

    /**
     * @param postseasonPassAtt  The number of passes attempted by the player this
     *                           postseason.
     */
    public void setPostseasonPassAtt(Integer postseasonPassAtt) {
        this.postseasonPassAtt = postseasonPassAtt;
    }

    /**
     * @return The number of passing yards thrown by the player this postseason.
     */
    public Integer getPostseasonPassYds() {
        return postseasonPassYds;
    }

    /**
     * @param postseasonPassYds  The number of passing yards thrown by the player this
     *                           postseason.
     */
    public void setPostseasonPassYds(Integer postseasonPassYds) {
        this.postseasonPassYds = postseasonPassYds;
    }

    /**
     * @return The number of passing touchdowns thrown by the player this postseason.
     */
    public Integer getPostseasonPassTds() {
        return postseasonPassTds;
    }

    /**
     * @param postseasonPassTds  The number of passing touchdowns thrown by the player
     *                           this postseason.
     */
    public void setPostseasonPassTds(Integer postseasonPassTds) {
        this.postseasonPassTds = postseasonPassTds;
    }

    /**
     * @return The number of interceptions thrown by the player this postseason.
     */
    public Integer getPostseasonPassInts() {
        return postseasonPassInts;
    }

    /**
     * @param postseasonPassInts  The number of interceptions thrown by the player this
     *                            postseason.
     */
    public void setPostseasonPassInts(Integer postseasonPassInts) {
        this.postseasonPassInts = postseasonPassInts;
    }

    /**
     * @return The longest pass (yds) thrown by the player this postseason.
     */
    public Integer getPostseasonPassLong() {
        return postseasonPassLong;
    }

    /**
     * @param passLong  The longest pass (yds) thrown by the player this postseason.
     */
    public void setPostseasonPassLong(Integer postseasonPassLong) {
        this.postseasonPassLong = postseasonPassLong;
    }

    /**
     * @return The player's passer rating for this postseason.
     */
    public Double getPostseasonPasserRating() {
        return postseasonPasserRating;
    }

    /**
     * @param postseasonPasserRating  The player's passer rating for this postseason.
     */
    public void setPostseasonPassRating(Double postseasonPasserRating) {
        this.postseasonPasserRating = postseasonPasserRating;
    }

    /**
     * @return The number of times the player was sacked this postseason.
     */
    public Integer getPostseasonSacked() {
        return postseasonSacked;
    }

    /**
     * @param postseasonSacked  The number of times the player was sacked this postseason.
     */
    public void setPostseasonSacked(Integer postseasonSacked) {
        this.postseasonSacked = postseasonSacked;
    }

    /**
     * @return The number yards lost when the player was sacked this postseason.
     */
    public Integer getPostseasonSackedYds() {
        return postseasonSackedYds;
    }

    /**
     * @param postseasonSackedYds  The number yards lost when the player was sacked
     *                             this postseason.
     */
    public void setPostseasonSackedYds(Integer postseasonSackedYds) {
        this.postseasonSackedYds = postseasonSackedYds;
    }

    /**
     * @return The number of rushes attempted by the player this postseason.
     */
    public Integer getPostseasonRushAtt() {
        return postseasonRushAtt;
    }

    /**
     * @param postseasonRushAtt  The number of rushes attempted by the player this
     *                           postseason.
     */
    public void setPostseasonRushAtt(Integer postseasonRushAtt) {
        this.postseasonRushAtt = postseasonRushAtt;
    }

    /**
     * @return The number of rushing yards gained by the player this postseason.
     */
    public Integer getPostseasonRushYds() {
        return postseasonRushYds;
    }

    /**
     * @param postseasonRushYds  The number of rushing yards gained by the player this
     *                           postseason.
     */
    public void setPostseasonRushYds(Integer postseasonRushYds) {
        this.postseasonRushYds = postseasonRushYds;
    }

    /**
     * @return The number of rushing touchdowns scored by the player this postseason.
     */
    public Integer getPostseasonRushTds() {
        return postseasonRushTds;
    }

    /**
     * @param postseasonRushTds  The number of rushing touchdowns scored by the player
     *                           this postseason.
     */
    public void setPostseasonRushTds(Integer postseasonRushTds) {
        this.postseasonRushTds = postseasonRushTds;
    }

    /**
     * @return The longest rush made by the player this postseason.
     */
    public Integer getPostseasonRushLong() {
        return postseasonRushLong;
    }

    /**
     * @param postseasonRushLong  The longest rush made by the player this postseason.
     */
    public void setPostseasonRushLong(Integer postseasonRushLong) {
        this.postseasonRushLong = postseasonRushLong;
    }

    /**
     * @return The number of passes thrown to the player this postseason.
     */
    public Integer getPostseasonTargets() {
        return postseasonTargets;
    }

    /**
     * @param postseasonTargets  The number of passes thrown to the player this postseason.
     */
    public void setPostseasonTargets(Integer postseasonTargets) {
        this.postseasonTargets = postseasonTargets;
    }

    /**
     * @return The number of passes caught by the player this postseason.
     */
    public Integer getPostseasonReceptions() {
        return postseasonReceptions;
    }

    /**
     * @param postseasonReceptions  The number of passes caught by the player this postseason.
     */
    public void setPostseasonReceptions(Integer postseasonReceptions) {
        this.postseasonReceptions = postseasonReceptions;
    }

    /**
     * @return The number of receiving yards gained by the player this postseason.
     */
    public Integer getPostseasonRecYds() {
        return postseasonRecYds;
    }

    /**
     * @param postseasonRecYds  The number of receiving yards gained by the player
     *                          this postseason.
     */
    public void setPostseasonRecYds(Integer postseasonRecYds) {
        this.postseasonRecYds = postseasonRecYds;
    }

    /**
     * @return The number of receiving touchdowns scored by the player this postseason.
     */
    public Integer getPostseasonRecTds() {
        return postseasonRecTds;
    }

    /**
     * @param postseasonRecTds  The number of receiving touchdowns scored by the player
     *                          this postseason.
     */
    public void setPostseasonRecTds(Integer postseasonRecTds) {
        this.postseasonRecTds = postseasonRecTds;
    }

    /**
     * @return The longest reception (yds) made by the player this postseason.
     */
    public Integer getPostseasonRecLong() {
        return postseasonRecLong;
    }

    /**
     * @param postseasonRecLong  The longest reception (yds) made by the player this
     *                           postseason.
     */
    public void setPostseasonRecLong(Integer postseasonRecLong) {
        this.postseasonRecLong = postseasonRecLong;
    }

    /**
     * @return The number of times the player has fumbled this postseason.
     */
    public Integer getPostseasonFumbles() {
        return postseasonFumbles;
    }

    /**
     * @param postseasonFumbles  The number of times the player has fumbled this postseason.
     */
    public void setPostseasonFumbles(Integer postseasonFumbles) {
        this.postseasonFumbles = postseasonFumbles;
    }

    /**
     * @return The number of passes intercepted by the player this postseason.
     */
    public Integer getPostseasonInterceptions() {
        return postseasonInterceptions;
    }

    /**
     * @param postseasonInterceptions  The number of passes intercepted by the player this
     *                                 postseason.
     */
    public void setPostseasonInterceptions(Integer postseasonInterceptions) {
        this.postseasonInterceptions = postseasonInterceptions;
    }

    /**
     * @return The number of interception return yards gained by the player this postseason.
     */
    public Integer getPostseasonIntYds() {
        return postseasonIntYds;
    }

    /**
     * @param postseasonIntYds  The number of interception return yards gained by the player this
     *                          postseason.
     */
    public void setPostseasonIntYds(Integer postseasonIntYds) {
        this.postseasonIntYds = postseasonIntYds;
    }

    /**
     * @return The number of touchdowns scored by the player on interception returns
     *         this postseason.
     */
    public Integer getPostseasonIntTds() {
        return postseasonIntTds;
    }

    /**
     * @param postseasonIntTds  The number of touchdowns scored by the player on interception returns
     *                          this postseason.
     */
    public void setPostseasonIntTds(Integer postseasonIntTds) {
        this.postseasonIntTds = postseasonIntTds;
    }

    /**
     * @return The longest return (yds) of an interception made by the player this postseason.
     */
    public Integer getPostseasonIntLong() {
        return postseasonIntLong;
    }

    /**
     * @param postseasonIntLong  The longest return (yds) of an interception made by the player
     *                           this postseason.
     */
    public void setPostseasonIntLong(Integer postseasonIntLong) {
        this.postseasonIntLong = postseasonIntLong;
    }

    /**
     * @return The number of passes defended by the player this postseason.
     */
    public Integer getPostseasonPassDef() {
        return postseasonPassDef;
    }

    /**
     * @param postseasonPassDef  The number of passes defended by the player this postseason.
     */
    public void setPostseasonPassDef(Integer postseasonPassDef) {
        this.postseasonPassDef = postseasonPassDef;
    }

    /**
     * @return The number of fumbles forced by the player this postseason.
     */
    public Integer getPostseasonFumForced() {
        return postseasonFumForced;
    }

    /**
     * @param postseasonFumForced  The number of fumbles forced by the player this postseason.
     */
    public void setPostseasonFumForced(Integer postseasonFumForced) {
        this.postseasonFumForced = postseasonFumForced;
    }

    /**
     * @return The number of fumbles recovered by the player this postseason.
     */
    public Integer getPostseasonFumRec() {
        return postseasonFumRec;
    }

    /**
     * @param postseasonFumRec  The number of fumbles recovered by the player this postseason.
     */
    public void setPostseasonFumRec(Integer postseasonFumRec) {
        this.postseasonFumRec = postseasonFumRec;
    }

    /**
     * @return The number of fumble return yards gained by the player this postseason.
     */
    public Integer getPostseasonFumRecYds() {
        return postseasonFumRecYds;
    }

    /**
     * @param postseasonFumRecYds  The number of fumble return yards gained by the player
     *                             this postseason.
     */
    public void setPostseasonFumRecYds(Integer postseasonFumRecYds) {
        this.postseasonFumRecYds = postseasonFumRecYds;
    }

    /**
     * @return The number of touchdowns scored by the player on fumble returns
     *         this postseason.
     */
    public Integer getPostseasonFumRecTds() {
        return postseasonFumRecTds;
    }

    /**
     * @param postseasonFumRecTds  The number of touchdowns scored by the player on fumble
     *                             returns this postseason.
     */
    public void setPostseasonFumRecTds(Integer postseasonFumRecTds) {
        this.postseasonFumRecTds = postseasonFumRecTds;
    }

    /**
     * @return The number of quarterback sacks made by the player this postseason.
     */
    public Double getPostseasonSacks() {
        return postseasonSacks;
    }

    /**
     * @param postseasonSacks  The number of quarterback sacks made by the player this
     *                         postseason.
     */
    public void setPostseasonSacks(Double postseasonSacks) {
        this.postseasonSacks = postseasonSacks;
    }

    /**
     * @return The number of solo tackles made by the player this postseason.
     */
    public Integer getPostseasonTackles() {
        return postseasonTackles;
    }

    /**
     * @param postseasonTackles  The number of solo tackles made by the player this postseason.
     */
    public void setPostseasonTackles(Integer postseasonTackles) {
        this.postseasonTackles = postseasonTackles;
    }

    /**
     * @return The number of tackle assists made by the player this postseason.
     */
    public Integer getPostseasonAssists() {
        return postseasonAssists;
    }

    /**
     * @param postseasonAssists  The number of tackle assists made by the player this postseason.
     */
    public void setPostseasonAssists(Integer postseasonAssists) {
        this.postseasonAssists = postseasonAssists;
    }

    /**
     * @return The number of safeties made by the player this postseason.
     */
    public Integer getPostseasonSafeties() {
        return postseasonSafeties;
    }

    /**
     * @param postseasonSafeties  The number of safeties made by the player this postseason.
     */
    public void setPostseasonSafeties(Integer postseasonSafeties) {
        this.postseasonSafeties = postseasonSafeties;
    }

    /**
     * @return The number of 17-19 yard field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaTeens() {
        return postseasonFgaTeens;
    }

    /**
     * @param postseasonFgaTeens  The number of 17-19 yard field goals attempted by the player
     *                            this postseason.
     */
    public void setPostseasonFgaTeens(Integer postseasonFgaTeens) {
        this.postseasonFgaTeens = postseasonFgaTeens;
    }

    /**
     * @return The number of successful 17-19 yard field goal attempts made by the player
     *         this postseason.
     */
    public Integer getPostseasonFgmTeens() {
        return postseasonFgmTeens;
    }

    /**
     * @param postseasonFgmTeens  The number of successful 17-19 yard field goal attempts made
     *                            by the player this postseason.
     */
    public void setPostseasonFgmTeens(Integer postseasonFgmTeens) {
        this.postseasonFgmTeens = postseasonFgmTeens;
    }

    /**
     * @return The number of 20-29 yard field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaTwenties() {
        return postseasonFgaTwenties;
    }

    /**
     * @param postseasonFgaTwenties  The number of 20-29 yard field goals attempted by the player
     *                               this postseason.
     */
    public void setPostseasonFgaTwenties(Integer postseasonFgaTwenties) {
        this.postseasonFgaTwenties = postseasonFgaTwenties;
    }

    /**
     * @return The number of successful 20-29 yard field goal attempts made by the player
     *         this postseason.
     */
    public Integer getPostseasonFgmTwenties() {
        return postseasonFgmTwenties;
    }

    /**
     * @param postseasonFgmTwenties  The number of successful 20-29 yard field goal attempts
     *                               made by the player this postseason.
     */
    public void setPostseasonFgmTwenties(Integer postseasonFgmTwenties) {
        this.postseasonFgmTwenties = postseasonFgmTwenties;
    }

    /**
     * @return The number of 30-39 yard field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaThirties() {
        return postseasonFgaThirties;
    }

    /**
     * @param postseasonFgaThirties  The number of 30-39 yard field goals attempted by the player
     *                               this postseason.
     */
    public void setPostseasonFgaThirties(Integer postseasonFgaThirties) {
        this.postseasonFgaThirties = postseasonFgaThirties;
    }

    /**
     * @return The number of successful 30-39 yard field goal attempts made by the player
     *         this postseason.
     */
    public Integer getPostseasonFgmThirties() {
        return postseasonFgmThirties;
    }

    /**
     * @param postseasonFgmThirties  The number of successful 30-39 yard field goal attempts
     *                               made by the player this postseason.
     */
    public void setPostseasonFgmThirties(Integer postseasonFgmThirties) {
        this.postseasonFgmThirties = postseasonFgmThirties;
    }

    /**
     * @return The number of 40-49 yard field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaFourties() {
        return postseasonFgaFourties;
    }

    /**
     * @param postseasonFgaFourties  The number of 40-49 yard field goals attempted by the player
     *                               this postseason.
     */
    public void setPostseasonFgaFourties(Integer postseasonFgaFourties) {
        this.postseasonFgaFourties = postseasonFgaFourties;
    }

    /**
     * @return The number of successful 40-49 yard field goal attempts made by the player
     *         this postseason.
     */
    public Integer getPostseasonFgmFourties() {
        return postseasonFgmFourties;
    }

    /**
     * @param postseasonFgmFourties  The number of successful 40-49 yard field goal attempts made
     *                               by the player this postseason.
     */
    public void setPostseasonFgmFourties(Integer postseasonFgmFourties) {
        this.postseasonFgmFourties = postseasonFgmFourties;
    }

    /**
     * @return The number of 50+ yard field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaFiftyPlus() {
        return postseasonFgaFiftyPlus;
    }

    /**
     * @param postseasonFgaFiftyPlus  The number of 50+ yard field goals attempted by the player
     *                                this postseason.
     */
    public void setPostseasonFgaFiftyPlus(Integer postseasonFgaFiftyPlus) {
        this.postseasonFgaFiftyPlus = postseasonFgaFiftyPlus;
    }

    /**
     * @return The number of successful 50+ yard field goal attempts made by the player this
     *         postseason.
     */
    public Integer getPostseasonFgmFiftyPlus() {
        return postseasonFgmFiftyPlus;
    }

    /**
     * @param postseasonFgmFiftyPlus  The number of successful 50+ yard field goal attempts made
     *                                by the player this postseason.
     */
    public void setPostseasonFgmFiftyPlus(Integer postseasonFgmFiftyPlus) {
        this.postseasonFgmFiftyPlus = postseasonFgmFiftyPlus;
    }

    /**
     * @return The total number of field goals attempted by the player this postseason.
     */
    public Integer getPostseasonFgaTotal() {
        return postseasonFgaTotal;
    }

    /**
     * @param postseasonFgaTotal  The total number of field goals attempted by the player
     *                            this postseason.
     */
    public void setPostseasonFgaTotal(Integer postseasonFgaTotal) {
        this.postseasonFgaTotal = postseasonFgaTotal;
    }

    /**
     * @return The total number of successful field goal attempts made by the player this
     *         postseason.
     */
    public Integer getPostseasonFgmTotal() {
        return postseasonFgmTotal;
    }

    /**
     * @param fgmTotal  The total number of successful field goal attempts made by the player
     *                  this season.
     */
    public void setPostseasonFgmTotal(Integer postseasonFgmTotal) {
        this.postseasonFgmTotal = postseasonFgmTotal;
    }

    /**
     * @return The longest field goal (yds) made by the player this postseason.
     */
    public Integer getPostseasonFgLong() {
        return postseasonFgLong;
    }

    /**
     * @param postseasonFgLong  The longest field goal (yds) made by the player this
     *                          postseason.
     */
    public void setPostseasonFgLong(Integer postseasonFgLong) {
        this.postseasonFgLong = postseasonFgLong;
    }

    /**
     * @return The total number of extra points attempted by the player this postseason.
     */
    public Integer getPostseasonXpa() {
        return postseasonXpa;
    }

    /**
     * @param postseasonXpa  The total number of extra points attempted by the player this
     *                       postseason.
     */
    public void setPostseasonXpa(Integer postseasonXpa) {
        this.postseasonXpa = postseasonXpa;
    }

    /**
     * @return The total number of successful extra point attempts made by the player
     *         this postseason.
     */
    public Integer getPostseasonXpm() {
        return postseasonXpm;
    }

    /**
     * @param postseasonXpm  The total number of successful extra point attempts made by the
     *                       player this postseason.
     */
    public void setPostseasonXpm(Integer postseasonXpm) {
        this.postseasonXpm = postseasonXpm;
    }

    /**
     * @return The total number of punts made by the player this postseason.
     */
    public Integer getPostseasonPunts() {
        return postseasonPunts;
    }

    /**
     * @param postseasonPunts  The total number of punts made by the player this postseason.
     */
    public void setPostseasonPunts(Integer postseasonPunts) {
        this.postseasonPunts = postseasonPunts;
    }

    /**
     * @return The total number of punt yards made by the player this postseason.
     */
    public Integer getPostseasonPuntYds() {
        return postseasonPuntYds;
    }

    /**
     * @param postseasonPuntYds  The total number of punt yards made by the player this
     *                           postseason.
     */
    public void setPostseasonPuntYds(Integer postseasonPuntYds) {
        this.postseasonPuntYds = postseasonPuntYds;
    }

    /**
     * @return The longest punt (yds) made by the player this postseason.
     */
    public Integer getPostseasonPuntLong() {
        return postseasonPuntLong;
    }

    /**
     * @param postseasonPuntLong  The longest punt (yds) made by the player this postseason.
     */
    public void setPostseasonPuntLong(Integer postseasonPuntLong) {
        this.postseasonPuntLong = postseasonPuntLong;
    }

    /**
     * @return The number of the player's punts that were blocked this postseason.
     */
    public Integer getPostseasonPuntBlocked() {
        return postseasonPuntBlocked;
    }

    /**
     * @param postseasonPuntBlocked  The number of the player's punts that were blocked this
     *                               postseason.
     */
    public void setPostseasonPuntBlocked(Integer postseasonPuntBlocked) {
        this.postseasonPuntBlocked = postseasonPuntBlocked;
    }

    /**
     * @return The number of punts returned by the player this postseason.
     */
    public Integer getPostseasonPuntRet() {
        return postseasonPuntRet;
    }

    /**
     * @param postseasonPuntRet  The number of punts returned by the player this postseason.
     */
    public void setPostseasonPuntRet(Integer postseasonPuntRet) {
        this.postseasonPuntRet = postseasonPuntRet;
    }

    /**
     * @return The number of punt return yards gained by the player this postseason.
     */
    public Integer getPostseasonPuntRetYds() {
        return postseasonPuntRetYds;
    }

    /**
     * @param postseasonPuntRetYds  The number of punt return yards gained by the player this
     *                              postseason.
     */
    public void setPostseasonPuntRetYds(Integer postseasonPuntRetYds) {
        this.postseasonPuntRetYds = postseasonPuntRetYds;
    }

    /**
     * @return The number of punts returned for touchdowns by the player this postseason.
     */
    public Integer getPostseasonPuntRetTds() {
        return postseasonPuntRetTds;
    }

    /**
     * @param postseasonPuntRetTds  The number of punts returned for touchdowns by the player
     *                              this postseason.
     */
    public void setPostseasonPuntRetTds(Integer postseasonPuntRetTds) {
        this.postseasonPuntRetTds = postseasonPuntRetTds;
    }

    /**
     * @return The longest punt return (yds) made by the player this postseason.
     */
    public Integer getPostseasonPuntRetLong() {
        return postseasonPuntRetLong;
    }

    /**
     * @param postseasonPuntRetLong  The longest punt return (yds) made by the player this
     *                               postseason.
     */
    public void setPostseasonPuntRetLong(Integer postseasonPuntRetLong) {
        this.postseasonPuntRetLong = postseasonPuntRetLong;
    }

    /**
     * @return The number of kickoffs returned by the player this postseason.
     */
    public Integer getPostseasonKickRet() {
        return postseasonKickRet;
    }

    /**
     * @param postseasonKickRet  The number of kickoffs returned by the player this postseason.
     */
    public void setPostseasonKickRet(Integer postseasonKickRet) {
        this.postseasonKickRet = postseasonKickRet;
    }

    /**
     * @return The number of kickoff return yards gained by the player this postseason.
     */
    public Integer getPostseasonKickRetYds() {
        return postseasonKickRetYds;
    }

    /**
     * @param postseasonKickRetYds  The number of kickoff return yards gained by the player
     *                              this postseason.
     */
    public void setPostseasonKickRetYds(Integer postseasonKickRetYds) {
        this.postseasonKickRetYds = postseasonKickRetYds;
    }

    /**
     * @return The number of kickoffs returned for touchdowns by the player this postseason.
     */
    public Integer getPostseasonKickRetTds() {
        return postseasonKickRetTds;
    }

    /**
     * @param postseasonKickRetTds  The number of kickoffs returned for touchdowns by the player
     *                              this postseason.
     */
    public void setPostseasonKickRetTds(Integer postseasonKickRetTds) {
        this.postseasonKickRetTds = postseasonKickRetTds;
    }

    /**
     * @return The longest kick return (yds) made by the player this postseason.
     */
    public Integer getPostseasonKickRetLong() {
        return postseasonKickRetLong;
    }

    /**
     * @param postseasonKickRetLong  The longest kick return (yds) made by the player this postseason.
     */
    public void setPostseasonKickRetLong(Integer postseasonKickRetLong) {
        this.postseasonKickRetLong = postseasonKickRetLong;
    }

    /**
     * @return The total number of touchdowns scored via other means by the player this
     *         postseason.
     */
    public Integer getPostseasonOtherTds() {
        return postseasonOtherTds;
    }

    /**
     * @param postseasonOtherTds  The total number of touchdowns scored via other means by the
     *                            player this postseason.
     */
    public void setPostseasonOtherTds(Integer postseasonOtherTds) {
        this.postseasonOtherTds = postseasonOtherTds;
    }

    /**
     * @return The total number of touchdowns scored by the player during this postseason.
     */
    public Integer getPostseasonAllTds() {
        return postseasonAllTds;
    }

    /**
     * @param postseasonAllTds  The total number of touchdowns scored by the player during this
     *                          postseason.
     */
    public void setPostseasonAllTds(Integer postseasonAllTds) {
        this.postseasonAllTds = postseasonAllTds;
    }

    /**
     * @return The total number of two point conversions scored by the player during this
     *         postseason.
     */
    public Integer getPostseasonTwoPointConv() {
        return postseasonTwoPointConv;
    }

    /**
     * @param postseasonTwoPointConv  The total number of two point conversions scored by the player
     *                                during this postseason.
     */
    public void setPostseasonTwoPointConv(Integer postseasonTwoPointConv) {
        this.postseasonTwoPointConv = postseasonTwoPointConv;
    }

    /**
     * @return A Boolean indicating if the player made the probowl this season.
     */
    public Boolean getProbowl() {
        return probowl;
    }

    /**
     * @param probowl  A Boolean indicating if the player made the probowl this season.
     */
    public void setProbowl(Boolean probowl) {
        this.probowl = probowl;
    }

    /**
     * @return A Boolean indicating if the player was selected as an all-pro this season.
     */
    public Boolean getAllPro() {
        return allPro;
    }

    /**
     * @param allPro  A Boolean indicating if the player was selected as an all-pro this season.
     */
    public void setAllPro(Boolean allPro) {
        this.allPro = allPro;
    }

    /**
     * @return A metric calculated from the player's statistics this season which
     *         approximates the amount of value the player has produced this season.
     */
    public Integer getAvgValue() {
        return avgValue;
    }

    /**
     * @param setAvgValue  A metric calculated from the player's statistics this season
     *                     which approximates the amount of value the player has produced
     *                     this season.
     */
    public void setAvgValue(Integer avgValue) {
        this.avgValue = avgValue;
    }

    /**
     * Compares this playerSeason to the argument. The result is <code>true</code>
     * if and only if the argument is a non-null instance of PlayerSeason that has equivalent
     * values for the playerId, season, and franchiseId attributes.
     *
     * @param obj  The object to compare this PlayerSeason against.
     * @return <code>true</code> if the argument is equal to this PlayerSeason object,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlayerSeason)) {
            return false;
        }
        PlayerSeason arg = (PlayerSeason) obj;
        return this.getPlayerId().equals(arg.getPlayerId()) &&
                this.getSeason().equals(arg.getSeason()) &&
                this.getFranchiseId().equals(arg.getFranchiseId());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this object
     * based on the playerId, season, and franchiseId fields.
     *
     * @return A hash code for this object.
     * @see #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getSeason(), getFranchiseId());
    }
}
