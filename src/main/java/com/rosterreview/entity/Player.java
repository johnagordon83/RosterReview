package com.rosterreview.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * An {@link Entity} defining a professional football player.
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="player")
public class Player {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="pfr_id")
    private String pfrId;

    @Column(name="nickname")
    private String nickname;

    @Column(name="first_name")
    private String firstName;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="suffix")
    private String suffix;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="player_id", referencedColumnName = "id", nullable = false,
                insertable=false, updatable=false)
    private Set<PlayerPosition> positions;

    @Column(name="height")
    private Integer height;

    @Column(name="weight")
    private Integer weight;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="college")
    private String college;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="player_id", referencedColumnName = "id", nullable = false,
                insertable=false, updatable=false)
    private Set<DraftPick> draftPicks;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="player_id", referencedColumnName = "id", nullable = false,
                insertable=false, updatable=false)
    private List<PlayerSeason> statistics;

    @Column(name="hof_year")
    private Integer hofYear;

    /**
     * A no-argument {@link Player} constructor required by Spring.
     */
    public Player() {}

    public Player(String id) {
        this.id = id;
        this.pfrId = null;
        this.nickname = null;
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
        this.suffix = null;
        this.positions = new HashSet<>();
        this.height = null;
        this.weight = null;
        this.birthDate = null;
        this.college = null;
        this.draftPicks = new HashSet<>();
        this.statistics = new ArrayList<>();
        this.hofYear = null;
    }

    public Player(String id, String pfrId, String nickname, String firstName,
            String middleName, String lastName, String suffix,
            Set<PlayerPosition> positions, Integer height, Integer weight,
            LocalDate birthDate, String college, Set<DraftPick> draftPicks,
            List<PlayerSeason> statistics, Integer hofYear) {

        this.id = id;
        this.pfrId = pfrId;
        this.nickname = nickname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.positions = positions;
        this.height = height;
        this.weight = weight;
        this.birthDate = birthDate;
        this.college = college;
        this.draftPicks = draftPicks;
        this.statistics = statistics;
        this.hofYear = hofYear;
    }

    /**
     * @return  a unique identifier for this player
     */
    public String getId() {
        return id;
    }

    /**
     * @param id  a unique identifier for this player
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for this player.
     *
     * @return  the unique identifier used by PFR for this player
     */
    public String getPfrId() {
        return pfrId;
    }

    /**
     * Sets the unique identifier used by
     * <a href="http://www.pro-football-reference.com">
     * www.pro-football-reference.com</a> for this player.
     *
     * @param pfrId  a unique identifier used by PFR for this player
     */
    public void setPfrId(String pfrId) {
        this.pfrId = pfrId;
    }

    /**
     * @return  the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname  the player's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return  the player's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName  the player's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return  the player's middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName  the player's middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return  the player's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName  the player's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return  the player's last name suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix  the player's last name suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return  the positions the player is most known for playing
     */
    public Set<PlayerPosition> getPositions() {
        return positions;
    }

    /**
     * @param positions  the {@link Position Positions} the player is most known
     *                   for playing
     */
    public void setPositions(Set<PlayerPosition> positions) {
        this.positions = positions;
    }

    /**
     * @return the player's height (inches)
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height  the player's height (inches)
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return  the player's weight (lbs)
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * @param weight  the player's weight (lbs)
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * @return  the player's birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate  the player's birth date
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return  the player's primary college
     */
    public String getCollege() {
        return college;
    }

    /**
     * @param college  the player's primary college
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * @return  a set of {@link DraftPick DraftPicks} that describe the
     *          player's draft history
     */
    public Set<DraftPick> getDraftPicks() {
        return draftPicks;
    }

    /**
     * @param draftPicks  a set of {@link DraftPick DraftPicks} that describe
     *                    the player's draft history
     */
    public void setDraftPicks(Set<DraftPick> draftPicks) {
        this.draftPicks = draftPicks;
    }

    /**
     * @return  a list of {@link PlayerSeason PlayerSeasons} that contain the
     *          player's statistics for each year of their career
     */
    public List<PlayerSeason> getStatistics() {
        return statistics;
    }

    /**
     * @param statistics  a list of {@link PlayerSeason PlayerSeasons} that
     *                    contain the player's statistics for each year of
     *                    their career
     */
    public void setStatistics(List<PlayerSeason> statistics) {
        this.statistics = statistics;
    }

    /**
     * @return  the year the player was inducted into the Pro Football Hall of Fame
     */
    public Integer getHofYear() {
        return hofYear;
    }

    /**
     * @param hofYear  the year the player was inducted into the Pro Football
     *                 Hall of Fame
     */
    public void setHofYear(Integer hofYear) {
        this.hofYear = hofYear;
    }

    /**
     * Generates a <code>String</code> representation of this {@link Player}.
     * <p>
     * Given the use of reflection, consider removing or re-implementing for
     * production grade code.
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}
