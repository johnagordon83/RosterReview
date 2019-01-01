package com.rosterreview.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An {@link Entity} class describing the statistics generated during a single season
 * of a professional football player's career.
 */

@Entity
@Table(name="player_season")
public class PlayerSeason implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @Column(name="player_id")
    protected String playerId;

    @Id
    @Column(name="franchise_id")
    protected String franchiseId;

    @Id
    @Column(name="season")
    protected Short season;

    @OneToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumns({
        @JoinColumn(name="franchise_id", referencedColumnName="franchise_id", insertable=false, updatable=false),
        @JoinColumn(name="season", referencedColumnName="season", insertable=false, updatable=false)
        })
    protected Team team;

    @Column(name="age")
    protected Short age;

    @Column(name="position")
    protected String position;

    @Column(name="jersey_number")
    protected Short jerseyNumber;

    @Column(name="games_played")
    protected Short gamesPlayed;

    @Column(name="games_started")
    protected Short gamesStarted;

    @Column(name="pass_comp")
    protected Short passComp;

    @Column(name="pass_att")
    protected Short passAtt;

    @Column(name="pass_yds")
    protected Short passYds;

    @Column(name="pass_tds")
    protected Short passTds;

    @Column(name="pass_ints")
    protected Short passInts;

    @Column(name="pass_long")
    protected Short passLong;

    @Column(name="passer_rating")
    protected Double passerRating;

    @Column(name="sacked")
    protected Short sacked;

    @Column(name="sacked_yds")
    protected Short sackedYds;

    @Column(name="rush_att")
    protected Short rushAtt;

    @Column(name="rush_yds")
    protected Short rushYds;

    @Column(name="rush_tds")
    protected Short rushTds;

    @Column(name="rush_long")
    protected Short rushLong;

    @Column(name="targets")
    protected Short targets;

    @Column(name="rec")
    protected Short receptions;

    @Column(name="rec_yds")
    protected Short recYds;

    @Column(name="rec_tds")
    protected Short recTds;

    @Column(name="rec_long")
    protected Short recLong;

    @Column(name="fumbles")
    protected Short fumbles;

    @Column(name="interceptions")
    protected Short interceptions;

    @Column(name="int_yds")
    protected Short intYds;

    @Column(name="int_tds")
    protected Short intTds;

    @Column(name="int_long")
    protected Short intLong;

    @Column(name="pass_def")
    protected Short passDef;

    @Column(name="fum_forced")
    protected Short fumForced;

    @Column(name="fum_rec")
    protected Short fumRec;

    @Column(name="fum_rec_yds")
    protected Short fumRecYds;

    @Column(name="fum_rec_tds")
    protected Short fumRecTds;

    @Column(name="sacks")
    protected Short sacks;

    @Column(name="tackles")
    protected Short tackles;

    @Column(name="assists")
    protected Short assists;

    @Column(name="safeties")
    protected Short safeties;

    @Column(name="fga_teens")
    protected Short fgaTeens;

    @Column(name="fgm_teens")
    protected Short fgmTeens;

    @Column(name="fga_twenties")
    protected Short fgaTwenties;

    @Column(name="fgm_twenties")
    protected Short fgmTwenties;

    @Column(name="fga_thirties")
    protected Short fgaThirties;

    @Column(name="fgm_thirties")
    protected Short fgmThirties;

    @Column(name="fga_fourties")
    protected Short fgaFourties;

    @Column(name="fgm_fourties")
    protected Short fgmFourties;

    @Column(name="fga_fifty_plus")
    protected Short fgaFiftyPlus;

    @Column(name="fgm_fifty_plus")
    protected Short fgmFiftyPlus;

    @Column(name="fga_total")
    protected Short fgaTotal;

    @Column(name="fgm_total")
    protected Short fgmTotal;

    @Column(name="fg_long")
    protected Short fgLong;

    @Column(name="xpa")
    protected Short xpa;

    @Column(name="xpm")
    protected Short xpm;

    @Column(name="punts")
    protected Short punts;

    @Column(name="punt_yds")
    protected Short puntYds;

    @Column(name="punt_long")
    protected Short puntLong;

    @Column(name="punt_blocked")
    protected Short puntBlocked;

    @Column(name="punt_ret")
    protected Short puntRet;

    @Column(name="punt_ret_yds")
    protected Short puntRetYds;

    @Column(name="punt_ret_tds")
    protected Short puntRetTds;

    @Column(name="punt_ret_long")
    protected Short puntRetLong;

    @Column(name="kick_ret")
    protected Short kickRet;

    @Column(name="kick_ret_yds")
    protected Short kickRetYds;

    @Column(name="kick_ret_tds")
    protected Short kickRetTds;

    @Column(name="kick_ret_long")
    protected Short kickRetLong;

    @Column(name="other_tds")
    protected Short otherTds;

    @Column(name="all_tds")
    protected Short allTds;

    @Column(name="two_point_conv")
    protected Short twoPointConv;

    @Column(name="playoff_games_played")
    protected Short playoffGamesPlayed;

    @Column(name="playoff_games_started")
    protected Short playoffGamesStarted;

    @Column(name="playoff_pass_comp")
    protected Short playoffPassComp;

    @Column(name="playoff_pass_att")
    protected Short playoffPassAtt;

    @Column(name="playoff_pass_yds")
    protected Short playoffPassYds;

    @Column(name="playoff_pass_tds")
    protected Short playoffPassTds;

    @Column(name="playoff_pass_ints")
    protected Short playoffPassInts;

    @Column(name="playoff_pass_long")
    protected Short playoffPassLong;

    @Column(name="playoff_passer_rating")
    protected Double playoffPasserRating;

    @Column(name="playoff_sacked")
    protected Short playoffSacked;

    @Column(name="playoff_sacked_yds")
    protected Short playoffSackedYds;

    @Column(name="playoff_rush_att")
    protected Short playoffRushAtt;

    @Column(name="playoff_rush_yds")
    protected Short playoffRushYds;

    @Column(name="playoff_rush_tds")
    protected Short playoffRushTds;

    @Column(name="playoff_rush_long")
    protected Short playoffRushLong;

    @Column(name="playoff_targets")
    protected Short playoffTargets;

    @Column(name="playoff_rec")
    protected Short playoffReceptions;

    @Column(name="playoff_rec_yds")
    protected Short playoffRecYds;

    @Column(name="playoff_rec_tds")
    protected Short playoffRecTds;

    @Column(name="playoff_rec_long")
    protected Short playoffRecLong;

    @Column(name="playoff_fumbles")
    protected Short playoffFumbles;

    @Column(name="playoff_interceptions")
    protected Short playoffInterceptions;

    @Column(name="playoff_int_yds")
    protected Short playoffIntYds;

    @Column(name="playoff_int_tds")
    protected Short playoffIntTds;

    @Column(name="playoff_int_long")
    protected Short playoffIntLong;

    @Column(name="playoff_pass_def")
    protected Short playoffPassDef;

    @Column(name="playoff_fum_forced")
    protected Short playoffFumForced;

    @Column(name="playoff_fum_rec")
    protected Short playoffFumRec;

    @Column(name="playoff_fum_rec_yds")
    protected Short playoffFumRecYds;

    @Column(name="playoff_fum_rec_tds")
    protected Short playoffFumRecTds;

    @Column(name="playoff_sacks")
    protected Short playoffSacks;

    @Column(name="playoff_tackles")
    protected Short playoffTackles;

    @Column(name="playoff_assists")
    protected Short playoffAssists;

    @Column(name="playoff_safeties")
    protected Short playoffSafeties;

    @Column(name="playoff_fga_teens")
    protected Short playoffFgaTeens;

    @Column(name="playoff_fgm_teens")
    protected Short playoffFgmTeens;

    @Column(name="playoff_fga_twenties")
    protected Short playoffFgaTwenties;

    @Column(name="playoff_fgm_twenties")
    protected Short playoffFgmTwenties;

    @Column(name="playoff_fga_thirties")
    protected Short playoffFgaThirties;

    @Column(name="playoff_fgm_thirties")
    protected Short playoffFgmThirties;

    @Column(name="playoff_fga_fourties")
    protected Short playoffFgaFourties;

    @Column(name="playoff_fgm_fourties")
    protected Short playoffFgmFourties;

    @Column(name="playoff_fga_fifty_plus")
    protected Short playoffFgaFiftyPlus;

    @Column(name="playoff_fgm_fifty_plus")
    protected Short playoffFgmFiftyPlus;

    @Column(name="playoff_fga_total")
    protected Short playoffFgaTotal;

    @Column(name="playoff_fgm_total")
    protected Short playoffFgmTotal;

    @Column(name="playoff_fg_long")
    protected Short playoffFgLong;

    @Column(name="playoff_xpa")
    protected Short playoffXpa;

    @Column(name="playoff_xpm")
    protected Short playoffXpm;

    @Column(name="playoff_punts")
    protected Short playoffPunts;

    @Column(name="playoff_punt_yds")
    protected Short playoffPuntYds;

    @Column(name="playoff_punt_long")
    protected Short playoffPuntLong;

    @Column(name="playoff_punt_blocked")
    protected Short playoffPuntBlocked;

    @Column(name="playoff_punt_ret")
    protected Short playoffPuntRet;

    @Column(name="playoff_punt_ret_yds")
    protected Short playoffPuntRetYds;

    @Column(name="playoff_punt_ret_tds")
    protected Short playoffPuntRetTds;

    @Column(name="playoff_punt_ret_long")
    protected Short playoffPuntRetLong;

    @Column(name="playoff_kick_ret")
    protected Short playoffKickRet;

    @Column(name="playoff_kick_ret_yds")
    protected Short playoffKickRetYds;

    @Column(name="playoff_kick_ret_tds")
    protected Short playoffKickRetTds;

    @Column(name="playoff_kick_ret_long")
    protected Short playoffKickRetLong;

    @Column(name="playoff_other_tds")
    protected Short playoffOtherTds;

    @Column(name="playoff_all_tds")
    protected Short playoffAllTds;

    @Column(name="playoff_two_point_conv")
    protected Short playoffTwoPointConv;

    @Column(name="probowl")
    protected Boolean probowl;

    @Column(name="all_pro")
    protected Boolean allPro;

    @Column(name="avg_value")
    protected Short avgValue;

    PlayerSeason() {}

    public PlayerSeason(String playerId, String franchiseId, Short season, Short age, String position, Short jerseyNumber,
            Short gamesPlayed, Short gamesStarted, Short passComp, Short passAtt, Short passYds, Short passTds, Short passInts,
            Short passLong, Double passerRating, Short sacked, Short sackedYds, Short rushAtt, Short rushYds, Short rushTds,
            Short rushLong, Short targets, Short receptions, Short recYds, Short recTds, Short recLong, Short fumbles,
            Short interceptions, Short intYds, Short intTds, Short intLong, Short passDef, Short fumForced, Short fumRec, Short fumRecYds,
            Short fumRecTds, Short sacks, Short tackles, Short assists, Short safeties, Short fgaTeens, Short fgmTeens, Short fgaTwenties,
            Short fgmTwenties, Short fgaThirties, Short fgmThirties, Short fgaFourties, Short fgmFourties, Short fgaFiftyPlus,
            Short fgmFiftyPlus, Short fgaTotal,    Short fgmTotal, Short fgLong, Short xpa, Short xpm, Short punts, Short puntYds,
            Short puntLong, Short puntBlocked,     Short puntRet, Short puntRetYds, Short puntRetTds, Short puntRetLong, Short kickRet,
            Short kickRetYds, Short kickRetTds, Short kickRetLong, Short otherTds, Short allTds, Short twoPointConv,
            Short playoffGamesPlayed, Short playoffGamesStarted, Short playoffPassComp, Short playoffPassAtt, Short playoffPassYds,
            Short playoffPassTds, Short playoffPassInts, Short playoffPassLong, Double playoffPasserRating, Short playoffSacked,
            Short playoffSackedYds, Short playoffRushAtt, Short playoffRushYds, Short playoffRushTds, Short playoffRushLong,
            Short playoffTargets, Short playoffReceptions, Short playoffRecYds, Short playoffRecTds, Short playoffRecLong,
            Short playoffFumbles, Short playoffInterceptions, Short playoffIntYds, Short playoffIntTds, Short playoffIntLong,
            Short playoffPassDef, Short playoffFumForced, Short playoffFumRec, Short playoffFumRecYds, Short playoffFumRecTds,
            Short playoffSacks, Short playoffTackles, Short playoffAssists, Short playoffSafeties, Short playoffFgaTeens,
            Short playoffFgmTeens, Short playoffFgaTwenties, Short playoffFgmTwenties, Short playoffFgaThirties, Short playoffFgmThirties,
            Short playoffFgaFourties, Short playoffFgmFourties, Short playoffFgaFiftyPlus, Short playoffFgmFiftyPlus,
            Short playoffFgaTotal, Short playoffFgmTotal, Short playoffFgLong, Short playoffXpa, Short playoffXpm, Short playoffPunts,
            Short playoffPuntYds, Short playoffPuntLong, Short playoffPuntBlocked, Short playoffPuntRet, Short playoffPuntRetYds,
            Short playoffPuntRetTds, Short playoffPuntRetLong, Short playoffKickRet, Short playoffKickRetYds, Short playoffKickRetTds,
            Short playoffKickRetLong, Short playoffOtherTds, Short playoffAllTds, Short playoffTwoPointConv, Boolean probowl,
            Boolean allPro, Short avgValue) {
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
        this.playoffGamesPlayed = playoffGamesPlayed;
        this.playoffGamesStarted = playoffGamesStarted;
        this.playoffPassComp = playoffPassComp;
        this.playoffPassAtt = playoffPassAtt;
        this.playoffPassYds = playoffPassYds;
        this.playoffPassTds = playoffPassTds;
        this.playoffPassInts = playoffPassInts;
        this.playoffPassLong = playoffPassLong;
        this.playoffPasserRating = playoffPasserRating;
        this.playoffSacked = playoffSacked;
        this.playoffSackedYds = playoffSackedYds;
        this.playoffRushAtt = playoffRushAtt;
        this.playoffRushYds = playoffRushYds;
        this.playoffRushTds = playoffRushTds;
        this.playoffRushLong = playoffRushLong;
        this.playoffTargets = playoffTargets;
        this.playoffReceptions = playoffReceptions;
        this.playoffRecYds = playoffRecYds;
        this.playoffRecTds = playoffRecTds;
        this.playoffRecLong = playoffRecLong;
        this.playoffFumbles = playoffFumbles;
        this.playoffInterceptions = playoffInterceptions;
        this.playoffIntYds = playoffIntYds;
        this.playoffIntTds = playoffIntTds;
        this.playoffIntLong = playoffIntLong;
        this.playoffPassDef = playoffPassDef;
        this.playoffFumForced = playoffFumForced;
        this.playoffFumRec = playoffFumRec;
        this.playoffFumRecYds = playoffFumRecYds;
        this.playoffFumRecTds = playoffFumRecTds;
        this.playoffSacks = playoffSacks;
        this.playoffTackles = playoffTackles;
        this.playoffAssists = playoffAssists;
        this.playoffSafeties = playoffSafeties;
        this.playoffFgaTeens = playoffFgaTeens;
        this.playoffFgmTeens = playoffFgmTeens;
        this.playoffFgaTwenties = playoffFgaTwenties;
        this.playoffFgmTwenties = playoffFgmTwenties;
        this.playoffFgaThirties = playoffFgaThirties;
        this.playoffFgmThirties = playoffFgmThirties;
        this.playoffFgaFourties = playoffFgaFourties;
        this.playoffFgmFourties = playoffFgmFourties;
        this.playoffFgaFiftyPlus = playoffFgaFiftyPlus;
        this.playoffFgmFiftyPlus = playoffFgmFiftyPlus;
        this.playoffFgaTotal = playoffFgaTotal;
        this.playoffFgmTotal = playoffFgmTotal;
        this.playoffFgLong = playoffFgLong;
        this.playoffXpa = playoffXpa;
        this.playoffXpm = playoffXpm;
        this.playoffPunts = playoffPunts;
        this.playoffPuntYds = playoffPuntYds;
        this.playoffPuntLong = playoffPuntLong;
        this.playoffPuntBlocked = playoffPuntBlocked;
        this.playoffPuntRet = playoffPuntRet;
        this.playoffPuntRetYds = playoffPuntRetYds;
        this.playoffPuntRetTds = playoffPuntRetTds;
        this.playoffPuntRetLong = playoffPuntRetLong;
        this.playoffKickRet = playoffKickRet;
        this.playoffKickRetYds = playoffKickRetYds;
        this.playoffKickRetTds = playoffKickRetTds;
        this.playoffKickRetLong = playoffKickRetLong;
        this.playoffOtherTds = playoffOtherTds;
        this.playoffAllTds = playoffAllTds;
        this.playoffTwoPointConv = playoffTwoPointConv;
        this.probowl = probowl;
        this.allPro = allPro;
        this.avgValue = avgValue;
    }

    /**
     * @return The unique identifier of the player.
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId  The unique identifier of the player.
     */
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
    public Short getSeason() {
        return season;
    }

    /**
     * @param season  The season (year) these statistics were generated.
     */
    public void setSeason(Short season) {
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
    public Short getAge() {
        return age;
    }

    /**
     * @param age  The age of the player during this season.
     */
    public void setAge(Short age) {
        this.age = age;
    }

    /**
     * @return The primary position the player played this season.
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position  The primary position the player played this season.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return The jersey number worn by the player this season.
     */
    public Short getJerseyNumber() {
        return jerseyNumber;
    }

    /**
     * @param jerseyNumber  The jersey number worn by the player this season.
     */
    public void setJerseyNumber(Short jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    /**
     * @return The number of games the player appeared in this season.
     */
    public Short getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @param gamesPlayed  The number of games the player appeared in this season.
     */
    public void setGamesPlayed(Short gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * @return The number of games the player started this season.
     */
    public Short getGamesStarted() {
        return gamesStarted;
    }

    /**
     * @param gamesStarted  The number of games the player started this season.
     */
    public void setGamesStarted(Short gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    /**
     * @return The number of passes completed by the player this season.
     */
    public Short getPassComp() {
        return passComp;
    }

    /**
     * @param passComp  The number of passes completed by the player this season.
     */
    public void setPassComp(Short passComp) {
        this.passComp = passComp;
    }

    /**
     * @return The number of passes attempted by the player this season.
     */
    public Short getPassAtt() {
        return passAtt;
    }

    /**
     * @param passAtt  The number of passes attempted by the player this season.
     */
    public void setPassAtt(Short passAtt) {
        this.passAtt = passAtt;
    }

    /**
     * @return The number of passing yards thrown by the player this season.
     */
    public Short getPassYds() {
        return passYds;
    }

    /**
     * @param passYds  The number of passing yards thrown by the player this season.
     */
    public void setPassYds(Short passYds) {
        this.passYds = passYds;
    }

    /**
     * @return The number of passing touchdowns thrown by the player this season.
     */
    public Short getPassTds() {
        return passTds;
    }

    /**
     * @param passTds  The number of passing touchdowns thrown by the player this season.
     */
    public void setPassTds(Short passTds) {
        this.passTds = passTds;
    }

    /**
     * @return The number of interceptions thrown by the player this season.
     */
    public Short getPassInts() {
        return passInts;
    }

    /**
     * @param passInts  The number of interceptions thrown by the player this season.
     */
    public void setPassInts(Short passInts) {
        this.passInts = passInts;
    }

    /**
     * @return The longest pass (yds) thrown by the player this season.
     */
    public Short getPassLong() {
        return passLong;
    }

    /**
     * @param passLong  The longest pass (yds) thrown by the player this season.
     */
    public void setPassLong(Short passLong) {
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
    public Short getSacked() {
        return sacked;
    }

    /**
     * @param sacked  The number of times the player was sacked this season.
     */
    public void setSacked(Short sacked) {
        this.sacked = sacked;
    }

    /**
     * @return The number yards lost when the player was sacked this season.
     */
    public Short getSackedYds() {
        return sackedYds;
    }

    /**
     * @param sacked  The number yards lost when the player was sacked this season.
     */
    public void setSackedYds(Short sackedYds) {
        this.sackedYds = sackedYds;
    }

    /**
     * @return The number of rushes attempted by the player this season.
     */
    public Short getRushAtt() {
        return rushAtt;
    }

    /**
     * @param rushAtt  The number of rushes attempted by the player this season.
     */
    public void setRushAtt(Short rushAtt) {
        this.rushAtt = rushAtt;
    }

    /**
     * @return The number of rushing yards gained by the player this season.
     */
    public Short getRushYds() {
        return rushYds;
    }

    /**
     * @param rushYds  The number of rushing yards gained by the player this season.
     */
    public void setRushYds(Short rushYds) {
        this.rushYds = rushYds;
    }

    /**
     * @return The number of rushing touchdowns scored by the player this season.
     */
    public Short getRushTds() {
        return rushTds;
    }

    /**
     * @param rushTds  The number of rushing touchdowns scored by the player this season.
     */
    public void setRushTds(Short rushTds) {
        this.rushTds = rushTds;
    }

    /**
     * @return The longest rush made by the player this season.
     */
    public Short getRushLong() {
        return rushLong;
    }

    /**
     * @param rushLong  The longest rush made by the player this season.
     */
    public void setRushLong(Short rushLong) {
        this.rushLong = rushLong;
    }

    /**
     * @return The number of passes thrown to the player this season.
     */
    public Short getTargets() {
        return targets;
    }

    /**
     * @param targets  The number of passes thrown to the player this season.
     */
    public void setTargets(Short targets) {
        this.targets = targets;
    }

    /**
     * @return The number of passes caught by the player this season.
     */
    public Short getReceptions() {
        return receptions;
    }

    /**
     * @param receptions  The number of passes caught by the player this season.
     */
    public void setReceptions(Short receptions) {
        this.receptions = receptions;
    }

    /**
     * @return The number of receiving yards gained by the player this season.
     */
    public Short getRecYds() {
        return recYds;
    }

    /**
     * @param recYds  The number of receiving yards gained by the player this season.
     */
    public void setRecYds(Short recYds) {
        this.recYds = recYds;
    }

    /**
     * @return The number of receiving touchdowns scored by the player this season.
     */
    public Short getRecTds() {
        return recTds;
    }

    /**
     * @param recTds  The number of receiving touchdowns scored by the player this season.
     */
    public void setRecTds(Short recTds) {
        this.recTds = recTds;
    }

    /**
     * @return The longest reception (yds) made by the player this season.
     */
    public Short getRecLong() {
        return recLong;
    }

    /**
     * @param recLong  The longest reception (yds) made by the player this season.
     */
    public void setRecLong(Short recLong) {
        this.recLong = recLong;
    }

    /**
     * @return The number of times the player has fumbled this season.
     */
    public Short getFumbles() {
        return fumbles;
    }

    /**
     * @param fumbles  The number of times the player has fumbled this season.
     */
    public void setFumbles(Short fumbles) {
        this.fumbles = fumbles;
    }

    /**
     * @return The number of passes intercepted by the player this season.
     */
    public Short getInterceptions() {
        return interceptions;
    }

    /**
     * @param interceptions  The number of passes intercepted by the player this season.
     */
    public void setInterceptions(Short interceptions) {
        this.interceptions = interceptions;
    }

    /**
     * @return The number of interception return yards gained by the player this season.
     */
    public Short getIntYds() {
        return intYds;
    }

    /**
     * @param intYds  The number of interception return yards gained by the player this
     *                season.
     */
    public void setIntYds(Short intYds) {
        this.intYds = intYds;
    }

    /**
     * @return The number of touchdowns scored by the player on interception returns
     *         this season.
     */
    public Short getIntTds() {
        return intTds;
    }

    /**
     * @param intTds  The number of touchdowns scored by the player on interception returns
     *                this season.
     */
    public void setIntTds(Short intTds) {
        this.intTds = intTds;
    }

    /**
     * @return The longest return (yds) of an interception made by the player this season.
     */
    public Short getIntLong() {
        return intLong;
    }

    /**
     * @param intLong  The longest return (yds) of an interception made by the player
     *                 this season.
     */
    public void setIntLong(Short intLong) {
        this.intLong = intLong;
    }

    /**
     * @return The number of passes defended by the player this season.
     */
    public Short getPassDef() {
        return passDef;
    }

    /**
     * @param passDef  The number of passes defended by the player this season.
     */
    public void setPassDef(Short passDef) {
        this.passDef = passDef;
    }

    /**
     * @return The number of fumbles forced by the player this season.
     */
    public Short getFumForced() {
        return fumForced;
    }

    /**
     * @param fumForced  The number of fumbles forced by the player this season.
     */
    public void setFumForced(Short fumForced) {
        this.fumForced = fumForced;
    }

    /**
     * @return The number of fumbles recovered by the player this season.
     */
    public Short getFumRec() {
        return fumRec;
    }

    /**
     * @param fumRec  The number of fumbles recovered by the player this season.
     */
    public void setFumRec(Short fumRec) {
        this.fumRec = fumRec;
    }

    /**
     * @return The number of fumble return yards gained by the player this season.
     */
    public Short getFumRecYds() {
        return fumRecYds;
    }

    /**
     * @param fumRecYds  The number of fumble return yards gained by the player
     *                   this season.
     */
    public void setFumRecYds(Short fumRecYds) {
        this.fumRecYds = fumRecYds;
    }

    /**
     * @return The number of touchdowns scored by the player on fumble returns
     *         this season.
     */
    public Short getFumRecTds() {
        return fumRecTds;
    }

    /**
     * @param fumRecTds  The number of touchdowns scored by the player on fumble
     *                   returns this season.
     */
    public void setFumRecTds(Short fumRecTds) {
        this.fumRecTds = fumRecTds;
    }

    /**
     * @return The number of quarterback sacks made by the player this season.
     */
    public Short getSacks() {
        return sacks;
    }

    /**
     * @param sacks  The number of quarterback sacks made by the player this season.
     */
    public void setSacks(Short sacks) {
        this.sacks = sacks;
    }

    /**
     * @return The number of solo tackles made by the player this season.
     */
    public Short getTackles() {
        return tackles;
    }

    /**
     * @param tackles  The number of solo tackles made by the player this season.
     */
    public void setTackles(Short tackles) {
        this.tackles = tackles;
    }

    /**
     * @return The number of tackle assists made by the player this season.
     */
    public Short getAssists() {
        return assists;
    }

    /**
     * @param assists  The number of tackle assists made by the player this season.
     */
    public void setAssists(Short assists) {
        this.assists = assists;
    }

    /**
     * @return The number of safeties made by the player this season.
     */
    public Short getSafeties() {
        return safeties;
    }

    /**
     * @param safeties  The number of safeties made by the player this season.
     */
    public void setSafeties(Short safeties) {
        this.safeties = safeties;
    }

    /**
     * @return The number of 17-19 yard field goals attempted by the player this season.
     */
    public Short getFgaTeens() {
        return fgaTeens;
    }

    /**
     * @param fgaTeens  The number of 17-19 yard field goals attempted by the player
     *                  this season.
     */
    public void setFgaTeens(Short fgaTeens) {
        this.fgaTeens = fgaTeens;
    }

    /**
     * @return The number of successful 17-19 yard field goal attempts made by the
     *         player this season.
     */
    public Short getFgmTeens() {
        return fgmTeens;
    }

    /**
     * @param fgmTeens  The number of successful 17-19 yard field goal attempts made
     *                  by the player this season.
     */
    public void setFgmTeens(Short fgmTeens) {
        this.fgmTeens = fgmTeens;
    }

    /**
     * @return The number of 20-29 yard field goals attempted by the player this season.
     */
    public Short getFgaTwenties() {
        return fgaTwenties;
    }

    /**
     * @param fgaTwenties  The number of 20-29 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaTwenties(Short fgaTwenties) {
        this.fgaTwenties = fgaTwenties;
    }

    /**
     * @return The number of successful 20-29 yard field goal attempts made by the player
     *         this season.
     */
    public Short getFgmTwenties() {
        return fgmTwenties;
    }

    /**
     * @param fgmTwenties  The number of successful 20-29 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmTwenties(Short fgmTwenties) {
        this.fgmTwenties = fgmTwenties;
    }

    /**
     * @return The number of 30-39 yard field goals attempted by the player this season.
     */
    public Short getFgaThirties() {
        return fgaThirties;
    }

    /**
     * @param fgaThirties  The number of 30-39 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaThirties(Short fgaThirties) {
        this.fgaThirties = fgaThirties;
    }

    /**
     * @return The number of successful 30-39 yard field goal attempts made by the player
     *         this season.
     */
    public Short getFgmThirties() {
        return fgmThirties;
    }

    /**
     * @param fgmThirties  The number of successful 30-39 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmThirties(Short fgmThirties) {
        this.fgmThirties = fgmThirties;
    }

    /**
     * @return The number of 40-49 yard field goals attempted by the player this season.
     */
    public Short getFgaFourties() {
        return fgaFourties;
    }

    /**
     * @param fgaFourties  The number of 40-49 yard field goals attempted by the player
     *                     this season.
     */
    public void setFgaFourties(Short fgaFourties) {
        this.fgaFourties = fgaFourties;
    }

    /**
     * @return The number of successful 40-49 yard field goal attempts made by the player
     *         this season.
     */
    public Short getFgmFourties() {
        return fgmFourties;
    }

    /**
     * @param fgmFourties  The number of successful 40-49 yard field goal attempts made by
     *                     the player this season.
     */
    public void setFgmFourties(Short fgmFourties) {
        this.fgmFourties = fgmFourties;
    }

    /**
     * @return The number of 50+ yard field goals attempted by the player this season.
     */
    public Short getFgaFiftyPlus() {
        return fgaFiftyPlus;
    }

    /**
     * @param fgaFiftyPlus  The number of 50+ yard field goals attempted by the player
     *                      this season.
     */
    public void setFgaFiftyPlus(Short fgaFiftyPlus) {
        this.fgaFiftyPlus = fgaFiftyPlus;
    }

    /**
     * @return The number of successful 50+ yard field goal attempts made by the player
     *         this season.
     */
    public Short getFgmFiftyPlus() {
        return fgmFiftyPlus;
    }

    /**
     * @param fgmFiftyPlus  The number of successful 50+ yard field goal attempts made
     *                      by the player this season.
     */
    public void setFgmFiftyPlus(Short fgmFiftyPlus) {
        this.fgmFiftyPlus = fgmFiftyPlus;
    }

    /**
     * @return The total number of field goals attempted by the player this season.
     */
    public Short getFgaTotal() {
        return fgaTotal;
    }

    /**
     * @param fgaTotal  The total number of field goals attempted by the player this
     *                  season.
     */
    public void setFgaTotal(Short fgaTotal) {
        this.fgaTotal = fgaTotal;
    }

    /**
     * @return The total number of successful field goal attempts made by the player
     *         this season.
     */
    public Short getFgmTotal() {
        return fgmTotal;
    }

    /**
     * @param fgmTotal  The total number of successful field goal attempts made by the
     *                  player this season.
     */
    public void setFgmTotal(Short fgmTotal) {
        this.fgmTotal = fgmTotal;
    }

    /**
     * @return The longest successful field goal (yds) attempt made by the player
     *         this season.
     */
    public Short getFgLong() {
        return fgLong;
    }

    /**
     * @param fgLong  The longest successful field goal (yds) attempt made by the
     *                player this season.
     */
    public void setFgLong(Short fgLong) {
        this.fgLong = fgLong;
    }

    /**
     * @return The total number of extra points attempted by the player this season.
     */
    public Short getXpa() {
        return xpa;
    }

    /**
     * @param xpa  The total number of extra points attempted by the player this season.
     */
    public void setXpa(Short xpa) {
        this.xpa = xpa;
    }

    /**
     * @return The total number of successful extra point attempts made by the player
     *         this season.
     */
    public Short getXpm() {
        return xpm;
    }

    /**
     * @param xpm  The total number of successful extra point attempts made by the player
     *             this season.
     */
    public void setXpm(Short xpm) {
        this.xpm = xpm;
    }

    /**
     * @return The total number of punts made by the player this season.
     */
    public Short getPunts() {
        return punts;
    }

    /**
     * @param punts  The total number of punts made by the player this season.
     */
    public void setPunts(Short punts) {
        this.punts = punts;
    }

    /**
     * @return The total number of punt yards made by the player this season.
     */
    public Short getPuntYds() {
        return puntYds;
    }

    /**
     * @param puntYds  The total number of punt yards made by the player this season.
     */
    public void setPuntYds(Short puntYds) {
        this.puntYds = puntYds;
    }

    /**
     * @return The longest punt (yds) made by the player this season.
     */
    public Short getPuntLong() {
        return puntLong;
    }

    /**
     * @param puntLong  The longest punt (yds) made by the player this season.
     */
    public void setPuntLong(Short puntLong) {
        this.puntLong = puntLong;
    }

    /**
     * @return The number of the player's punts that were blocked this season.
     */
    public Short getPuntBlocked() {
        return puntBlocked;
    }

    /**
     * @param puntBlocked  The number of the player's punts that were blocked this season.
     */
    public void setPuntBlocked(Short puntBlocked) {
        this.puntBlocked = puntBlocked;
    }

    /**
     * @return The number of punts returned by the player this season.
     */
    public Short getPuntRet() {
        return puntRet;
    }

    /**
     * @param puntRet  The number of punts returned by the player this season.
     */
    public void setPuntRet(Short puntRet) {
        this.puntRet = puntRet;
    }

    /**
     * @return The number of punt return yards gained by the player this season.
     */
    public Short getPuntRetYds() {
        return puntRetYds;
    }

    /**
     * @param puntRetYds  The number of punt return yards gained by the player this season.
     */
    public void setPuntRetYds(Short puntRetYds) {
        this.puntRetYds = puntRetYds;
    }

    /**
     * @return The number of punts returned for touchdowns by the player this season.
     */
    public Short getPuntRetTds() {
        return puntRetTds;
    }

    /**
     * @param puntRetTds  The number of punts returned for touchdowns by the player this season.
     */
    public void setPuntRetTds(Short puntRetTds) {
        this.puntRetTds = puntRetTds;
    }

    /**
     * @return The longest punt return (yds) made by the player this season.
     */
    public Short getPuntRetLong() {
        return puntRetLong;
    }

    /**
     * @param puntRetLong  The longest punt return (yds) made by the player this season.
     */
    public void setPuntRetLong(Short puntRetLong) {
        this.puntRetLong = puntRetLong;
    }

    /**
     * @return The number of kickoffs returned by the player this season.
     */
    public Short getKickRet() {
        return kickRet;
    }

    /**
     * @param kickRet  The number of kickoffs returned by the player this season.
     */
    public void setKickRet(Short kickRet) {
        this.kickRet = kickRet;
    }

    /**
     * @return The number of kickoff return yards gained by the player this season.
     */
    public Short getKickRetYds() {
        return kickRetYds;
    }

    /**
     * @param kickRetYds  The number of kickoff return yards gained by the player this season.
     */
    public void setKickRetYds(Short kickRetYds) {
        this.kickRetYds = kickRetYds;
    }

    /**
     * @return The number of kickoffs returned for touchdowns by the player this season.
     */
    public Short getKickRetTds() {
        return kickRetTds;
    }

    /**
     * @param kickRetTds  The number of kickoffs returned for touchdowns by the player
     *                    this season.
     */
    public void setKickRetTds(Short kickRetTds) {
        this.kickRetTds = kickRetTds;
    }

    /**
     * @return The longest kick return (yds) made by the player this season.
     */
    public Short getKickRetLong() {
        return kickRetLong;
    }

    /**
     * @param kickRetLong  The longest kick return (yds) made by the player this season.
     */
    public void setKickRetLong(Short kickRetLong) {
        this.kickRetLong = kickRetLong;
    }

    /**
     * @return The total number of touchdowns scored via other means by the player this season.
     */
    public Short getOtherTds() {
        return otherTds;
    }

    /**
     * @param otherTds  The total number of touchdowns scored via other means by the player
     *                  this season.
     */
    public void setOtherTds(Short otherTds) {
        this.otherTds = otherTds;
    }

    /**
     * @return The total number of touchdowns scored by the player this season.
     */
    public Short getAllTds() {
        return allTds;
    }

    /**
     * @param allTds  The total number of touchdowns scored by the player this season.
     */
    public void setAllTds(Short allTds) {
        this.allTds = allTds;
    }

    /**
     * @return The total number of two point conversions scored by the player this season.
     */
    public Short getTwoPointConv() {
        return twoPointConv;
    }

    /**
     * @param twoPointConv  The total number of two point conversions scored by the player
     *                      this season.
     */
    public void setTwoPointConv(Short twoPointConv) {
        this.twoPointConv = twoPointConv;
    }

    /**
     * @return The number of games the player appeared in this post-season.
     */
    public Short getPlayoffGamesPlayed() {
        return playoffGamesPlayed;
    }

    /**
     * @param gamesPlayed  The number of games the player appeared in this post-season.
     */
    public void setPlayoffGamesPlayed(Short playoffGamesPlayed) {
        this.playoffGamesPlayed = playoffGamesPlayed;
    }

    /**
     * @return The number of games the player started this post-season.
     */
    public Short getPlayoffGamesStarted() {
        return playoffGamesStarted;
    }

    /**
     * @param playoffGamesStarted  The number of games the player started this
     *                             post-season.
     */
    public void setPlayoffGamesStarted(Short playoffGamesStarted) {
        this.playoffGamesStarted = playoffGamesStarted;
    }

    /**
     * @return The number of passes completed by the player this post-season.
     */
    public Short getPlayoffPassComp() {
        return playoffPassComp;
    }

    /**
     * @param playoffPassComp  The number of passes completed by the player this
     *                         post-season.
     */
    public void setPlayoffPassComp(Short playoffPassComp) {
        this.playoffPassComp = playoffPassComp;
    }

    /**
     * @return The number of passes attempted by the player this post-season.
     */
    public Short getPlayoffPassAtt() {
        return playoffPassAtt;
    }

    /**
     * @param playoffPassAtt  The number of passes attempted by the player this
     *                        post-season.
     */
    public void setPlayoffPassAtt(Short playoffPassAtt) {
        this.playoffPassAtt = playoffPassAtt;
    }

    /**
     * @return The number of passing yards thrown by the player this post-season.
     */
    public Short getPlayoffPassYds() {
        return playoffPassYds;
    }

    /**
     * @param playoffPassYds  The number of passing yards thrown by the player this
     *                        post-season.
     */
    public void setPlayoffPassYds(Short playoffPassYds) {
        this.playoffPassYds = playoffPassYds;
    }

    /**
     * @return The number of passing touchdowns thrown by the player this post-season.
     */
    public Short getPlayoffPassTds() {
        return playoffPassTds;
    }

    /**
     * @param playoffPassTds  The number of passing touchdowns thrown by the player
     *                        this post-season.
     */
    public void setPlayoffPassTds(Short playoffPassTds) {
        this.playoffPassTds = playoffPassTds;
    }

    /**
     * @return The number of interceptions thrown by the player this post-season.
     */
    public Short getPlayoffPassInts() {
        return playoffPassInts;
    }

    /**
     * @param playoffPassInts  The number of interceptions thrown by the player this
     *                         post-season.
     */
    public void setPlayoffPassInts(Short playoffPassInts) {
        this.playoffPassInts = playoffPassInts;
    }

    /**
     * @return The longest pass (yds) thrown by the player this post-season.
     */
    public Short getPlayoffPassLong() {
        return playoffPassLong;
    }

    /**
     * @param passLong  The longest pass (yds) thrown by the player this post-season.
     */
    public void setPlayoffPassLong(Short playoffPassLong) {
        this.playoffPassLong = playoffPassLong;
    }

    /**
     * @return The player's passer rating for this post-season.
     */
    public Double getPlayoffPasserRating() {
        return playoffPasserRating;
    }

    /**
     * @param playoffPasserRating  The player's passer rating for this post-season.
     */
    public void setPlayoffPassRating(Double playoffPasserRating) {
        this.playoffPasserRating = playoffPasserRating;
    }

    /**
     * @return The number of times the player was sacked this post-season.
     */
    public Short getPlayoffSacked() {
        return playoffSacked;
    }

    /**
     * @param playoffSacked  The number of times the player was sacked this post-season.
     */
    public void setPlayoffSacked(Short playoffSacked) {
        this.playoffSacked = playoffSacked;
    }

    /**
     * @return The number yards lost when the player was sacked this post-season.
     */
    public Short getPlayoffSackedYds() {
        return playoffSackedYds;
    }

    /**
     * @param playoffSackedYds  The number yards lost when the player was sacked
     *                          this post-season.
     */
    public void setPlayoffSackedYds(Short playoffSackedYds) {
        this.playoffSackedYds = playoffSackedYds;
    }

    /**
     * @return The number of rushes attempted by the player this post-season.
     */
    public Short getPlayoffRushAtt() {
        return playoffRushAtt;
    }

    /**
     * @param playoffRushAtt  The number of rushes attempted by the player this
     *                        post-season.
     */
    public void setPlayoffRushAtt(Short playoffRushAtt) {
        this.playoffRushAtt = playoffRushAtt;
    }

    /**
     * @return The number of rushing yards gained by the player this post-season.
     */
    public Short getPlayoffRushYds() {
        return playoffRushYds;
    }

    /**
     * @param playoffRushYds  The number of rushing yards gained by the player this
     *                        post-season.
     */
    public void setPlayoffRushYds(Short playoffRushYds) {
        this.playoffRushYds = playoffRushYds;
    }

    /**
     * @return The number of rushing touchdowns scored by the player this post-season.
     */
    public Short getPlayoffRushTds() {
        return playoffRushTds;
    }

    /**
     * @param playoffRushTds  The number of rushing touchdowns scored by the player
     *                        this post-season.
     */
    public void setPlayoffRushTds(Short playoffRushTds) {
        this.playoffRushTds = playoffRushTds;
    }

    /**
     * @return The longest rush made by the player this post-season.
     */
    public Short getPlayoffRushLong() {
        return playoffRushLong;
    }

    /**
     * @param playoffRushLong  The longest rush made by the player this post-season.
     */
    public void setPlayoffRushLong(Short playoffRushLong) {
        this.playoffRushLong = playoffRushLong;
    }

    /**
     * @return The number of passes thrown to the player this post-season.
     */
    public Short getPlayoffTargets() {
        return playoffTargets;
    }

    /**
     * @param playoffTargets  The number of passes thrown to the player this post-season.
     */
    public void setPlayoffTargets(Short playoffTargets) {
        this.playoffTargets = playoffTargets;
    }

    /**
     * @return The number of passes caught by the player this post-season.
     */
    public Short getPlayoffReceptions() {
        return playoffReceptions;
    }

    /**
     * @param playoffReceptions  The number of passes caught by the player this post-season.
     */
    public void setPlayoffReceptions(Short playoffReceptions) {
        this.playoffReceptions = playoffReceptions;
    }

    /**
     * @return The number of receiving yards gained by the player this post-season.
     */
    public Short getPlayoffRecYds() {
        return playoffRecYds;
    }

    /**
     * @param playoffRecYds  The number of receiving yards gained by the player
     *                       this post-season.
     */
    public void setPlayoffRecYds(Short playoffRecYds) {
        this.playoffRecYds = playoffRecYds;
    }

    /**
     * @return The number of receiving touchdowns scored by the player this post-season.
     */
    public Short getPlayoffRecTds() {
        return playoffRecTds;
    }

    /**
     * @param playoffRecTds  The number of receiving touchdowns scored by the player
     *                       this post-season.
     */
    public void setPlayoffRecTds(Short playoffRecTds) {
        this.playoffRecTds = playoffRecTds;
    }

    /**
     * @return The longest reception (yds) made by the player this post-season.
     */
    public Short getPlayoffRecLong() {
        return playoffRecLong;
    }

    /**
     * @param playoffRecLong  The longest reception (yds) made by the player this
     *                        post-season.
     */
    public void setPlayoffRecLong(Short playoffRecLong) {
        this.playoffRecLong = playoffRecLong;
    }

    /**
     * @return The number of times the player has fumbled this post-season.
     */
    public Short getPlayoffFumbles() {
        return playoffFumbles;
    }

    /**
     * @param playoffFumbles  The number of times the player has fumbled this post-season.
     */
    public void setPlayoffFumbles(Short playoffFumbles) {
        this.playoffFumbles = playoffFumbles;
    }

    /**
     * @return The number of passes intercepted by the player this post-season.
     */
    public Short getPlayoffInterceptions() {
        return playoffInterceptions;
    }

    /**
     * @param playoffInterceptions  The number of passes intercepted by the player this
     *                              post-season.
     */
    public void setPlayoffInterceptions(Short playoffInterceptions) {
        this.playoffInterceptions = playoffInterceptions;
    }

    /**
     * @return The number of interception return yards gained by the player this post-season.
     */
    public Short getPlayoffIntYds() {
        return playoffIntYds;
    }

    /**
     * @param playoffIntYds  The number of interception return yards gained by the player this
     *                       post-season.
     */
    public void setPlayoffIntYds(Short playoffIntYds) {
        this.playoffIntYds = playoffIntYds;
    }

    /**
     * @return The number of touchdowns scored by the player on interception returns
     *         this post-season.
     */
    public Short getPlayoffIntTds() {
        return playoffIntTds;
    }

    /**
     * @param playoffIntTds  The number of touchdowns scored by the player on interception returns
     *                       this post-season.
     */
    public void setPlayoffIntTds(Short playoffIntTds) {
        this.playoffIntTds = playoffIntTds;
    }

    /**
     * @return The longest return (yds) of an interception made by the player this post-season.
     */
    public Short getPlayoffIntLong() {
        return playoffIntLong;
    }

    /**
     * @param playoffIntLong  The longest return (yds) of an interception made by the player
     *                        this post-season.
     */
    public void setPlayoffIntLong(Short playoffIntLong) {
        this.playoffIntLong = playoffIntLong;
    }

    /**
     * @return The number of passes defended by the player this post-season.
     */
    public Short getPlayoffPassDef() {
        return playoffPassDef;
    }

    /**
     * @param playoffPassDef  The number of passes defended by the player this post-season.
     */
    public void setPlayoffPassDef(Short playoffPassDef) {
        this.playoffPassDef = playoffPassDef;
    }

    /**
     * @return The number of fumbles forced by the player this post-season.
     */
    public Short getPlayoffFumForced() {
        return playoffFumForced;
    }

    /**
     * @param playoffFumForced  The number of fumbles forced by the player this post-season.
     */
    public void setPlayoffFumForced(Short playoffFumForced) {
        this.playoffFumForced = playoffFumForced;
    }

    /**
     * @return The number of fumbles recovered by the player this post-season.
     */
    public Short getPlayoffFumRec() {
        return playoffFumRec;
    }

    /**
     * @param playoffFumRec  The number of fumbles recovered by the player this post-season.
     */
    public void setPlayoffFumRec(Short playoffFumRec) {
        this.playoffFumRec = playoffFumRec;
    }

    /**
     * @return The number of fumble return yards gained by the player this post-season.
     */
    public Short getPlayoffFumRecYds() {
        return playoffFumRecYds;
    }

    /**
     * @param playoffFumRecYds  The number of fumble return yards gained by the player
     *                          this post-season.
     */
    public void setPlayoffFumRecYds(Short playoffFumRecYds) {
        this.playoffFumRecYds = playoffFumRecYds;
    }

    /**
     * @return The number of touchdowns scored by the player on fumble returns
     *         this post-season.
     */
    public Short getPlayoffFumRecTds() {
        return playoffFumRecTds;
    }

    /**
     * @param playoffFumRecTds  The number of touchdowns scored by the player on fumble
     *                          returns this post-season.
     */
    public void setPlayoffFumRecTds(Short playoffFumRecTds) {
        this.playoffFumRecTds = playoffFumRecTds;
    }

    /**
     * @return The number of quarterback sacks made by the player this post-season.
     */
    public Short getPlayoffSacks() {
        return playoffSacks;
    }

    /**
     * @param playoffSacks  The number of quarterback sacks made by the player this
     *                      post-season.
     */
    public void setPlayoffSacks(Short playoffSacks) {
        this.playoffSacks = playoffSacks;
    }

    /**
     * @return The number of solo tackles made by the player this post-season.
     */
    public Short getPlayoffTackles() {
        return playoffTackles;
    }

    /**
     * @param playoffTackles  The number of solo tackles made by the player this post-season.
     */
    public void setPlayoffTackles(Short playoffTackles) {
        this.playoffTackles = playoffTackles;
    }

    /**
     * @return The number of tackle assists made by the player this post-season.
     */
    public Short getPlayoffAssists() {
        return playoffAssists;
    }

    /**
     * @param playoffAssists  The number of tackle assists made by the player this post-season.
     */
    public void setPlayoffAssists(Short playoffAssists) {
        this.playoffAssists = playoffAssists;
    }

    /**
     * @return The number of safeties made by the player this post-season.
     */
    public Short getPlayoffSafeties() {
        return playoffSafeties;
    }

    /**
     * @param playoffSafeties  The number of safeties made by the player this post-season.
     */
    public void setPlayoffSafeties(Short playoffSafeties) {
        this.playoffSafeties = playoffSafeties;
    }

    /**
     * @return The number of 17-19 yard field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaTeens() {
        return playoffFgaTeens;
    }

    /**
     * @param playoffFgaTeens  The number of 17-19 yard field goals attempted by the player
     *                         this post-season.
     */
    public void setPlayoffFgaTeens(Short playoffFgaTeens) {
        this.playoffFgaTeens = playoffFgaTeens;
    }

    /**
     * @return The number of successful 17-19 yard field goal attempts made by the player
     *         this post-season.
     */
    public Short getPlayoffFgmTeens() {
        return playoffFgmTeens;
    }

    /**
     * @param playoffFgmTeens  The number of successful 17-19 yard field goal attempts made
     *                         by the player this post-season.
     */
    public void setPlayoffFgmTeens(Short playoffFgmTeens) {
        this.playoffFgmTeens = playoffFgmTeens;
    }

    /**
     * @return The number of 20-29 yard field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaTwenties() {
        return playoffFgaTwenties;
    }

    /**
     * @param playoffFgaTwenties  The number of 20-29 yard field goals attempted by the player
     *                            this post-season.
     */
    public void setPlayoffFgaTwenties(Short playoffFgaTwenties) {
        this.playoffFgaTwenties = playoffFgaTwenties;
    }

    /**
     * @return The number of successful 20-29 yard field goal attempts made by the player
     *         this post-season.
     */
    public Short getPlayoffFgmTwenties() {
        return playoffFgmTwenties;
    }

    /**
     * @param playoffFgmTwenties  The number of successful 20-29 yard field goal attempts
     *                            made by the player this post-season.
     */
    public void setPlayoffFgmTwenties(Short playoffFgmTwenties) {
        this.playoffFgmTwenties = playoffFgmTwenties;
    }

    /**
     * @return The number of 30-39 yard field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaThirties() {
        return playoffFgaThirties;
    }

    /**
     * @param playoffFgaThirties  The number of 30-39 yard field goals attempted by the player
     *                            this post-season.
     */
    public void setPlayoffFgaThirties(Short playoffFgaThirties) {
        this.playoffFgaThirties = playoffFgaThirties;
    }

    /**
     * @return The number of successful 30-39 yard field goal attempts made by the player
     *         this post-season.
     */
    public Short getPlayoffFgmThirties() {
        return playoffFgmThirties;
    }

    /**
     * @param playoffFgmThirties  The number of successful 30-39 yard field goal attempts
     *                            made by the player this post-season.
     */
    public void setPlayoffFgmThirties(Short playoffFgmThirties) {
        this.playoffFgmThirties = playoffFgmThirties;
    }

    /**
     * @return The number of 40-49 yard field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaFourties() {
        return playoffFgaFourties;
    }

    /**
     * @param playoffFgaFourties  The number of 40-49 yard field goals attempted by the player
     *                            this post-season.
     */
    public void setPlayoffFgaFourties(Short playoffFgaFourties) {
        this.playoffFgaFourties = playoffFgaFourties;
    }

    /**
     * @return The number of successful 40-49 yard field goal attempts made by the player
     *         this post-season.
     */
    public Short getPlayoffFgmFourties() {
        return playoffFgmFourties;
    }

    /**
     * @param playoffFgmFourties  The number of successful 40-49 yard field goal attempts made
     *                            by the player this post-season.
     */
    public void setPlayoffFgmFourties(Short playoffFgmFourties) {
        this.playoffFgmFourties = playoffFgmFourties;
    }

    /**
     * @return The number of 50+ yard field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaFiftyPlus() {
        return playoffFgaFiftyPlus;
    }

    /**
     * @param playoffFgaFiftyPlus  The number of 50+ yard field goals attempted by the player
     *                             this post-season.
     */
    public void setPlayoffFgaFiftyPlus(Short playoffFgaFiftyPlus) {
        this.playoffFgaFiftyPlus = playoffFgaFiftyPlus;
    }

    /**
     * @return The number of successful 50+ yard field goal attempts made by the player this
     *         post-season.
     */
    public Short getPlayoffFgmFiftyPlus() {
        return playoffFgmFiftyPlus;
    }

    /**
     * @param playoffFgmFiftyPlus  The number of successful 50+ yard field goal attempts made
     *                             by the player this post-season.
     */
    public void setPlayoffFgmFiftyPlus(Short playoffFgmFiftyPlus) {
        this.playoffFgmFiftyPlus = playoffFgmFiftyPlus;
    }

    /**
     * @return The total number of field goals attempted by the player this post-season.
     */
    public Short getPlayoffFgaTotal() {
        return playoffFgaTotal;
    }

    /**
     * @param playoffFgaTotal  The total number of field goals attempted by the player
     *                         this post-season.
     */
    public void setPlayoffFgaTotal(Short playoffFgaTotal) {
        this.playoffFgaTotal = playoffFgaTotal;
    }

    /**
     * @return The total number of successful field goal attempts made by the player this
     *         post-season.
     */
    public Short getPlayoffFgmTotal() {
        return playoffFgmTotal;
    }

    /**
     * @param fgmTotal  The total number of successful field goal attempts made by the player
     *                  this season.
     */
    public void setPlayoffFgmTotal(Short playoffFgmTotal) {
        this.playoffFgmTotal = playoffFgmTotal;
    }

    /**
     * @return The longest field goal (yds) made by the player this post-season.
     */
    public Short getPlayoffFgLong() {
        return playoffFgLong;
    }

    /**
     * @param playoffFgLong  The longest field goal (yds) made by the player this
     *                       post-season.
     */
    public void setPlayoffFgLong(Short playoffFgLong) {
        this.playoffFgLong = playoffFgLong;
    }

    /**
     * @return The total number of extra points attempted by the player this post-season.
     */
    public Short getPlayoffXpa() {
        return playoffXpa;
    }

    /**
     * @param playoffXpa  The total number of extra points attempted by the player this
     *                    post-season.
     */
    public void setPlayoffXpa(Short playoffXpa) {
        this.playoffXpa = playoffXpa;
    }

    /**
     * @return The total number of successful extra point attempts made by the player
     *         this post-season.
     */
    public Short getPlayoffXpm() {
        return playoffXpm;
    }

    /**
     * @param playoffXpm  The total number of successful extra point attempts made by the
     *                    player this post-season.
     */
    public void setPlayoffXpm(Short playoffXpm) {
        this.playoffXpm = playoffXpm;
    }

    /**
     * @return The total number of punts made by the player this post-season.
     */
    public Short getPlayoffPunts() {
        return playoffPunts;
    }

    /**
     * @param playoffPunts  The total number of punts made by the player this post-season.
     */
    public void setPlayoffPunts(Short playoffPunts) {
        this.playoffPunts = playoffPunts;
    }

    /**
     * @return The total number of punt yards made by the player this post-season.
     */
    public Short getPlayoffPuntYds() {
        return playoffPuntYds;
    }

    /**
     * @param playoffPuntYds  The total number of punt yards made by the player this
     *                        post-season.
     */
    public void setPlayoffPuntYds(Short playoffPuntYds) {
        this.playoffPuntYds = playoffPuntYds;
    }

    /**
     * @return The longest punt (yds) made by the player this post-season.
     */
    public Short getPlayoffPuntLong() {
        return playoffPuntLong;
    }

    /**
     * @param playoffPuntLong  The longest punt (yds) made by the player this post-season.
     */
    public void setPlayoffPuntLong(Short playoffPuntLong) {
        this.playoffPuntLong = playoffPuntLong;
    }

    /**
     * @return The number of the player's punts that were blocked this post-season.
     */
    public Short getPlayoffPuntBlocked() {
        return playoffPuntBlocked;
    }

    /**
     * @param playoffPuntBlocked  The number of the player's punts that were blocked this
     *                            post-season.
     */
    public void setPlayoffPuntBlocked(Short playoffPuntBlocked) {
        this.playoffPuntBlocked = playoffPuntBlocked;
    }

    /**
     * @return The number of punts returned by the player this post-season.
     */
    public Short getPlayoffPuntRet() {
        return playoffPuntRet;
    }

    /**
     * @param playoffPuntRet  The number of punts returned by the player this post-season.
     */
    public void setPlayoffPuntRet(Short playoffPuntRet) {
        this.playoffPuntRet = playoffPuntRet;
    }

    /**
     * @return The number of punt return yards gained by the player this post-season.
     */
    public Short getPlayoffPuntRetYds() {
        return playoffPuntRetYds;
    }

    /**
     * @param playoffPuntRetYds  The number of punt return yards gained by the player this
     *                           post-season.
     */
    public void setPlayoffPuntRetYds(Short playoffPuntRetYds) {
        this.playoffPuntRetYds = playoffPuntRetYds;
    }

    /**
     * @return The number of punts returned for touchdowns by the player this post-season.
     */
    public Short getPlayoffPuntRetTds() {
        return playoffPuntRetTds;
    }

    /**
     * @param playoffPuntRetTds  The number of punts returned for touchdowns by the player
     *                           this post-season.
     */
    public void setPlayoffPuntRetTds(Short playoffPuntRetTds) {
        this.playoffPuntRetTds = playoffPuntRetTds;
    }

    /**
     * @return The longest punt return (yds) made by the player this post-season.
     */
    public Short getPlayoffPuntRetLong() {
        return playoffPuntRetLong;
    }

    /**
     * @param playoffPuntRetLong  The longest punt return (yds) made by the player this
     *                            post-season.
     */
    public void setPlayoffPuntRetLong(Short playoffPuntRetLong) {
        this.playoffPuntRetLong = playoffPuntRetLong;
    }

    /**
     * @return The number of kickoffs returned by the player this post-season.
     */
    public Short getPlayoffKickRet() {
        return playoffKickRet;
    }

    /**
     * @param playoffKickRet  The number of kickoffs returned by the player this post-season.
     */
    public void setPlayoffKickRet(Short playoffKickRet) {
        this.playoffKickRet = playoffKickRet;
    }

    /**
     * @return The number of kickoff return yards gained by the player this post-season.
     */
    public Short getPlayoffKickRetYds() {
        return playoffKickRetYds;
    }

    /**
     * @param playoffKickRetYds  The number of kickoff return yards gained by the player
     *                           this post-season.
     */
    public void setPlayoffKickRetYds(Short playoffKickRetYds) {
        this.playoffKickRetYds = playoffKickRetYds;
    }

    /**
     * @return The number of kickoffs returned for touchdowns by the player this post-season.
     */
    public Short getPlayoffKickRetTds() {
        return playoffKickRetTds;
    }

    /**
     * @param playoffKickRetTds  The number of kickoffs returned for touchdowns by the player
     *                           this post-season.
     */
    public void setPlayoffKickRetTds(Short playoffKickRetTds) {
        this.playoffKickRetTds = playoffKickRetTds;
    }

    /**
     * @return The longest kick return (yds) made by the player this post-season.
     */
    public Short getPlayoffKickRetLong() {
        return playoffKickRetLong;
    }

    /**
     * @param playoffKickRetLong  The longest kick return (yds) made by the player this post-season.
     */
    public void setPlayoffKickRetLong(Short playoffKickRetLong) {
        this.playoffKickRetLong = playoffKickRetLong;
    }

    /**
     * @return The total number of touchdowns scored via other means by the player this
     *         post-season.
     */
    public Short getPlayoffOtherTds() {
        return playoffOtherTds;
    }

    /**
     * @param playoffOtherTds  The total number of touchdowns scored via other means by the
     *                         player this post-season.
     */
    public void setPlayoffOtherTds(Short playoffOtherTds) {
        this.playoffOtherTds = playoffOtherTds;
    }

    /**
     * @return The total number of touchdowns scored by the player during this post-season.
     */
    public Short getPlayoffAllTds() {
        return playoffAllTds;
    }

    /**
     * @param playoffAllTds  The total number of touchdowns scored by the player during this
     *                       post-season.
     */
    public void setPlayoffAllTds(Short playoffAllTds) {
        this.playoffAllTds = playoffAllTds;
    }

    /**
     * @return The total number of two point conversions scored by the player during this
     *         post-season.
     */
    public Short getPlayoffTwoPointConv() {
        return playoffTwoPointConv;
    }

    /**
     * @param playoffTwoPointConv  The total number of two point conversions scored by the player
     *                             during this post-season.
     */
    public void setPlayoffTwoPointConv(Short playoffTwoPointConv) {
        this.playoffTwoPointConv = playoffTwoPointConv;
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
    public Short getAvgValue() {
        return avgValue;
    }

    /**
     * @param setAvgValue  A metric calculated from the player's statistics this season
     *                     which approximates the amount of value the player has produced
     *                     this season.
     */
    public void setAvgValue(Short avgValue) {
        this.avgValue = avgValue;
    }

    /**
     * Compares this playerSeason to the argument. The result is <code>true</code>
     * if and only if the argument is a non-null instance of PlayerSeason that has equivalent
     * values for the playerId, season, and franchiseId attributes.
     *
     * @param obj  The object to compare this team against.
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
