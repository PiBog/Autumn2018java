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

import ua.pp.sola.totalizer.domain.Outcome;
import ua.pp.sola.totalizer.domain.Round;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class contained helpful methods :)
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public class TotalizerService {

        public String getMaxPrizeFromList(List<Round> roundList) {

        Round maxRound = roundList.stream().parallel()
                .max(Comparator.comparing(Round::getMaxPrize)).get();

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        return ("" + maxRound.getHappyDay() + " was won max PRIZE: " +
                new DecimalFormat("#,### UAH", symbols).format(maxRound.getMaxPrize()));
    }

    public void printDistribution(List<Round> roundList) {

        roundList.stream().parallel().forEach(Round::showDistribution);


    }

    public void calculateDistribution(String date, String outcomes, List<Round> list) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate resultDate = LocalDate.parse(date, formatter);

        Round round = list.stream().parallel()
                .filter(x -> x.getDate().equals(resultDate))
                .findFirst().get();

        char[] chars = outcomes.toCharArray();
        List<Outcome> pattern = round.getGameResult();
        int hits = 0;

        for (int i = 0; i < pattern.size(); i++) {
            Outcome current = Outcome.getOutcome(String.valueOf(chars[i]));
            if (current.equals(pattern.get(i))) {
                hits++;
            }


        }

        final int lambda = hits;

        int amount = (hits < 10)
                ? 0
                : round.getHitList()
                .stream().filter(x -> lambda == x.getHits())
                .findFirst().get().getPrize();

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        System.out.println("Result: hits: " + hits + ", amount: "
                + new DecimalFormat("#,### UAH").format(amount));
    }
}

