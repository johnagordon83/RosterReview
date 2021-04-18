package com.rosterreview.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An {@link Entity} defining a football position played by a specific {@link Player}.
 */

@Entity
@Table(name="player_position")
public class PlayerPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="player_id")
    protected String playerId;

    @Id
    @Column(name="position")
    @Enumerated(EnumType.STRING)
    protected Position position;

    /**
     * A no-argument {@link PlayerPosition} constructor required by Spring.
     */
    PlayerPosition() {}

    public PlayerPosition(String playerId, Position position) {
        this.playerId = playerId;
        this.position = position;
    }

    /**
     * @return the player's unique identifier
     */
    @JsonIgnore
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId  the player's unique identifier
     */
    @JsonProperty
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return the Position played by the player
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position  the Position played by the player
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Compares this {@link PlayerPosition} to the argument. The result is
     * <code>true</code> if and only if the argument is a non-null instance
     * of PlayerPosition that has equivalent values for the playerId and
     * position fields.
     *
     * @param obj  The object to compare this team against.
     * @return     <code>true</code> if the argument is equal to this PlayerPosition
     *             object, <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlayerPosition)) {
            return false;
        }
        PlayerPosition arg = (PlayerPosition) obj;
        return this.playerId.equals(arg.getPlayerId()) &&
               this.position.equals(arg.getPosition());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this object
     * based on the playerId and position fields.
     *
     * @return A hash code for this object.
     * @see    #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.playerId, this.position);
    }

    /**
     * Generates a <code>String</code> representation of this {@link PlayerPosition}.
     * Given the use of reflection, consider removing or re-implementing for
     * production grade code.
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}