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

    @Id
    @Column(name="season_type")
    @Enumerated(EnumType.STRING)
    protected SeasonType seasonType;

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

    @Column(name="probowl")
    protected Boolean probowl;

    @Column(name="all_pro")
    protected Boolean allPro;

    @Column(name="avg_value", nullable=false)
    protected Integer avgValue = 0;

    /**
     * An enum defining types of football seasons
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

    public PlayerSeason() {}

    public PlayerSeason(String playerId, String franchiseId, Integer season, SeasonType seasonType, Integer age,
            Position position, Integer jerseyNumber, Integer gamesPlayed, Integer gamesStarted, Integer passComp,
            Integer passAtt, Integer passYds, Integer passTds, Integer passInts, Integer passLong, Double passerRating,
            Integer sacked, Integer sackedYds, Integer rushAtt, Integer rushYds, Integer rushTds, Integer rushLong,
            Integer targets, Integer receptions, Integer recYds, Integer recTds, Integer recLong, Integer fumbles,
            Integer interceptions, Integer intYds, Integer intTds, Integer intLong, Integer passDef, Integer fumForced,
            Integer fumRec, Integer fumRecYds, Integer fumRecTds, Double sacks, Integer tackles, Integer assists,
            Integer safeties, Integer fgaTeens, Integer fgmTeens, Integer fgaTwenties, Integer fgmTwenties,
            Integer fgaThirties, Integer fgmThirties, Integer fgaFourties, Integer fgmFourties, Integer fgaFiftyPlus,
            Integer fgmFiftyPlus, Integer fgaTotal, Integer fgmTotal, Integer fgLong, Integer xpa, Integer xpm,
            Integer punts, Integer puntYds, Integer puntLong, Integer puntBlocked, Integer puntRet, Integer puntRetYds,
            Integer puntRetTds, Integer puntRetLong, Integer kickRet, Integer kickRetYds, Integer kickRetTds,
            Integer kickRetLong, Integer otherTds, Integer allTds, Integer twoPointConv, Boolean probowl, Boolean allPro,
            Integer avgValue) {
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
     * @return The type of season these statistics were generated in.
     */
    public SeasonType getSeasonType() {
        return seasonType;
    }

    /**
     * @param seasonType  The type of season these statistics were generated in.
     */
    public void setSeasonType(SeasonType seasonType) {
        this.seasonType = seasonType;
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
               this.getFranchiseId().equals(arg.getFranchiseId()) &&
               this.getSeason().equals(arg.getSeason()) &&
               this.getSeasonType().equals(arg.getSeasonType());
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
        return Objects.hash(this.getPlayerId(), this.getFranchiseId(), this.getSeason(), this.getSeasonType());
    }
}