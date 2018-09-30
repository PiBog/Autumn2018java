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

import lombok.extern.log4j.Log4j;
import ua.pp.sola.totalizer.domain.Round;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * A class contains methods for UI
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log4j
public class UIService {

    private final InstancesService data;
    private boolean isActive;
    private BufferedReader reader;

    public UIService(InstancesService data) {
        this.data = data;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        isActive = true;
    }

    public void start() {
//        displayMaxPrize();
//        displayDistribution();
        calculate();
    }

    /**
     * Method show max prize from list
     */
    private void displayMaxPrize() {
        System.out.println(TotalizerService.getMaxPrizeFromRoundList(data.getRoundList()));
    }

    /**
     * Method print list of the distribution of the games results
     */
    private void displayDistribution() {

        data.getRoundList().stream()
                .parallel()
                .map(Round::getDistributionInfo)
                .forEach(System.out::println);
    }


    private void calculate() {
        LocalDate date;
        Round round = null;
        int hit;
        if (isActive) {

            while (round == null) {
                date = inputDateDialogue();
                round = TotalizerService.findRoundByDate(date, data.getRoundList());
                if (round == null) {
                    System.out.println("Round don't exist, retry please! ");
                }
            }
            if (isActive) {
                hit = inputOutcomesDialogue(round);
                if (isActive) {
                    String result = TotalizerService.resultToString(round, hit);
                    try {
                        reader.close();
                    } catch (IOException e) {
                        log.error(e.getStackTrace());
                    }
                    System.out.println("calculating...");
                    System.out.println(result);
                }
            }
        }

    }

    private LocalDate inputDateDialogue() {
        LocalDate lDate = null;
        boolean dateIsValid = false;
        while (!dateIsValid) {
            System.out.println("Please enter valid date (yyyy.mm.dd) or 'q' for exit:");
            String date = readString();
            if (date.equalsIgnoreCase("q")) {
                this.isActive = false;
                dateIsValid = true;
            } else {
                lDate = TotalizerService.validateDate(date);
                if (lDate != null) {
                    dateIsValid = true;
                } else {
                    System.out.println("Date invalid[" + date + "], please input valid date or quit!");
                }
            }
        }
        return lDate;
    }

    private String readString() {
        String date = null;
        try {
            while (date == null) {
                date = this.reader.readLine();
                if (date.isEmpty())emptyMesage();
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }
        return date;
    }

    private int inputOutcomesDialogue(Round round) {
        int hit = -1;
        boolean outcomesIsValid = false;

        while ((!outcomesIsValid)) {
            System.out.println("Enter valid outcomes (1/2/X)(14 numbers) or 'q' for exit:");
            String outcome = readString();
            if (outcome.equalsIgnoreCase("q")) {
                this.isActive = false;
                outcomesIsValid = true;
            } else {
                hit = TotalizerService.compareOutcomes(outcome, round);
                if (hit > -1) {
                    outcomesIsValid = true;
                } else {
                    System.out.println("Outcome invalid[" + outcome + "], please input valid outcome or quit!");
                }
            }
        }
        return hit;
    }

    private void emptyMesage(){
        System.out.println("Please input any data!");
    }

}

