package cn.monitoring.factory.domain.vo;

import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.domain.EquipmentModule;
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
public class EquipmentVo {
    private EquipmentInfo equipmentInfo;
    private List<EquipmentModule> modules;
}
