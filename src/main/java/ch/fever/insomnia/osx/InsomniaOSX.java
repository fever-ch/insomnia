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

package ch.fever.insomnia.osx;

import ch.fever.insomnia.Insomnia;
import ch.fever.insomnia.InsomniaMode;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;

import static ch.fever.insomnia.osx.InsomniaOSX.OSXLib.CFStringRef;

/**
 * Insomnia JNA class for OSX/Darwin architecture.
 *
 * This class might be directly instantiated even though we recommend the use of auto-detection mechanisms provided
 * by {@link Insomnia#getInstance()} or {@link ch.fever.insomnia.InsomniaProvider}.
 */
public class InsomniaOSX implements Insomnia {


    @Override
    public boolean apply(InsomniaMode insomniaMode, String reason) {
        switch (insomniaMode) {
            case DISABLED:
                return OSXLib.INSTANCE.IOPMAssertionRelease(assertionID.getValue()) == kIOReturnSuccess;

            case SYSTEM_UP:
                return OSXLib.INSTANCE.IOPMAssertionCreateWithName(preventUserIdleSystemSleep, kIOPMAssertionLevelOn, CFStringRef.toCFString(reason), assertionID) == kIOReturnSuccess;

            case SYSTEM_AND_DISPLAY_UP:
                return OSXLib.INSTANCE.IOPMAssertionCreateWithName(preventUserIdleDisplaySleep, kIOPMAssertionLevelOn, CFStringRef.toCFString(reason), assertionID) == kIOReturnSuccess;

            default:
                return false;
        }
    }

    interface OSXLib extends Library {
        int IOPMAssertionCreateWithName(CFStringRef kIOPMAssertionTypeNoDisplaySleep, Object kIOPMAssertionLevelOn,
                                        CFStringRef reasonForActivity, ShortByReference assertionID);

        int IOPMAssertionRelease(short assertionID);

        CFStringRef CFStringCreateWithCharacters(
                Void alloc, //  always pass NULL
                char[] chars,
                CFIndex numChars
        );


        OSXLib INSTANCE = (OSXLib) Native.loadLibrary(OSXLib.class);


        class CFIndex extends NativeLong {

            static CFIndex valueOf(int i) {
                CFIndex idx = new CFIndex();
                idx.setValue(i);
                return idx;
            }
        }


        class CFStringRef extends PointerByReference {

            static CFStringRef toCFString(String s) {
                final char[] chars = s.toCharArray();
                return INSTANCE.CFStringCreateWithCharacters(null, chars, CFIndex.valueOf(chars.length));
            }

        }

    }


    short kIOReturnSuccess = 0;
    short kIOPMAssertionLevelOn = 255;
    short kIOPMAssertionLevelOff = 0;

    private final CFStringRef preventUserIdleSystemSleep = CFStringRef.toCFString("PreventUserIdleSystemSleep");
    private final CFStringRef preventUserIdleDisplaySleep = CFStringRef.toCFString("PreventUserIdleDisplaySleep");

    public InsomniaOSX() {
        super();
    }

    final private ShortByReference assertionID = new ShortByReference();
}

