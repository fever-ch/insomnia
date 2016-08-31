/*
 * Copyright (C) 2015 Raphael P. Barazzutti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.fever.insomnia;

import ch.fever.insomnia.osx.InsomniaOSX;
import ch.fever.insomnia.windows.InsomniaWindows;

class InsomniaHelper {
    private static class StaticHolder {
        static final Insomnia INSTANCE = InsomniaHelper.prepareInsomnia();
    }


    /**
     * No need of constructor
     */
    private InsomniaHelper() {
    }


    static Insomnia getNonNullInstance() throws UnsupportedArchitectureException {
        if (StaticHolder.INSTANCE != null)
            return StaticHolder.INSTANCE;
        else
            throw new UnsupportedArchitectureException();
    }

    static Insomnia getInstance() {
        return StaticHolder.INSTANCE;
    }


    static Insomnia prepareInsomnia() {
        String os = System.getProperty("os.name").toLowerCase();

        if ((os.contains("win")))
            return new InsomniaWindows();
        else if ((os.contains("mac")))
            return new InsomniaOSX();
        else
            return null;
    }


}
