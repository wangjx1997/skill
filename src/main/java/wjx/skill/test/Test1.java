package wjx.skill.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.*;
import java.util.Map;

/**
 * Created by WangJX on 2019/1/14.
 */
public class Test1 {

    public void nioTest() {
        InputStream inputStream = getInputStream("D:\\data\\test.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    private InputStream getInputStream(String pathname) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(getFile(pathname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private File getFile(String pathname) {
        return new File(pathname);
    }

    private static Map<String, String> getMap(Search search) {
        Map<String, String> map = JSONObject.parseObject(JSONObject.toJSON(search).toString(), new TypeReference<Map<String, String>>() {
        });
        return map;
    }

    public static void main(String[] args) {
//        Search search = new Search();
//        String[] map = {"百度地图", "腾讯地图", "搜狗地图", "高德地图"};
//        search.setO2o(map);
//        search.setProvince("湖北省");
//        System.out.println(getMap(search));

//        BigDecimal bg = new BigDecimal("0.3");
//        for(int i=0;i<4-1;i++){
//            bg=bg.multiply(bg);
//        }
//        BigDecimal f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
//            /*Double l = new Double(Integer.parseInt(order_fee) * f1);*/
//        BigDecimal bigl=new BigDecimal("1").multiply(f1);
//        Double l=bigl.doubleValue();
//        System.out.println(l);
//        Map map = new HashMap();
//        map.put("id", "safs");
//        System.out.println(map.get("id"));
//        map.put("id", "ff");
//        System.out.println(map.get("id"));

        String s = "<div class=\\\"tab-pane\\\" id=\\\"tab_base\\\">\\n  <div class=\\\"row\\\">\\n    <div class=\\\"col-sm-6 \\\">\\n      <section class=\\\"panel panel-default\\\">\\n        <div class=\\\"panel-body\\\" data-id=\\\"51633745\\\" >\\n          \\n  <div class=\\\"bordb clearfix padb10 marb10\\\">\\n    <div class=\\\"auto\\\">\\n      <div class=\\\"lineh26 color3\\\">基本信息<\\/div>\\n      \\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    编号：\\n  <\\/div>\\n  <div class=\\\"value j-text_asset_6ced94  \\\" data-column=\\\"text_asset_6ced94\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    类 型：\\n  <\\/div>\\n  <div class=\\\"value j-text_asset_e311e7  \\\" data-column=\\\"text_asset_e311e7\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    客户名称：\\n  <\\/div>\\n  <div class=\\\"value j-name  \\\" data-column=\\\"name\\\" data-value-format=\\\"\\\">\\n    北京博科星通科技有限公司\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    姓名：\\n  <\\/div>\\n  <div class=\\\"value j-company_name  \\\" data-column=\\\"company_name\\\" data-value-format=\\\"\\\">\\n    朱海强\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    职位：\\n  <\\/div>\\n  <div class=\\\"value j-text_asset_82edad  \\\" data-column=\\\"text_asset_82edad\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n        \\n    <\\/div>\\n  <\\/div>\\n\\n\\n\\n\\n  <div class=\\\"bordb clearfix padb10 marb10\\\">\\n    <div class=\\\"auto\\\">\\n      <div class=\\\"lineh26 color3\\\">联系信息<\\/div>\\n      \\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    国家：\\n  <\\/div>\\n  <div class=\\\"value j-text_asset_b8d298  \\\" data-column=\\\"text_asset_b8d298\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_area_show\\\">\\n  <div class=\\\"name\\\">\\n    Email：\\n  <\\/div>\\n  <div class=\\\"value j-note  \\\" data-column=\\\"note\\\" data-value-format=\\\"text\\\">\\n    <p><\\/p>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_area_show\\\">\\n  <div class=\\\"name\\\">\\n    座机：\\n  <\\/div>\\n  <div class=\\\"value j-text_area_asset_6f7e19  \\\" data-column=\\\"text_area_asset_6f7e19\\\" data-value-format=\\\"\\\">\\n    <p><\\/p>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    QQ：\\n  <\\/div>\\n  <div class=\\\"value j-address.wangwang  \\\" data-column=\\\"address.wangwang\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    微信号：\\n  <\\/div>\\n  <div class=\\\"value j-address.wechat  \\\" data-column=\\\"address.wechat\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    Skype：\\n  <\\/div>\\n  <div class=\\\"value j-text_asset_38bf4c  \\\" data-column=\\\"text_asset_38bf4c\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit url_field_show\\\">\\n  <div class=\\\"name\\\">\\n    网址：\\n  <\\/div>\\n    <div class=\\\"value  \\\" data-column=\\\"address.url\\\" data-value-format=\\\"url\\\">\\n      --\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_area_show\\\">\\n  <div class=\\\"name\\\">\\n    其他联系方式：\\n  <\\/div>\\n  <div class=\\\"value j-text_area_asset_03e3ae  \\\" data-column=\\\"text_area_asset_03e3ae\\\" data-value-format=\\\"\\\">\\n    <p><\\/p>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_area_show\\\">\\n  <div class=\\\"name\\\">\\n    备注1：\\n  <\\/div>\\n  <div class=\\\"value j-text_area_asset_516a56  \\\" data-column=\\\"text_area_asset_516a56\\\" data-value-format=\\\"\\\">\\n    <p><\\/p>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit geo_address_field_show\\\">\\n  <div class=\\\"name\\\">\\n    地址：\\n  <\\/div>\\n  <div class=\\\"value  \\\" data-column=\\\"address.detail_address\\\" data-value-format=\\\"geo\\\">\\n\\n    <font><\\/font>\\n      <a target=\\\"_blank\\\" class=\\\"text-primary\\\" href=\\\"javascript:;\\\">查看地图<\\/a>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list quick_edit text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    传真：\\n  <\\/div>\\n  <div class=\\\"value j-address.fax  \\\" data-column=\\\"address.fax\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n    <\\/div>\\n  <\\/div>\\n\\n\\n\\n\\n  <div class=\\\"bordb clearfix padb10 marb10\\\">\\n    <div class=\\\"auto\\\">\\n      <div class=\\\"lineh26 color3\\\">其他信息<\\/div>\\n      \\n<div class=\\\"list quick_edit field_map_field_show\\\">\\n  <div class=\\\"name\\\">\\n    跟进状态：\\n  <\\/div>\\n  <div class=\\\"value j-status  \\\" data-column=\\\"status_mapped\\\" data-value-format=\\\"\\\">\\n    初访\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list  field_map_field_show\\\">\\n  <div class=\\\"name\\\">\\n    资料来源：\\n  <\\/div>\\n  <div class=\\\"value j-channel  \\\" data-column=\\\"channel_mapped\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n    <\\/div>\\n  <\\/div>\\n\\n\\n\\n\\n  <div class=\\\" clearfix padb10 marb10\\\">\\n    <div class=\\\"auto\\\">\\n      <div class=\\\"lineh26 color3\\\">系统信息<\\/div>\\n      \\n<div class=\\\"list  text_field_show\\\">\\n  <div class=\\\"name\\\">\\n    业务类型：\\n  <\\/div>\\n  <div class=\\\"value j-custom_field_template  \\\" data-column=\\\"custom_field_template.name\\\" data-value-format=\\\"\\\">\\n    --\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      负责人：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      良泉\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      创建人：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      良泉\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      前负责人：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      --\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      所属部门：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      恋企网络\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      前所属部门：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      --\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list select2_field_show\\\">\\n    <div class=\\\"name\\\">\\n      所属公海：\\n    <\\/div>\\n    <div class=\\\"value link_entity   \\\">\\n      --\\n    <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list  datetime_field_show\\\">\\n  <div class=\\\"name\\\">\\n    创建时间：\\n  <\\/div>\\n  <div class=\\\"value j-created_at  \\\" data-column=\\\"created_at\\\" data-value-format=\\\"time\\\">\\n    <time datetime=\\\"2019-07-30T19:51:29+08:00\\\">2019-07-30 19:51<\\/time>\\n  <\\/div>\\n<\\/div>\\n\\n<div class=\\\"list  datetime_field_show\\\">\\n  <div class=\\\"name\\\">\\n    更新于：\\n  <\\/div>\\n  <div class=\\\"value j-updated_at  \\\" data-column=\\\"updated_at\\\" data-value-format=\\\"time\\\">\\n    <time datetime=\\\"2019-08-01T17:18:42+08:00\\\">2019-08-01 17:18<\\/time>\\n  <\\/div>\\n<\\/div>\\n\\n    <\\/div>\\n  <\\/div>\\n\\n\\n\\n\\n        <\\/div>\\n      <\\/section>\\n    <\\/div>\\n\\n    <div class=\\\"col-sm-6 \\\">\\n    <\\/div>\\n  <\\/div>\\n  <div class=\\\"clearfix\\\"><\\/div>\\n<\\/div>";
        System.out.println(s.replace("\\",""));
    }
}
