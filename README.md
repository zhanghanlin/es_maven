es_maven
========
* 测试该框架需要有依赖服务  
	* elasticsearch-rtf：GitHub:[elasticsearch-rtf](https://github.com/medcl/elasticsearch-rtf/)  
	* 下载elasticsearch-rtf服务后需要将elasticsearch.yml配置文件替换为项目中的bak/elasticsearch.yml,具体修改请自行比对  
	* bak/elasticsearch.yml中需要将$RedisIP替换为配置的Reids的IP, $RedisPort需要替换为Redis的端口  
	* 启动elasticsearch后进入[http://localhost:9200/_plugin/rtf/](http://localhost:9200/_plugin/rtf/)  
	* 点击New Index ,名称为es_search

* Oracle  
	* 表p_product[id,name]  
	* 表p_brand[id,name]

* ojdbc14 Jar包  
	* 由于Oracle限制ojdbc14需要自行下载  
	* ojdbc14包路径/lib/ojdbc14-10.2.0.3.0.jar

* 启动脚本  
	* 在项目根目录执行：jettyTest.bat

* 初始化数据  
	* 根据DB初始化(需要依赖上述Oracle服务)  
		* 访问URL:http://localhost:8081/es_maven/init/init.jsp?ids=$ids&type=$type  
	* Test数据Init  
		* 访问URL:[http://localhost:8081/es_maven/init/test/init.jsp](http://localhost:8081/es_maven/init/test/init.jsp)

* 查看数据  
	* 访问URL:http://localhost:8081/es_maven/interface/get.jsp?id=$id&type=$type

* 自动完成示例  
	* 访问URL:[http://localhost:8081/es_maven/index.jsp](http://localhost:8081/es_maven/index.jsp)

* 需要注意问题  
	* 注入bean：esNode  
```xml
		<bean id="esNode" class="com.search.es.ElasticsearchNodeFactoryBean">
			<property name="configLocation" value="classpath:conf/elasticsearch.properties" />
		</bean>
```  
  
	* Java Bean使用该Bean  
```java
		@Autowired
		ElasticsearchNodeFactoryBean esNode;

		private Client esClient;
	
		public Client getEsClient() {
			if (esClient == null) {
				try {
					esClient = esNode.getObject().client();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return esClient;
		}
```  
  
	* JSP使用该Bean  
```java
		InternalNode node = (InternalNode)context.getBean("esNode");
		Client esclient = node.client();
```
