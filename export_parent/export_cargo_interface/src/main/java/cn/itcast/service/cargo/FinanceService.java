package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import com.github.pagehelper.PageInfo;

public interface FinanceService {
    //根据id查询
    Finance findById(String id);

    //保存
    void save(Finance finance);

    //更新
    void update(Finance finance);

    //删除
    void delete(String id);

    //发票分页查询
    public PageInfo findAll(FinanceExample example, int page, int size);
}
