/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lychee.constant;

/**
 * Nacos常量.
 *
 * @author Chill
 */
public interface NacosConstant {

	/**
	 * nacos 地址
	 */
	String NACOS_ADDR = "127.0.0.1:8848";

	/**
	 * nacos 配置前缀
	 */
	String NACOS_CONFIG_PREFIX = "lychee";
	/**
	 * nacos 配置前缀
	 */
	String NACOS_CONFIG_NAMESPACE = "5c0fd2fd-9ab9-498b-aad0-bd992c565eb9";

	/**
	 * nacos 配置文件类型
	 */
	String NACOS_CONFIG_FORMAT = "properties";
	/**
	 * nacos 组配置后缀
	 */
	String NACOS_GROUP_SUFFIX = "-group";

	String SHARED_DATA_IDS = "lychee-datasource.properties";

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @return dataId
	 */
	static String dataId(String appName, String profile) {
		return dataId(appName, profile, NACOS_CONFIG_FORMAT);
	}

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @param format  文件类型
	 * @return dataId
	 */
	static String dataId(String appName, String profile, String format) {
		return appName + "-" + profile + "." + format;
	}

}