package com.rosterreview.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * An {@link Entity} class describing a football team. A team represents
 * a professional football franchise in a single year.
 */

@Entity
@Table(name="team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="franchise_id")
    protected String franchiseId;

    @Id
    @Column(name="season")
    protected Integer season;

    @Column(name="league")
    protected String league;

    @Column(name="location")
    protected String location;

    @Column(name="name")
    protected String name;

    @Column(name="abbrev")
    protected String abbrev;

    @Column(name="pfr_id")
    protected String pfrId;

    @Column(name="pfr_abbrev")
    protected String pfrAbbrev;

    Team() {}

    public Team(String franchiseId, Integer season, String league, String location, String name, String abbrev, String pfrId,
            String pfrAbbrev) {
        this.franchiseId = franchiseId;
        this.season = season;
        this.league = league;
        this.location = location;
        this.name = name;
        this.abbrev = abbrev;
        this.pfrId = pfrId;
        this.pfrAbbrev = pfrAbbrev;
    }

    /**
     * @return A unique identifier for the franchise that controls this team.
     */
    public String getFranchiseId() {
        return franchiseId;
    }

    /**
     * @param id  A unique identifier for the franchise that controls this team.
     */
    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    /**
     * @return The season (year) this team was active.
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * @return The season (year) this team was active.
     */
    public void setSeason(Integer season) {
        this.season = season;
    }

    /**
     * @return The professional football league this team is associated with.
     */
    public String getLeague() {
        return league;
    }

    /**
     * @param league  The professional football league this team is associated with.
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * @return This team's host location (ex. Chicago, New England).
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location  This team's host location (ex. Chicago, New England).
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return This team's name (ex. Cowboys, Bears, etc.).
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  This team's name (ex. Cowboys, Bears, etc.).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the abbreviation used to denote this team.
     * <p>
     * Abbreviations are typically three letter intened
     * versions of the host location's name, but be influenced
     * by other sources such as the team's name to prevent use of
     * an abbreviation by multiple franchises.
     *
     * @return The abbreviation used to denote the team.
     */
    public String getAbbrev() {
        return abbrev;
    }

    /**
     * Set the abbreviation used to denote this team.
     * <p>
     * Abbreviations are typically three letter intened
     * versions of the host location's name, but other sources
     * such as the team's name may be used to prevent use of
     * an abbreviation by multiple franchises.
     *
     * @param abbrev  The abbreviation used to denote the team.
     */
    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    /**
     * Gets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for the franchise
     * that controls this team.
     *
     * @return The unique identifier used by PFR for the franchise
     *         that controls this team.
     */
    public String getPfrId() {
        return pfrId;
    }

    /**
     * Sets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for the franchise
     * that controls this team.
     *
     * @param pfrId  A unique identifier used by PFR for the franchise
     *               that controls this team.
     */
    public void setPfrId(String pfrId) {
        this.pfrId = pfrId;
    }

    /**
     * Gets the abbreviation used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for this team.
     *
     * @return The abbreviation used by PFR to denote the team.
     */
    public String getPfrAbbrev() {
        return pfrAbbrev;
    }

    /**
     * Sets the abbreviation used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for this team.
     *
     * @param pfrId  The abbreviation used by PFR to denote the team.
     */
    public void setPfrAbbrev(String pfrAbbrev) {
        this.pfrAbbrev = pfrAbbrev;
    }

    /**
     * Compares this team to the argument. The result is <code>true</code>
     * if and only if the argument is a non-null instance of Team that has
     * equivalent values for the league, franchiseId, and season fields.
     *
     * @param obj  The object to compare this team against.
     * @return <code>true</code> if the argument is equal to this Team object,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team arg = (Team) obj;
        return this.getLeague().equals(arg.getLeague()) &&
                this.getFranchiseId().equals(arg.getFranchiseId()) &&
                this.getSeason().equals(arg.getSeason());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this object
     * based on the league, franchiseId, and season fields.
     *
     * @return A hash code for this object.
     * @see #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getLeague(), getFranchiseId(), getSeason());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}