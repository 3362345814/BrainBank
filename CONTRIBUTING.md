# 代码提交规范（Conventional Commits）

## 提交格式

---

# 团队开发指南

欢迎加入本项目协作开发！请阅读以下指南，确保协作顺畅。

---

## 分支规范

| 分支名           | 说明                 |
|---------------|--------------------|
| main          | 主分支，保持稳定版本（不可直接开发） |
| dev           | 开发主线，所有功能合并到这里     |
| feature/xxx   | 新功能开发分支            |
| bugfix/xxx    | Bug 修复分支           |
| hotfix/xxx    | 紧急线上修复             |
| release/x.y.z | 预发布分支              |

---

## 开发流程

1. 从 `dev` 分支拉出自己的功能分支：

   ```bash
   git checkout dev
   git pull origin dev
   git checkout -b feature/功能名
   ```

2. 编码、测试后提交（请遵守提交规范）：

    ```bash
    git add .
    git commit -m "feat(user): 添加用户登录接口"
    git push origin feature/功能名
    ```

3. 到远程仓库提交 Pull Request：
    - 目标分支：dev
    - 标题写明模块 + 功能
    - 描述写清楚做了什么，是否有影响

4. 合并后删除本地/远程分支：
    ```bash
    git branch -d feature/功能名
    git push origin --delete feature/功能名
    ```

---

## 提交规范

请使用以下格式提交代码：
`<type>(<scope>): <message>`

- `<type>`：提交类型，如 feat、fix、docs、chore、refactor、style、test、perf
- `<scope>`：提交范围，如 user、order、admin
- `<message>`：提交描述，简洁明了

| 类型       | 描述                      |
|----------|-------------------------|
| feat     | 新功能（feature）            |
| fix      | 修复 bug                  |
| docs     | 文档变更                    |
| style    | 代码格式（不影响功能，例如空格、分号等）    |
| refactor | 代码重构（既不是新增功能，也不是修改 bug） |
| perf     | 性能优化                    |
| test     | 增加测试                    |
| chore    | 构建过程或辅助工具的变动            |
| revert   | 回滚到上一个版本                |
| build    | 打包                      |

