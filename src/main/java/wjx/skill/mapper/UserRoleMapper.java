package wjx.skill.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.model.Pro;
import wjx.skill.model.UserRole;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int insert(UserRole record);

    void update(UserRole userRole);

    void remove(@Param("userId") Integer userId);
    //int insertSelective(UserRole record);


    List<Pro> se(Integer id);

    void ins(@Param("pro") List<Pro> pro);
}