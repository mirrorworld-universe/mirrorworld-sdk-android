# Mirror World Android SDK

Mirror World's Official Android SDK

## Getting started

Create a developer account on the [Developer dashboard](https://app.mirrorworld.fun). Create project and create an API Key.

## Import SDK


> **Notice:**
>
> *The minimum version that SDK requires is Android 4.4.*

1.  Download and uncompress [Mirror World Android SDK](https://github.com/mirrorworld-universe/mirrorworld-sdk-android/releases/latest).
2.  Put the `mirrorsdk.aar` under the 'libs' folder. You may create a 'libs' folder if it doesn't exist
    <img src="https://market-assets.mirrorworld.fun/docs/build.png" width="30%" height="30%" />

3.  Add configeration to build.gradle:

```java
dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar','*.aar'])
        }
```

4. Config CustomTab!
   If you want to use CustomTab to show content to users (Recommended), you need to configure the following on your AndroidManifest.xml:

**Add permission for internet**
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

**Register this activity**
```xml
<activity
    android:name="com.mirror.sdk.activities.RedirectActivity"
    android:exported="true">

    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>
        <data android:scheme="mwsdk"/>
    </intent-filter>
</activity>
```

**Add dependency**
```xml
implementation 'androidx.browser:browser:1.4.0'
```

You should now see CustomTab when you try to open our prepared web page.


>*Tips: If you want your app to have only one task, you can set your own activity to SingTast:*
>```xml
>android:launchMode="singleTask"
>```

## Usage
### Choose your chain
Developers must use specified classes to call APIs on specified chains.  
All API on all chains is same to call, but the instance's package is different.
You may select which package you want to use when typing API:
<img src="https://market-assets.mirrorworld.fun/docs/android-sdk-select-chain.png" width="100%" height="100%" />

### Initialization
We can initialize Mirror World SDK with the following code:

```java
Context context = this;
String apiKey = "your-api-key";
MirrorEnv env = MirrorEnv.DevNet;

MirrorWorld.initSDK(context,apiKey,env);
```

### Guide user to login
Users need to log in to use some MirrorSDK features (or APIs).
If you want them to login for the first time or once again, use the following code:

```java
MirrorWorld.startLogin(new LoginListener() {
    @Override
    public void onLoginSuccess() {
        Log.i("Mirror","User login success!");
    }

    @Override
    public void onLoginFail() {
        Log.i("Mirror","User login failed!");
    }
});
```

But you may not want to let them login every time, so you can use CheckAuthenticated API to check if they have already logged in.
According the result, you can show them a login button or hide your login button and make them enter your dApp or game directly.

```java
MirrorWorld.isLoggedIn(new BoolListener() {
    @Override
    public void onBool(boolean boolValue) {
        Log.i("Mirror","This user's login state is:" + boolean);
    }
});
```

## Full API Documentation

You can view the documentation for Mirror World SDK for Mobile on
[our Official Documentation Site](https://docs.mirrorworld.fun/android/android-api)
