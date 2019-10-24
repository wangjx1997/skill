package wjx.skill;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import wjx.skill.mapper.SkillMapper;
import wjx.skill.mapper.TestMapper;
import wjx.skill.mapper.UserMapper;
import wjx.skill.mapper.UserRoleMapper;
import wjx.skill.model.ListedCompany;
import wjx.skill.model.Pro;
import wjx.skill.repository.EsSkillRepository;
import wjx.skill.util.ExcleUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(SkillApplicationTests.class);
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    private SkillMapper skillMapper;
    @Autowired
    private EsSkillRepository esSkillRepository;
    @Autowired
    private TransportClient client;
    @Autowired
    private TestMapper testMapper;
    @Before
    public void initData() {
        // 清空所有
        //esSkillRepository.deleteAll();
    }
    public static String  getID32(){
        return java.util.UUID.randomUUID().toString().replace("-","");
    }
    @Test
    public void contextLoads() {

        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("C:\\Users\\汪锦玺\\Desktop\\工作\\新项目\\线索1.0\\stock.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<ListedCompany> baseRowModels = ExcleUtil.readExcel(inputStream, ListedCompany.class);
        List<ListedCompany> listedCompanies = baseRowModels.stream()
                .filter(listedCompany -> !StringUtils.isEmpty(listedCompany.getStockType()))
                .collect(Collectors.toList());
        listedCompanies.forEach(listedCompany -> testMapper.insert(listedCompany));

//        System.out.println(listedCompanies);
//        String s = "edd73669cc5449a18408a75a1b7878f6";
//        List<Pro> pros = userRoleMapper.se(-1);
//        pros.forEach(pro -> {
//            pro.setProvince_number(getID32());
//            pro.setParent_number(s);
//            tree(pro);
//
//
//        });
//        userRoleMapper.ins(pros);


        //System.out.println(new PasswordHelper().encryptPassword("wjx_admin","123456"));
        //System.out.println(userMapper.findByUserName("wjx_admin"));
//        Integer blogId[] = {2,4,5,6,7,8};
//        for (Integer info : blogId) {
//            Skill returnBlog = skillMapper.getBlogById(info);
//            logger.info("数据库保存的文章-{}", JSONObject.toJSONString(returnBlog));
//            EsSkill esSkill = new EsSkill(returnBlog, userMapper.getUserById(returnBlog.getUser_id()));
//            esSkillRepository.save(esSkill);
//            EsSkill esSkill1 = esSkillRepository.findByBlogId(info);
//            logger.info("es保存的文章-{}", JSONObject.toJSONString(esSkill1));
//        }

//        EsSkill esSkill1 = esSkillRepository.findByBlogId(2);
//        System.out.println(JSONObject.toJSONString(esSkill1));
//
//
//        try {
//            UpdateResponse updateResponse = client.prepareUpdate("blog", "blog", esSkill1.getId()).setDoc(jsonBuilder().startObject()
//                    .field("voteSize", "200").endObject()).execute().get();
//            System.out.println(updateResponse);
//
//            GetResponse response = client.prepareGet("blog", "blog", esSkill1.getId()).execute().actionGet();
//            String string = JSONObject.toJSONString(response.getSource().get("voteSize"));
//            System.out.println(string);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void tree(Pro pro) {
        // 通过id获取下级
        List<Pro> ses = userRoleMapper.se(pro.getId());
        ses.forEach(se->{
            // 生成number
            se.setProvince_number(getID32());
            // 添加父number
            se.setParent_number(pro.getProvince_number());
            tree(se);
        });
        if (!CollectionUtils.isEmpty(ses)) {
            userRoleMapper.ins(ses);
        }
    }

}
