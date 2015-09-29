Favor
=======
[![Build Status](https://travis-ci.org/soarcn/Favor.svg)](https://travis-ci.org/soarcn/Favor)

One of the most easiest way to use sharepreference in android

Under development

This library works on android 2.2+

How to use this library
======

1 Define the interface

```java 
public interface Profile {

    @Favor("city")
    @Default("Sydney")
    String city();

    @Favor()
    void setAge(int age);
}
```

2 The FavorAdatper class generates an implementation of the interface.

```java 
profile = new FavorAdapter.Builder(getContext()).build().create(Profile.class);
profile.setAge(32);
```



Contribute
=======

- Feel free to fork it

About me
=======

I'm Kai, an 32 years old android developer based in Sydney.


License
=======

    Copyright 2011, 2015 Kai Liao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
