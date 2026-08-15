# 残月列车PRO排位（ranked play）显示系统 - 客户端

Fabric 1.21.1 客户端模组，接收服务端排位结算推送并展示段位变化。

## 功能

- 接收服务端 S2C 排位结算推送（胜负、加减分、段位、升段保护）
- 动作栏即时反馈
- 零算分：所有排位计算在服务端完成，客户端纯展示
- 可选安装：不安装不影响服务端排位算分

## 构建

```bash
./gradlew :client:build
```

构建产物：`client/build/libs/habirank-client-0.1.0.jar`

## 依赖

- Minecraft 1.21.1
- Fabric Loader >= 0.16.0
- Fabric API
- Java 21+

## 安装

将构建好的 jar 文件放入 `.minecraft/mods/` 目录。

## 相关仓库

- 服务端模组：StarRailExpress-rankedplay-server

## License

GPL-3.0
