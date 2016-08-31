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

package ch.fever.insomnia.windows;

import ch.fever.insomnia.Insomnia;
import ch.fever.insomnia.InsomniaMode;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

import static ch.fever.insomnia.windows.InsomniaWindows.Kernel32.*;

/**
 * Insomnia JNA class for Windows32/64 architecture.
 *
 * This class might be directly instantiated even though we recommend the use of auto-detection mechanisms provided
 * by {@link Insomnia#getInstance()} or {@link ch.fever.insomnia.InsomniaProvider}.
 */
public class InsomniaWindows implements Insomnia {
    @Override
    public boolean apply(InsomniaMode insomniaMode, String reason) {
        switch (insomniaMode) {
            case DISABLED:
                return !kernel32.SetThreadExecutionState(ES_CONTINUOUS).equals(Pointer.NULL);

            case SYSTEM_UP:
                return !kernel32.SetThreadExecutionState(ES_CONTINUOUS | ES_SYSTEM_REQUIRED | ES_AWAYMODE_REQUIRED).equals(Pointer.NULL);

            case SYSTEM_AND_DISPLAY_UP:
                return !kernel32.SetThreadExecutionState(ES_CONTINUOUS | ES_SYSTEM_REQUIRED | ES_AWAYMODE_REQUIRED | ES_DISPLAY_REQUIRED).equals(Pointer.NULL);

            default:
                return false;
        }
    }


    interface Kernel32 extends StdCallLibrary {
        Pointer SetThreadExecutionState(int value);

        int ES_AWAYMODE_REQUIRED = 0x00000040;
        int ES_DISPLAY_REQUIRED = 0x00000002;
        int ES_SYSTEM_REQUIRED = 0x00000001;
        int ES_CONTINUOUS = 0x80000000;
        int ES_USER_PRESENT = 0x00000004;
    }

    final private Kernel32 kernel32;


    public InsomniaWindows() {
        super();
        kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
    }


}

