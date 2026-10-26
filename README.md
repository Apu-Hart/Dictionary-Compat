# Dictionary Compat

Minecraft 1.21.1 / NeoForge 的动物词典 ABI 独立兼容补丁。

## 维护与署名

修复与二次维护：[Apu-Hart](https://github.com/Apu-Hart)

本补丁为 Apu-Hart 独立编写的兼容层，不是 Alex’s Mobs 或 Citadel 本体的修改版。此次“二次维护”指补丁的独立构建与发布维护，不表示原模组由 Apu-Hart 创作。

Alex’s Mobs 原作者及移植维护者包括 Alexthe668、Carro1001、Paint_Ninja、Raguto；Citadel 署名包括 Alexthe666、Gegy1000。保留原有 LICENSE、NOTICE 及第三方署名，不替换原作者版权信息。

相关上游：

- https://github.com/Raguto/AlexsMobs-1.21.1
- https://github.com/Raguto/Citadel-1.21.1

## 范围

仅针对已审查的 Alex’s Mobs 二进制与 Citadel 2.7.6 组合，桥接动物词典目录方法的 ResourceLocation / String ABI 差异。未知文件指纹将明确拒绝，不提供任意版本通用兼容保证。

不包含第三方 JAR、资源、Quest Games 私有源码、测试探针、玩家文件或凭据。

## 构建

使用 Java 21 与 Gradle 8.12：

```text
gradle -p public-build --no-daemon clean build
```

## 许可证

本独立补丁采用 GPL-3.0-only，完整许可见 `source/dictionarycompat/LICENSE`，第三方告知见 `source/dictionarycompat/NOTICE.txt`。
