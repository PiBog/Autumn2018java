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
package ua.pp.sola.totalizer.service;

import ua.pp.sola.totalizer.domain.Hit;
import ua.pp.sola.totalizer.domain.Outcome;
import ua.pp.sola.totalizer.domain.Round;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Class contained helpful methods :)
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public class TotalizerService {


    /**
     * Method print info about max prize in list of round
     */
    public static String getMaxPrizeFromRoundList(List<Round> roundList) {

        Optional<Round> searchResult = roundList.stream().parallel()
                .max(Comparator.comparing(Round::getMaxPrize));

        return searchResult
                .map(Round::getMaxPrizeInfoAsString)
                .orElse("Something went wrong!!!");
    }

    /**
     * Validate date and return as LocalDate
     */
    public static LocalDate validateDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate date;

        try {
            date = LocalDate.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
        return date;
    }

    /**
     * Method find and return round from list by date
     *
     * @return round
     */
    public static Round findRoundByDate(LocalDate date, List<Round> list) {


        return list.stream()
                .filter(x -> x.getDate() != null && x.getDate().equals(date))
                .findFirst().orElse(null);
    }

    /**
     * Method compare user outcomes with sample
     *
     * @param result user outcome
     * @param round  with valid sample
     * @return number of coincidences or -1 if input data invalid
     */
    public static int compareOutcomes(String result, Round round) {
        int hits = 0;
        char[] chars = result.toCharArray();
        if (chars.length != 14) return -1;
        else {
            List<Outcome> pattern = round.getGameResult();
            for (int i = 0; i < pattern.size(); i++) {
                Outcome current = Outcome.getOutcome(String.valueOf(chars[i]));
                if (current == null) {
                    return -1;
                } else if (current.equals(pattern.get(i))) {
                    hits++;
                }
            }
        }

        return hits;
    }

    /**
     * Display on screen result of the user's try
     *
     * @param round round for comparing
     * @param hits  number of the user's valid hits
     */

    public static String resultToString(final Round round, final int hits) {
        int amount = (hits < 10)
                ? 0
                : round.getHitList()
                .stream().filter(x -> hits == x.getHits())
                .findFirst().orElse(new Hit(0, 0, 0)).getPrize();

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        return ("Result: hits: " + hits + ", amount: "
                + new DecimalFormat("#,### UAH").format(amount));
    }


}

