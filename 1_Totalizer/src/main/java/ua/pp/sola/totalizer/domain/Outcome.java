/*Bohdan Pysarenko © All right reserved.
 *
 */
package ua.pp.sola.totalizer.domain;

import lombok.Getter;

/**
 * An enum of possible outcomes
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Getter
public enum Outcome {
    FIRST("1", "team #1 won: "), SECOND("1", "team #2 won: "), DRAW("X", "draw: ");

    String describe;
    String mark;

    Outcome(String mark, String describe) {
        this.describe = describe;
    }

    public static Outcome getOutcome(String string) {
        switch (string) {
            case "1":
            case "+1":
                return FIRST;
            case "2":
            case "+2":
                return SECOND;
            case "+x":
            case "x":
            case "+X":
            case "X":
                return DRAW;
            default:
                return null;
        }
    }

    public Outcome getMe() {
        return this;
    }
}
