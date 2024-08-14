
## 接入

根build.gradle添加配置：

``` 
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

```

``` 

dependencies {
	        implementation 'com.github.751496032:OkHttpUtils:Tag'
	}

```

## post请求

异步请求：

```
Map<String, String> params = new HashMap<>();
params.put("key", value);
OkHttpUtils.post()
    .asForm() // form表单方式post ，不调用asForm()方法默认是json方式
    .url(CHECK_VERSION_URL)
    .parameters(params)
    .execute(new ICallback() {
        @Override
        public void onSuccess(String response) {
                System.out.println("更新内容：" + formatJson(response));
                }
        
        @Override
        public void onFailure(int code, String message) {
        
                }
    });

```

同步请求：

```

OkHttpUtils.post()
    .url(CHECK_VERSION_URL)
    .parameters(params)
    .executeSync()
```

## post上传文件

```
 // 支持同步与异步请求
 OkHttpUtils.postFile()
                .setProgressListener((currentLength, contentLength, progress) -> {
                    System.out.print("上传进度: " + progress + "%");
                    System.out.print("\r");
                })
                .setFile(uploadFile)
                .url(url)
                .parameters(params)
                .executeSync();
```


## get请求

```
 // 支持同步与异步请求
OkHttpUtils.get()
    .url("xxxx")
    .parameters(params)
    .executeSync()

```