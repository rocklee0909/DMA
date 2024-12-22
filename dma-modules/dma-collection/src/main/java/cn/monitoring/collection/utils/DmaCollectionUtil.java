package cn.monitoring.collection.utils;

public class DmaCollectionUtil {
    /**
     * 根据主题生成表名
     * @param dmaTopic
     * @return
     */
    public static String generateTableName(String dmaTopic) {
        // 这里根据具体的业务规则生成表名，示例简单将主题名转换为小写并替换一些特殊字符作为表名
        return dmaTopic.toLowerCase().replace(".", "_").replace("-", "_");
    }
}
