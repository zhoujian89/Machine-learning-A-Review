Machine-learning-A-Review
=========================
###　　　　　　　　　　　　Author:ZhouJian
###　　　　　　　　　 E-mail:jianzhou@tju.edu.cn
##<a name="index"/>目录
* [Distribution](#distribution)
* [Bayesian](#title)
* [Regression](#text)
* [Classification](#Classification)
    * [线性判别分析(LDA)](#LDA)
    * [朴素贝叶斯(Naive Bayes)](#LDA)
    * [多行文本](#LDA)
    * [文字高亮](#LDA)
* [Cluster](#text)

##<a name="distribution"/>Distribution
###伯努利分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/Ber.jpg)
###二项分布
二项分布是伯努利多次实验的结果

![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/二项分布.jpg)
###Beta分布
Gamma函数可把很多数学概念从整数集合延拓到实数集合,Beta分布就是二项分布推广成实数域上

![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/Beta.jpg)
###多项式分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/多项分布.jpg)
###狄拉克雷分布
狄拉克雷分分布就是多项分布推广成实数域上

![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/di1.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/di2.jpg)
###Gamma分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/Gamma.jpg)
###高斯分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/高斯1.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/高斯2.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/高斯3.jpg)
###高斯-Gamma分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/高斯-Gamma.jpg)
###高斯-Wishart分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/高斯-Wis.jpg)
###学生t分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/t1.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/t2.jpg)
###均匀分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/均匀分布.jpg)
###Wishart分布
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/Wis.jpg)

##<a name="Classification"/>Classification
##<a name="LDA"/>线性判别分析(LDA)
线性判别分析(LDA)主要思想学习一个投影W把高维数据降到低维，这一维能很好地判断数据属于哪一类。当 x是二维的，要找一条直线（方向为 W）来做投影，然后寻找最能使样本点分离的直线，如图所示：
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher.jpg)

![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher1.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher3.jpg)

直接最大化J(w)，并不一定能找到合适的W。如上图所示，两类样本点均匀分布在两个椭圆里，向量u1-u2
表示两椭圆中心的连线方向，当投影到横轴 x1 上时能够获得更大的中心点间距J(w),但是由于有重叠,x1 不能分离样本点。投影到纵轴x2上,虽然J(w)较小，但能够分离样本点。因此我们还需要考虑不同类别样本点
之间的方差，要使得投影后，同一类样本点方差越小。
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher4.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher5.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher6.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher7.jpg)
![](https://github.com/zhoujian89/Machine-learning-A-Review/blob/master/Image/fisher8.jpg)


直接回车不能换行，<br>
可以使用\<br>。
但是使用html标签就丧失了markdown的意义。  
可以在上一行文本后面补两个空格，  
这样下一行的文本就换行了。

或者就是在两行文本直接加一个空行。

也能实现换行效果，不过这个行间距有点大dian。


