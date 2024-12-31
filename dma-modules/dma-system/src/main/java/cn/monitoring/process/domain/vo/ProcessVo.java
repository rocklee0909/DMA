package cn.monitoring.process.domain.vo;

import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.domain.EquipmentModule;
import cn.monitoring.process.domain.ProcessFlow;
import cn.monitoring.process.domain.ProcessRecipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liru
 * @version 1.0
 * @date 2024/12/27 15:27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVo {
    private ProcessRecipe processRecipe;
    private List<ProcessFlow> flows;
}
