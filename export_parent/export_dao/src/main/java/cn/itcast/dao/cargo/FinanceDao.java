package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;

import java.util.List;

public interface FinanceDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String financeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    int insert(Finance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    int insertSelective(Finance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    List<Finance> selectByExample(FinanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    Finance selectByPrimaryKey(String financeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Finance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_finance
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Finance record);
}