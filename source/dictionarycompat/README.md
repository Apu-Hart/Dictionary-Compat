# 动物词典 ABI 独立兼容补丁（本地候选）

作者 Apu-Hart。仅供本次隔离验证，不代表上线或分发授权。

保留原 Alex's Mobs 和 Citadel 文件，新增客户端 String 返回描述符，通过 Invoker 调用原 ResourceLocation 返回描述符，再使用真实资源路径的 toString。不是第三方改包，不包含第三方类或资源，不改变实体、孢子囊、注册表或存档格式。

## 已审查组合

- Minecraft 1.21.1、Java 21、NeoForge 21.1.x。
- 本地 Alex's Mobs 1.22.17：SHA-256 `60ad623ae00f4d786e2290e9c1294607e37d69bd2c66175fdb15c0b2671e694e`。
- Citadel 2.7.6：SHA-256 `9e12468c49e5a95b7adbf22b3b4d05bc55565989b89c40b985cd73bdfe63c3c2`。
- 发布者原包 Alex's Mobs SHA-256 `6e502855f79e4c9f2a11d560a9b88a3ab295aa378c44b0f0a0dd95f95d0301a6` 已经兼容，插件明确不变换。

其他构建明确拒绝，不应强行修改指纹常量来安装。升级任何依赖后须重新审查，优先移除不再需要的候选补丁。

## 构建

本项目使用纯 Java Gradle 插件，无全局初始化脚本修改。需要已生成的 Quest Games 开发依赖缓存（clientLegacyClasspath.txt 和 neoforge-21.1.213-merged.jar），编译 Minecraft 公共 ResourceLocation 类型及 NeoForge/Mixin API；真实测试运行时另核查为 21.1.248/21.1.251。

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-21.0.11'
& '<Quest Games 工程>\gradlew.bat' -p '<本源码目录>' '-PquestProject=<Quest Games 工程>' --gradle-user-home '<隔离 Gradle 缓存>' --offline --no-daemon --console=plain clean build
```

`abiCheck` 七个正负例不等同于 GUI 测试。实际转换类导出与 Citadel 调用点的游戏内链接/渲染证据在 lab/reports/evidence 和独立客户端日志中。两次 clean build 的 JAR SHA-256 相同。

## 安装与回滚边界

仅在 lab 测试实例 mods 添加本候选，不改原 JAR。本次也在独立服务端安装以验证客户端 Mixin 不被加载；补丁没有新增服务端玩法、网络包或持久化数据。

游戏正常退出后，从隔离实例 mods 移出 `dictionarycompat-0.1.0-candidate.1.jar` 即回滚补丁代码。原依赖不变，所以回滚将恢复原本的词典 ABI 故障。测试存档的完整回滚使用经过 SHA/CRC 核验的 backups ZIP，先另存当前实验结果，恢复到新的隔离目录；不要覆盖正式世界。

`dictionaryprobe`、`dictionaryworldprobe`、`questgamesprobe` 都是 QA 探针，不是修复文件，禁止部署正式服或分发给玩家。

完整许可证在 LICENSE，来源和第三方署名在 NOTICE.txt。自身源码按 GPL-3.0-only 提供。未包含第三方修改版；第三方资源的再分发义务未被此补丁许可证替代。
