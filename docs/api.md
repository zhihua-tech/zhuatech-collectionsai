# CollectionsAI API

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/admin/dashboard` | 应收回款运营中心 |
| GET | `/api/admin/work-orders` | 催收任务列表 |
| GET | `/api/shopfloor/dashboard` | 催收专员工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交沟通结果 |
| POST | `/api/ai/collections/score` | 生成风险分层与催收策略 |

评分接口输入发票、逾期天数、金额、失约次数、最近联系、争议与战略客户标记；输出风险、策略、触达时限、审批要求和建议动作。
