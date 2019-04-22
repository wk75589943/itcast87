package cn.itcast.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {
    //获取厂家销量统计数据
    List<Map> getFactoryData(String companyId);
    //获取销售排行（统计前15名）
    List<Map> getSellData(String companyId);
    //获取系统访问压力数据
    List<Map> getOnlineData(String companyId);
}
