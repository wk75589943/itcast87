package cn.itcast.service.stat;

import java.util.List;

public interface StatService {
    //获取厂家销量统计数据
    List getFactoryData(String companyId);
    //获取销售排行（统计前15名）
    List getSellData(String companyId);
    //获取系统访问压力数据
    List getOnlineData(String companyId);
}
