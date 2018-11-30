package com.qftx.common.util.constants;

import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;

/**   
 * 系统常量
 * 
 * @version V1.0   
 * @author yhq [unicorn668@163.com] 创建时间：2012-11-2 上午10:44:38   
 */
public interface SysConstant {
	public final static String DEFAULT_SYS_TYPE = "5";//默认当前系统“慧营销” 单位平台
	public final static String OPERATE_STATUS_SUS = "1";//操作成功状态,用于弹出框跳转”
	public final static String OPERATE_STATUS_KEY = "operate_key";//操作成功状态key,用于弹出框跳转”
	public static final String VALIDATE_CODE = "img_code";// 验证码参数名
	public static final String VALIDATE_CODE_SESSION = "IMAGE_VERIFICATION_SESSION_KEY";// 验证码SESSION
	public static final String TRY_MAX_COUNT = "tryMaxCount"; // 最大登录次数
	public final static String ROLE_SUPERMAN_YHQ = "ROLE_SUPERMAN_YHQ";//超级用户权限名，数据库中不能存储
	public final static String MAIN_ACTION = "/auth/mainPage.do";      // 登录成功后调用的请求地址，不存在于资源表中
	
	public static final String USERNAME = "j_username";// 用户名参数名
	public static final String PASSWORD = "j_password";// 密码参数名
	public static final String PATH = "j_path";// 请求的免登地址
	public static final String AVOID_TYPE = "tsm_hgt"; // 免登接口类型 -- 暂时只有客户端
	public static final String AVOID_KEY = ConfigInfoUtils.getStringValue("tsm.avoidlanding.key"); // 免登录验证KEY
	public static final String IS_LOGIN_TURN = "is_login_turn";//是否为登陆跳转  用于首页判断是否为登陆进入
	public static final String AUTH_MD5_HEAD = "HYXWEB";//认证MD5加密前缀
	
	public static final String RANDOM_STR = SysBaseModelUtil.getModelId();
	
	
	public static final String USER_MESSAGE_COUNT = "USER_MESSAGE_COUNT";
	/** session KEY */
	public static final String USER_MENU_TREE = "USER_MENU_TREE";
	public static final String _MEMBERSESSION = "_memberSession";
	public static final String _TWO_ROLESESSION = "_two_roleSession";
	public static final String _ONE_ROLESESSION = "_one_roleSession";
	public static final String _THREE_ROLESESSION = "_three_roleSession";
	public static final String _FOUR_ROLESESSION = "_four_roleSession";
	public static final String _FIVE_ROLESESSION = "_five_roleSession";
	public static final String _FOLLOW_SIGN ="_follow_sign";
	
	public static final String get_group_json = "get_group_json";
	public static final String get_all_group_user_json = "get_all_group_user_json";
	public static final String get_high_sea_user_json = "get_high_sea_user_json";
	public static final String get_all_group_json = "get_all_group_json";
	public static final String get_group_user_json = "get_group_user_json";
	public static final String get_all_sale_user_json = "get_all_sale_user_json"; // 所有销售人员树
	public static final String get_all_service_user_json = "get_all_service_user_json"; // 所有客服人员树
	
	public static final String USER_GROUP_TYPE = "USER_GROUP_TYPE"; // 2:客服，其他：销售
	
	 // 产品编码 用于确定菜单
	public static final String PRODUCT_CODE = ConfigInfoUtils.getStringValue("tsm.product.code");
	
}
