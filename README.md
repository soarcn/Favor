Favor
=======
[![Build Status](https://travis-ci.org/soarcn/Favor.svg)](https://travis-ci.org/soarcn/Favor)

A easy way to use android sharepreference

*Under development, Don't use it in production environment*

This library works on android 2.2+

How to use this library
=======

- Using Gradle

```groovy
    compile 'com.cocosw:favor:0.0.x@aar'
```
- Using Maven

```xml
<dependency>
    <groupId>com.cocosw</groupId>
    <artifactId>favor</artifactId>
    <version>0.0.x</version>
    <type>apklib</type>
</dependency>
```

API
=======

1 Define the interface

```java 
@AllFavor
public interface Account {
    @Default("No Name")
    String getUserName();
    String setPassword(String password);
}
```

2 The FavorAdatper class generates an implementation of the interface.

```java 
account = new FavorAdapter.Builder(getContext()).build().create(Account.class);
account.setPassword("Passw0rd");
String username = account.getUserName();
```

API Declaration
======

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

@Commit
------

By default, we will call editor.apply() (>api9), you can enforce it to use editor.commit() by @Commit

```java
    @Favor
    @Commit
    void setAddress(String address);
```
    
RxPreference
------
    
You are RxJava fan, easy!

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
