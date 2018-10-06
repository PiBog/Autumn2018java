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
package ua.pp.sola.autumn2018java.bookieapp.domain.sportevent;

import lombok.Getter;
import lombok.Setter;
import ua.pp.sola.autumn2018java.bookieapp.util.DateUtil;

/**
 * A tennis game
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class TennisSportEvent extends SportEvent {
    public TennisSportEvent(String name, String start, String end) {
        this.eventName = name;
        this.startDate = DateUtil.dateParser(start);
        this.endDate = DateUtil.dateParser(end);
    }
}
