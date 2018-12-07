# 数据库文档

| 数据库实例 | 版本 | 数据库 | 用户 | schema |
| :------: | :------: | :------: | :------: | :------: | 
| postgresql | 11.0 | test | test | public |

### 数据表
表中允许为空的字段不做约束、由程序限制， 时间统一为UTC时间， 默认零时区

users 用户表

| 字段 | 类型 | 允许为空 | 描述 |
| :------ | :------ | :------: | ------: |
| id | character(32) | N | 用户主键 |
| username | character varying(64) | N | 用户名称 |
| nickname | character varying(64) | Y | 用户昵称 |
| password | character varying(128) | N | 用户密码 |
| email | character varying(128) | Y | 邮箱 |
| mobile | character varying(32) | Y | 手机号 |
| profile | character varying(255) | Y | 个人简介 |
| create_time | timestamp(6) with time zone | N | 创建时间 |

files 文件表

| 字段 | 类型 | 允许为空 | 描述 |
| :------ | :------ | :------: | ------: |
| id | character(32) | N | 文件主键 |
| store_id | character(32) | N | 存储空间 |
| creater_id | character(32) | N | 创建用户 |
| store_space | character varying(128) | N | 文件所属存储空间名称 |
| file_name | character varying(255) | N | 文件名 |
| file_size | bigint | N | 用户角度文件大小  单位 byte |
| source_id | character(32) | N | 存储来源 |
| source_type | character(32) | N | 存储来源类型  upload   mount   system    task |
| is_dir | boolean | N | 是否为目录 |
| check_sum | character varying(255) | Y | 文件的校验码 |
| format | character varying(32) | Y | 文件格式  以文件扩展名为依据 |
| status | character varying(16) | N | 文件状态   new   uploading   available   failed   deleted |
| create_time | timestamp(6) with time zone | N | 创建时间 |
| modify_time | timestamp(6) with time zone | N | 修改时间 |
| description | character varying(255) | Y | 描述信息 |

