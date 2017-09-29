# Proguard rules that are applied to your test apk/code.
-ignorewarnings

-keep class android.support.v7.widget.** { *; }
-keep class android.support.v4.widget.** { *; }
-keep class android.support.design.** { *; }
-keep class android.support.constraint.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }

-keepattributes *Annotation*

-dontnote junit.framework.**
-dontnote junit.runner.**

-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**
-dontwarn com.squareup.javawriter.JavaWriter
# Uncomment this if you use Mockito
-dontwarn org.mockito.**

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions