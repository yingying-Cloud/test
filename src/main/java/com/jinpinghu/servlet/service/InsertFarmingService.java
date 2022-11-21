package com.jinpinghu.servlet.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbCropFarming;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResCropFarmingFile;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.bean.TbResCropFarmingToolFile;
import com.jinpinghu.db.bean.TbResCropFarmingWater;
import com.jinpinghu.db.bean.TbResCropFarmingWaterFile;
import com.jinpinghu.db.bean.TbResCropFarmingWork;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbFileDao;

import fw.jbiz.ZObject;
import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;

public class InsertFarmingService extends ZObject {

	public static TimerTask task;
	
	public static void start() {
		task = new TimerTask() {
			@Override
			public void run() {
				EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
				
				TbCropFarmingDao cropFarmingDao = new TbCropFarmingDao(em);
				TbFileDao fileDao = new TbFileDao(em);
				//农智云中的农事记录
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer queryFarmingListString = new StringBuffer();
					queryFarmingListString.append(" SELECT trcff.id, group_concat(distinct trcffgp.work_id),1,CONCAT(IFNULL(trcff.farming_name,''), ");
					queryFarmingListString.append(" if(describe_ is not null and describe_ <> '',':',''),IFNULL(describe_,'')), ");
					queryFarmingListString.append(" tu.nickname,trcff.input_time,0 FROM nongzhiyun.tb_res_crop_farming_farming trcff  ");
					queryFarmingListString.append(" left join nongzhiyun.tb_res_crop_farming_farming_greenhouses_plant trcffgp ");
					queryFarmingListString.append(" on trcff.id = trcffgp.res_crop_farming_farming_id left join nongzhiyun.tb_crop_farming tcf ");
					queryFarmingListString.append(" on tcf.id = trcff.crop_farming_id left join nongzhiyun.tb_user tu on tcf.user_tab_id = tu.id ");
					queryFarmingListString.append(" where trcff.del_flag = 0 and trcff.input_time not in (select input_time from jinpinghu.tb_crop_farming) ");
					queryFarmingListString.append(" group by trcff.id order by trcff.input_time desc ");
					List<Object[]> farmingList = em.createNativeQuery(queryFarmingListString.toString()).getResultList();
					if (farmingList != null) {
						for(int i=0;i<farmingList.size();i++) {
							Object[] farming = farmingList.get(i);
							TbCropFarming cropFarming = new TbCropFarming(null,Integer.valueOf(farming[2].toString()),(String)farming[3], (String)farming[4],  (Date)farming[5],0);
							cropFarmingDao.save(cropFarming);
							String workIds = (String) farming[1];
							if(!StringUtils.isEmpty(workIds)) {
								String[] workIdArray = workIds.split(",");
								for (int j = 0; j < workIdArray.length; j++) {
									TbResCropFarmingWork resCropFarmingWork = new TbResCropFarmingWork();
									resCropFarmingWork.setCropFarmingId(cropFarming.getId());
									resCropFarmingWork.setWorkId(Integer.valueOf(workIdArray[j]));
									cropFarmingDao.save(resCropFarmingWork);
								}
							}
							List<Object[]> farmingFileList = em.createNativeQuery("SELECT tf.file_name,tf.file_size,tf.file_type,tf.file_url FROM nongzhiyun.tb_res_crop_farming_farming_file trcfff left join nongzhiyun.tb_file tf on trcfff.file_id = tf.id where trcfff.res_crop_farming_farming_id = "+farming[0]).getResultList();
							for(int j=0;j<farmingFileList.size();j++) {
								Object[] farmingFile = farmingFileList.get(j);
								TbFile file = new TbFile((String)farmingFile[0], (String)farmingFile[1], (Integer)farmingFile[2],(String)farmingFile[3]);
								fileDao.save(file);
								TbResCropFarmingFile resCropFarmingFile = new TbResCropFarmingFile(cropFarming.getId(), file.getId(), 0);
								fileDao.save(resCropFarmingFile);
							}
						}
					}
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				//增加农智云中的农资
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer insertToolString = new StringBuffer();
					insertToolString.append(" INSERT INTO jinpinghu.tb_tool(enterprise_id,name,specification,unit ")
					.append(" ,type,uniform_price,del_flag,code) select 1,tt.tool_name,jttc.specification,tt.unit,case when tt.tool_type = 1 then 12 ")
					.append(" when tt.tool_type = 2 then 13 when tt.tool_type = 3 then 14 when tt.tool_type = 4 then 15 end, ")
					.append(" '2',0,jttc.code from nongzhiyun.tb_res_crop_farming_tool_tool trcftt left join nongzhiyun.tb_res_crop_farming_tool trcft ")
					.append(" on trcftt.res_crop_farming_tool_id = trcft.id left join nongzhiyun.tb_crop_farming tcf on tcf.id = trcftt.crop_farming_id ")
					.append(" left join nongzhiyun.tb_user tu on tcf.user_tab_id = tu.id left join nongzhiyun.tb_res_crop_farming_tool_greenhouses_plant")
					.append(" trcftgp on trcftgp.res_crop_farming_tool_id = trcft.id left join nongzhiyun.tb_tool tt on trcftt.tool_id = tt.id ")
					.append(" left join jinpinghu.tb_tool jtt on tt.tool_name = jtt.name and jtt.enterprise_id = 1  ")
					.append(" left join jinpinghu.tb_tool_catalog jttc on jttc.name = tt.tool_name where trcft.del_flag = 0 ")
					.append(" and trcftt.del_flag = 0 and tcf.del_flag = 0 and  trcftgp.work_id is not null and trcft.input_time ")
					.append(" not in (select input_time from jinpinghu.tb_res_crop_farming_tool) and jtt.id is null group by tt.id ");
					em.createNativeQuery(insertToolString.toString()).executeUpdate();
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				//农智云中的农资记录
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer queryFarmingToolString = new StringBuffer();
					queryFarmingToolString.append(" select trcft.id trcftId, trcftgp.work_id,1,tu.nickname,jtt.id jttId,trcftt.tool_name,tt.spec,tt.unit,CAST(round(sum(if(trcftt.tool_num is null or trcftt.tool_num = '',0,trcftt.tool_num)),2) as char), ")
					.append(" trcft.input_time,trcft.del_flag from nongzhiyun.tb_res_crop_farming_tool_tool trcftt  ")
					.append(" left join nongzhiyun.tb_res_crop_farming_tool trcft on trcftt.res_crop_farming_tool_id = trcft.id ")
					.append(" left join nongzhiyun.tb_crop_farming tcf on tcf.id = trcftt.crop_farming_id ")
					.append(" left join nongzhiyun.tb_user tu on tcf.user_tab_id = tu.id left join nongzhiyun.tb_res_crop_farming_tool_greenhouses_plant ")
					.append(" trcftgp on trcftgp.res_crop_farming_tool_id = trcft.id left join nongzhiyun.tb_tool tt on trcftt.tool_id = tt.id")
					.append(" left join jinpinghu.tb_tool jtt on tt.tool_name = jtt.name and jtt.enterprise_id = 1 ")
					.append(" where trcft.del_flag = 0 and trcftt.del_flag = 0 and tcf.del_flag = 0 and  trcftgp.work_id is not null ")
					.append(" and trcft.input_time not in (select input_time from jinpinghu.tb_res_crop_farming_tool) group by trcft.id ,trcftgp.work_id,tt.id ");
					List<Object[]> farmingToolList = em.createNativeQuery(queryFarmingToolString.toString()).getResultList();
					if (farmingToolList != null) {
						for(int i=0;i<farmingToolList.size();i++) {
							Object[] farmingTool = farmingToolList.get(i);
							TbResCropFarmingTool resCropFarmingTool = new TbResCropFarmingTool((Integer)farmingTool[1],1,(String)farmingTool[3],(Integer)farmingTool[4], (String)farmingTool[5],
									(String)farmingTool[6], (String)farmingTool[7], (String)farmingTool[8],(Date)farmingTool[9], (Integer)farmingTool[10]);
							cropFarmingDao.save(resCropFarmingTool);
							List<Object[]> farmingToolFileList = em.createNativeQuery("SELECT tf.file_name,tf.file_size,tf.file_type,tf.file_url FROM nongzhiyun.tb_res_crop_farming_tool_file trcfff left join nongzhiyun.tb_file tf on trcfff.file_id = tf.id where trcfff.res_crop_farming_tool_id = "+farmingTool[0]).getResultList();
							for(int j=0;j<farmingToolFileList.size();j++) {
								Object[] farmingToolFile = farmingToolFileList.get(j);
								TbFile file = new TbFile((String)farmingToolFile[0], (String)farmingToolFile[1], (Integer)farmingToolFile[2],(String)farmingToolFile[3]);
								fileDao.save(file);
								TbResCropFarmingToolFile resCropFarmingToolFile = new TbResCropFarmingToolFile(resCropFarmingTool.getId(), file.getId(), 0);
								fileDao.save(resCropFarmingToolFile);
							}
						}
					}
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				
				//农智云中的灌溉水量
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer queryFarmingWaterString = new StringBuffer();
					queryFarmingWaterString.append(" SELECT trcfw.id, 1,trcfwgp.work_id,tu.nickname,trcfw.traffic,trcfw.start_irrigation_time,trcfw.end_irrigation_time, ")
					.append(" trcfw.water_amount,trcfw.describe_,trcfw.input_time,trcfw.del_flag FROM nongzhiyun.tb_res_crop_farming_water trcfw ")
					.append(" left join nongzhiyun.tb_res_crop_farming_water_greenhouses_plant trcfwgp on trcfw.id = trcfwgp.res_crop_farming_water_id ")
					.append(" left join nongzhiyun.tb_crop_farming tcf on trcfw.crop_farming_id = tcf.id left join nongzhiyun.tb_user tu on tcf.user_tab_id = tu.id ")
					.append(" where trcfw.del_flag = 0 and tcf.del_flag = 0 and trcfw.input_time not in (select input_time from jinpinghu.tb_res_crop_farming_water) ");

					List<Object[]> farmingWaterList = em.createNativeQuery(queryFarmingWaterString.toString()).getResultList();
					if (farmingWaterList != null) {
						for(int i=0;i<farmingWaterList.size();i++) {
							Object[] farmingWater = farmingWaterList.get(i);
							TbResCropFarmingWater resCropFarmingWater = new TbResCropFarmingWater();
							resCropFarmingWater.setEnterpriseId(1);
							resCropFarmingWater.setWorkId((Integer)farmingWater[2]);
							resCropFarmingWater.setAddPeople((String)farmingWater[3]);
							resCropFarmingWater.setTraffic((String)farmingWater[4]);
							resCropFarmingWater.setStartIrrigationTime((Date)farmingWater[5]);
							resCropFarmingWater.setEndIrrigationTime((Date)farmingWater[6]);
							resCropFarmingWater.setWaterAmount((String)farmingWater[7]);
							resCropFarmingWater.setDescribe((String)farmingWater[8]);
							resCropFarmingWater.setInputTime((Date)farmingWater[9]);
							resCropFarmingWater.setDelFlag((Integer)farmingWater[10]);
							cropFarmingDao.save(resCropFarmingWater);
							List<Object[]> farmingWaterFileList = em.createNativeQuery("SELECT tf.file_name,tf.file_size,tf.file_type,tf.file_url FROM nongzhiyun.tb_res_crop_farming_water_file trcfff left join nongzhiyun.tb_file tf on trcfff.file_id = tf.id where trcfff.res_crop_farming_water_id = "+farmingWater[0]).getResultList();
							for(int j=0;j<farmingWaterFileList.size();j++) {
								Object[] farmingWaterFile = farmingWaterFileList.get(j);
								TbFile file = new TbFile((String)farmingWaterFile[0], (String)farmingWaterFile[1], (Integer)farmingWaterFile[2],(String)farmingWaterFile[3]);
								fileDao.save(file);
								TbResCropFarmingWaterFile resCropFarmingWaterFile = new TbResCropFarmingWaterFile(resCropFarmingWater.getId(), file.getId(), 0);
								fileDao.save(resCropFarmingWaterFile);
							}
						}
					}
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				
				//农智云中的种植计划
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer insertWorkString = new StringBuffer();
					insertWorkString.append(" INSERT INTO jinpinghu.tb_work(id, enterprise_id, add_people, work_name, work_sn, ")
					.append(" land_block_sn, area, crop, start_time, end_time, recovery_time, expected_production, input_time, ")
					.append(" del_flag, purchase_source, purchase_people, purchase_time,greenhouses_id) SELECT id,1,'余魁',work_name,work_sn, ")
					.append(" null,mj,NULL, ifnull(plant_start_time,start_time) ,end_time,NULL,expect_production,input_time,del_flag,supplier_name,purchase_people, ")
					.append(" purchase_date,greenhouses_id FROM nongzhiyun.tb_work where work_type = 1 and id not in (select id from jinpinghu.tb_work) and del_flag = 0 and base_id = 54 ");
					em.createNativeQuery(insertWorkString.toString()).executeUpdate();
					
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer insertWorkChildString = new StringBuffer();
					insertWorkChildString.append(" INSERT INTO jinpinghu.tb_work_child(work_id, name, start_time, end_time) ")
					.append(" SELECT twc.work_id, twc.name, twc.start_time, twc.end_time FROM nongzhiyun.tb_work_child twc  ")
					.append(" left join nongzhiyun.tb_work tw on twc.work_id = tw.id  where tw.base_id = 54 ")
					.append(" and tw.del_flag = 0 and work_id in (select id from jinpinghu.tb_work) and work_id not in (select work_id from jinpinghu.tb_work_child) ");
					em.createNativeQuery(insertWorkChildString.toString()).executeUpdate();
					
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				
				
				//农智云中的寻棚记录
				try {
					ZJpaHelper.beginTransaction(em);
					StringBuffer queryFarmingListString = new StringBuffer();
					queryFarmingListString.append(" SELECT tgf.id tgfId,group_concat(distinct tw.id) workId,1,tgf.describe_,tu.nickname,tgf.input_time,0  ");
					queryFarmingListString.append(" FROM nongzhiyun.tb_res_greenhouses_farming_greenhouses trgfg ");
					queryFarmingListString.append(" left join nongzhiyun.tb_greenhouses_farming tgf on trgfg.greenhouses_farming_id = tgf.id  ");
					queryFarmingListString.append(" left join nongzhiyun.tb_user tu on tgf.user_tab_id = tu.id ");
					queryFarmingListString.append(" left join nongzhiyun.tb_work tw on ((trgfg.greenhouses_id = tw.greenhouses_id  ");
					queryFarmingListString.append(" and tgf.input_time >= tw.start_time and tgf.input_time <= tw.end_time) ");
					queryFarmingListString.append(" or (trgfg.greenhouses_id = tw.plant_greenhouses_id and tgf.input_time >= tw.plant_start_time and tgf.input_time < tw.start_time) ");
					queryFarmingListString.append(" or (tw.work_type = 2 and trgfg.greenhouses_id = tw.plant_greenhouses_id AND tgf.input_time >= tw.plant_start_time AND (tgf.input_time < tw.end_time or (tw.end_time is null)))) ");
					queryFarmingListString.append(" where tgf.del_flag = 0 and tw.id is not null  ");
					queryFarmingListString.append(" and tgf.input_time not in (select input_time from jinpinghu.tb_crop_farming) and tw.base_id = 54 group by tgf.id ");
					@SuppressWarnings("unchecked")
					List<Object[]> farmingList = em.createNativeQuery(queryFarmingListString.toString()).getResultList();
					if (farmingList != null) {
						for(int i=0;i<farmingList.size();i++) {
							Object[] farming = farmingList.get(i);
							TbCropFarming cropFarming = new TbCropFarming(null,1,(String)farming[3], (String)farming[4],  (Date)farming[5],0);
							cropFarmingDao.save(cropFarming);
							String workIds = (String) farming[1];
							if(!StringUtils.isEmpty(workIds)) {
								String[] workIdArray = workIds.split(",");
								for (int j = 0; j < workIdArray.length; j++) {
									TbResCropFarmingWork resCropFarmingWork = new TbResCropFarmingWork();
									resCropFarmingWork.setCropFarmingId(cropFarming.getId());
									resCropFarmingWork.setWorkId(Integer.valueOf(workIdArray[j]));
									cropFarmingDao.save(resCropFarmingWork);
								}
							}
							List<Object[]> farmingFileList = em.createNativeQuery("SELECT tf.file_name,tf.file_size,tf.file_type,tf.file_url FROM nongzhiyun.tb_res_greenhouses_farming_file trcfff left join nongzhiyun.tb_file tf on trcfff.file_id = tf.id where tf.id is not null and trcfff.greenhouses_farming_id = "+farming[0]).getResultList();
							for(int j=0;j<farmingFileList.size();j++) {
								Object[] farmingFile = farmingFileList.get(j);
								TbFile file = new TbFile((String)farmingFile[0], (String)farmingFile[1], (Integer)farmingFile[2],(String)farmingFile[3]);
								fileDao.save(file);
								TbResCropFarmingFile resCropFarmingFile = new TbResCropFarmingFile(cropFarming.getId(), file.getId(), 0);
								fileDao.save(resCropFarmingFile);
							}
						}
					}
					ZJpaHelper.commit(em);
				} catch (Exception e) {
					// TODO: handle exception
					ZJpaHelper.rollback(em);
					e.printStackTrace();
				}
				
//				try {
//					ZJpaHelper.beginTransaction(em);
//					LocalDate videoImageLastDate = null;
//					LocalDate videoImageTable = LocalDate.now();
//					while(videoImageLastDate == null) {
//						try {
//							videoImageLastDate = ((java.sql.Date) em.createNativeQuery(" select date(input_time) from tb_video_img_"+videoImageTable.format(DateTimeFormatter.ofPattern("yyyy_MM"))+" order by input_time desc limit 1 ").getSingleResult()).toLocalDate();
//							break;
//						} catch (Exception e) {
//							// TODO: handle exception
//							videoImageTable = videoImageTable.minusMonths(1);
//						}
//						
//					}
//					if (videoImageLastDate != null) {
//						StringBuffer videoImageInsertString;
//						while(LocalDate.now().isAfter(videoImageLastDate)) {
//							videoImageInsertString = new StringBuffer();
//							videoImageInsertString.append(" INSERT INTO jinpinghu.tb_video_img_").append(videoImageLastDate.format(DateTimeFormatter.ofPattern("yyyy_MM")))
//							.append("(video_id, file_url, input_time, del_flag) ").append("select video_id, file_url, input_time, del_flag from nongzhiyun.tb_video_img_")
//							.append(videoImageLastDate.format(DateTimeFormatter.ofPattern("yyyy_MM"))).append(" where input_time not in (select input_time from jinpinghu.tb_video_img_")
//							.append(videoImageLastDate.format(DateTimeFormatter.ofPattern("yyyy_MM"))).append(")")
//							.append(" and date(input_time) = '").append(videoImageLastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("'");
//							em.createNativeQuery(videoImageInsertString.toString()).executeUpdate();
//							videoImageLastDate = videoImageLastDate.plusDays(1);
//						}
//					}
//					ZJpaHelper.commit(em);
//				} catch (Exception e) {
//					// TODO: handle exception
//					ZJpaHelper.rollback(em);
//					e.printStackTrace();
//				}
				
				ZJpaHelper.closeEntityManager(em);
			}
			
		};
		
		long CD = 24 * 60 * 60 * 1000;
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(task, 0, CD, TimeUnit.MILLISECONDS);
	}

}
