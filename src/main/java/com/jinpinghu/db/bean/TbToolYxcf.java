package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_tool_yxcf")
public class TbToolYxcf extends BaseZEntity  implements java.io.Serializable {
	private Integer id;
	private Integer toolId;
	private String effectiveIngredientsName;
	private String effectiveIngredientsValue;
	private String unit;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "tool_id")
	public Integer getToolId() {
		return toolId;
	}
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}
	@Column(name = "effective_ingredients_name")
	public String getEffectiveIngredientsName() {
		return effectiveIngredientsName;
	}
	public void setEffectiveIngredientsName(String effectiveIngredientsName) {
		this.effectiveIngredientsName = effectiveIngredientsName;
	}
	@Column(name = "effective_ingredients_value")
	public String getEffectiveIngredientsValue() {
		return effectiveIngredientsValue;
	}
	public void setEffectiveIngredientsValue(String effectiveIngredientsValue) {
		this.effectiveIngredientsValue = effectiveIngredientsValue;
	}
	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
