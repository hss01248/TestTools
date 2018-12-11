# TestTools
a tool collection for debug,test and performance optimization

[![](https://jitpack.io/v/hss01248/TestTools.svg)](https://jitpack.io/#hss01248/TestTools)

# usage

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
		
			maven { url 'https://jitpack.io' }
		}
	}

```


Step 2. Add the dependency
```
	dependencies {
	        debugImplementation 'com.github.hss01248.TestTools:testtool-no-op:1.0.5'
		releaseImplementation 'com.github.hss01248.TestTools:testtool:1.0.5'
	}
```



## init

```
 TestTool.init(this,true,false);
MyLog.init(true, "cameralog", 2, new IJsonToStr() {
            @Override
            public String toStr(Object o) {
                return "{}";
            }
        });
MyToast.init(this,true,false);
```

