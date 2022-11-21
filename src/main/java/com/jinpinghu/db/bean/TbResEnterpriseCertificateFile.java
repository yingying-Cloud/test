package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_enterprise_certificate_file")
public class TbResEnterpriseCertificateFile extends BaseZEntity implements java.io.Serializable {

	// Fields

		private Integer id;
		private Integer enterpriseCertificateId;
		private Integer fileId;
		private Integer delFlag;

		// Constructors

		/** default constructor */
		public TbResEnterpriseCertificateFile() {
		}

		/** full constructor */
		public TbResEnterpriseCertificateFile(Integer enterpriseCertificateId, Integer fileId, Integer delFlag) {
			this.setEnterpriseCertificateId(enterpriseCertificateId);
			this.fileId = fileId;
			this.delFlag = delFlag;
		}

		// Property accessors
		@Id
		@GeneratedValue(strategy = IDENTITY)

		@Column(name = "id", unique = true, nullable = false)

		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Column(name = "file_id")

		public Integer getFileId() {
			return this.fileId;
		}

		public void setFileId(Integer fileId) {
			this.fileId = fileId;
		}

		@Column(name = "del_flag", nullable = false)

		public Integer getDelFlag() {
			return this.delFlag;
		}

		public void setDelFlag(Integer delFlag) {
			this.delFlag = delFlag;
		}
		@Column(name = "enterprise_certificate_id")

		public Integer getEnterpriseCertificateId() {
			return enterpriseCertificateId;
		}

		public void setEnterpriseCertificateId(Integer enterpriseCertificateId) {
			this.enterpriseCertificateId = enterpriseCertificateId;
		}
}
