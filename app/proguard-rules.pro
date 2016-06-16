# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\develop\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# 整体
-ignorewarnings                     # 忽略警告，避免打包时某些警告出现
-optimizationpasses 5               # 指定代码的压缩级别
-dontusemixedcaseclassnames         # 是否使用大小写混合
-dontskipnonpubliclibraryclasses    # 是否混淆第三方jar
-dontpreverify                      # 混淆时是否做预校验
-verbose                            # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

# Android
-dontwarn android.os.**
-keep class android.os.**{*;}
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.widget
-keep class * extends android.view.View                         # 保持自己定义的类不被混淆

-keepclassmembers enum * {                  # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {    # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity { #保持类成员
   public void *(android.view.View);
}

# v4  v7 等
-dontwarn android.support.v4.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-keep class android.support.v4.** { *; }        # 保持哪些类不被混淆
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep class android.support.annotation.** { *; }        # 保持哪些类不被混淆

# -dontwarn android.support.v7.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-keep class android.support.v7.** { *; }        # 保持哪些类不被混淆
-keep public class * extends android.support.v7.**

-dontwarn android.support.design.**
-keep class android.support.design.** {*;}
-keep public class * extends android.support.design.**

-keep class android.support.graphics.** {*;}


# Glide
-keep class com.bumptech.glide.** {*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule

# Okio
-keep public class okhttp3.** {*;}
-keep public class okio.** {*;}
-keep public class org.codehaus.** {*;}
-keep public class java.nio.** {*;}


# retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Rx and RxAndroid
-keep class rx.** {*;}

# Gson
-keep class com.google.gson.** {*;}
-dontobfuscate
-dontoptimize

# android-gif-drawable so 呢？
-keep class pl.droidsonroids.gif.** {*;}

# PhotoView
-keep class uk.co.senab.photoview.** {*;}

# 微信支付
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

# 支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.auth.AlipaySDK{ public *;}
-keep class com.alipay.sdk.auth.APAuthInfo{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

# 其它
-keep class com.apache.**{*;}
-keep class com.google.**{*;}
-keep class com.mapabc.**{*;}
-keep class com.amap.**{*;}
-keep class com.iflytek.**{*;}
-keep class com.umeng.**{*;}
-keep class com.protobuf.**{*;}
-keep class com.protocol.**{*;}


# 主题切换
-keep class com.peerless2012.qingniantuzhai.colorui.** {*;}
