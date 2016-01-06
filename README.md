# Android AppVersion Plugin

The `appversion-plugin` is a gradle plugin that automates the task of applying the `versionCode` and `versionName` to
your Android app by using the Git _tags_ you create.

Usage
-----

Apply the plugin in your `build.gradle`:

```gradle
apply plugin: 'com.github.mitchwongho.appversion'

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.mitchwongho.gradle:appversion-plugin:1.0.0'
    }
}

android {
    ...
    defaultConfig {
        ...
        versionCode project.appversion.code
        versionName project.appversion.name
    }
```

License
--------

    Copyright 2016 Mitchell Wong Ho

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.