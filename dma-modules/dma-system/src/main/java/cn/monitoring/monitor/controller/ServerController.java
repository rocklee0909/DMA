package cn.monitoring.monitor.controller;

import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.security.annotation.RequiresPermissions;
import cn.monitoring.monitor.domain.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 * 
 * @author liru
 */
@RestController
@RequestMapping("/server")
public class ServerController
{
    @RequiresPermissions("monitor:server:list")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
