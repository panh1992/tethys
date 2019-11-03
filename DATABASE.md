# 数据库文档

| 数据库实例 |版本 |数据库|用户 |schema |
|:---------:|:---:|:---:|:----:|:---:|
|postgresql|11.0 |test |test  |public|

### 数据表

表中允许为空的字段不做约束、由程序限制， 时间统一为UTC时间， 默认零时区

user 用户表

|字段 |类型 |允许为空 |描述 |
|:----|:----|:--------:|----:|
|id |character(32) |N |用户主键 |
|username |character varying(64) |N |用户名称 |
|nickname |character varying(64) |Y |用户昵称 |
|password |character varying(128) |N |用户密码 |
|email |character varying(128) |Y |邮箱 |
|mobile |character varying(32) |Y |手机号 |
|profile |character varying(255) |Y |个人简介 |
|create_time |timestamp(6) with time zone |N |创建时间 |
