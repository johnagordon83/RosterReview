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

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An {@link Entity} describing the statistics generated during a single season
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
    private String playerId;

    @Id
    @Column(name="franchise_id")
    private String franchiseId;

    @Id
    @Column(name="season")
    private Integer season;

    @Id
    @Column(name="season_type")
    @Enumerated(EnumType.STRING)
    private SeasonType seasonType;

    @OneToOne()
    @Fetch(FetchMode.JOIN)
    @JoinColumns({
        @JoinColumn(name="franchise_id", referencedColumnName="franchise_id",
                insertable=false, updatable=false),
        @JoinColumn(name="season", referencedColumnName="season",
                insertable=false, updatable=false)
        })
    private Team team;

    @Column(name="age")
    private Integer age;

    @Column(name="position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name="jersey_number")
    private Integer jerseyNumber;

    @Column(name="games_played", nullable=false)
    private Integer gamesPlayed = 0;

    @Column(name="games_started", nullable=false)
    private Integer gamesStarted = 0;

    @Column(name="pass_comp", nullable=false)
    private Integer passComp = 0;

    @Column(name="pass_att", nullable=false)
    private Integer passAtt = 0;

    @Column(name="pass_yds", nullable=false)
    private Integer passYds = 0;

    @Column(name="pass_tds", nullable=false)
    private Integer passTds = 0;

    @Column(name="pass_ints", nullable=false)
    private Integer passInts = 0;

    @Column(name="pass_long", nullable=false)
    private Integer passLong = 0;

    @Column(name="passer_rating", nullable=false)
    private Double passerRating = 0.0;

    @Column(name="sacked", nullable=false)
    private Integer sacked = 0;

    @Column(name="sacked_yds", nullable=false)
    private Integer sackedYds = 0;

    @Column(name="rush_att", nullable=false)
    private Integer rushAtt = 0;

    @Column(name="rush_yds", nullable=false)
    private Integer rushYds = 0;

    @Column(name="rush_tds", nullable=false)
    private Integer rushTds = 0;

    @Column(name="rush_long")
    private Integer rushLong = 0;

    @Column(name="targets", nullable=false)
    private Integer targets = 0;

    @Column(name="rec", nullable=false)
    private Integer receptions = 0;

    @Column(name="rec_yds", nullable=false)
    private Integer recYds = 0;

    @Column(name="rec_tds", nullable=false)
    private Integer recTds = 0;

    @Column(name="rec_long", nullable=false)
    private Integer recLong = 0;

    @Column(name="fumbles", nullable=false)
    private Integer fumbles = 0;

    @Column(name="interceptions", nullable=false)
    private Integer interceptions = 0;

    @Column(name="int_yds", nullable=false)
    private Integer intYds = 0;

    @Column(name="int_tds", nullable=false)
    private Integer intTds = 0;

    @Column(name="int_long", nullable=false)
    private Integer intLong = 0;

    @Column(name="pass_def", nullable=false)
    private Integer passDef = 0;

    @Column(name="fum_forced", nullable=false)
    private Integer fumForced = 0;

    @Column(name="fum_rec", nullable=false)
    private Integer fumRec = 0;

    @Column(name="fum_rec_yds", nullable=false)
    private Integer fumRecYds = 0;

    @Column(name="fum_rec_tds", nullable=false)
    private Integer fumRecTds = 0;

    @Column(name="sacks", nullable=false)
    private Double sacks = 0.0;

    @Column(name="tackles", nullable=false)
    private Integer tackles = 0;

    @Column(name="assists", nullable=false)
    private Integer assists = 0;

    @Column(name="safeties", nullable=false)
    private Integer safeties = 0;

    @Column(name="fga_teens", nullable=false)
    private Integer fgaTeens = 0;

    @Column(name="fgm_teens", nullable=false)
    private Integer fgmTeens = 0;

    @Column(name="fga_twenties", nullable=false)
    private Integer fgaTwenties = 0;

    @Column(name="fgm_twenties", nullable=false)
    private Integer fgmTwenties = 0;

    @Column(name="fga_thirties", nullable=false)
    private Integer fgaThirties = 0;

    @Column(name="fgm_thirties", nullable=false)
    private Integer fgmThirties = 0;

    @Column(name="fga_fourties", nullable=false)
    private Integer fgaFourties = 0;

    @Column(name="fgm_fourties", nullable=false)
    private Integer fgmFourties = 0;

    @Column(name="fga_fifty_plus", nullable=false)
    private Integer fgaFiftyPlus = 0;

    @Column(name="fgm_fifty_plus", nullable=false)
    private Integer fgmFiftyPlus = 0;

    @Column(name="fga_total", nullable=false)
    private Integer fgaTotal = 0;

    @Column(name="fgm_total", nullable=false)
    private Integer fgmTotal = 0;

    @Column(name="fg_long", nullable=false)
    private Integer fgLong = 0;

    @Column(name="xpa", nullable=false)
    private Integer xpa = 0;

    @Column(name="xpm", nullable=false)
    private Integer xpm = 0;

    @Column(name="punts", nullable=false)
    private Integer punts = 0;

    @Column(name="punt_yds", nullable=false)
    private Integer puntYds = 0;

    @Column(name="punt_long", nullable=false)
    private Integer puntLong = 0;

    @Column(name="punt_blocked", nullable=false)
    private Integer puntBlocked = 0;

    @Column(name="punt_ret", nullable=false)
    private Integer puntRet = 0;

    @Column(name="punt_ret_yds", nullable=false)
    private Integer puntRetYds = 0;

    @Column(name="punt_ret_tds", nullable=false)
    private Integer puntRetTds = 0;

    @Column(name="punt_ret_long", nullable=false)
    private Integer puntRetLong = 0;

    @Column(name="kick_ret", nullable=false)
    private Integer kickRet = 0;

    @Column(name="kick_ret_yds", nullable=false)
    private Integer kickRetYds = 0;

    @Column(name="kick_ret_tds", nullable=false)
    private Integer kickRetTds = 0;

    @Column(name="kick_ret_long", nullable=false)
    private Integer kickRetLong = 0;

    @Column(name="other_tds", nullable=false)
    private Integer otherTds = 0;

    @Column(name="all_tds", nullable=false)
    private Integer allTds = 0;

    @Column(name="two_point_conv", nullable=false)
    private Integer twoPointConv = 0;

    @Column(name="probowl")
    private Boolean probowl;

    @Column(name="all_pro")
    private Boolean allPro;

    @Column(name="avg_value", nullable=false)
    private Integer avgValue = 0;

    /**
     * An enum defining the types of football seasons
     */
    public enum SeasonType {
        /**
         * Regular season
         */
        REGULAR,

        /**
         * Post-season
         */
        POST
    }

    /**
     * A no-argument constructor required by Spring
     */
    public PlayerSeason() {}

    public PlayerSeason(String playerId, String franchiseId, Integer season,
            SeasonType seasonType, Integer age, Position position,
            Integer jerseyNumber, Integer gamesPlayed, Integer gamesStarted,
            Integer passComp, Integer passAtt, Integer passYds, Integer passTds,
            Integer passInts, Integer passLong, Double passerRating, Integer sacked,
            Integer sackedYds, Integer rushAtt, Integer rushYds, Integer rushTds,
            Integer rushLong, Integer targets, Integer receptions, Integer recYds,
            Integer recTds, Integer recLong, Integer fumbles, Integer interceptions,
            Integer intYds, Integer intTds, Integer intLong, Integer passDef,
            Integer fumForced, Integer fumRec, Integer fumRecYds, Integer fumRecTds,
            Double sacks, Integer tackles, Integer assists, Integer safeties,
            Integer fgaTeens, Integer fgmTeens, Integer fgaTwenties,
            Integer fgmTwenties, Integer fgaThirties, Integer fgmThirties,
            Integer fgaFourties, Integer fgmFourties, Integer fgaFiftyPlus,
            Integer fgmFiftyPlus, Integer fgaTotal, Integer fgmTotal,
            Integer fgLong, Integer xpa, Integer xpm, Integer punts,
            Integer puntYds, Integer puntLong, Integer puntBlocked, Integer puntRet,
            Integer puntRetYds, Integer puntRetTds, Integer puntRetLong,
            Integer kickRet, Integer kickRetYds, Integer kickRetTds,
            Integer kickRetLong, Integer otherTds, Integer allTds,
            Integer twoPointConv, Boolean probowl, Boolean allPro, Integer avgValue) {
        this.playerId = playerId;
        this.franchiseId = franchiseId;
        this.season = season;
        this.seasonType = seasonType;
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
        this.probowl = probowl;
        this.allPro = allPro;
        this.avgValue = avgValue;
    }

    /**
     * @return  the unique identifier of the player
     */
    @JsonIgnore
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId  the unique identifier of the player
     */
    @JsonProperty
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return  the unique identifier of the football team the player played
     *          for this season
     */
    public String getFranchiseId() {
        return franchiseId;
    }

    /**
     * @param franchiseId  the unique identifier of the football team the player
     *                     played for this season
     */
    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    /**
     * @return  the season (year) these statistics were generated
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * @param season  the season (year) these statistics were generated
     */
    public void setSeason(Integer season) {
        this.season = season;
    }

    /**
     * @return  the type of season these statistics were generated in
     */
    public SeasonType getSeasonType() {
        return seasonType;
    }

    /**
     * @param seasonType  the type of season these statistics were generated in
     */
    public void setSeasonType(SeasonType seasonType) {
        this.seasonType = seasonType;
    }

    /**
     * @return  the team the player played for this season
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team  the team the player played for this season
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * @return  the age of the player during this season
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age  the age of the player during this season
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return  the primary position the player played this season
     */
     public Position getPosition() {
        return position;
    }

    /**
     * @param position  the primary position the player played this season
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return  the jersey number worn by the player this season
     */
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    /**
     * @param jerseyNumber  the jersey number worn by the player this season
     */
    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    /**
     * @return  the number of games the player appeared in this season
     */
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @param gamesPlayed  the number of games the player appeared in this season
     */
    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * @return  the number of games the player started this season
     */
    public Integer getGamesStarted() {
        return gamesStarted;
    }

    /**
     * @param gamesStarted  the number of games the player started this season
     */
    public void setGamesStarted(Integer gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    /**
     * @return  the number of passes completed by the player this season
     */
    public Integer getPassComp() {
        return passComp;
    }

    /**
     * @param passComp  the number of passes completed by the player this season
     */
    public void setPassComp(Integer passComp) {
        this.passComp = passComp;
    }

    /**
     * @return  the number of passes attempted by the player this season
     */
    public Integer getPassAtt() {
        return passAtt;
    }

    /**
     * @param passAtt  the number of passes attempted by the player this season
     */
    public void setPassAtt(Integer passAtt) {
        this.passAtt = passAtt;
    }

    /**
     * @return  the number of passing yards thrown by the player this season
     */
    public Integer getPassYds() {
        return passYds;
    }

    /**
     * @param passYds  the number of passing yards thrown by the player this season
     */
    public void setPassYds(Integer passYds) {
        this.passYds = passYds;
    }

    /**
     * @return  the number of passing touchdowns thrown by the player this season
     */
    public Integer getPassTds() {
        return passTds;
    }

    /**
     * @param passTds  the number of passing touchdowns thrown by the player
     * this season
     */
    public void setPassTds(Integer passTds) {
        this.passTds = passTds;
    }

    /**
     * @return  the number of interceptions thrown by the player this season
     */
    public Integer getPassInts() {
        return passInts;
    }

    /**
     * @param passInts  the number of interceptions thrown by the player this season
     */
    public void setPassInts(Integer passInts) {
        this.passInts = passInts;
    }

    /**
     * @return  the longest pass (yds) thrown by the player this season
     */
    public Integer getPassLong() {
        return passLong;
    }

    /**
     * @param passLong  the longest pass (yds) thrown by the player this season
     */
    public void setPassLong(Integer passLong) {
        this.passLong = passLong;
    }

    /**
     * @return  the player's passer rating for the season
     */
    public Double getPasserRating() {
        return passerRating;
    }

    /**
     * @param passerRating  the player's passer rating for this season
     */
    public void setPassRating(Double passerRating) {
        this.passerRating = passerRating;
    }

    /**
     * @return  the number of times the player was sacked this season
     */
    public Integer getSacked() {
        return sacked;
    }

    /**
     * @param sacked  the number of times the player was sacked this season
     */
    public void setSacked(Integer sacked) {
        this.sacked = sacked;
    }

    /**
     * @return  the number yards lost when the player was sacked this season
     */
    public Integer getSackedYds() {
        return sackedYds;
    }

    /**
     * @param sackedYds  the number yards lost when the player was sacked this season
     */
    public void setSackedYds(Integer sackedYds) {
        this.sackedYds = sackedYds;
    }

    /**
     * @return  the number of rushes attempted by the player this season
     */
    public Integer getRushAtt() {
        return rushAtt;
    }

    /**
     * @param rushAtt  the number of rushes attempted by the player this season
     */
    public void setRushAtt(Integer rushAtt) {
        this.rushAtt = rushAtt;
    }

    /**
     * @return  the number of rushing yards gained by the player this season
     */
    public Integer getRushYds() {
        return rushYds;
    }

    /**
     * @param rushYds  the number of rushing yards gained by the player this season
     */
    public void setRushYds(Integer rushYds) {
        this.rushYds = rushYds;
    }

    /**
     * @return  the number of rushing touchdowns scored by the player this season
     */
    public Integer getRushTds() {
        return rushTds;
    }

    /**
     * @param rushTds  the number of rushing touchdowns scored by the player this season
     */
    public void setRushTds(Integer rushTds) {
        this.rushTds = rushTds;
    }

    /**
     * @return  the longest rush made by the player this season
     */
    public Integer getRushLong() {
        return rushLong;
    }

    /**
     * @param rushLong  the longest rush made by the player this season
     */
    public void setRushLong(Integer rushLong) {
        this.rushLong = rushLong;
    }

    /**
     * @return  the number of passes thrown to the player this season
     */
    public Integer getTargets() {
        return targets;
    }

    /**
     * @param targets  the number of passes thrown to the player this season
     */
    public void setTargets(Integer targets) {
        this.targets = targets;
    }

    /**
     * @return  the number of passes caught by the player this season
     */
    public Integer getReceptions() {
        return receptions;
    }

    /**
     * @param receptions  the number of passes caught by the player this season
     */
    public void setReceptions(Integer receptions) {
        this.receptions = receptions;
    }

    /**
     * @return  the number of receiving yards gained by the player this season
     */
    public Integer getRecYds() {
        return recYds;
    }

    /**
     * @param recYds  the number of receiving yards gained by the player this season
     */
    public void setRecYds(Integer recYds) {
        this.recYds = recYds;
    }

    /**
     * @return  the number of receiving touchdowns scored by the player this season
     */
    public Integer getRecTds() {
        return recTds;
    }

    /**
     * @param recTds  the number of receiving touchdowns scored by the player this season
     */
    public void setRecTds(Integer recTds) {
        this.recTds = recTds;
    }

    /**
     * @return  the longest reception (yds) made by the player this season
     */
    public Integer getRecLong() {
        return recLong;
    }

    /**
     * @param recLong  the longest reception (yds) made by the player this season
     */
    public void setRecLong(Integer recLong) {
        this.recLong = recLong;
    }

    /**
     * @return  the number of times the player has fumbled this season
     */
    public Integer getFumbles() {
        return fumbles;
    }

    /**
     * @param fumbles  the number of times the player has fumbled this season
     */
    public void setFumbles(Integer fumbles) {
        this.fumbles = fumbles;
    }

    /**
     * @return  the number of passes intercepted by the player this season
     */
    public Integer getInterceptions() {
        return interceptions;
    }

    /**
     * @param interceptions  the number of passes intercepted by the player this season
     */
    public void setInterceptions(Integer interceptions) {
        this.interceptions = interceptions;
    }

    /**
     * @return  the number of interception return yards gained by the player this season
     */
    public Integer getIntYds() {
        return intYds;
    }

    /**
     * @param intYds  the number of interception return yards gained by the player this
     *                season
     */
    public void setIntYds(Integer intYds) {
        this.intYds = intYds;
    }

    /**
     * @return  the number of touchdowns scored by the player on interception returns
     *          this season
     */
    public Integer getIntTds() {
        return intTds;
    }

    /**
     * @param intTds  the number of touchdowns scored by the player on
     *                interception returns this season
     */
    public void setIntTds(Integer intTds) {
        this.intTds = intTds;
    }

    /**
     * @return  the longest return (yds) of an interception made by the player
     *          this season
     */
    public Integer getIntLong() {
        return intLong;
    }

    /**
     * @param intLong  the longest return (yds) of an interception made by the
     *                 player this season
     */
    public void setIntLong(Integer intLong) {
        this.intLong = intLong;
    }

    /**
     * @return  the number of passes defended by the player this season
     */
    public Integer getPassDef() {
        return passDef;
    }

    /**
     * @param passDef  the number of passes defended by the player this season
     */
    public void setPassDef(Integer passDef) {
        this.passDef = passDef;
    }

    /**
     * @return  the number of fumbles forced by the player this season
     */
    public Integer getFumForced() {
        return fumForced;
    }

    /**
     * @param fumForced  the number of fumbles forced by the player this season
     */
    public void setFumForced(Integer fumForced) {
        this.fumForced = fumForced;
    }

    /**
     * @return  the number of fumbles recovered by the player this season
     */
    public Integer getFumRec() {
        return fumRec;
    }

    /**
     * @param fumRec  the number of fumbles recovered by the player this season
     */
    public void setFumRec(Integer fumRec) {
        this.fumRec = fumRec;
    }

    /**
     * @return  the number of fumble return yards gained by the player this season
     */
    public Integer getFumRecYds() {
        return fumRecYds;
    }

    /**
     * @param fumRecYds  the number of fumble return yards gained by the player
     *                   this season
     */
    public void setFumRecYds(Integer fumRecYds) {
        this.fumRecYds = fumRecYds;
    }

    /**
     * @return  the number of touchdowns scored by the player on fumble returns
     *          this season
     */
    public Integer getFumRecTds() {
        return fumRecTds;
    }

    /**
     * @param fumRecTds  the number of touchdowns scored by the player on fumble
     *                   returns this season
     */
    public void setFumRecTds(Integer fumRecTds) {
        this.fumRecTds = fumRecTds;
    }

    /**
     * @return  the number of quarterback sacks made by the player this season
     */
    public Double getSacks() {
        return sacks;
    }

    /**
     * @param sacks  the number of quarterback sacks made by the player this season
     */
    public void setSacks(Double sacks) {
        this.sacks = sacks;
    }

    /**
     * @return  the number of solo tackles made by the player this season
     */
    public Integer getTackles() {
        return tackles;
    }

    /**
     * @param tackles  the number of solo tackles made by the player this season
     */
    public void setTackles(Integer tackles) {
        this.tackles = tackles;
    }

    /**
     * @return  the number of tackle assists made by the player this season
     */
    public Integer getAssists() {
        return assists;
    }

    /**
     * @param assists  the number of tackle assists made by the player this season
     */
    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    /**
     * @return  the number of safeties made by the player this season
     */
    public Integer getSafeties() {
        return safeties;
    }

    /**
     * @param safeties  the number of safeties made by the player this season
     */
    public void setSafeties(Integer safeties) {
        this.safeties = safeties;
    }

    /**
     * @return  the number of 17-19 yard field goals attempted by the player
     *          this season
     */
    public Integer getFgaTeens() {
        return fgaTeens;
    }

    /**
     * @param fgaTeens  the number of 17-19 yard field goals attempted by the
     *                  player this season
     */
    public void setFgaTeens(Integer fgaTeens) {
        this.fgaTeens = fgaTeens;
    }

    /**
     * @return  the number of successful 17-19 yard field goal attempts made by
     *          the player this season
     */
    public Integer getFgmTeens() {
        return fgmTeens;
    }

    /**
     * @param fgmTeens  the number of successful 17-19 yard field goal attempts
     *                  made by the player this season
     */
    public void setFgmTeens(Integer fgmTeens) {
        this.fgmTeens = fgmTeens;
    }

    /**
     * @return  the number of 20-29 yard field goals attempted by the player
     *          this season
     */
    public Integer getFgaTwenties() {
        return fgaTwenties;
    }

    /**
     * @param fgaTwenties  the number of 20-29 yard field goals attempted by
     *                     the player this season
     */
    public void setFgaTwenties(Integer fgaTwenties) {
        this.fgaTwenties = fgaTwenties;
    }

    /**
     * @return  the number of successful 20-29 yard field goal attempts made
     *          by the player this season
     */
    public Integer getFgmTwenties() {
        return fgmTwenties;
    }

    /**
     * @param fgmTwenties  the number of successful 20-29 yard field goal
     *                     attempts made by the player this season
     */
    public void setFgmTwenties(Integer fgmTwenties) {
        this.fgmTwenties = fgmTwenties;
    }

    /**
     * @return  the number of 30-39 yard field goals attempted by the
     *          player this season
     */
    public Integer getFgaThirties() {
        return fgaThirties;
    }

    /**
     * @param fgaThirties  the number of 30-39 yard field goals attempted by
     *                     the player this season
     */
    public void setFgaThirties(Integer fgaThirties) {
        this.fgaThirties = fgaThirties;
    }

    /**
     * @return  the number of successful 30-39 yard field goal attempts made
     *          by the player this season
     */
    public Integer getFgmThirties() {
        return fgmThirties;
    }

    /**
     * @param fgmThirties  the number of successful 30-39 yard field goal
     *                     attempts made by the player this season
     */
    public void setFgmThirties(Integer fgmThirties) {
        this.fgmThirties = fgmThirties;
    }

    /**
     * @return  the number of 40-49 yard field goals attempted by the player
     *          this season
     */
    public Integer getFgaFourties() {
        return fgaFourties;
    }

    /**
     * @param fgaFourties  the number of 40-49 yard field goals attempted by
     *                     the player this season
     */
    public void setFgaFourties(Integer fgaFourties) {
        this.fgaFourties = fgaFourties;
    }

    /**
     * @return  the number of successful 40-49 yard field goal attempts made
     *          by the player this season
     */
    public Integer getFgmFourties() {
        return fgmFourties;
    }

    /**
     * @param fgmFourties  the number of successful 40-49 yard field goal
     *                     attempts made by the player this season
     */
    public void setFgmFourties(Integer fgmFourties) {
        this.fgmFourties = fgmFourties;
    }

    /**
     * @return  the number of 50+ yard field goals attempted by the player
     *          this season
     */
    public Integer getFgaFiftyPlus() {
        return fgaFiftyPlus;
    }

    /**
     * @param fgaFiftyPlus  the number of 50+ yard field goals attempted by
     *                      the player this season
     */
    public void setFgaFiftyPlus(Integer fgaFiftyPlus) {
        this.fgaFiftyPlus = fgaFiftyPlus;
    }

    /**
     * @return  the number of successful 50+ yard field goal attempts made by
     *          the player this season
     */
    public Integer getFgmFiftyPlus() {
        return fgmFiftyPlus;
    }

    /**
     * @param fgmFiftyPlus  the number of successful 50+ yard field goal
     *                      attempts made by the player this season
     */
    public void setFgmFiftyPlus(Integer fgmFiftyPlus) {
        this.fgmFiftyPlus = fgmFiftyPlus;
    }

    /**
     * @return  the total number of field goals attempted by the player this season
     */
    public Integer getFgaTotal() {
        return fgaTotal;
    }

    /**
     * @param fgaTotal  the total number of field goals attempted by the player
     *                  this season
     */
    public void setFgaTotal(Integer fgaTotal) {
        this.fgaTotal = fgaTotal;
    }

    /**
     * @return  the total number of successful field goal attempts made by the
     *          player this season
     */
    public Integer getFgmTotal() {
        return fgmTotal;
    }

    /**
     * @param fgmTotal  the total number of successful field goal attempts made
     *                  by the player this season
     */
    public void setFgmTotal(Integer fgmTotal) {
        this.fgmTotal = fgmTotal;
    }

    /**
     * @return  the longest successful field goal (yds) attempt made by the
     *          player this season
     */
    public Integer getFgLong() {
        return fgLong;
    }

    /**
     * @param fgLong  the longest successful field goal (yds) attempt made by
     *                the player this season
     */
    public void setFgLong(Integer fgLong) {
        this.fgLong = fgLong;
    }

    /**
     * @return  the total number of extra points attempted by the player this season
     */
    public Integer getXpa() {
        return xpa;
    }

    /**
     * @param xpa  the total number of extra points attempted by the player this season
     */
    public void setXpa(Integer xpa) {
        this.xpa = xpa;
    }

    /**
     * @return  the total number of successful extra point attempts made by
     *          the player this season
     */
    public Integer getXpm() {
        return xpm;
    }

    /**
     * @param xpm  the total number of successful extra point attempts made by
     *             the player this season
     */
    public void setXpm(Integer xpm) {
        this.xpm = xpm;
    }

    /**
     * @return the total number of punts made by the player this season
     */
    public Integer getPunts() {
        return punts;
    }

    /**
     * @param punts  the total number of punts made by the player this season
     */
    public void setPunts(Integer punts) {
        this.punts = punts;
    }

    /**
     * @return the total number of punt yards made by the player this season
     */
    public Integer getPuntYds() {
        return puntYds;
    }

    /**
     * @param puntYds  the total number of punt yards made by the player this season
     */
    public void setPuntYds(Integer puntYds) {
        this.puntYds = puntYds;
    }

    /**
     * @return the longest punt (yds) made by the player this season
     */
    public Integer getPuntLong() {
        return puntLong;
    }

    /**
     * @param puntLong  the longest punt (yds) made by the player this season
     */
    public void setPuntLong(Integer puntLong) {
        this.puntLong = puntLong;
    }

    /**
     * @return the number of the player's punts that were blocked this season
     */
    public Integer getPuntBlocked() {
        return puntBlocked;
    }

    /**
     * @param puntBlocked  the number of the player's punts that were blocked
     *                     this season
     */
    public void setPuntBlocked(Integer puntBlocked) {
        this.puntBlocked = puntBlocked;
    }

    /**
     * @return the number of punts returned by the player this season
     */
    public Integer getPuntRet() {
        return puntRet;
    }

    /**
     * @param puntRet  the number of punts returned by the player this season
     */
    public void setPuntRet(Integer puntRet) {
        this.puntRet = puntRet;
    }

    /**
     * @return the number of punt return yards gained by the player this season
     */
    public Integer getPuntRetYds() {
        return puntRetYds;
    }

    /**
     * @param puntRetYds  the number of punt return yards gained by the player
     *                    this season
     */
    public void setPuntRetYds(Integer puntRetYds) {
        this.puntRetYds = puntRetYds;
    }

    /**
     * @return  the number of punts returned for touchdowns by the player
     *          this season
     */
    public Integer getPuntRetTds() {
        return puntRetTds;
    }

    /**
     * @param puntRetTds  the number of punts returned for touchdowns by the
     *                    player this season
     */
    public void setPuntRetTds(Integer puntRetTds) {
        this.puntRetTds = puntRetTds;
    }

    /**
     * @return the longest punt return (yds) made by the player this season
     */
    public Integer getPuntRetLong() {
        return puntRetLong;
    }

    /**
     * @param puntRetLong  the longest punt return (yds) made by the player
     *                     this season
     */
    public void setPuntRetLong(Integer puntRetLong) {
        this.puntRetLong = puntRetLong;
    }

    /**
     * @return the number of kickoffs returned by the player this season
     */
    public Integer getKickRet() {
        return kickRet;
    }

    /**
     * @param kickRet  the number of kickoffs returned by the player this season
     */
    public void setKickRet(Integer kickRet) {
        this.kickRet = kickRet;
    }

    /**
     * @return the number of kickoff return yards gained by the player this season
     */
    public Integer getKickRetYds() {
        return kickRetYds;
    }

    /**
     * @param kickRetYds  the number of kickoff return yards gained by the
     *                    player this season
     */
    public void setKickRetYds(Integer kickRetYds) {
        this.kickRetYds = kickRetYds;
    }

    /**
     * @return  the number of kickoffs returned for touchdowns by the player
     *          this season
     */
    public Integer getKickRetTds() {
        return kickRetTds;
    }

    /**
     * @param kickRetTds  the number of kickoffs returned for touchdowns by
     *                    the player this season
     */
    public void setKickRetTds(Integer kickRetTds) {
        this.kickRetTds = kickRetTds;
    }

    /**
     * @return the longest kick return (yds) made by the player this season
     */
    public Integer getKickRetLong() {
        return kickRetLong;
    }

    /**
     * @param kickRetLong  the longest kick return (yds) made by the player
     *                     this season
     */
    public void setKickRetLong(Integer kickRetLong) {
        this.kickRetLong = kickRetLong;
    }

    /**
     * @return  the total number of touchdowns scored via other means by the
     *          player this season
     */
    public Integer getOtherTds() {
        return otherTds;
    }

    /**
     * @param otherTds  the total number of touchdowns scored via other means by the player
     *                  this season
     */
    public void setOtherTds(Integer otherTds) {
        this.otherTds = otherTds;
    }

    /**
     * @return the total number of touchdowns scored by the player this season
     */
    public Integer getAllTds() {
        return allTds;
    }

    /**
     * @param allTds  the total number of touchdowns scored by the player this season
     */
    public void setAllTds(Integer allTds) {
        this.allTds = allTds;
    }

    /**
     * @return  the total number of two point conversions scored by the player
     *          this season
     */
    public Integer getTwoPointConv() {
        return twoPointConv;
    }

    /**
     * @param twoPointConv  the total number of two point conversions scored
     *                      by the player this season
     */
    public void setTwoPointConv(Integer twoPointConv) {
        this.twoPointConv = twoPointConv;
    }

    /**
     * @return  a Boolean indicating if the player made the probowl this season
     */
    public Boolean getProbowl() {
        return probowl;
    }

    /**
     * @param probowl  a Boolean indicating if the player made the probowl
     *                 this season
     */
    public void setProbowl(Boolean probowl) {
        this.probowl = probowl;
    }

    /**
     * @return  a Boolean indicating if the player was selected as an all-pro
     *          this season
     */
    public Boolean getAllPro() {
        return allPro;
    }

    /**
     * @param allPro  a Boolean indicating if the player was selected as an
     *                all-pro this season
     */
    public void setAllPro(Boolean allPro) {
        this.allPro = allPro;
    }

    /**
     * @return  a metric calculated from the player's statistics this season
     *          which approximates the amount of value the player has produced
     *          this season
     */
    public Integer getAvgValue() {
        return avgValue;
    }

    /**
     * @param avgValue  a metric calculated from the player's statistics this
     *                  season which approximates the amount of value the player
     *                  has produced this season
     */
    public void setAvgValue(Integer avgValue) {
        this.avgValue = avgValue;
    }

    /**
     * Determines if this playerSeason is equal to the argument.
     * <p>
     * The result is <code>true</code> if and only if the argument is a non-null
     * instance of {@link PlayerSeason} that has equivalent values for the
     * playerId, season, and franchiseId attributes.
     *
     * @param obj  The object to compare this PlayerSeason against.
     * @return     <code>true</code> if the argument is equal to this PlayerSeason
     *             object, <code>false</code> otherwise.
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

        return this.playerId.equals(arg.getPlayerId()) &&
               this.franchiseId.equals(arg.getFranchiseId()) &&
               this.season.equals(arg.getSeason()) &&
               this.seasonType.equals(arg.getSeasonType());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this
     * object based on the playerId, season, and franchiseId fields.
     *
     * @return A hash code for this object.
     * @see #equals
     */
    @Override
    public int hashCode() {
        return  Objects.hash(this.playerId, this.franchiseId, this.season,
                this.seasonType);
    }

    /**
     * Generates a <code>String</code> representation of this {@link PlayerSeason}.
     * <p>
     * Given the use of reflection, consider removing or re-implementing for
     * production grade code.
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}