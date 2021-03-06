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

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An {@link Entity} class describing the selection of a {@link Player}
 * at a professional football draft event.
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="draft_pick")
public class DraftPick implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="player_id")
    protected String playerId;

    @Id
    @Column(name="league")
    protected String league;

    @Column(name="franchise_id")
    protected String franchiseId;

    @Column(name="draft_year")
    protected Integer year;

    @OneToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumns({
        @JoinColumn(name="franchise_id", referencedColumnName="franchise_id", insertable=false, updatable=false),
        @JoinColumn(name="draft_year", referencedColumnName="season", insertable=false, updatable=false)
        })
    protected Team team;

    @Column(name="round")
    protected Integer round;

    @Column(name="slot")
    protected Integer slot;

    @Column(name="supplemental")
    protected Boolean supplemental;

    DraftPick() {}

    public DraftPick(String playerId, String league, String franchiseId, Integer year, Integer round, Integer slot, Boolean supplemental) {
        this.playerId = playerId;
        this.league = league;
        this.franchiseId = franchiseId;
        this.year = year;
        this.round = round;
        this.slot = slot;
        this.supplemental = supplemental;
    }

    /**
     * @return The unique identifier of the drafted player.
     */
    @JsonIgnore
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId  The unique identifier of the drafted player.
     */
    @JsonProperty
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return The id of the team that drafted the player.
     */
    public String getFranchiseId() {
        return franchiseId;
    }

    /**
     * @param franchiseId  The id of the team that drafted the player.
     */
    public void setFanchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    /**
     * @return The year the player was drafted.
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param setYear  The year the player was drafted.
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return The league that operated the draft event.
     */
    public String getLeague() {
        return league;
    }

    /**
     * @param league  The league that operated this draft event.
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * @return The team that drafted the player.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team  The team that drafted the player.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * @return  The round in which the player was drafted.
     */
    public Integer getRound() {
        return round;
    }

    /**
     * @param round  The round in which the player was drafted.
     */
    public void setRound(Integer round) {
        this.round = round;
    }

    /**
     * @return  The draft slot at which the player was selected.
     */
    public Integer getSlot() {
        return slot;
    }

    /**
     * @param slot  The draft slot at which the player was selected.
     */
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    /**
     * @return <code>true</code> if the player was drafted in a supplemental draft,
     *         <code>false</code> otherwise.
     */
    public Boolean isSupplemental() {
        return supplemental;
    }

    /**
     * @param supplemental  A boolean indicating if the player was drafted in a
     *                      supplemental draft.
     */
    public void setSupplemental(Boolean supplemental) {
        this.supplemental = supplemental;
    }

    /**
     * Compares this draft pick to the argument. The result is <code>true</code>
     * if and only if the argument is a non-null instance of DraftPick that has equivalent
     * values for the playerId, franchiseId, and year attributes.
     *
     * @param obj  The object to compare this team against.
     * @return <code>true</code> if the argument is equal to this DraftPick object,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DraftPick)) {
            return false;
        }
        DraftPick arg = (DraftPick) obj;
        return this.getPlayerId().equals(arg.getPlayerId()) &&
                this.getLeague().equals(arg.getLeague());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this object
     * based on the playerId, franchiseId, and year fields.
     *
     * @return A hash code for this object.
     * @see #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getLeague());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}