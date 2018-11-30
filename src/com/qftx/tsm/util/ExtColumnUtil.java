package com.qftx.tsm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.qftx.common.util.StringUtils;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;

/**
 * 扩展字段处理工具类
 * 
 * @author chenhm
 *
 */
public class ExtColumnUtil {

	public static final Map<String, String> defined_string_columns = new HashMap<String, String>();
	public static final Map<String, String> defined_date_columns = new HashMap<String, String>();
	static {
		defined_string_columns.put("defined1", "defined1");
		defined_string_columns.put("defined2", "defined2");
		defined_string_columns.put("defined3", "defined3");
		defined_string_columns.put("defined4", "defined4");
		defined_string_columns.put("defined5", "defined5");
		defined_string_columns.put("defined6", "defined6");
		defined_string_columns.put("defined7", "defined7");
		defined_string_columns.put("defined8", "defined8");
		defined_string_columns.put("defined9", "defined9");
		defined_string_columns.put("defined10", "defined10");
		defined_string_columns.put("defined11", "defined11");
		defined_string_columns.put("defined12", "defined12");
		defined_string_columns.put("defined13", "defined13");
		defined_string_columns.put("defined14", "defined14");
		defined_string_columns.put("defined15", "defined15");

		defined_date_columns.put("defined16", "defined16");
		defined_date_columns.put("defined17", "defined17");
		defined_date_columns.put("defined18", "defined18");
	}

	/**
	 * @param fieldSet
	 * @param bean
	 * @throws Exception
	 */
	public static void setFieldValues(CustFieldSet fieldSet, Object bean) throws Exception {
		String fieldCode = fieldSet.getFieldCode();

		if (fieldSet.getDataType() == 3) {
			List<OptionBean> optionList = fieldSet.getOptionList();
			String id = BeanUtils.getProperty(bean, fieldCode);
			BeanUtils.setProperty(bean, fieldCode, null);

			if (org.apache.commons.lang3.StringUtils.isNotBlank(id) && optionList != null) {
				for (OptionBean optionBean : optionList) {
					if (id.equals(optionBean.getOptionlistId())) {
						BeanUtils.setProperty(bean, fieldCode, optionBean.getOptionName());
					}
				}
			}
		} else if (fieldSet.getDataType() == 4) {
			List<OptionBean> optionList = fieldSet.getOptionList();
			String ids = BeanUtils.getProperty(bean, fieldCode);
			String[] idss = ids.split(",");
			StringBuffer showview = new StringBuffer();

			BeanUtils.setProperty(bean, fieldCode, null);
			if (org.apache.commons.lang3.StringUtils.isNotBlank(ids) && optionList != null) {
				a: for (String id : idss) {
					b: for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							if (idss[idss.length - 1] == id) {
								showview.append(optionBean.getOptionName());
								break b;
							} else {
								showview.append(optionBean.getOptionName()).append(",");
								break b;
							}
						}
					}
				}
				BeanUtils.setProperty(bean, fieldCode, showview.toString());
			}
		}
	}
	
	
	/**
	 * 设置扩展字段属性值
	 * 
	 * @param fieldSets
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static final <T> T getExtData(List<CustFieldSet> fieldSets, T bean)
			throws Exception {
		for (CustFieldSet fieldSet : fieldSets) {
			String fieldCode = fieldSet.getFieldCode();
			if (defined_string_columns.containsKey(fieldCode)) {
				if (fieldSet.getEnable() == CustFieldSet.FIELD_ENABLED
						&& StringUtils.isNotEmpty(BeanUtils.getProperty(bean, fieldCode))) {
					setFieldValues(fieldSet, bean);
				}
			} else if (defined_date_columns.containsKey(fieldCode)) {
				Object value = PropertyUtils.getProperty(bean, fieldCode);

				if (fieldSet.getEnable() == CustFieldSet.FIELD_NOT_ENABLE && null != value && value instanceof Date) {
					String propertyName = "show" + fieldCode;
					BeanUtils.setProperty(bean, propertyName, new SimpleDateFormat("yyyy-MM-dd").format((Date) value));
				}
			}

		}
		return bean;
	}

}
