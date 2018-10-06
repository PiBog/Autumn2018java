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
package ua.pp.sola.autumn2018java.bookieapp.domain.bet;

import ua.pp.sola.autumn2018java.bookieapp.util.BetSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of
 *
 * @param
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public class BetContainer {

    private List<Bet> allBets;
    private List<PlacedBet> activeBet;

    public BetContainer() {
        this.activeBet = new ArrayList<>();
        createBets();
    }

    public void showBetList(){
        for (int i =0;i<allBets.size(); i++){
            System.out.println(1+":"+allBets.get(i).toString());
        }
    }

    public void placeBet(int i, int sum){
        activeBet.add(new PlacedBet(allBets.get(i),sum));
    }

    public void createBets(){
        this.allBets = BetSupplier.generateBetList();
    }

    public int betQuantity() {
        return allBets.size();
    }
}
