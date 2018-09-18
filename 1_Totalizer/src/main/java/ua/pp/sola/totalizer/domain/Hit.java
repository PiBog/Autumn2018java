/* Copyright © 2018 Bohdan Pysarenko. E-mail: <Pisarenko.B.O@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
