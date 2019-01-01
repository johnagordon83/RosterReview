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

/**
 * An {@link Entity} class defining a football position played by a specific {@link Player}.
 */

@Entity
@Table(name="player_position")
public class PlayerPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @Column(name="player_id")
    protected String playerId;

    @Id
    @Column(name="position")
    @Enumerated(EnumType.STRING)
    protected Position position;

    PlayerPosition() {}

    public PlayerPosition(String playerId, Position position) {
        this.playerId = playerId;
        this.position = position;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Compares this playerPosition to the argument. The result is
     * <code>true</code> if and only if the argument is a non-null instance
     * of PlayerPosition that has equivalent values for the playerId and
     * position fields.
     *
     * @param obj  The object to compare this team against.
     * @return <code>true</code> if the argument is equal to this PlayerPosition
     *         object, <code>false</code> otherwise.
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
        return this.getPlayerId().equals(arg.getPlayerId()) &&
                this.getPosition().equals(arg.getPosition());
    }

    /**
     * Uses {@link Objects#hash(Object...)} to calculate a hash code for this object
     * based on the playerId and position fields.
     *
     * @return A hash code for this object.
     * @see #equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getPosition());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, new RecursiveToStringStyle());
    }
}