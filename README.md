# LiveEvent
Live Event is a quick and easy solution to get a reliable and lifecycleaware EventBus.

Every observer will only be called once and only active observer will receive the event.

## Get LiveEvent
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.ueen:LiveEvent:1.1'
	}
```

## Usage

### Sending
e.g. in your Repository

```kotlin
    val event1 = LiveEvent<Any>()
    val event2 = LiveEvent<Int>()
    
    fun trigger() {
        event1.post()
        event2.post(7)
    }
```
leave ```post``` empty if you just want the event or put a value inside (has to match the ```LiveEvent<identifier>```)

use ```event.post()``` LiveData handles the Threading

### Forwarding (optional)
e.g. in your ViewModel

```kotlin
    val event1beacon = LiveEvent<Any>().forward(repository.getEvent1)
    val event2beacon = LiveEvent<Int>().forward(repository.getEvent2)
```

(optional) if you want to piggyback on the Event

```kotlin
    val event2beacon = LiveEvent<Int>().forward(repository.getEvent2, Observer { it: Int ->
        //do smth with it: Int
    })
```

### Recieving
e.g. in your Activity

```kotlin
        viewModel.getEvent1beacon.observe(this, onEvent = {it: Any ->

        })
        viewModel.getEvent2beacon.observe(this, onEvent = {it: Int ->

        })
```

License
-------

    Copyright (C) 2020 ueen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
