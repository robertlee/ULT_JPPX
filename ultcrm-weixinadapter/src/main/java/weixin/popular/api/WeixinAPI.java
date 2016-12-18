package weixin.popular.api;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import weixin.popular.bean.BaseResult;
import weixin.popular.bean.FollowResult;
import weixin.popular.bean.Group;
import weixin.popular.bean.UserInfo;
import weixin.popular.client.LocalHttpClient;

public class WeixinAPI extends BaseAPI {
	
	private static Logger logger = Logger.getLogger(WeixinAPI.class);
	/**
	 * 获取微信Code
	 *  
	 *  <summary>
	 * @param appId
	 * @param appSecret
	 * @param redirectUrl
	 * @return
	 */
	public static Map GetWeiXinCodeURL(String appId, String appSecret,
			String scope,
			String redirectUrl) {
		/*
		 *  微信登录授权
		 
		 String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" +
		 appId + "&redirect_uri=" + redirectUrl
		 +"&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		 
		 HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(OPEN_URI + "/connect/qrconnect")
					.addParameter("appId", appId)
					.addParameter("secret", appSecret).addParameter("redirectUrl", redirectUrl)
					.build();
		*/
		
		
		 /*
		 // 微信OpenId授权
		 String url =
			 "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
			 + "&redirect_uri=" + redirectUrl
			 +"&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		 */
		 HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(OPEN_URI + "/connect/oauth2/authorize")
					.addParameter("appId", appId)
					.addParameter("scope", scope)
					.addParameter("state", "STATE")
					.addParameter("response_type", "code").addParameter("redirectUrl", redirectUrl)
					.build();
		 /*
		 //	 微信用户信息授权
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ appId
					+ "&redirect_uri="
					+ redirectUrl
					+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		*/
		return LocalHttpClient.executeJsonResult(httpUriRequest, Map.class);
	}

	/**
	 * 获取用户OPENID
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static Map getOpenId(String code,String appId,String secret,String  grant_type) {
		logger.info("start get openid with code "+code);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI + "/sns/oauth2/access_token")
				.addParameter("appid", appId)
				.addParameter("secret", secret)
				.addParameter("code", code)
				.addParameter("grant_type", grant_type)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, Map.class);
	}
	
	/**
	 * 获取用户基本信息
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static UserInfo userInfo(String access_token, String openid) {
		logger.info("start get userInfo with access_token "+access_token+" and openid "+openid);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI + "/sns/userinfo")
				.addParameter("access_token", access_token)
				.addParameter("openid", openid).addParameter("lang", "zh_CN")
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, UserInfo.class);
	}

	/**
	 * 获取关注列表
	 * 
	 * @param access_token
	 * @param next_openid
	 *            第一次获取使用null
	 * @return
	 */
	public static FollowResult userGet(String access_token, String next_openid) {
		HttpUriRequest httpUriRequest = RequestBuilder
				.post()
				.setUri(BASE_URI + "/cgi-bin/user/get")
				.addParameter("access_token", access_token)
				.addParameter("next_openid",
						next_openid == null ? "" : next_openid).build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,
				FollowResult.class);
	}

	/**
	 * 创建分组
	 * 
	 * @param access_token
	 * @param name
	 * @return
	 */
	public static Group groupsCreate(String access_token, String name) {
		String groupJson = "{\"group\":{\"name\":\"" + name + "\"}}";
		HttpUriRequest httpUriRequest = RequestBuilder
				.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/cgi-bin/groups/create")
				.addParameter("access_token", access_token)
				.setEntity(
						new StringEntity(groupJson, Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, Group.class);
	}

	/**
	 * 查询所有分组
	 * 
	 * @param access_token
	 * @return
	 */
	public static Group groupsGet(String access_token) {
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI + "/cgi-bin/groups/get")
				.addParameter("access_token", access_token).build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, Group.class);
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static Group groupsGetid(String access_token, String openid) {
		String groupJson = "{\"openid\":\"" + openid + "\"}";
		HttpUriRequest httpUriRequest = RequestBuilder
				.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/cgi-bin/groups/getid")
				.addParameter("access_token", access_token)
				.setEntity(
						new StringEntity(groupJson, Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, Group.class);
	}

	/**
	 * 修改分组名
	 * 
	 * @param access_token
	 * @param id
	 *            分组ID
	 * @param name
	 *            分组名
	 * @return
	 */
	public static BaseResult groupsUpdate(String access_token, String id,
			String name) {
		String groupJson = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name
				+ "\"}}";
		HttpUriRequest httpUriRequest = RequestBuilder
				.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/cgi-bin/groups/update")
				.addParameter("access_token", access_token)
				.setEntity(
						new StringEntity(groupJson, Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,
				BaseResult.class);
	}

	/**
	 * 移动用户分组
	 * 
	 * @param access_token
	 * @param openid
	 * @param to_groupid
	 * @return
	 */
	public static BaseResult groupsMembersUpdate(String access_token,
			String openid, String to_groupid) {
		String groupJson = "{\"openid\":\"" + openid + "\",\"to_groupid\":"
				+ to_groupid + "}";
		HttpUriRequest httpUriRequest = RequestBuilder
				.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/cgi-bin/groups/menbers/update")
				.addParameter("access_token", access_token)
				.setEntity(
						new StringEntity(groupJson, Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,
				BaseResult.class);
	}
	
	public static void main(String[] args){
		
	}

}
