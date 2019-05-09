# MeetDoctor

## 目录
> * [项目介绍](#1-项目介绍)
> * [配置](#2-配置)
> * [部分文件使用说明](#3-使用说明)
> * [待完善TODO](#4-待完善TODO（潜在会引发bug）)
> * [贡献者](#5-贡献者)

------

### 1. 项目介绍

安心了(原名遇医)App

### 2. 配置

1. 本项目使用主流IDE `Android Studio` 开发，版本为 **v3.3.2**
2. `clone`即可，注意**不要**修改`gradle 3.2.1`版本（因为并没有做`androidX`的适配，如果想适配你也真是个天才...）
3. 本项目使用了**混淆**，如果要添加其他的库注意根据说明在`proguard.pro`中添加相应的混淆
4. 本项目已经使用了签名文件`keystore.jks`，账号与密码在`gradle`文件中，想改自己就改

### 3. 使用说明

> `core`模块是**核心**，考虑到封装代码复用等因素，如果没有高复用性的内容，**请不要**添加在里面，下面**仅**对`core`模块做具体说明

------
folder  | function
---|---
activity | 基类活动
app | 应用Application
database | 数据库
delegate | Fragment页面
img | 图片加载Glide
net | 网络核心库
log | 日志工具类
speech | 语音模块
storage | 缓存SharePreference


- 本模块UI采用[Fragmentation](https://github.com/YoKeyword/Fragmentation)的**单Activity+多Fragment**架构。具体参见文档 [Fragmentation Wiki](https://github.com/YoKeyword/Fragmentation/wiki)

- activity 用代理模式实现`ProxyActivity`

- UI
    
    folder | file | function
    ---|---|---
    activity|ProxyActivity|单activity的基类，用于进行初始配置
    delegate|BaseDelegate|基类Fragment继承自SwipeBackFragment，管理通信、数据
    delegate|PermissionDelegate|权限检查基类，继承自BaseDelegate，所有的必须权限要放进去
    delegate|LatteDelegate|UI基类，继承自PermissionDelegate，全局UI配置

    ```
    /**
     * 1. 首先定义一个Activity主入口
     *    继承自抽象类ProxyActivity，覆写抽象方法setRootDelegate，作为页面主入口
     * 2. 然后就可以写Fragment辽
     *    每个delegate都要继承自抽象类LatteDelegate，覆写setLayout和onBindView方法
     *    具体说明见下面
     */

    // xxxActivity.java
    class xxxActivity extends ProxyActivity{
        @Override
        public LatteDelegate setRootDelegate() {
            return new xxxDelegate();
        }
    }

    // xxxDelegate.java
    class xxxDelegate extends LatteDelegate{
        @Override
        public Object setLayout() {
            // 绑定布局文件
            return R.layout.delegate_xxx;
        }

        @Override
        public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
            // 进行layout的绑定view操作
            ...
        }
    }
    ```

- net [`Retrofit2`](https://github.com/square/retrofit) + [`Okhttp3`](https://github.com/square/okhttp) 实现
    ```
    // 实现思路比较抽象，需要有比较强的面向对象功底才能看懂，但是本模块使用流式API方便了使用。下面仅介绍使用方法
    // 1.首先修改RestCreator中的BASE_URL，为所有请求的api头
    // 2.如下形式调用
    RestClientBuilder builder =
            RestClient.builder()
                .url("url") // 去掉baseUrl的链接，注意第一个字符不要加"/"。e.g:user/login
                .params("type", type) // post,get等请求的参数
                .loader(context) // 加载提示，可选
                .success((response) -> { // 使用了Java8 lambda表达式，对应ISuccess接口
                    // 200 成功回调
                })
                .failure(() -> {
                    // 断网等情况没有成功发出请求
                })
                .error((code, msg) -> {
                    // 请求失败，返回restful 错误码和错误信息
                });
    builder.build.post(); // 还可 get,put,delete,postRow,putRow,upload,download

    ```


- img [`Glide`](https://github.com/bumptech/glide)，log [`Logger`](https://github.com/orhanobut/logger)，storage `SharePreference`,speech [`Baidu Speech API`](https://ai.baidu.com/docs#/ASR-Android-SDK/top)
    ```
    // 具体使用方法

    // 图片加载：注意不是用Glide而是用GlideApp！
    GlideApp.with(...).load(...).into(...);

    // 日志类：先在全局Application中配置如下
    Logger.addLogAdapter(new AndroidLogAdapter());
    // 之后调用
    LatteLogger.d(); // i,w,e,json等

    // 缓存
    LattePreference.setXxx()/getXxx();

    // 语音识别模块，重点！
    private SpeechRecognizer recognizer;
    recognizer = new SpeechRecognizer(activity, new RecogListener() {
            // 所有回调见 IRecogListener
            // 部分全局实现见 RecogListener
            @Override
            public void onFinishResult(String recogResult) {
                // showToast(recogResult);
            }
        });

    ```

### 4. 待完善TODO（潜在会引发bug）

1. png及jpg格式背景图优化，考虑使用`Glide`减少占用内存并提高清晰度
2. `View`重叠重复绘制问题严重，一开始没有考虑，现在导致`Graphics层`占用过高产生卡顿(比如`HomeActivity`)
3. **代码复用**问题，除`core`外，其他或多或少尤其是在UI方面的重复比较多，还在思考如何解决
4. **（处理完成）**考虑使用单Activity多Fragment的框架`Fragmentation`作为主架构亦可显著提高流畅度，但现在可能有bug待修复

### 5. 贡献者

[@Rainbow](https://github.com/RainbowDDB) 主架构开发，如有问题请联系`1274200453@qq.com`