package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Entrust;
import cn.itcast.domain.cargo.EntrustExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface EntrustService {
    //根据id查询
    Entrust findById(String id);

    //保存
    void save(Entrust entrust);

    //更新
    void update(Entrust entrust);

    //删除
    void delete(String id);

    //发票分页查询
    public PageInfo findAll(EntrustExample example, int page, int size);
}
