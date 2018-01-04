    
# mybatis-debby

MyBatist通用CRUD扩展框架。

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

## 通用CRUD

org.mybatis.debby.x.DebbyMapper定义了通用CRUD方法。

继承DebbyMapper则会拥有通用的CRUD功能。

### 通用方法说明

```
public interface DebbyMapper<ENTITY, PK extends Serializable> {
    
    /**
     * Insert an entity object.
     * After, it has the primary key value assigned and we can get from the entity object we just inserted.
     *
     * @param entity
     */
    void insert(ENTITY entity);
    
    /**
     * Insert an entity object selectively.
     *
     * Don't like {@link #insert(Object)}}, the method just insert the property which is not null.
     *
     * @param entity
     */
    void insertSelective(ENTITY entity);

    /**
     * Update an entity object by primary key.
     *
     * @param entity
     */
    void updateByPrimaryKey(ENTITY entity);
    
    /**
     * Update an entity selectively by primary key.
     *
     * Don't like {@link #updateByPrimaryKey(Object)}}, the method just update the property which is not null.
     *
     * @param entity
     */
    void updateByPrimaryKeySelective(ENTITY entity);

    /**
     * A enhanced update method that accept different updated conditions.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int updateByCriteria(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Select an entity by primary key.
     *
     * @param pk
     * @return
     */
    ENTITY selectByPrimaryKey(PK pk);

    /**
     * A enhanced select method that accept different selective conditions.
     *
     * @param criteria
     * @return
     */
    List<ENTITY> selectByCriteria(EntityCriteria criteria);
    
    /**
     * Count the records by different conditions.
     *
     * @param criteria
     * @return
     */
    long selectCountByCriteria(EntityCriteria criteria);

    /**
     * Delete a entity by primary key.
     *
     * @param pk
     */
    void deleteByPrimaryKey(PK pk);

    /**
     * A enhanced select method that accept different deleted conditions.
     *
     * @param criteria
     * @return
     */
    int deleteByCriteria(EntityCriteria criteria);

}

```

#### insert

保存记录，配置了主键生成策略，单主键时自动返回主键值。

> 不支持联合主键（baseResultMap中定义了多个id节点）时主键值返回，若有此需求，重新定义insert方法。

#### insertSelective

选择性保存记录。忽略实体空属性。配置了主键生成策略，单主键时自动返回主键值。

> 不支持联合主键（baseResultMap中定义了多个id节点）时主键值返回，若有此需求，重新定义insert方法。

#### updateByPrimaryKey

根据主键更新记录。

> 不支持联合主键。

#### updateByPrimaryKeySelective

根据主键选择性更新记录。忽略实体空属性。

> 不支持联合主键。

#### updateByCriteria

根据条件来更新记录。

#### selectByPrimaryKey

根据主键查询。

> 不支持联合主键。

#### selectByCriteria

根据条件查询。

#### selectCountByCriteria

根据条件查询满足的记录的总数。

#### deleteByPrimaryKey

根据主键删除记录。

> 不支持联合主键。

#### deleteByCriteria

根据条件删除记录。

### 通用方法覆盖

当通用方法不满足需求时，通过重定义可以实现重载效果。

定义同名并且同SqlCommandType类型的statement实现通用方法的覆盖。

同SqlCommandType是指同Statement类型，比如下下面的定义方式则不能实现重载insert方法：
```
<select id ="insert" />
```
这里虽然id和通用的insert方法名称一致，但是这里的SqlCommandType为SELECT.

#### Annotation方式

```
@Insert("INSERT into t_blog(id,name,title,member_id) VALUES(#{id}, #{name}, #{title}, #{member.id})")
void insert(Blog blog);
```

#### XML文件方式

```
<insert id="insert" parameterType="org.mybatis.debby.entity.Blog">
    <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
      SELECT LAST_INSERT_ID()
    </selectKey>
     insert into t_blog (id, title, 
      content, member_id
      )
     values(#{id,typeHandler=org.apache.ibatis.type.IntegerTypeHandler}, #{title,typeHandler=org.apache.ibatis.type.StringTypeHandler}, 
      #{content,typeHandler=org.apache.ibatis.type.StringTypeHandler}, #{member.id,typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
      )
</insert>
```
上面两种方式都可以实现覆盖通用insert方法的效果。


## 联合主键支持

## 问题

1. 主键

a. 单主键
自增(identity,sequence),代码主动生成(例如uuid)
b. 联合主键
自增 + 代码主动生成
自增 + 自增 (不考虑https://dba.stackexchange.com/questions/35449/how-to-use-2-auto-increment-columns-in-mysql-phpmyadmin)
代码主动生成+代码主动生成

通过JPA注解配合来实现主键生成策略的配置
作为嵌入式主键类，要满足以下几点要求。
1. 必须实现 Serializable 接口、必须有默认的 public 无参数的构造方法、必须覆盖 equals 和 hashCode 方法，这些要求与使用复合主键的要求相同。
2. 将嵌入式主键类使用 @Embeddable 标注，表示这个是一个嵌入式类。
3. 通过 @EmbeddedId 注释标注实体中的嵌入式主键

