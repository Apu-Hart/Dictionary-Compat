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

首次构建需要访问公开依赖仓库。`public-build` 不依赖私有 Quest Games 工程；`source` 中原有本机缓存构建脚本仅作为历史记录保留。

产物：`public-build/build/libs/dictionarycompat-0.1.0-candidate.1.jar`。

已有隔离依赖缓存下实际构建与已发布二进制 SHA-256 一致：

```text
7a3b191c6567e46fc82b3d330cb01a9c8115620bc7085af8bdee177b522dae52
```

七项策略检查通过；游戏运行验证不等同于这些策略检查。代码注释检查没有发现新增普通解释性注释，不删除许可证、版权或必要署名。

## 许可证

本独立补丁采用 GPL-3.0-only，完整许可见 `source/dictionarycompat/LICENSE`，第三方告知见 `source/dictionarycompat/NOTICE.txt`。
