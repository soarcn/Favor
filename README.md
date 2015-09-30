Favor
=======
[![Build Status](https://travis-ci.org/soarcn/Favor.svg)](https://travis-ci.org/soarcn/Favor)

A easy way to use android sharepreference

*Under development, Don't use it in production environment*

This library works on android 2.2+

How to use this library
======

1 Define the interface

```java 
public interface Profile {

    @Favor("city")
    @Default("Sydney")
    String city();

    @Favor
    void setAge(int age);
}
```

2 The FavorAdatper class generates an implementation of the interface.

```java 
profile = new FavorAdapter.Builder(getContext()).build().create(Profile.class);
profile.setAge(32);
String city = profile.city();
```

API Declaration
======

@Favor @Default
-----

```java
    @Favor("city")
    @Default("Sydney")
    String city();
```

equals

```java
    PreferenceManager.getDefaultSharedPreferences(context).getString("city","Sydney");
```

Or you can simplify it to

```java
    @Favor
    @Default("Sydney")
    String city();
```

@AllFavor
-----

```java
@AllFavor
public interface Account {
    @Default("No Name")
    String getUserName();
    String getPassword();
}
```

@Commit
------

By default, we will call editor.apply() when you set your preference(>api9), you can enforce to use editor.commit() by @Commit

```java
    @Favor
    @Commit
    void setAddress(String address);
```
    
RxPreference (TODO)
------
    
If you use RxJava/RxPreference, simple

```java
    @Favor
    @Default("No Name")
    Preference<String> name();
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
