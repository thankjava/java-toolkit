![license](https://img.shields.io/badge/license-Apache2.0-100000.svg)
![version](https://img.shields.io/maven-central/v/com.thankjava.toolkit/fast-toolkit)

> #### 介绍
- JDK >= 1.8 (本软件基于1.8, 第三方依赖根据其所需JDK版本而定)

    ```
    这是一个用于快速开发的支持依赖库，用于封装常用的功能，在不依赖大型框架体上用于进行快速开发或者测试
      
    toolkit 完全基于jdk提供函数
      
    toolkit3d封装了常用的其他工具函数，提供更加简洁的api函数
    ```

---
- 2.0.1 此次变更内容 
    
    - BeanCopierUtil 调整了识别List<?> 泛型的方式
    - 新增ImageCommonUtil 简单JPEG图片压缩Api
    - TimeUtil 支持自定义日期格式
    - 取消了FastJson默认格式化美化输出时展示null节点
    - mysql的推荐驱动由 5.x -> 8.x
    
- [ChangeLog](https://github.com/lazy-koala/java-toolkit/blob/master/doc/ChangeLog.md)

- [API List](https://github.com/lazy-koala/java-toolkit/blob/master/doc/API.md)

---
- Maven 获取

  ```xml
  <dependency>
    <groupId>com.thankjava.toolkit</groupId>
    <artifactId>fast-toolkit</artifactId>
    <version>2.0.1</version>
  </dependency>
  ```

  ```xml
  <dependency>
    <groupId>com.thankjava.toolkit3d</groupId>
    <artifactId>fast-toolkit3d</artifactId>
    <version>2.0.1</version>
  </dependency>
  ```
  
---
> #### 关于我们

[![org](https://img.shields.io/badge/org-@LazyKoala-yellow.svg)](https://github.com/lazy-koala/)
[![author](https://img.shields.io/badge/author-@acexy-blue.svg)](https://github.com/acexy/)

