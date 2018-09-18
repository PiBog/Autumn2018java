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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * A class represents the result of a roundNumber.
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

    private Round(int year, int week, String roundNumber, LocalDate date,
                  List<Hit> hitList, List<Outcome> gameResult) {
        this.year = year;
        this.week = week;
        this.roundNumber = roundNumber;
        this.date = date;
        this.hitList = hitList;
        this.gameResult = gameResult;
    }

    public static Round instanceOf(String line) {

        List<String> splitLine = Stream.of(line.split(";")).map(String::new)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        int tmpYear = Integer.parseInt(splitLine.get(0));
        int tmpWeek = Integer.parseInt(splitLine.get(1));
        String tmpRound = splitLine.get(2);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        LocalDate tmpDate = (splitLine.get(3).isEmpty()) ? null : LocalDate.parse(splitLine.get(3), pattern);

        List<Hit> tmpHitList = new ArrayList<>();
        for (int i = 4, j = 14; i < 14; i += 2, j--) {
            tmpHitList.add(Hit.instanceOf(j, splitLine.get(i), splitLine.get(i + 1)));
        }

        List<Outcome> tmpGameResult = new ArrayList<>();
        for (int i = 14; i < 28; i++) {
            tmpGameResult.add(Outcome.getOutcome(splitLine.get(i)));
        }

        return new Round(tmpYear, tmpWeek, tmpRound, tmpDate, tmpHitList, tmpGameResult);
    }

    public int getMaxPrize() {

        Optional<Hit> maxPrizeHit =  hitList.stream().max(Comparator.comparing(Hit::getPrize));
        maxPrizeHit.ifPresent(hit -> {});//.getPrize();

        return 0;
    }

    public String getHappyDay() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return date.format(formatter);
    }


    public void showDistribution() {
        DecimalFormat formatter = new DecimalFormat("00.00%', '");
        StringBuilder result = new StringBuilder();

        Map<Outcome, Long> resultMap = gameResult.stream()
                .collect(Collectors.groupingBy(Outcome::getMe, Collectors.counting()));

        Arrays.asList(Outcome.FIRST, Outcome.SECOND, Outcome.DRAW).stream()
                .forEach(x -> result
                        .append(x.getDescribe()
                                + formatter.format(
                                ((double) Optional
                                        .ofNullable(resultMap.get(x))
                                        .orElse(0l) / gameResult.size()))));

        System.out.println(result.toString());
    }
}
