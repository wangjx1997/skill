package wjx.skill.mapper;


import org.springframework.stereotype.Repository;
import wjx.skill.model.ListedCompany;

@Repository
public interface TestMapper {
    int insert(ListedCompany listedCompany);
}