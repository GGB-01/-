# Day2
## 1.什么是JavaScript
![alt text](../图片/Day2_1.png)
![alt text](../图片/Day2_2.jpg)
## 2.js引入方式
![alt text](../图片/Day2_3.png)
## 3.JS基础语法
#### 一，书写语法
![alt text](../图片/Day2_4.png)
#### 二，输出语句
![alt text](../图片/Day2_5.png)
#### 三，变量
![alt text](../图片/Day2_6.png)

##### var 定义变量
- 特点1：属于全局变量
- 特点2：可以重复定义
~~~~

        {
        var a=10;
        a="张三";
        a="李四";
        alert(a);
         //var 定义变量
        //特点1：属于全局变量
        //特点2：可以重复定义
        
        }

~~~~
##### 注意事项
![alt text](../图片/Day2_7.png)
~~~~

        {
            let x=1;
            //let x=2; 不可重复定义报错
        }
       // alert(x);x为局部变量，无法弹出

       {
        const pi=3.14;
        //pi=3.15;const为常量 无法改变
        alert(pi);
       }

~~~~



