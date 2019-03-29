# 智能医疗APP

APP后端

------

# 接口文档

**接口统一前缀  (文件名)/api/** 

以下为接口文档。

## 1. 注册登录

[1.1 模拟登录](#11-模拟登录)

[1.2 实际登录](#12-实际登录)

[1.3 注册](#13-注册)

[1.4 异步检验用户存在](#14-异步检验用户存在)

[1.5 登录状态检验](#15-登录状态检验)

## 2.问诊

[2.1 进入问询页面前查询状态](#21-进入问询页面前查询状态)

[2.2 问询页面的说话接口](#22-问询页面的说话接口)

## 3.个人信息

[3.1 获取对象列表](#31-获取对象列表)

[3.2 创建新的对象](#32-创建新的对象)

## 一、注册登录

### 1.1 模拟登录

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/mock` |      |

### 1.2 实际登陆

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `POST` `/api/user/login` |      |

- **请求参数**
```
{
    'username':1254635(明文)
    'password':abcd(md5加密)
}
```
- **响应**
```
{
    ①httpcode:200
     'result':1 (登陆成功),'is_member':0(未新建对象)
    ②httpcode:200
     'result':1 (登陆成功),'is_member':1(已创建对象）
    ③httpcode:200
     'result':0,'name':1(账号或者密码错误)
    ④httpcode:401
    (请求无效参数)
    ⑤httpcode:200
    'result':0,'name':0(账户不存在)
}
```

### 1.3 注册

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/user/registered` |      |

- **请求参数**
```
{
    `username`:123456(明文)
    `password`:abcd(明文)
}
```
- **响应**
```
{
    ①httpcode:200(注册成功)
    ②httpcode:400(用户已存在)
    ③httpcode:401(请求参数无效)
    ④httpcode:404(插入数据库发生意外错误,刷新重试)
}
```

### 1.4 异步检验用户存在

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/user/check` |      |

- **请求参数**
```
{
    `username`:123456(明文)
}
```
- **响应**
```
{
    ①httpcode:200(用户名可以用)
    ②httpcode:400(用户已存在)
    ③httpcode:401(请求参数无效)
}
```

### 1.5 登录状态检验

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/user/checklogin` |      |

- **响应**
```
{
    ①httpcode:200(已登录)
    ②httpcode:406(未登录)
}
```

## 二、问诊

### 2.1 进入问询页面前查询状态

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/ask/state` |      |

- **响应**
```
{
    ①httpcode:402(No Member)
    ②httpcode:200(有对象)
     'state':0,'list':'空空如也' （无过往记录）
     'state':1,'list':'[]'（有过往记录，且返回了过往列表）
}
```

### 2.2 问询页面的说话接口

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `POST` `/api/ask/answer` |      |

- **请求参数**
```
{
    `type`: 1(正常输入语音类型)、2(回答问题，程度类型)、3(回答问题，boolean类型)、4(申请重开问询页面)
    `question`:(string) (如果为类型2和类型3，就需要加上此参数，否则不用）
    `w`:（string) 用户的答复
}
```
- **响应**
```
{
    ①httpcode:401(缺乏一些参数,或者type参数无效)
    ②httpcode:402(用户没有创建对象)
    ③httpcode:200(正常回复)
     'type'=>1(文字回复) 'answer'=>（内容）
     'type'=>2(提出问题回复,程度问题) 'answer'=>（问题题目）
     'type'=>3(提出问题回复,boolean问题) 'anser'=>(问题题目）
}
```

## 三、个人信息

### 3.1 获取对象列表

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `GET` `/api/person/GetMemberList` |      |

- **响应**
```
{
    `list`:[{
             "id",
             "user_id",
             "name",
             "alias_name",（别名）
             "sex",
             "weight",
             "height",
             "birthday"
            },
            {}
           ],
    `chosen_id`:(int)（当前选中的id）
}
```

### 3.2 创建新的对象

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `POST` `/api/person/CreateMember` |      |

- **请求参数**
```
{
    `name`:(string)
    `alias`:(string)
    `sex`:(0是女性，1是男性)
    `height`:(double)(单位前端定，不用传上来)
    `weight`:(double)(身高和体重只传双浮点数)
    `birthday`:(YYYY-MM-DD)
}
```
- **响应**
```
正常响应200
{
    `list`:[{
             "id",
             "user_id",
             "name",
             "alias_name",（别名）
             "sex",
             "weight",
             "height",
             "birthday"
            },
            {}
           ],(创建对象后刷新一下列表)
    `chosen_id`:(int)（当前选中的id）
}
非正常响应401
{
    'Name is required'(没输入用户名)
    'Weight is no avail!','Height is no avail!','Sex is no avail!','Birthday is no avail!'(各种参数不合法)
}
```

### 3.3 切换对象

| URL和方式               | 备注 |
| ----------------------- | ---- |
| `POST` `/api/person/ChangeMember` |      |

- **请求参数**
```
{
    `object`:(int)要切换的对象id
}
```
- **响应**
```
{
    ①httpcode:401
    body:'object is required' => 参数object缺失
    body:'object  is no avail' => 参数object不合法，可能非本人的对象
    ②httpcode:200 （切换成功）
}

```
