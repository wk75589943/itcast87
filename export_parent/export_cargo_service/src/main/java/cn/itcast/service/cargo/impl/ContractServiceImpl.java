package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractDao contractDao;

	@Override
	public Contract findById(String id) {
		return contractDao.selectByPrimaryKey(id);
	}

	/**
	 * 保存
	 */
	public void save(Contract contract) {
		//设置基本属性
		String id = UUID.randomUUID().toString();
		contract.setId(id);
		//合同总金额，状态
		contract.setTotalAmount(0.0);
		contract.setState(0); //0:草稿  ，1：已提交  2：已报运

		//
		contract.setExtNum(0);//附件数量
		contract.setProNum(0);//货物数量

		//保存时间
		contract.setCreateTime(new Date());

		contractDao.insertSelective(contract);
	}

	//更新
	public void update(Contract contract) {
		contractDao.updateByPrimaryKeySelective(contract);
	}

	//删除
	public void delete(String id) {
		contractDao.deleteByPrimaryKey(id);
	}

	//分页查询
	public PageInfo findAll(ContractExample example, int page, int size) {
		PageHelper.startPage(page,size);
		List<Contract> list = contractDao.selectByExample(example);
		return new PageInfo(list);
	}
}
