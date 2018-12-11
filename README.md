# TestTools
a tool collection for debug,test and performance optimization



```
implementation 'com.github.hss01248.TestTools:testtool:1.0.5'

implementation 'com.github.hss01248.TestTools:testtool-no-op:1.0.5'




 TestTool.init(this,true,false);
 
 MyLog.init(true, "cameralog", 2, new IJsonToStr() {
            @Override
            public String toStr(Object o) {
                return "{}";
            }
        });
MyToast.init(this,true,false);

```

