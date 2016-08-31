[![build status](https://secure.travis-ci.org/rbarazzutti/insomnia.svg?branch=master)](http://travis-ci.org/rbarazzutti/insomnia)

# insomnia
Insomnia is a simple cross-platform toolkit that allows the programmer to prevent the operating system to enter in sleep mode.

Usage example:

```java
import ch.fever.insomnia.Insomnia;
import ch.fever.insomnia.InsomniaMode;

import java.util.Optional;

public class MyMediaApp {
    public void main(String args[]) {
        Optional<Insomnia> insomnia = Insomnia.getOptInstance();

        insomnia.ifPresent(i -> i.apply(InsomniaMode.SYSTEM_AND_DISPLAY_UP));

        // display a nice animation

        insomnia.ifPresent(i -> i.apply(InsomniaMode.DISABLED));

        System.exit(0);
    }
}

```

---

Copyright (C) 2016 Raphaël P. Barazzutti

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
