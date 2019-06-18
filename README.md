将多语excel转换为前端所需多语js文件
---------------------

**参数：**

 - -i 必须，输入的EXCEL文件，必须为2003格式 比如-i d:\lang.xls
 - -o 必须，指定JS文件输出目录 比如 -o d:\js
 - -t 非必须，指定模版文件	-t d:\lang.html
 - -suffix 非必须 指定输出文件的后缀，比如js properties等，示例 -suffix js :输出文件后缀为js
 - -prefix 非必须 指定输出文件的后缀，比如为了匹配spring后台多语，文件一般以messages_打头，示例 -prefix messages_ :输出文件后缀为messages_en

如果没有指定文件，则程序会调用内部默认模版进行JS输出  
模版采用freemarker语法  
三个参数中，如果没有指定全路径，则默认是取程序当前路径作为根目录  
用法示例  


`convert -i d:\lang.xls -o d:\js  `

`convert -i d:\lang.xls -o js  `

`convert -i d:\lang.xls -o js -t template.html  `

`convert -i d:\lang.xls -o js -t c:\template.html`


输出的JS文件名为EXCEL第一行的内容，比如,EXCEL文档结构如下


 索引名  | zh_cn  | zh_hk | en   
 ---- | ----- | ------   | ------  
 title  | 标题 | 标题 | title 
 
 则工具会生成zh_cn.js  zh_hk.js en.js 三个多语文件
