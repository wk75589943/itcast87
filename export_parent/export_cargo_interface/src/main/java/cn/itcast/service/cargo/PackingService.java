package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Entrust;
import cn.itcast.domain.cargo.EntrustExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;

public interface PackingService {
    //根据id查询
    Packing findById(String id);

    //保存
    void save(Packing packing);

    //更新
    void update(Packing packing);

    //删除
    void delete(String id);

    //发票分页查询
    public PageInfo findAll(PackingExample example, int page, int size);
}
