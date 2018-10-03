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
import ua.pp.sola.autumn2018java.bookieapp.domain.user.Player;

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


    public UIService() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        this.isActive = true;
        auth();

    }

    private String readOneLine() {

        while (true) {
            try {
                String line = this.reader.readLine();
                if (line.isEmpty()) return "";
                else return line;
            } catch (IOException e) {
                return "";
            }
        }
    }

    private void auth() {
        Player player;
        while (isActive) {
            System.out.println("Please enter your account number (q for exit)");
            String account = readOneLine();
            if (account.equalsIgnoreCase("q")) isActive=false;
            else {

            }

        }
    }

    private void reg() {

    }


}
