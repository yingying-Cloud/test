package com.jinpinghu.db.bean;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_enterprise_old_grade")
public class TbEnterpriseOldGrade extends BaseZEntity  implements java.io.Serializable {

    // Fields

    private Integer id;
    private String enterpriseId;
    private String source;
    private Date inputTime;
    private String capitalAbility;
    private String developmentAbility;
    private String corporateCredit;
    private String achievement;
    private String plantYield;
    private String technicalAbility;
    private Integer delFlag;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "enterprise_id")
    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @Column(name = "input_time")
    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }
    @Column(name = "capital_ability")
    public String getCapitalAbility() {
        return capitalAbility;
    }

    public void setCapitalAbility(String capitalAbility) {
        this.capitalAbility = capitalAbility;
    }
    @Column(name = "development_ability")
    public String getDevelopmentAbility() {
        return developmentAbility;
    }

    public void setDevelopmentAbility(String developmentAbility) {
        this.developmentAbility = developmentAbility;
    }
    @Column(name = "corporate_credit")
    public String getCorporateCredit() {
        return corporateCredit;
    }

    public void setCorporateCredit(String corporateCredit) {
        this.corporateCredit = corporateCredit;
    }
    @Column(name = "achievement")
    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
    @Column(name = "plant_yield")
    public String getPlantYield() {
        return plantYield;
    }

    public void setPlantYield(String plantYield) {
        this.plantYield = plantYield;
    }
    @Column(name = "technical_ability")
    public String getTechnicalAbility() {
        return technicalAbility;
    }

    public void setTechnicalAbility(String technicalAbility) {
        this.technicalAbility = technicalAbility;
    }
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
