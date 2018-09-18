/*Bohdan Pysarenko © All right reserved.
 *
 */
package ua.pp.sola.totalizer.domain;

import lombok.Getter;
import lombok.Setter;


/**
 * A class represents hits,  number of games and price of
 * a specified hit number in a specified roundNumber.
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class Hit {
    private int hits;
    private int gameNumberHit;
    private int prize;

    private Hit(int hits, int gameNumberHit, int prize){
        this.hits = hits;
        this.gameNumberHit = gameNumberHit;
        this.prize = prize;
    }

    public static Hit instanceOf(int hitNumber, String gameNumber, String prize){
        int gameNumb = Integer.parseInt(gameNumber);
        int prizeNumb = Integer.parseInt(prize.replaceAll("[^\\d.]+",""));
    return new Hit(hitNumber,gameNumb,prizeNumb);
    }
}
