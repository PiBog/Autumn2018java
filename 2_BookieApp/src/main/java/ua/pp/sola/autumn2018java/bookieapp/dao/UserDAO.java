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
package ua.pp.sola.autumn2018java.bookieapp.dao;

import ua.pp.sola.autumn2018java.bookieapp.domain.user.User;

/**
 * DAO interface for User object
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public interface UserDAO {
    /**
     * Get user data from storage
     *
     * @param username user's name
     * @param account number of user's account
     * @return user object if user exist otherwise return -1
     */
    User getUser(String username, int account);

    User addUser(String name, int account, int balance, String curr, String dateOfBorn);

    int getUserBalance(int account);

    void updUserBalance(int account, int newBalance);
}
