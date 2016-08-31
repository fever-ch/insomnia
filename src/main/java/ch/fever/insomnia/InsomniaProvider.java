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

import javax.inject.Provider;

/**
 * A provider for Guice, Spring and other dependency injection frameworks that supports JSR-330
 */
public class InsomniaProvider implements Provider<Insomnia> {
    /**
     * @return an instance of insomnia
     * {@link Provider#get()}
     */
    @Override
    public Insomnia get() {
        return InsomniaHelper.prepareInsomnia();
    }
}
