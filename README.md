    
# mybatis-debby

MyBatist单表CRUD扩展框架。
A MyBatis extension framework to support single table CRUD operation.

## 插入返回主键值

当ResultMap中定义了id节点，则框架默认会把对应的列当作为主键，在insert中增加SelectKey来处理主键值的返回。
>注意：不支持联合主键定义，若有联合主键需求，在mapper文件中定义id="insert"的sql statment来覆盖框架默认生成的insert。

### SelectKey

不同的数据库对于获取主键的值有不同的脚本，MyBatis提供了"Multi-db vendor support"， 通过配置DatabaseIdProvider来支持不同的数据库。SelectKey多数据库支持也采用此方式。

### 自增长主键

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

> 配置DatabaseIdProvider时请使用上面的对应关系以支持SelectKey多数据库支持。

#### 非自增长主键

对于Oracle这样，不支持主键自增长，框架提供了一个额外的设置方式，通过配置XConfiguration类的additionalDatabaseDialects属性，设置数据库与主键获取脚本语言的对应关系。

示例：

```
<property name="oracle" value="select seq_users.nextval from dual" />
```

 > 与上类型配置DatabaseIdProvider的时候Oracle数据库的databaseId对应为"oracle"。








