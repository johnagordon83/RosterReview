package com.rosterreview.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  An enum defining recognized football positions played by {@link Player Players}.
 */

public enum Position {

    /**
     * Quarterback
     */
    QB("QB"),

    /**
     * Running Back
     * <p>
     * Should be used to generically classify players that have played multiple running back
     * positions including halfback, tailback, fullback, wingback, etc.
     */
    RB("RB",  "WB", "B"),

    /**
     * Halfback
     */
    HB("HB", "TB", "RH", "LH"),

    /**
     * Fullback
     */
    FB("FB", "BB"),

    /**
     * Tight End
     */
    TE("TE"),

    /**
     * Wide Receiver
     */
    WR("WR", "FL", "SE", "E", "RE", "LE"),

    /**
     * Offensive Line
     * <p>
     * Should be used to generically classify players that have played multiple
     * offensive line positions such as offensive tackle, guard, or center.
     */
    OL("OL"),

    /**
     * Offensive Tackle
     */
    T("T", "RT", "LT"),

    /**
     * Offensive Guard
     */
    G("G", "RG", "LG"),

    /**
     * Center
     */
    C("C"),

    /**
     * Defensive Line
     * <p>
     * Should be used to generically classify players that have played multiple
     * defensive line positions such as defensive tackle, nose tackle,
     * or defensive end.
     */
    DL("DL"),

    /**
     * Defensive End
     */
    DE("DE", "RDE", "LDE"),

    /**
     * Defensive Tackle
     */
    DT("DT", "RDT", "LDT", "DG"),

    /**
     * Nose Tackle
     */
    NT("NT", "MG"),

    /**
     * Linebacker
     * <p>
     * Should be used to generically classify players that have played multiple
     * linebacker positions such as outside, inside, or middle linebacker.
     */
    LB("LB", "RLB", "LLB"),

    /**
     * Outside Linebacker
     */
    OLB("OLB", "ROLB", "LOLB"),

    /**
     * Middle Linebacker
     */
    MLB("MLB"),

    /**
     * Inside Linebacker
     * <p>
     * Should be used to generically classify players that have played either
     * inside or middle linebacker.
     */
    ILB("ILB", "LILB", "RILB"),

    /**
     * Defensive Back
     * <p>
     * Should be used to generically classify players that have played multiple
     * secondary positions such as corner, or safety.
     */
    DB("DB"),

    /**
     * Cornerback
     */
    CB("CB", "LCB", "RCB", "DH", "LDH", "RDH"),

    /**
     * Safety
     * <p>
     * Should be used to generically classify players that have played both
     * free safety and strong safety.
     */
    S("S", "LS", "RS"),

    /**
     * Free Safety
     */
    FS("FS"),

    /**
     * Strong Safety
     */
    SS("SS"),

    /**
     * Kick Returner
     */
    KR("KR"),

    /**
     * Punt Returner
     */
    PR("PR"),

    /**
     * Kicker
     */
    K("K"),

    /**
     * Punter
     */
    P("P");

    private static final Map<String, Position> ALIAS_MAP = new HashMap<>();

    private List<String> aliases;

    static {
        for (Position position : Position.values()) {
            for (String alias : position.aliases) {
                ALIAS_MAP.put(alias, position);
            }
        }
    }

    private Position(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Retrieves an instance of {@link Position} that corresponds to the passed alias argument.
     *
     * @param alias                      the <code>String</code> value for which a
     *                                   Position equivalent should be retrieved
     * @return                           the Position equivalent of the alias argument
     * @throws IllegalArgumentException  if no enum matches the alias argument
     */
    public static Position getPositionByAlias(String alias) throws IllegalArgumentException {
        Position position = ALIAS_MAP.get(alias);
        if (position == null) {
            throw new IllegalArgumentException("No defined Position exists with alias '" + alias + "'");
        }
        return position;
    }
}