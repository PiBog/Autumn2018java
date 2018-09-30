/*Bohdan Pysarenko © All right reserved.
 *
 */
package ua.pp.sola.totalizer.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * A class represents the result of an one round.
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log4j
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Round {


    private int year;
    private int week;
    private String roundNumber;
    private LocalDate date;
    private List<Hit> hitList;
    private List<Outcome> gameResult;

    public Round(int year, int week, String roundNumber, LocalDate date,
                 List<Hit> hitList, List<Outcome> gameResult) {
        this.year = year;
        this.week = week;
        this.roundNumber = roundNumber;
        this.date = date;
        this.hitList = hitList;
        this.gameResult = gameResult;
    }

    /**
     *  Method returns max prize from round
     *  @return max prize or 0 if nothing was founded
     */
    public int getMaxPrize() {

        Optional<Hit> maxPrizeHit = hitList.stream().max(Comparator.comparing(Hit::getPrize));

        return maxPrizeHit.map(Hit::getPrize).orElse(0);
    }

    /**
     * Method returns string with date of round and max prize
     */
    public String getMaxPrizeInfoAsString(){
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        String prize = new DecimalFormat("#,### UAH", symbols).format(this.getMaxPrize());
        return ("" + this.getDayAsString() + " was won max PRIZE: " + prize);
    }

    /**
     * Method returns date of round as string
     *
     * @return string with date of round
     */

    public String getDayAsString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return date.format(formatter);
    }

/**
 * Method returns string with distribution of result weighted by winner number
 * @return string with the distribution of the 1/2/X results
 */
    public String getDistributionInfo() {
        DecimalFormat formatter = new DecimalFormat("00.00%', '");
        StringBuilder result = new StringBuilder();

        Map<Outcome, Long> resultMap = gameResult.stream()
                .collect(Collectors.groupingBy(Outcome::getMe, Collectors.counting()));

        Stream.of(Outcome.FIRST, Outcome.SECOND, Outcome.DRAW)
                .forEach(x -> result.append(x.getDescribe())
                        .append(formatter.format(((double) Optional
                                .ofNullable(resultMap.get(x))
                                .orElse(0L) / gameResult.size()))));

        return (result.toString());
    }
}
