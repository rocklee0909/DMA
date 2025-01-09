package cn.monitoring.collection.domain;

import java.util.Date;

import cn.monitoring.job.api.domain.SysJob;
import cn.monitoring.system.api.domain.RemoteDataEventConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 事件配置对象 data_event_config
 * 
 * @author liru
 * @date 2025-01-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataEventConfig extends RemoteDataEventConfig
{
    private static final long serialVersionUID = 1L;

    /** 定时任务 */
    private SysJob sysJob;
}
