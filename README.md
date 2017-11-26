    
# mybatis-debby

MyBatist通用CRUD扩展框架。

A MyBatis extension framework to support common CRUD operation.

## baseResultMap定义

id为'baseResultMap'的ResultMap提供了表列与实体属性的完整对应关系。此定义是框架使用的最基础配置，若对应的Mapper xml文件中没有此定义则对应的Mapper接口不会拥有通用的CRUD功能。

baseResultMap定义如下：

```
<resultMap id="baseResultMap" type="Blog">
    <id column="id" property="id" />
	<result column="create_date" property="createDate" />
	<result column="modify_date" property="modifyDate" />
	<result column="title" property="title" />
	<result column="content" property="content" />
	<result column="member_id" property="member.id" /> 
</resultMap>
```

>注意：对于关联实体映射需单独提供一个result节点定义。

```
<result column="member_id" property="member.id" /> 
```

除了对关联实体多有如上的定义外，其与的与MyBatis描述的ResultMap定义无任何区别，包括使用association、collection等功能。

## XConfiguration

XConfiguration为全局配置对象。

## 主键生成策略

主键生成策略关系到插入数据时主键值的返回。

> 注意：baseResultMap中'id'节点定义的列会被默认视为主键列，若无'id'节点则主键生成策略配置无效，想对应的mapper接口执行插入声明时不会有主键值返回，同时不支持多个'id'节点定义（也就是联合主键）。

框架提供二种主键生成策略的定义：

- IDENTITY策略
- NORMAL策略

### IDENTITY策略

适用于支持IDENTITY主键策略的数据库，比如说SQL Server、MySQL、DB2、HSQLDB。

配置此策略相当于在INSERT声明中这样定义：

```
<insert id="insertBlog" parameterType="Blog" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
    insert into blog (id, create_date, modify_date, title, content, member_id) values(#{id}, #{createDate}, #{modifyDate}, #{title}, #{content}, #{member.id})
</insert>
```
使用到了useGeneratedKeys, keyProperty, keyColumn属性定义。

### NORMAL策略

NORMAL策略适用于支持IDENTITY主键策略和不支持IDENTITY主键策略的数据库。定义此策略相当于定义SelectKey，主键获取语法根据数据库自定义。

支持SEQUENCE生成主键的数据库可以采用此策略。

框架对于部分数据库提供了内置的实现，如下：

| 数据库    |策略类                | 主键获取语法               |执行时机         |
| -------- |:--------------      |:-------------------------:| :-------------:|
|DB2       |XDB2KeyStrategy      |VALUES IDENTITY_VAL_LOCAL()| after          |
|MySQL     |XMySQLKeyStrategy    |SELECT LAST_INSERT_ID()    | after          |
|SQL Server|XSQLServerKeyStrategy|SELECT SCOPE_IDENTITY()    | after          |
|Derby     |XDerbyKeyStrategy    |VALUES IDENTITY_VAL_LOCAL()| after          |
|HSQLDB    |XHSQLDBKeyStrategy   |CALL IDENTITY()            | after          |
|Sybase    |XSybaseKeyStrategy   |SELECT @@IDENTITY          | after          |
|H2        |XH2KeyStrategy       |SELECT LAST_INSERT_ID()    | after          |

> 执行时机主键获取时间：数据插入后（after）、数据插入前（before）

通过继承XNormalKeyStrategy类实现自定义生成策略。

### 配置方式

通过设置XConfiguration的keyStrategy属性来设置对应的策略，默认使用IDENTITY策略。

```
    XConfiguration xConfiguration = new XConfiguration(configuration);
    xConfiguration.setKeyStrategy(new XMySQLKeyStrategy());
```

## 通用功能



 