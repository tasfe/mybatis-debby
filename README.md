    
# mybatis-debby

MyBatist单表CRUD扩展框架。
A MyBatis extension framework to support single table CRUD operation.

## baseResultMap定义

使用框架的第一步就是在mapper文件中定义id为'baseResultMap'的ResultMap。定义中包含列与实体属性的完整对应关系。

 baseResultMap中对于关联实体映射需单独提供一个result节点定义，如下：

```
<result column="member_id" property="member.id" /> 
```

其余与普通的ResultMap没有任何区别。


## insert返回主键值

当ResultMap中定义了id节点，则框架默认会把对应的列当作为主键，在insert中增加SelectKey来处理主键值的返回。
>注意：不支持联合主键定义，若有联合主键需求，在mapper文件中定义id="insert"的statment来覆盖框架默认生成的insert。

#### SelectKey多数据源支持

不同的数据库对于获取主键的值有不同的脚本，MyBatis提供了"Multi-db vendor support"， 通过DatabaseIdProvider的vendor配置来支持不同的数据库。SelectKey多数据库支持也采用此方式。

##### 自增长主键型数据库（系统内置支持）
SelectKey默认支持的数据库以及与对应的DatabaseId的映射关系如下表所示：

| 数据库         | databaseId    |
| ------------- |:-------------:| 
| DB2           | db2           | 
| MySQL         | mysql         | 
| SQL Server    | sqlserver     | 
| Cloudscape    | cloudscape    |
| Apache Derby  | derby         |
| HSQLDB        | hsqldb        |
| Sybase        | sybase        |
| DB2_MF        | db2_mf        |
| Informix      | informix      |

> vendor配置使用上述关系。

##### 非自增长主键型数据库

对于Oracle这样，不支持主键自增长，框架提供了一个额外的设置方式，通过配置XConfiguration类的additionalDatabaseDialects属性，设置数据库与主键获取脚本语言的对应关系。

示例：

```
<property name="oracle" value="select seq_users.nextval from dual" />
```

 > vendor中增加Oracle对应databaseId为"oracle"的配置。

 