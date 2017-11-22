# CrackCaptcahLogin


由“修改小米范验证码登陆爆破工具”修改而来，介绍和使用方法可以参考原作者博客（http://www.cnblogs.com/SEC-fsq/p/5712792.html）。

修改后工具截图
![修改后工具截图](https://fuping.site/images/CrackCaptcahLogin/2.jpg)
## 功能结构对比
与原版相比，有以下两个优点：
1.	增加本地打码功能接口，可以自行添加验证码识别的方法。这样针对简单的验证码，不必使用云打码，使用自己的验证码识别功能即可。
2.	破解jxbrowser组件，重新整合代码，使得二次开发更方便。


本地验证码识别可以调用YzmToText.getCode()方法来实现。
![本地验证码识别](https://fuping.site/images/CrackCaptcahLogin/新接口.jpg)

针对其他验证码的识别，修改YzmToText中的方法即可。

## 使用方法

#### 1.tesseract-ocr下载安装
此步骤非必须，如果使用tess4j识别验证码的话需要安装该文件。本次验证码识别采用了tess4j，所以需要安装该文件。
下载地址：https://sourceforge.net/projects/tesseract-ocr-alt/files/
![错误](https://fuping.site/images/CrackCaptcahLogin/error1.jpg)
如果识别验证码时提示如上错误，则需安装VC ++ 2015依赖库
下载地址：https://www.microsoft.com/en-us/download/details.aspx?id=48145

#### 2.下载代码并打包jar文件（或者直接下载编译好的jar文件）
代码下载地址：https://github.com/fupinglee/CrackCaptcahLogin
打包的jar文件：https://github.com/fupinglee/CrackCaptcahLogin/releases

#### 3.在jar同级目录下创建文件夹
创建tmp文件夹（用来存放下载的验证码）、字典目录dict（username.txt和password.txt）、tessdata目录（训练识别验证码的库）。
其结构如下：
![目录结构](https://fuping.site/images/CrackCaptcahLogin/5.jpg)

####  4.工具使用
使用命令`java -jar CrackCaptcahLogin.jar`打开工具，输入url，根据id、name、class属性识别用户名、密码、验证码输入框、及登录按钮。
动态效果图如下所示：
![使用效果](https://fuping.site/images/CrackCaptcahLogin/jobs.gif)

## FAQ
#### 1.验证码不同步
刚开始新增本地识别接口的时候，遇到有验证码不同步的情况。当时处理验证码是另外写了一个下载验证码的步骤，然后识别后提交，结果发现验证码一直不正确。以为是cookie的原因，就想着在获取验证码的同时将返回的cookie再置入浏览器，来达到同步的效果。结果还是不行。然后在使用云打码的时候发现验证码都是正确的，就看了一下验证码是怎样获取的。
![验证码获取](https://fuping.site/images/CrackCaptcahLogin/7.jpg)

结果发现多走了许多弯路，验证码数据内容已经返回了，直接将该数据流保存即可获取验证码图片。

#### 2.本地验证码无法识别
由网站下载下来的验证码，直接使用tesseract可以识别，但是使用程序就无法识别了，即使设置了识别库也不行。然后发现经过处理后可以识别。如下：

![验证码识别测试](https://fuping.site/images/CrackCaptcahLogin/8.jpg)
处理前后验证码对比：
![验证码处理前后对比](https://fuping.site/images/CrackCaptcahLogin/10.jpg)
## 总结
这个识别验证码也不是万能的，无论识别简单的还是复杂的验证码还需要自己修改，不过只需修改YzmToText.java中的代码即可。这里只给出了一个示例而已。
原版可以去[博客](http://www.cnblogs.com/SEC-fsq/p/5712792.html)查看（再次强调），不管修改的还是原版的，功能可能还不完善，代码已放在GitHub，可以自行修改。简单的我已经完成了，困难的交给你们。








