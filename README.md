# Hiraishin No Jutsu
Teleport your event like Hokage Jutsu !

![](https://thumbs.gfycat.com/LawfulEvenDiscus-size_restricted.gif)

- Build with Rx and Kotlin
- Lightweight, only one file, less than 100 lines
- Easy to use

## Download
```groovy
// required rx android
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

// download hiraishin no jutsu
implementation 'com.utsman.hiraishin:hirasihin:1.0.1'
```

## Usage
### String key
#### Post
```kotlin
// teleport string key for notify your event
HiraishinNoJutsu.getInstance().post("key")
```
#### Observer
```kotlin
HiraishinNoJutsu.getInstance().observer { key, composite ->
   // your event when key is arrived
   // composite.dispose() if you will stop observer
}
```

### Intent
#### Post
```kotlin
// create intent with extras
val intent = Intent().apply {
    putExtra("event", "whatever")
}
// teleport your intent, like send broadcast
HiraishinNoJutsu.getInstance().postIntent("key", intent)
```
#### Observer
```kotlin
HiraishinNoJutsu.getInstance().observerIntent { key, intent, composite ->
   // intent is arrived with key
}

// or create function with intent
private fun subscriber(intent: Intent) {
    // your code with intent
}

// and install your function on composite disposable
compositeDisposable.hiraishinIntent("key", this::subscriber)

```

---
```
Copyright 2020 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```