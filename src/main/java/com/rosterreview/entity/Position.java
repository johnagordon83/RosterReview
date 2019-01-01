package com.rosterreview.entity;

/**
 *  An enum defining recognized football positions played by {@link Player Player(s)}.
 */

public enum Position {

    /**
     * Quarterback
     */
    QB,

    /**
     * Running Back
     * <p>
     * Used to generically classify players that have played multiple runningback
     * positions including halfback, tailback, fullback, wingback, etc.
     */
    RB,

    /**
     * Halfback
     */
    HB,

    /**
     * Fullback
     */
    FB,

    /** Wide Receiver */
    WR,

    /**
     * Tight End
     */
    TE,

    /**
     * Offensive Line
     * <p>
     * Used to generically classify players that have played multiple
     * offensive line positions such as offensive tackle, guard, or center.
     */
    OL,

    /**
     * Offensive Tackle
     */
    T,

    /**
     * Offensive Guard
     */
    G,

    /**
     * Center
     */
    C,

    /**
     * Defensive Line
     * <p>
     * Used to generically classify players that have played multiple
     * defensive line positions such as defensive tackle, nose tackle,
     * or defensive end.
     */
    DL,

    /**
     * Defensive End
     */
    DE,

    /**
     * Defensive Tackle
     */
    DT,

    /**
     * Nose Tackle
     */
    NT,

    /**
     * Linebacker
     * <p>
     * Used to generically classify players that have played multiple
     * linebacker positions such as outside, inside, or middle linebacker.
     */
    LB,

    /**
     * Outside Linebacker
     */
    OLB,

    /**
     * Inside Linebacker
     * <p>
     * Used to generically classify players that have played either
     * inside or middle linebacker.
     */
    ILB,

    /**
     * Defensive Back
     * <p>
     * Used to generically classify players that have played multiple
     * secondary positions such as corner, free safety or strong safety.
     */
    DB,

    /**
     * Cornerback
     */
    CB,

    /**
     * Safety
     * <p>
     * Used to generically classify players that have played both
     * free safety and strong safety.
     */
    S,

    /**
     * Free Safety
     */
    FS,

    /**
     * Strong Safety
     */
    SS,

    /**
     * Kicker
     */
    K,

    /**
     * Punter
     */
    P,

    /**
     * Long Snapper
     */
    LS
}