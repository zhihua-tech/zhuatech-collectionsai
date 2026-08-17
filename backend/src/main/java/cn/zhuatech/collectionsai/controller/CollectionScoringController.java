/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.collectionsai.controller;

import cn.zhuatech.collectionsai.common.ApiResponse;
import cn.zhuatech.collectionsai.service.CollectionScoringService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/collections")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class CollectionScoringController {
    private final CollectionScoringService service;
    public CollectionScoringController(CollectionScoringService service) { this.service = service; }
    @PostMapping("/score")
    public ApiResponse<CollectionScoringService.Result> score(@Valid @RequestBody CollectionScoringService.Request request) {
        return ApiResponse.ok("催收优先级已生成", service.score(request));
    }
}
