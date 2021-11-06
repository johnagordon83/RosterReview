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
 * An {@link Entity} describing a a professional football franchise
 * in a single year.
 */

@Entity
@Table(name="team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="franchise_id")
    private String franchiseId;

    @Id
    @Column(name="season")
    private Integer season;

    @Column(name="league")
    private String league;

    @Column(name="location")
    private String location;

    @Column(name="name")
    private String name;

    @Column(name="abbrev")
    private String abbrev;

    @Column(name="pfr_id")
    private String pfrId;

    @Column(name="pfr_abbrev")
    private String pfrAbbrev;

    /**
     * A no-argument {@link Team} constructor required by Spring.
     */
    Team() {}

    public Team(String franchiseId, Integer season, String league,
            String location, String name, String abbrev, String pfrId,
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
     * @return  the unique identifier for the franchise that controls this team
     */
    public String getFranchiseId() {
        return franchiseId;
    }

    /**
     * @param franchiseId  the unique identifier for the franchise that
     *                     controls this team
     */
    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    /**
     * @return  the season (year) this team was active
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * @param season  the season (year) this team was active
     */
    public void setSeason(Integer season) {
        this.season = season;
    }

    /**
     * @return  the professional football league this team is associated with
     */
    public String getLeague() {
        return league;
    }

    /**
     * @param league  the professional football league this team is associated with
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * @return  this team's host location (ex. Chicago, New England)
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location  this team's host location (ex. Chicago, New England)
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return  this team's name (ex. Cowboys, Bears, etc.)
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  this team's name (ex. Cowboys, Bears, etc.)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the abbreviation used to denote this team.
     * <p>
     * Abbreviations are typically three letter versions of the host location's
     * name, but may be influenced by other sources such as the team's name to
     * prevent use of an abbreviation by multiple franchises.
     *
     * @return the abbreviation used to denote the team
     */
    public String getAbbrev() {
        return abbrev;
    }

    /**
     * Set the abbreviation used to denote this team.
     * <p>
     * Abbreviations are typically three letter versions of the host location's
     * name, but other sources such as the team's name may be used to prevent
     * reuse of an abbreviation by multiple franchises.
     *
     * @param abbrev  the abbreviation used to denote the team
     */
    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    /**
     * Gets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">www.pro-football-reference.com</a>
     * for the franchise that controls this team.
     *
     * @return  the unique identifier used by PFR for the franchise that controls
     *          this team
     */
    public String getPfrId() {
        return pfrId;
    }

    /**
     * Sets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">www.pro-football-reference.com</a>
     * for the franchise that controls this team
     *
     * @param pfrId  the unique identifier used by PFR for the franchise that
     *               controls this team
     */
    public void setPfrId(String pfrId) {
        this.pfrId = pfrId;
    }

    /**
     * Gets the abbreviation used by
     * <a href="http://www.pro-football-reference.com">www.pro-football-reference.com</a>
     * for this team.
     *
     * @return  the abbreviation used by PFR to denote the team
     */
    public String getPfrAbbrev() {
        return pfrAbbrev;
    }

    /**
     * Sets the abbreviation used by
     * <a href="http://www.pro-football-reference.com">www.pro-football-reference.com</a>
     * for this team.
     *
     * @param pfrAbbrev  the abbreviation used by PFR to denote the team
     */
    public void setPfrAbbrev(String pfrAbbrev) {
        this.pfrAbbrev = pfrAbbrev;
    }

    /**
     * Determines if this {@link Team} is equal to the argument. The result
     * is <code>true</code> if and only if the argument is a non-null instance
     * of Team that has equivalent values for the league, franchiseId, and
     * season fields.
     *
     * @param obj  the object to compare this team against.
     * @return     <code>true</code> if the argument is equal to this Team object,
     *             <code>false</code> otherwise.
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

        return this.league.equals(arg.getLeague()) &&
                this.franchiseId.equals(arg.getFranchiseId()) &&
                this.season.equals(arg.getSeason());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this
     * object based on the league, franchiseId, and season fields.
     *
     * @return  a hash code for this object
     * @see     #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.league, this.franchiseId, this.season);
    }

    /**
     * Generates a <code>String</code> representation of this {@link Team}.
     * <p>
     * Given the use of reflection, consider removing or re-implementing for
     * production grade code.
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}