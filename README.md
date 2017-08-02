# NFBRestClient
Easy http client... Base On OKHTTP3 

# Feature
- Request POST METHOD
- POST Multipart / Upload file
- Header Authorization
- Request GET METHOD
- Log Utils
 
 
### Dependencies used by NFBRestClient
| Name | Type |
| ------ | ------ |
|com.squareup.okhttp3:okhttp:3.8.0 | Dependencies
|com.android.support:support-annotations:25.1.0| Dependencies    

# How to use

### 1. Gradle
Compile NFBRestClient on your project
>Gradle (Top-level).

```gradle
...
buildscript {
    repositories {
        jcenter() 
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.2'
    }
}
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io'}
    }
}
...
```


>Gradle (On your Module:App)

```gradle
...
dependencies {
    compile 'com.github.opannapo:NFBRestClient:Beta-1.0.0'
} 
...
```

### 3. API Class
Create Class of API Control,
then extend NFBRestClient Api class.
```java
public class API extends Api {
    @Override
    public String getBaseUrl() {
        //Base URL
        return "http://www.development.api";
    }
    
    @Override
    public TimeoutAttr getConnectTimeout() {
        //Connect Timeout 10 seconds
        return new TimeoutAttr(10, TimeUnit.SECONDS);
    }
    
    @Override
    public TimeoutAttr getWriteTimeout() {
        //Write Timeout 10 seconds
        return new TimeoutAttr(10, TimeUnit.SECONDS); 
    }
    
    @Override
    public TimeoutAttr getReadTimeout() {
        //Read Timeout 30 seconds
        return new TimeoutAttr(30, TimeUnit.SECONDS); 
    }
    
    @Override
    public boolean isLogEnable() {
        //ENABLE LOG
        return true; 
    }
}
```

### 4. POST Object
Create Class of POST Object,
then extend POST class.
```java
@Url("/user/login")
@BodyType(BodyType.POST_DEFAULT)
public class Login extends POST {
    @PostParam(POST.TYPE_TEXT)
    private String username;
    @PostParam(POST.TYPE_TEXT)
    private String password;

    //SETTER - GETTER Below..
} 
```

### 5. POST Multipart Object
Create Class of POST Object,
then extend POST class.
```java
@Url("/product/upload")
@BodyType(BodyType.POST_MULTIPART)
public class ProductUpload extends POST {
    @PostParam(POST.TYPE_TEXT)
    private String productName;
    @PostParam(POST.TYPE_TEXT)
    private String productDesc;
    @PostParam(POST.TYPE_FILE)
    private String image; 

    //SETTER - GETTER Below..
}
```

### 6. POST RAW Object
Create Class of POST Object,
then extend POST class.
```java
@Url("/product/edit")
@BodyType(BodyType.POST_RAW)
public class ProductEdit extends POST {
    @PostRawData(POST.TYPE_JSON)
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
} 
```

### 7. GET Object
Create Class of GET Object,
then extend GET class.
```java
@Url("/user/search?")
public class UserSearch extends GET {
    @GetParam
    private String name;
    @GetParam
    private String city;

    //SETTER - GETTER Below.. 
}
```

### 8. Execute POST Method
```java
Login login = new Login();
login.setUsername("myusername88");
login.setPassword("1234MyPassword");
new API().preparePost(login.build()).execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 9. Execute POST Multipart / Upload
```java
String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
String fileName = "coffeImage.jpg";
File imageFile = new File(baseDir + File.separator + "testFolder" + File.separator + fileName);

ProductUpload post = new ProductUpload();
post.setProductName("Sumatera Cofee");
post.setProductDesc("This is Sumatera Coffe, Make you can't sleeping for 3 days");
post.setImage(imageFile.getAbsolutePath());
new API().preparePost(post.build()).execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 10. Execute POST With Header Authorization
```java
String jwtLongerExp = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
    ".eyJleHAiOjE5MTE3MzQ3OTQsImlkIjoiNzc4NDEwNzEyMTIyMzMwNCIsImlhdCI6MTUwMTQ5MTU4Mn0" +
    ".vL_jxUFVYnVFFF7aWYx3KhNAUjLvRxEqMeZhevI9jsQ";

ProductUpload post = new ProductUpload();
post.setProductName("Sumatera Cofee");
post.setProductDesc("This is Sumatera Coffe, Make you can't sleeping for 3 days");
post.setImage(imageFile.getAbsolutePath());

new API().authorization("Bearer " + jwtLongerExp)
.preparePost(post.build())
.execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 11. Execute POST With Raw (JSON)
```java
String jwtLongerExp = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
    ".eyJleHAiOjE5MTE3MzQ3OTQsImlkIjoiNzc4NDEwNzEyMTIyMzMwNCIsImlhdCI6MTUwMTQ5MTU4Mn0" +
    ".vL_jxUFVYnVFFF7aWYx3KhNAUjLvRxEqMeZhevI9jsQ";

JSONObject data = new JSONObject();
try {
    data.put("param1", "this is param1");
    data.put("param2", "this is param2");
} catch (JSONException e) {
    e.printStackTrace();
}

ProductEdit raw = new ProductEdit();
raw.setData(data.toString());
new API().preparePost(raw.build()).execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 12. Execute GET With parameters
```java
UserSearch get = new UserSearch();
get.setParam1("this is param1");
get.setParam2("this is param2");
new API().prepareGet(get.build()).execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 13. Execute GET With URL
```java
new API().prepareGet("/term").execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```

### 14. Execute GET With Header Authorization
```java
String jwtLongerExp = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
    ".eyJleHAiOjE5MTE3MzQ3OTQsImlkIjoiNzc4NDEwNzEyMTIyMzMwNCIsImlhdCI6MTUwMTQ5MTU4Mn0" +
    ".vL_jxUFVYnVFFF7aWYx3KhNAUjLvRxEqMeZhevI9jsQ";

new API().authorization("Bearer " + jwtLongerExp)
.prepareGet("/term")
.execute(new ExecuteCallback() {
    @Override
    public void onResponse(String url, Call call, Object o) {
        ...
    }

    @Override
    public void onFailure(String url, Call call, Object e) {
        ...
    }
}); 
```


## 14. Annotations
| Name | Param | Desc | Sample
| ------ | ------ | ------ | ------ |
|@Url | String URL | Target Class, Return String | @Url("/user/loign")
|@BodyType | Integer | Target Class, Return Integer | @BodyType(BodyType.POST_DEFAULT) or @BodyType(BodyType.POST_RAW) or @BodyType(BodyType.POST_MULTIPART)
|@PostParam | Integer | Target Field, Return Integer | @PostParam(POST.TYPE_TEXT) private String username; @PostParam(POST.TYPE_FILE) private String imageFile;
|@PostRawData | Integer | Target Field, Return Integer | @PostRawData(POST.TYPE_JSON) private String dataJson;
|@GetParam | - | - | @GetParam private String param1;
