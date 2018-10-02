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
package ua.pp.sola.autumn2018java.bookieapp.domain.wager;

/**
 * An implementation of currency
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public enum Currency {
    EUR("EUR"), USD("USD"), HUF("HUF");
    private String description;

    Currency(String value) {
        this.description = value;
    }


    public static Currency getEnum(String str) {
        for (Currency demoType : Currency.values()) {
            if (demoType.toString().equals(str)) {
                return demoType;
            }
        }
        return null;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return description;
    }


}
