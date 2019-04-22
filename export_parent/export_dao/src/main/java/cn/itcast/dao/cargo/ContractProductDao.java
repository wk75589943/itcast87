package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.ContractProduct;
import cn.itcast.domain.cargo.ContractProductExample;
import cn.itcast.vo.ContractProductVo;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//购销合同货物
public interface ContractProductDao {

    //删除
    int deleteByPrimaryKey(String id);

    //保存
    int insertSelective(ContractProduct record);

    //条件查询
    List<ContractProduct> selectByExample(ContractProductExample example);

    //id查询
    ContractProduct selectByPrimaryKey(String id);

    //更新
    int updateByPrimaryKeySelective(ContractProduct record);

    //根据船期查询所有货物数据
    List<ContractProductVo> findByShipTime(@Param("companyId") String companyId, @Param("inputDate") String inputDate);
}