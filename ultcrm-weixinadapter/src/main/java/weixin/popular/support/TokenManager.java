package weixin.popular.support;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import weixin.popular.api.TokenAPI;
import weixin.popular.bean.Token;

/**
 * TokenManager token 自动刷新
 * @author LiYi
 *
 */
public class TokenManager {

	private static Map<String,String> tokenMap = new LinkedHashMap<String,String>();

	private static Map<String,Timer> timerMap = new HashMap<String, Timer>();

	/**
	 * 初始化token 刷新，每118分钟刷新一次。
	 * @param appid
	 * @param secret
	 */
	public static void init(final String appid,final String secret){
		if(timerMap.containsKey(appid)){
			timerMap.get(appid).cancel();
		}
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setToken(appid,secret);
			}
		},0,1000*60*118);
		timerMap.put(appid,timer);
	}

	/**
	 * 获取 access_token
	 * @param appid
	 * @return
	 */
	public static String getToken(String appid){
		return tokenMap.get(appid);
	}
	
	/**
	 * 设置access_token
	 * @param appid
	 * @return
	 */
	public static String setToken(String appid,String secret){
		Token token = TokenAPI.token(appid,secret);
		tokenMap.put(appid,token.getAccess_token());
		return tokenMap.get(appid);
	}

}
