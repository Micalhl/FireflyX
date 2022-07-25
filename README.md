![FireflyX.png](https://s2.loli.net/2022/07/25/mz3LBybCPiwfEMF.png)

# FireflyX

一个轻量级的基础插件, 功能目前不算多, 但是会慢慢开发出更多的功能. 

## 特色
- 多版本支持. 支持 `1.12-1.19` 的所有版本, 其他版本暂未测试.
- 模块化. 所有功能都可以在 `modules.yml` 中独立开启或关闭.
- 语言自定义. 可以配置多国语言, 且几乎所有提示信息都可以在语言文件中配置.

## 功能

- 注册登录系统, 跟 `Authme` 相似, 包括大小写 Bug 修复等.
- 对于 `1.13` 以下版本的发射器崩服 Bug 修复.
- 飞行功能.
- 家功能. 可设置多个家.
- 加入退出提示, 对于玩家退出时人数显示错误也有修补.
- 独立经济功能, 脱离 `EssentialsX` , 其他使用经济插件的插件例如 `QuickShop` 也可以正常使用.
- 查看在线玩家.
- 查看延迟
- 服务器主城功能.
- 随机传送, 可以自定义范围.
- 传送功能, 例如 `/tpa` 和 `/tpahere`.
- 查看服务器 TPS.

## 部分截图

![image.png](https://s2.loli.net/2022/07/25/tcBgYb76pnoDFO9.png)

## 获取

FireflyX 是免费的, 你可以通过以下步骤自行构建插件.

**Windows 平台**

```
gradlew.bat clean build
```

**macOS/Linux 平台**

```
./gradlew clean build
```

构建好的插件应该可以在 `./build/libs` 文件夹中找到.
