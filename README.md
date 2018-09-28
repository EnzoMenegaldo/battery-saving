# battery-saving

# About
battery-saving is an android library defining a new language helping developers to build mobile applications that can regulate themselves according to the battery level.

# How to use it

1. Create local Maven repo

The library is not available in the maven central repository.
To add it to the local Maven repo, download the project and run the command in the android studio's terminal : 
```groovy
  ./gradlew clean build publishToMavenLocal
 ```

2. Add these dependencies to build.gradle

      ```groovy
      implementation('com.emenegal:battery-saving:1.0@aar') {
      transitive = true
      }

      annotationProcessor 'org.atteo.classindex:classindex:3.3'
      testAnnotationProcessor 'org.atteo.classindex:classindex:3.3'
      androidTestAnnotationProcessor 'org.atteo.classindex:classindex:3.3'
      ```
      
      
battery-saving uses the external library [ClassIndex](https://github.com/atteo/classindex) which uses the annotation processor.        For recent Gradel versions, you must explicitly add annotationProcessor dependencies.


3. Annotate variables you want to let the library manage according to the battery level
    ```java
       @BPrecision(Value = True, Threshold = 80)
       public static boolean aValue;
       
       @IPrecision(min = 0, max = 50, method = DefaultMethod.class, params = {}
       public static double aValue;
       
       @EPrecision(klass = DefaultEnum.class)
       public static IEnum aValue;
    ```
    
4. Annotate classes containing at least one field annotated as above
    ```java
       @ResourceStrategy
       public class aClass {
       ...
       }
    ``` 
    
4. Start the library
    ```java
        StrategyManager.INSTANCE.initialize(Context);
    ``` 
    
5. Stop the library
    ```java
        StrategyManager.INSTANCE.stop(Context);
    ``` 
        
# How it works

## Indexation

battery-saving uses ClassIndex to index the annotated classes at compile time and retrieve all the annotated fields at the library initialization.

## Get Battery information

At the initialization, the library sets up a BroadcastReceiver to catch the intent sent by the Android system about a battery state changing.

The library unregisters the BroadcastReceiver when StrategyManager.INSTANCE.stop(Context) is called.

## Managing strategy

If the mobile device is plugged then the library will act like the battery was 100%.

Else the library will manage the variable according to the battery level and their parameters.



# Variable annotations

## @BPrecision

This annotation must only be used for boolean variables.

Two parameters :
  - Value : define the boolean value that the variable takes when the battery level is higher than its threshold.
  - Threshold : define the battery level limit at which the boolean value is supposed to change.

Here is an example:
 ```java
       @BPrecision(Value = True, Threshold = 80)
       public static boolean aValue;
 ```

## @IPrecision

This annotation must only be used for double variables. 

Four parameters :
  - Min : the lowest value that can take the variable.
  - Max : the highest value that can take the variable.
  - Method : a class which implement the ‘IMethod’ interface defining the way the variable will change according to the battery level.
  - Params : a double array which contains potential parameters used by the method.

Here is an example:

```java
      @IPrecision(min = 0, max = 50, method = DefaultMethod.class, params = {}
       public static double aValue;
       
       
       public interface IMMethod{
          public double execute(int batteryLevel, IPrecision annotation);
       }
       
       public class DefaultMethod implements IMethod {
          public double execute(int batteryLevel, IPrecision annotation) {
             return (annotation.max() - annotation.min()) * (double)batteryLevel / 100.0D + annotation.min();
          }
       }
       
```



## @EPrecision

This annotation must only be used for IEnum variables. IEnum is an interface defining in the library. It allows you to use custom enums.  
The variable takes the value of its closest inferior value to battery level.

One parameter :
  - klass : custom enum implementing the IEnum interface.
 
Here is an example:

```java
       @EPrecision(klass = DefaultEnum.class)
       public static IEnum aValue;
       
       
       public enum DefaultEnum implements IEnum {
          Small(0),Medium(50),Large(80);

          int batteryLevel;

          private DefaultEnum(int batteryLevel) {
              this.batteryLevel = batteryLevel;
          }

          public int getBatteryLevel() {
              return this.batteryLevel;
          }
      }
```

