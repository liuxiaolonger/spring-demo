package com.etoc.constant;

/**
 * 
 * rest风格rel值常量 <功能详细描述>
 *
 * @author chuyh
 * @version [版本号, 2018年10月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RelConst {

	/**
	 * 分页资源链接
	 */
	public static final String REL_PAGE = "page";

	/**
	 * 如果当前资源表示的是一个集合,则用来指向该集合中的单个资源
	 */
	public static final String REL_ITEM = "item";
	/**
	 * 如果当前资源包含在某一个集合中,则用来指向包含该资源的集合.
	 */
	public static final String REL_COLLECTION = "collection";

	/**
	 * 资源集合的第一个资源
	 */
	public static final String REL_FIRST = "first";
	/**
	 * 资源集合的最后一个资源
	 */
	public static final String REL_LAST = "last";
	/**
	 * 资源集合的上一个资源
	 */
	public static final String REL_PREVIOUS = "previous";
	/**
	 * 资源集合的下一个资源
	 */
	public static final String REL_NEXT = "next";

	/**
	 * 指向一个可以搜索当前资源极其相关资源的链接
	 */
	public static final String REL_SEARCH = "search";

	/**
	 * 根据id查询单条对象
	 */
	public static final String REL_SELF = "self";

	/**
	 * 状态资源
	 */
	public static final String REL_STATE = "state";

	/**
	 * 指向一个与当前资源相关的资源
	 */
	public static final String REL_RELATED = "related";

	/**
	 * 新增资源
	 */
	public static final String REL_ADD = "add";

	/**
	 * 删除资源
	 */
	public static final String REL_REMOVE = "remove";

	/**
	 * 修改资源对象
	 */
	public static final String REL_MODIFY = "modify";

	/**
	 * 上传资源
	 */
	public static final String REL_UPLOAD = "upload";

	/**
	 * 下载资源
	 */
	public static final String REL_DOWNLOAD = "download";

	/**
	 * 角色个性化rel
	 */
	public static class RelRoleConst {
		/**
		 * 通过角色id查询关联的资源集合
		 */
		public static final String REL_RELATED_RESOURCE = "relatedResource";

		/**
		 * 通过角色id查询关联的用户集合
		 */
		public static final String REL_RELATED_USER = "relatedUser";

	}

	/**
	 * 用户个性化rel
	 */
	public static class RelUserConst {
		/**
		 * 通过用户id查询关联的角色集合
		 */
		public static final String REL_RELATED_ROLE = "relatedRole";
		/**
		 * 通过用户id查询关联的用户组集合
		 */
		public static final String REL_RELATED_GROUP = "relatedGroup";
		/**
		 * 通过用户id查询关联的资源集合
		 */
		public static final String REL_RELATED_RESOURCE = "relatedResource";

		/**
		 * 获取所有机构信息或机构树
		 */
		public static final String REL_SEARCH_ORGANIZATION = "searchOrganization";

		/**
		 * 资源树
		 */
		public static final String REL_SEARCH_RESOURCE = "searchResource";
		/**
		 * 获取所有角色
		 */
		public static final String REL_SEARCH_ROLE = "searchRole";

		/**
		 * 重置用户密码
		 */
		public static final String REL_MODIFY_PASSWORD = "modifyPassword";

	}

	/**
	 * 用户组个性化rel
	 */
	public static class RelUserGroupConst {
		/**
		 * 用户组中查询所有用户资源
		 */
		public static final String REL_SEARCH_USER = "searchUser";
	}

	/**
	 * 平台产品个性化rel
	 */
	public static class RelProduct {
		/**
		 * 平台产品中 自动生成产品编码
		 */
		public static final String REL_SELF_PRODUCTNO = "selfProductNo";
		/**
		 * 平台产品中 按地区类型查询所有地区资源
		 */
		public static final String REL_SEARCH_AREA = "searchArea";
		/**
		 * 平台产品中 查询业务类型下拉列表
		 */
		public static final String REL_SEARCH_SERVICE = "searchService";

	}
	
	/**
	 * 代理商类型个性化rel
	 */
	public static class RelProxy {
		/**
		 * 获取所有供应商名称下拉菜单
		 */
		public static final String REL_SEARCH_ISPNAME = "GET_ispNames";
		

	}

	/**
	 * 通道模板个性化rel
	 */
	public static class RelRouteway {
		/**
		 * 通道模板中获取 产品树
		 */
		public static final String REL_SEARCH_PRODUCT = "searchProduct";

		/**
		 * 通道模板获取供应商产品集合
		 */
		public static final String REL_SEARCH_LARGESS = "searchLargess";
		
		/**
		 * 通道模板中根据id获取单条供应商产品
		 */
		public static final String REL_SELF_LARGESS = "selfLargess";
		
		/**
		 * 通道模板获取供应商名称
		 */
		public static final String REL_SEARCH_ISPNAME = "searchIspName";
		
		/**
		 * 通道模板中获取 折扣价格
		 */
		public static final String REL_SEARCH_PURCHASEPRICE = "searchPurchasePrice";

		/**
		 * 通道模板中根据id获取供应商信息
		 */
		public static final String REL_SELF_ISPINFO = "selfIspName";
		
	}

	/**
	 * 地区号段个性化rel
	 */
	public static class RelAreaPhoneConst {
		/**
		 * 地区号段中查询所有省、市的地区资源
		 */
		public static final String REL_SEARCH_AREA = "searchArea";
		/**
		 * 地区号段中根据父级id及类型 查询其子级地域信息
		 */
		public static final String REL_SEARCH_SON_AREA = "searchSonArea";

		/**
		 * 地区号段中根据地区主键 查询所属省、市的地区资源
		 */
		public static final String REL_SELF_AREA = "selfArea";

	}

	/**
	 * 接口类型个性化rel
	 */
	public static class RelInterfaceConst {
		/**
		 * 根据接口类型code查询代理商接口限制
		 */
		public static final String REL_RELATED_CHANNEL = "relatedChannel";
		/**
		 * 配置代理商接口限制
		 */
		public static final String REL_CONFIG_INTERFACE = "config";

		/**
		 * 全查询代理商
		 */
		public static final String REL_SEARCH_CHANNELNAME = "searchChannelName";
		
		/**
		 * 根据接口code 查询接口限制
		 */
		public static final String REL_SEARCH_INTERFACES = "searchInterfaces";
		
	}

	/**
	 * 供应商产品个性化rel
	 */
	public static class RelLargessResource {
		/**
		 * 根据供应商id获取供应商信息
		 */
		public static final String REL_SELF_ISPNAME = "selfIsp";
		/**
		 * 根据地区类型查询所有地区资源
		 */
		public static final String REL_SEARCH_AREATYPE = "searchAreaType";
		/**
		 * 根据地区类型查询所有地区资源
		 */
		public static final String SEARCH_PURCHASEDISCOUNT = "searchPurchaseDiscount";

	}

	/**
	 * 供应商账户个性化rel
	 */
	public static class RelIspCountResource {

		/**
		 * 根据地区类型查询所有地区资源
		 */
		public static final String SEARCH_ISPNAME = "searchIspName";
		/**
		 * 根据地区类型查询所有地区资源
		 */
		public static final String SEARCH_CURRENTDISCOUNT = "searchCurrentDiscount";

	}

	/**
	 * 供应商交易个性化rel
	 */
	public static class RelIspTradeDetail {
		/**
		 * 供应商交易中 查询供应商id和名称组成的下拉菜单
		 */
		public static final String SEARCH_ISPNAMES = "searchIspNames";

		/**
		 * 供应商交易中 根据供应商id查询供应商信息
		 */
		public static final String SEARCH_ISP = "selfIsp";

	}

	/**
	 * 供应商个性化rel
	 */
	public static class RelIsp {
		/**
		 * 查询供应商接口表
		 */
		public static final String SEARCH_INTERFACE = "searchInterface";
		/**
		 * 查询供应商接口表
		 */
		public static final String MODIFY_INTERFACE = "modifyInterface";
		/**
		 * 查询供应商接口表
		 */
		public static final String Add_INTERFACE = "addInterface";
		/**
		 * 查询供应商接口表
		 */
		public static final String IMPORT_INTERFACE = "importInterface";

	}

	/**
	 * 代理商账户个性化rel
	 */
	public static class RelChannelAccount {
		/**
		 * 查询单个代理商
		 */
		public static final String SEARCH_CHANNEL = "searchChannel";
		/**
		 * 查询交易明细
		 */
		public static final String SEARCH_TRADE = "searchtrade";
		/**
		 * 查询折扣管理
		 */
		public static final String SEARCH_CURRENTDISCOUNT = "currentdiscount";
		/**
		 * 查询地区
		 */
		public static final String SEARCH_AREA = "searchArea";
		/**
		 * 查询产品包
		 */
		public static final String SEARCH_PRODUCT = "searchProduct";

	}

	/**
	 * 代理商个性化rel
	 */
	public static class RelChannel {
		/**
		 * 代理商中 根据键查询参数信息
		 */
		public static final String SEARCH_SYSTEMS = "searchSystems";
		/**
		 * 代理商中 根据地区id查询地区信息
		 */
		public static final String SELF_AREA = "selfArea";
		/**
		 * 代理商合同
		 */
		public static final String SEARCH_CONTRACT = "searchContract";
		/**
		 * 代理商资质
		 */
		public static final String SEARCH_QUALIFICATION = "searchQualification";
		/**
		 * 代理商交付
		 */
		public static final String SEARCH_CHANNELORDER = "searchChannelOrder";
		/**
		 * 代理商接口权限
		 */
		public static final String SEARCH_CHANNELINTERFACE = "searchChannelInterface";
		/**
		 * 代理商产品
		 */
		public static final String SEARCH_CHANNELPRODCUT = "searchChannelProdcut";

		/**
		 * 查询代理商用户
		 */
		public static final String SEARCH_CHANNELUSERS = "searchChannelUsers";
		
		/**
		 * 查询代理商用户
		 */
		public static final String SELF_PROXYMODEL = "selfProxyModel";
	}

	/**
	 * 代理商订购个性化rel
	 */
	public static class RelChannelOrder {
		/**
		 * 查询单个代理商
		 */
		public static final String SEARCH_CHANNEL = "searchChannel";
		/**
		 * 交付方式
		 */
		public static final String SEARCH_ORDERWAY = "searchOrderWay";

	}

	/**
	 * 代理商接口个性化rel
	 */
	public static class RelChannelInterface {
		/**
		 * 查询单个代理商
		 */
		public static final String SEARCH_CHANNEL = "searchChannel";
		/**
		 * 交付方式
		 */
		public static final String SEARCH_INTERFACE = "searchInterface";
		/**
		 * 交付方式
		 */
		public static final String SEARCH_INTERFACELIST = "searchInterfaceList";

	}

	/**
	 * 代理商通道个性化rel
	 */
	public static class RelChannelRouteway {
		/**
		 * 查询单个代理商
		 */
		public static final String SEARCH_CHANNEL = "searchChannel";
		/**
		 * 全查询代理商
		 */
		public static final String SEARCH_CHANNELLIST = "searchChannelList";
		/**
		 * 通道查询
		 */
		public static final String SEARCH_ROUTEMODEL = "searchRouteModel";
		/**
		 * 地区号段中查询所有省、市的地区资源
		 */
		public static final String REL_SEARCH_AREA = "searchArea";
		/**
		 * 修改代理商模板
		 */
		public static final String MODIFY_CHANNELROUTEWAY = "modifyChannelRouteway";
	}

	/**
	 * 代理商公告个性化rel
	 */
	public static class RelChannelNotice {
		/**
		 * 修改者
		 */
		public static final String SEARCH_UPDATEUSER = "searchUpdateUser";
		/**
		 * 发布者
		 */
		public static final String SEARCH_PUBLISHUSER = "searchPublishUser";
		/**
		 * 代理商
		 */
		public static final String SEARCH_CHANNELLIST = "searchChannelList";

	}

	/**
	 * 供应商订购个性化rel
	 */
	public static class RelIspOrder {
		/**
		 * 供应商订购同步
		 */
		public static final String MODIFY_SYNC = "modifySync";
		/**
		 * 供应商订购回调
		 */
		public static final String MODIFY_BACK = "modifyBack";
		
		
		/**
		 * 供应商单个查询
		 */
		public static final String SEARCH_ISP = "searchIsp";
		/**
		 * 供应商名称的下拉菜单
		 */
		public static final String SEARCH_ISPNAME = "searchIspName";
		/**
		 * 根据供应商id获取供应商名称
		 */
		public static final String GET_ISPNAME = "selfIspName";
		/**
		 * 代理商名称的下拉菜单
		 */
		public static final String SEARCH_CHANNELNAME = "searchChannelName";
		/**
		 * 根据代理商id获取代理商名称
		 */
		public static final String GET_CHANNELNAME = "selfChannelName";
		/**
		 * 根据地区类型查找地区集合
		 */
		public static final String SEARCH_AREAS = "searchAreas";
		/**
		 * 根据地区id 查找地区信息
		 */
		public static final String GET_AREANAME = "selfAreaName";
		/**
		 * 获取产品名称下拉菜单
		 */
		public static final String SEARCH_PRODUCTNAMES = "searchProductNames";
		/**
		 * 根据产品编号获取产品名称
		 */
		public static final String GET_PRODUCTNAME = "selfProductName";
		/**
		 * 根据供应商产品id获取产品名称
		 */
		public static final String GET_LARGESSNAME = "selfLargessName";
		
	}

	/**
	 * 文件订购查询个性化rel
	 */
	public static class RelBatchOrder {
		/**
		 * 查询产品
		 */
		public static final String SEARCH_PRODUCT = "searchProduct";
		/**
		 * 代理商查询
		 */
		public static final String SEARCH_CHANNEL = "searchChannel";
		/**
		 * 文件订购查询明细查询
		 */
		public static final String SEARCH_BATCHDETAIL = "searchBatchDetail";

	}

	/**
	 * 文件订购明细查询个性化rel
	 */
	public static class RelBatchDetail {
		/**
		 * 查询产品
		 */
		public static final String SEARCH_PRODUCT = "searchProduct";
		/**
		 * 根据地区主键id 查找地区信息
		 */
		public static final String SEARCH_AREA = "selfArea";
		/**
		 * 供应商名称的下拉菜单
		 */
		public static final String SEARCH_ISP = "searchIsp";
		/**
		 * 订购详情
		 */
		public static final String SEARCH_API = "searchApi";

	}
	
	
	/**
	 * 供应商统计个性化rel
	 */
	public static class RelIspStatistics {
		/**
		 * 供应商名称的下拉菜单
		 */
		public static final String SEARCH_ISP = "searchIsp";
		
		/**
		 * 查询供应商产品分页信息
		 */
		public static final String SEARCH_LARGESS = "selfLargess";

		/**
		 * 根据供应商id查询供应商信息
		 */
		public static final String SEARCH_ISPNAME = "selfIspName";
		
		/**
		 * 根据供应商产品id查询供应商产品名称
		 */
		public static final String SEARCH_LARGESSNAME= "selfLargessName";

	}
	   /**
     * 代理商统计个性化rel
     */
    public static class RelChannelStatistics {
        /**
         * 查询供应商产品分页信息
         */
        public static final String SEARCH_PRODUCTNAME = "productName";

        /**
         * 根据供应商id查询供应商信息
         */
        public static final String SEARCH_CHANNELNAME = "channelName";
        /**
         * 获取代理商下拉菜单
         */
        public static final String SEARCH_CHANNEL = "searchChannelName";

    }
	
	/**
	 * 统计首页个性化rel
	 */
	public static class RelPlatform {
		/**
		 * 首页统计查询
		 */
		public static final String SEARCH_HOMEPAGE = "homePage";
		
		/**
		 * 通过产品编号获取产品名称
		 */
		public static final String SELF_PRODUCNAME = "selfProducName";

	}

}
