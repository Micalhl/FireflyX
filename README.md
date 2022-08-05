<div align="center">
<a href="https://sm.ms/image/kFzO3neLlJjU96u" target="_blank"><img src="https://s2.loli.net/2022/07/26/kFzO3neLlJjU96u.png" ></a>

----

FireflyX 是一个轻量的基础插件, 功能目前不算多, 但是会慢慢开发出更多的功能.
</div>

## 特色

- 多版本支持. 支持 `1.12-1.19` 的所有版本, 其他版本暂未测试.
- 模块化. 所有功能都可以在 `modules.yml` 中独立开启或关闭.
- 语言自定义. 可以配置多国语言, 且几乎所有提示信息都可以在语言文件中配置.

## 功能

- 艾特功能.
- 注册登录系统, 跟 `Authme` 相似, 包括大小写 Bug 修复等.
- 返回功能, 传送后使用 `/back` 返回.
- 对于 `1.13` 以下版本的发射器崩服 Bug 修复.
- 飞行功能.
- 家功能. 可设置多个家.
- 加入退出提示, 对于玩家退出时人数显示错误也有修补.
- 独立经济功能, 脱离 `EssentialsX` , 其他使用经济插件的插件例如 `QuickShop` 也可以正常使用.
- 加入服务器后的提示信息, 类似于 Ess 的 `motd.txt`.
- 一键售卖物品, 指定价格, 适用于菜单. 同时提供 `Kether` 公有动作: `sell item "STONE" price 10.0`, 返回一共消费多少钱.
- 查看在线玩家.
- 查看延迟
- 服务器主城功能.
- 安全延时关闭服务器功能.
- 随机传送, 可以自定义范围.
- 传送功能, 例如 `/tpa` 和 `/tpahere`.
- 查看服务器 TPS.

## 部分截图

![image.png](https://s2.loli.net/2022/07/25/tcBgYb76pnoDFO9.png)
![iShot_2022-07-31_13.12.09.png](https://s2.loli.net/2022/07/31/KYgi9cfPFvGbECw.png)
![iShot_2022-07-31_13.16.44.png](https://s2.loli.net/2022/07/31/wZWzUo8t3SFhDMe.png)
![iShot_2022-07-31_13.16.53.png](https://s2.loli.net/2022/07/31/2vewOtikchqPS3A.png)
![iShot_2022-07-31_13.17.17.png](https://s2.loli.net/2022/07/31/edkgVfn1FSP46XD.png)
![iShot_2022-07-31_13.17.45.png](https://s2.loli.net/2022/07/31/NFCfsgkjVqI5iXW.png)
![iShot_2022-07-31_13.18.22.png](https://s2.loli.net/2022/07/31/w5ptsZnCe9FNVbT.png)

## 获取

开源地址: https://github.com/Micalhl/FireflyX

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
