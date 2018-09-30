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
package ua.pp.sola.totalizer;

import lombok.extern.java.Log;
import ua.pp.sola.totalizer.service.InstancesService;
import ua.pp.sola.totalizer.service.UIService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class with Entry point for the application. It uses TotalizerService
 * to get results and prints them to the console.
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log
public class TotalizerApp {


    public static void main(String[] args) throws Exception{

        String argss = "d:\\toto.csv";
        File file = new File(argss);
        List<String> fileAsList =  Files.readAllLines(Paths.get(file.getPath()));

        UIService uiService = new UIService(new InstancesService(fileAsList));
        uiService.start();



    }


}
