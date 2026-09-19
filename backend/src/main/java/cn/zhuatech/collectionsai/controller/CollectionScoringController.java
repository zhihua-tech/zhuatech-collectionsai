/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.collectionsai.controller;

import cn.zhuatech.collectionsai.common.ApiResponse;
import cn.zhuatech.collectionsai.service.CollectionScoringService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/ai/collections")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class CollectionScoringController {
    private final CollectionScoringService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CollectionScoringController(CollectionScoringService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/score")
    public ApiResponse<CollectionScoringService.Result> score(@Valid @RequestBody CollectionScoringService.Request request) {
        return ApiResponse.ok("催收优先级已生成", service.score(request));
    }
}
