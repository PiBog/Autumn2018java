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
package ua.pp.sola.autumn2018java.bookieapp.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import ua.pp.sola.autumn2018java.bookieapp.dao.Storage;
import ua.pp.sola.autumn2018java.bookieapp.dao.UserDAO;
import ua.pp.sola.autumn2018java.bookieapp.domain.bet.BetContainer;
import ua.pp.sola.autumn2018java.bookieapp.domain.bet.PlacedBet;
import ua.pp.sola.autumn2018java.bookieapp.domain.user.Player;
import ua.pp.sola.autumn2018java.bookieapp.domain.wager.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * An implementation of user interface
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log4j
@Getter
@Setter
public class UIService {

    private UserDAO storage = new Storage();
    private BufferedReader reader;
    private boolean isActive;
    private Player activePlayer;
    private BetContainer betStorage;

    public UIService() {

        reader = new BufferedReader(new InputStreamReader(System.in));
        this.betStorage = new BetContainer();

    }

    public void run() {
        this.isActive = true;
        auth();
        repl();
        showResult();
    }

    private String readOneLine() {
        String line = "";
        while (line.isEmpty()) {
            try {
                line = this.reader.readLine();
                if (line.isEmpty() && line == null) line = "";
                else return line;
            } catch (IOException e) {
                line = "";
            }
            System.out.println("Empty line! Please enter valid text.");
        }
        return line;
    }

    private void auth() {
        System.out.println("Hi, what is your name?");
        String name = readOneLine();
        System.out.println("What is your account number?");
        int account = Integer.parseInt(readOneLine());
        System.out.println("How much money do you have (more than 0)?");
        int balance = Integer.parseInt(readOneLine());
        System.out.println("What is your currency? (UAH, EUR or USD)");
        Currency currency = Currency.getEnum(readOneLine());
        System.out.println("When were you born? eg.:1990-02-03");
        String dateOfBirth = readOneLine();
        activePlayer = Player.builder()
                .username(name)
                .accountNumber(account)
                .balance(balance)
                .currency(currency)
                .dateOfBirth(dateOfBirth)
                .build();
        System.out.println("Welcome " + activePlayer.getUsername() + "!");
        System.out.println("Your balance is:" +
                activePlayer.getBalance() + " " +
                activePlayer.getCurrency());

    }

    private void repl() {
        System.out.println("Please choose an outcome to bet on! (choose a number or press q for quit)");
        while (isActive) {
            int betNumber = betSelecting();
            if (betNumber == -1) {
                isActive = false;
            } else {
                int sum = betOn();
                if (sum == -1) {
                    isActive = false;
                } else {
                    betStorage.placeBet(betNumber, sum);
                    System.out.println("What are you want to bet on? (choose a number or press q for quit)");

                }

            }
        }
    }

    private int betSelecting() {
        betStorage.showBetList();
        int bet = 0;
        while (bet == 0) {
            String line = readOneLine();
            if (line.equals("q")) {
                return -1;
            } else {
                try {
                    bet = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    bet = 0;
                    System.out.println("Invalid line");
                }
                if (bet < 0 || bet >= betStorage.betQuantity()) {
                    bet = 0;
                }

            }
        }

        return bet;
    }

    private int betOn() {
        System.out.println("How much do you want to bet on it? (q for quit)");
        int sum = 0;
        int balance = activePlayer.getBalance();
        while (sum == 0) {
            String lineSum = readOneLine();
            if (lineSum.equals("q")) {
                return -1;
            } else {
                try {
                    sum = Integer.parseInt(lineSum);
                } catch (NumberFormatException e) {
                    sum = 0;
                    System.out.println("Invalid line");
                }

                if (sum>balance) {
                    sum = 0;
                    System.out.println("You don't have enough money, your balance is " + balance);
                }

            }

        }
        balance = activePlayer.increaseBalnce(sum);
        System.out.println("Your new balance is "+balance);
        return sum;
    }

    private void showResult(){
        System.out.println("Results:");

        System.out.println("Results:");
    }

}

