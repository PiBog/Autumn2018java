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

import lombok.Getter;
import ua.pp.sola.totalizer.domain.Hit;
import ua.pp.sola.totalizer.domain.Outcome;
import ua.pp.sola.totalizer.domain.Round;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class for working with data
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Getter
public class InstancesService {

    private List<Round> roundList;


    /**
     * Construct new instance which keeps parsed from list of strings list of rounds
     */
    public InstancesService(List<String> list) {
        this.roundList = list.stream().map(this::parseToRound)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
    }

    /**
     * Creates instance of Round from string source
     *
     * @param line string with data
     * @return instance of Round
     */
    public Round parseToRound(String line) {

        List<String> splitLine = Stream.of(line.split(";")).map(String::new)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        int tmpYear = Integer.parseInt(splitLine.get(0));
        int tmpWeek = Integer.parseInt(splitLine.get(1));
        String tmpRound = splitLine.get(2);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        LocalDate tmpDate = (splitLine.get(3).isEmpty()) ? null : LocalDate.parse(splitLine.get(3), pattern);

        List<Hit> tmpHitList = new ArrayList<>();
        for (int i = 4, j = 14; i < 14; i += 2, j--) {
            tmpHitList.add(this.parseToHit(j, splitLine.get(i), splitLine.get(i + 1)));
        }

        List<Outcome> tmpGameResult = new ArrayList<>();
        for (int i = 14; i < 28; i++) {
            tmpGameResult.add(Outcome.getOutcome(splitLine.get(i)));
        }

        return new Round(tmpYear, tmpWeek, tmpRound, tmpDate, tmpHitList, tmpGameResult);
    }

    /**
     * Creates instance of Hit from string sources
     *
     * @param hitNumber  number of coincidence
     * @param gameNumber number of winners
     * @param prize      sum of won prize
     * @return instance of Round
     */
    public Hit parseToHit(int hitNumber, String gameNumber, String prize) {
        int gameNumb = Integer.parseInt(gameNumber);
        int prizeNumb = Integer.parseInt(prize.replaceAll("[^\\d.]+", ""));
        return new Hit(hitNumber, gameNumb, prizeNumb);
    }
}
