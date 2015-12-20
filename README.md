Favor
=======
[![Build Status](https://travis-ci.org/soarcn/Favor.svg)](https://travis-ci.org/soarcn/Favor)  [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Favor-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2695)

A easy way of using Android SharedPreferences.

How to use this library
=======

- Using Gradle

```groovy
    compile 'com.cocosw:favor:0.2.0@aar'
```
- Using Maven

```xml
<dependency>
    <groupId>com.cocosw</groupId>
    <artifactId>favor</artifactId>
    <version>0.2.0</version>
    <type>apklib</type>
</dependency>
```

API
=======

1 Define a interface.

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

And you can simplify it, Favor will extract the key from the method name

```java
    @Favor
    @Default("Sydney")
    String city();
```

@Commit
------

By default, Favor will call editor.apply() (>api9), but you can force it to use editor.commit() by @Commit

```java
    @Favor
    @Commit
    void setAddress(String address);
```
    
RxPreference
------
    
You are a RxJava fan, easy! (rx-preferences dependency is required)

```java
    @Favor
    @Default("No Name")
    Preference<String> name();
```    

Advanced usage
-------

Favor support put/get all primitive types, including int/long/float/String/bool, String set is also supported for API>=11.
From 0.2.0, Favor (Experimentally) supports Serializable object saving/loading.

```java
    public class Image implements Serializable {
    ....
    }
   
    @Favor
    Image image();

    @Favor
    void setImage(Image image);
```

There is one limitation that you can't set default value for Serializable preference item.


Proguard
=======

```xml
# Favor
-dontwarn com.cocosw.favor.** { *; }
-keep class com.cocosw.favor.** { *; }
```

Contribute
=======

- Feel free to fork it

About me
=======

I'm Kai, an 32 years old android developer living in Sydney.


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
