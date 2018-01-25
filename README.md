    
# mybatis-debby

MyBatist通用CRUD扩展框架。

x and xx
x or xx
x and (xx or xxx and (xxxx or xxxxx and xxxxxx))
(x or xx) and (xxx or xxxx and (xxxxx or xxxxxx and xxxxxxx))

大于一个用括号

x.and(xx)
x.or(xx)
x.and(xx, or(xxx, and(xxxx, or(xxxxx))))
x.or(xx, or(xxx, and(xxxx, or(xxxxx))))

ComplexCriterion

private Stirng connector;
private List<SimpleCriterion> simpleCriterion;
private List<ComplexCriterion> subCriterions;

where(x, and(xx)) - SimpleCriterion, CombinedCriterion
where(x, or(xx))
where(x, and(xx, or(xxx), and(xxx, or(xxx), and(xxxxxx))))
where(or(x, xx), and(xxx, and(xxxxx, or(xxxxxx), and(xxxxxxx
where(and(x, xx), and(xxx, and(xxxxx, or(xxxxxx), and(xxxxxxx))))

1. 多于一个参数用括号





