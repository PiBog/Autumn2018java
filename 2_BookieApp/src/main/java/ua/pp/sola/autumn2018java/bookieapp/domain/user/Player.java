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
package ua.pp.sola.autumn2018java.bookieapp.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.pp.sola.autumn2018java.bookieapp.domain.wager.Currency;

import java.time.LocalDateTime;

/**
 * A player POJO
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@Builder()
public class Player  {
    private String username;
    private int accountNumber;
    private int balance;
    private Currency currency;
    private String dateOfBirth;

    public Player(){}

    public Player(String username, int accountNumber, int balance, Currency currency, String dateOfBirth) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.dateOfBirth = dateOfBirth;
    }

    public int increaseBalnce(int odd){
        this.balance-=odd;
        return  this.balance;
    }
}
