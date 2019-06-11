package com.oliver.controller;

import com.oliver.bean.vo.Result;
import com.oliver.cache.HostCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.oliver.controller SsoClientListController
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/10 13:49
 */
@RestController
public class SsoClientListController {

    @Resource
    private HostCache hostCache;

    @GetMapping("clients")
    public Result<List> getClientList() {
        return hostCache.getClientList();
    }

    @GetMapping("cache")
    public void refresh() {
        hostCache.initCache();
    }
}
