package wjx.skill.test;

import javax.validation.constraints.NotNull;

/**
 * Created by 旺旺 on 2018/8/29.
 */
public class Search {
    private String userId ;
    private String companyName;
    private String regCapital;
    private long regCapital_less;
    private long regCapital_more;
    private long regCapital_begin;
    private long regCapital_end;
    private String regDate;
    private String trade;
    private String companySize;
    private long companySizeMax;
    private long companySizeMin;

    public long getCompanySizeMax() {
        return companySizeMax;
    }

    public void setCompanySizeMax(long companySizeMax) {
        this.companySizeMax = companySizeMax;
    }

    public long getCompanySizeMin() {
        return companySizeMin;
    }

    public void setCompanySizeMin(long companySizeMin) {
        this.companySizeMin = companySizeMin;
    }

    @NotNull(message = "地区不能为空")
    private String province;
    private String city;
    private String dataSource;
    private Integer numberType;
    private String number;
    private String contractPerson;
    private Integer start;
    private Integer size;
    private Integer downSize;

    private String[] source;
    //private String source_str;

    private String[] o2o;
    //private String o2o_str;

    private String[] levels;
    //private String levels_str;


    public String[] getSource() {
        return source;
    }

    public void setSource(String[] source) {
        this.source = source;
    }

    public String[] getO2o() {
        return o2o;
    }

    public void setO2o(String[] o2o) {
        this.o2o = o2o;
    }

    public String[] getLevels() {
        return levels;
    }

    public void setLevels(String[] levels) {
        this.levels = levels;
    }

    public long getRegCapital_less() {
        return regCapital_less;
    }

    public void setRegCapital_less(long regCapital_less) {
        this.regCapital_less = regCapital_less;
    }

    public long getRegCapital_more() {
        return regCapital_more;
    }

    public void setRegCapital_more(long regCapital_more) {
        this.regCapital_more = regCapital_more;
    }

    public long getRegCapital_begin() {
        return regCapital_begin;
    }

    public void setRegCapital_begin(long regCapital_begin) {
        this.regCapital_begin = regCapital_begin;
    }

    public long getRegCapital_end() {
        return regCapital_end;
    }

    public void setRegCapital_end(long regCapital_end) {
        this.regCapital_end = regCapital_end;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        if (regCapital != null && !regCapital.isEmpty()) {
            if (regCapital.contains("-")){
                String[] split = regCapital.split("-");
                regCapital = split[0]+"万至"+split[1];
            }
            this.regCapital = regCapital;
        }
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        if(companySize.contains("以下")){
            companySize="0-19人";
        }
        this.companySize = companySize;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getNumberType() {
        return numberType;
    }

    public void setNumberType(Integer numberType) {
        this.numberType = numberType;
    }

    public Integer getDownSize() {
        return downSize;
    }

    public void setDownSize(Integer downSize) {
        this.downSize = downSize;
    }

    @Override
    public String toString() {
        return "Search{" +
                "userId='" + userId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", regCapital='" + regCapital + '\'' +
                ", regCapital_less=" + regCapital_less +
                ", regCapital_more=" + regCapital_more +
                ", regCapital_begin=" + regCapital_begin +
                ", regCapital_end=" + regCapital_end +
                ", regDate='" + regDate + '\'' +
                ", trade='" + trade + '\'' +
                ", companySize='" + companySize + '\'' +
                ", companySizeMax=" + companySizeMax +
                ", companySizeMin=" + companySizeMin +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", numberType=" + numberType +
                ", number='" + number + '\'' +
                ", contractPerson='" + contractPerson + '\'' +
                ", start=" + start +
                ", size=" + size +
                '}';
    }
}
