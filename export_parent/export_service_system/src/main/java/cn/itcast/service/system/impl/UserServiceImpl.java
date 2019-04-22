package cn.itcast.service.system.impl;

import cn.itcast.common.utils.Encrypt;
import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 分页：
     * 1.startPage
     * 2.查询全部列表
     * 3.构造pagebean
     */
    public PageInfo findAll(String companyId, int page, int size) {
        //1.调用startPage方法
        PageHelper.startPage(page, size);
        //2.查询全部列表
        List<User> list = userDao.findAll(companyId);
        //构造pagebean
        return new PageInfo(list);
    }

    //查询所有部门
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    //保存

    /**
     * 1.设置基础参数（id）
     * 2.保存
     *
     * @param user
     */
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(Encrypt.md5(user.getPassword(),user.getEmail()));
        userDao.save(user);
    }

    //更新
    public void update(User user) {
        userDao.update(user);
    }

    //删除
    public void delete(String id) {
        userDao.delete(id);
    }

    //根据id查询
    public User findById(String id) {
        return userDao.findById(id);
    }
    //根据邮箱账号查询用户对象
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * 分配角色
     *
     * @param userid  用户id
     * @param roleIds 分配的角色id数组
     */
    public void updateUserRoles(String userid, String[] roleIds) {
        //1.根据userId删除中间表数据
        userDao.deleteUserRole(userid);
        //2.循环角色id列表，向中间表中保存数据
        if(roleIds==null){
            return;
        }
        for (String roleId : roleIds) {
            userDao.saveUserRole(userid,roleId);
        }
    }

}
