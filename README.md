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
	        debugImplementation 'com.github.hss01248.TestTools:testtool-no-op:1.0.4'
		releaseImplementation 'com.github.hss01248.TestTools:testtool:1.0.4'
	}
  ```

