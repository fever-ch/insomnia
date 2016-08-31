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

import java.util.Optional;

/**
 * Insomnia class
 *
 * Subclasses of this one are targeting various operating systems (up to now, Microsoft Windows and Apple OSX). Obviously
 * these methods might behave in a weird manner if accessed in a concurrent way.
 *
 * Insomnia subclasses give no guarantee in term of synchronization, it's up to the developer to handle this. We expect that
 * in normal usage of Insomnia, the application does not face such issues. This framework does not address concurrency,
 * if your software might access under some circumstances concurrently Insomnia, please use the appropriate synchronization
 * mechanism to avoid any issue.
 */
public interface Insomnia {
    /**
     * Change the standby behavior of the machine
     *
     * @param insomniaMode mode to be requested
     * @return true if the request has been properly processed by the operating system
     */
    default boolean apply(InsomniaMode insomniaMode) {
        return apply(insomniaMode, "No reason given");
    }

    /**
     * Change the standby behavior of the machine
     *
     * @param insomniaMode mode to be requested
     * @param reason       reason invoked
     * @return true if the request has been properly processed by the operating system
     */
    boolean apply(InsomniaMode insomniaMode, String reason);

    /**
     * @return a singleton of the insomnia
     * @throws UnsupportedArchitectureException if no support is available on the current architecture
     */
    static Insomnia getInstance() throws UnsupportedArchitectureException {
        return InsomniaHelper.getNonNullInstance();
    }

    /**
     * @return a optional containing the singleton of the insomnia
     */
    static Optional<Insomnia> getOptInstance() {
        return Optional.ofNullable(InsomniaHelper.getInstance());
    }
}
