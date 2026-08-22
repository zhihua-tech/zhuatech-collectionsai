/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.collectionsai;
import cn.zhuatech.collectionsai.service.CollectionScoringService; import org.junit.jupiter.api.Test; import java.math.BigDecimal; import static org.assertj.core.api.Assertions.assertThat;
class CollectionScoringServiceTests { private final CollectionScoringService service=new CollectionScoringService();
 @Test void routesDisputeToManualResolution(){var result=service.score(new CollectionScoringService.Request("INV-9",65,new BigDecimal("260000"),2,20,true,false));assertThat(result.strategy()).isEqualTo("DISPUTE_RESOLUTION");assertThat(result.humanApprovalRequired()).isTrue();}
 @Test void keepsNewInvoiceFriendly(){var result=service.score(new CollectionScoringService.Request("INV-10",3,new BigDecimal("8000"),0,2,false,false));assertThat(result.strategy()).isEqualTo("FRIENDLY_REMINDER");}}
