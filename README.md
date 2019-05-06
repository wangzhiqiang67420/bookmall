# bookmall电商书城系统说明
- ## 目的
  - ### 在校学习，熟悉开发流程,。存在许多缺陷，比如购物车和订单不能分店铺统计和付款，界面较为简陋,另外还有许多地方的用户体验性也不好。
- ## 依赖环境
  - ### jdk1.8,maven,mysql(创建bookmall数据库后，运行resource目录下的sql脚本，记得在application.properties改数据库密码,登录系统的账号和密码  自行查看user表)
- ## 使用框架:
  - ### 后端是springboot+mybatis+shiro，前端界面使用bootstrap框架搭建  
- ## 系统划分:
  - ### 该系统分为前台展示和后台管理两大模块。  
  - ### 前台主要是为消费者服务。该子系统实现了注册，登录，以及从浏览、下单到支付的整个流程，支付使用的是支付宝的沙箱环境，属于模拟环境。  
  - ### 后台主要是为商家服务，实现了权限管理，店铺、商品和订单等的管理，以及生成一些简单的报表信息。访问/admin进入后台  
- ## 运行项目
  - ### 方法一：在ide(推荐idea)运行项目,配置好启动环境，直接运行main方法
  - ### 方法二: 在项目根目录下,运行以下maven命令  
    ```mvn spring-boot:run```
  - ### 方法三: 在ide或直接用maven打成的war包放到tomcat运行,具体操作可以百度，google
  - ### 方法四: 使用命令运行jar或war  
    ```java -jar xxx.jar```
  - ### 具体可以自行搜索spring-boot项目的启动方式