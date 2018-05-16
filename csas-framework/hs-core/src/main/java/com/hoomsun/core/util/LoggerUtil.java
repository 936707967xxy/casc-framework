/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.enums.OptionType;
import com.hoomsun.core.model.SystemLoggger;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.thread.RunningAbstract;

/**
 * 作者：liusong <br>
 * 创建时间：2017年10月23日 <br>
 * 描述：
 */
public class LoggerUtil extends RunningAbstract {
	private static final Logger log = LoggerFactory.getLogger(LoggerUtil.class);
	private static String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int", "double", "long",
			"short", "byte", "boolean", "char", "float" };

	private static ClassPool pool = ClassPool.getDefault();

	private SystemLoggger pushData;
	private MongoTemplate mongoTemplate;
	private JoinPoint joinPoint;

	public LoggerUtil() {
		super();
	}

	public LoggerUtil(long intervalTime, long stepTime, int pushNum, boolean increment) {
		super(intervalTime, stepTime, pushNum, increment);
	}

	public LoggerUtil(SystemLoggger pushData, MongoTemplate mongoTemplate) {
		super();
		this.pushData = pushData;
		this.mongoTemplate = mongoTemplate;
	}

	public LoggerUtil(SystemLoggger pushData) {
		super();
		this.pushData = pushData;
	}

	public LoggerUtil(JoinPoint joinPoint,MongoTemplate mongoTemplate) {
		super();
		this.joinPoint = joinPoint;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void doWork() {
		int step = 1;
		while (step <= pushNum && keepRun) {
			try {
				Signature signature = joinPoint.getSignature();
				MethodSignature methodSignature = (MethodSignature) signature;
				Method targetMethod = methodSignature.getMethod();
				Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
				
				Object[] args = joinPoint.getArgs();
				//获取request
				HttpServletRequest request = null;
				for (Object object : args) {
					if (object instanceof HttpServletRequest ) {
						request = (HttpServletRequest) object;
						break;
					}
				}
				// 获取方法上注解中表明的权限
				LoggerAnnotation annotation = realMethod.getAnnotation(LoggerAnnotation.class);
				if (annotation != null && null != request ) {
//					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
					StringBuilder optionParam = new StringBuilder();
					OptionType optionType = annotation.optionType();
					if (optionType == OptionType.DELETE || optionType == OptionType.UPDATE) {
						ServletContext servletContext = request.getSession().getServletContext();
						ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
						String beanId = annotation.beanId();
						String method = annotation.selectMethod();
						int idIndex = annotation.idIndex();
						
						log.info("### logger 方法参数 ### " + Arrays.toString(joinPoint.getArgs()));
						Object objParam = args[idIndex];
						Object id = objParam;
						log.info("### logger objParam ### " + objParam);
						if (!(objParam instanceof String)) {
							Class<? extends Object> param = objParam.getClass();
							String idName = annotation.idName();
							Field field = param.getDeclaredField(idName);
							field.setAccessible(true);
							id = field.get(objParam);
						}
						
						Object obj = ctx.getBean(beanId);
						Class<? extends Object> clazz = obj.getClass();
						Method query = clazz.getDeclaredMethod(method, String.class);
						Object result = query.invoke(obj, id);

						Class<?> resultCla = result.getClass();
						Field[] fields = resultCla.getDeclaredFields();
						optionParam.append(optionType.getType() + " " + resultCla.getName() + " 【 ");
						for (Field field : fields) {
							field.setAccessible(true);
							optionParam.append(field.getName());
							optionParam.append(" = ");
							optionParam.append(field.get(result));
							optionParam.append(" , ");
						}
						optionParam.append("】");
						log.info("#### logger Parameter ### " + optionParam.toString());
					}

					SessionRouter session = LoginUtil.getLoginSession(request);
					String empName = "";// 登录人
					String empWorkNum = "";// 登陆id
					if (session == null) {
						empName = "匿名用户";
						empWorkNum = "匿名用户";
					} else {
						empName = session.getEmpName();
						empWorkNum = session.getEmpWorkNum();
					}
					String clientInfo = getClientInfo(request);// 获取系统和浏览器名称
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
					Calendar ca = Calendar.getInstance();
					String operDate = df.format(ca.getTime());// 操作时间
					// 请求的IP
					String ip = request.getHeader("x-forwarded-for");
					if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
						ip = request.getHeader("Proxy-Client-IP");
					}
					if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
						ip = request.getHeader("WL-Proxy-Client-IP");
					}
					if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
						ip = request.getRemoteAddr();
					}
					String classType = joinPoint.getTarget().getClass().getName();
					Class<?> clazz = Class.forName(classType);
					String clazzName = clazz.getName();
					String target = clazz.getSimpleName(); // 操作的controller名称
					String methodName = joinPoint.getSignature().getName(); // 操作方法名称
					String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName); // 方法的参数列表
					String logContent = writeLogInfo(paramNames, joinPoint); // 方法参数名称、方法参数值（包括对象和基本类型）
					String moduleName = annotation.moduleName();// 模块名
					String option = annotation.option();// 操作内容
					// 存入数据
					SystemLoggger logAspect = new SystemLoggger();
					logAspect.setLoginName(empWorkNum);
					logAspect.setEmpName(empName);
					logAspect.setLoginDate(operDate);
					logAspect.setLoginIP(ip);
					logAspect.setLoginClient(clientInfo);
					logAspect.setMethodName(methodName);
					logAspect.setTargets(target);
					logAspect.setArgs(logContent);
					logAspect.setModuleName(moduleName);
					logAspect.setOption(option);
					logAspect.setOptionType(optionType.getType());
					if (!StringUtils.isBlank(optionParam)) {
						logAspect.setBackParam(optionParam.toString());
					}
					mongoTemplate.insert(logAspect);
					log.info("#####数据添加成功#######" + pushData);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("######:" + e);
			}finally {
				keepRun = false;
				step = step - 1;
			}
		}
	}

	public SystemLoggger getPushData() {
		return pushData;
	}

	public void setPushData(SystemLoggger pushData) {
		this.pushData = pushData;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 得到方法参数的名称
	 * 
	 * @param cls
	 * @param clazzName
	 * @param methodName
	 * @return
	 * @throws NotFoundException
	 */
	private static String[] getFieldsName(Class<? extends Object> cls, String clazzName, String methodName) throws Exception {

		// ClassClassPath classPath = new ClassClassPath(this.getClass());
		ClassClassPath classPath = new ClassClassPath(cls);
		pool.insertClassPath(classPath);
		CtClass cc = pool.get(clazzName);
		CtMethod cm = cc.getDeclaredMethod(methodName);
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			// exception
		}
		String[] paramNames = new String[cm.getParameterTypes().length];
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < paramNames.length; i++) {
			paramNames[i] = attr.variableName(i + pos); // paramNames即参数名
		}
		cc.detach();
		return paramNames;
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 得到参数的值
	 * 
	 * @param obj
	 */
	public static String getFieldsValue(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		String typeName = obj.getClass().getName();
		for (String t : types) {
			if (t.equals(typeName))
				return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("【");
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				for (String str : types) {
					if (f.getType().getName().equals(str)) {
						sb.append(f.getName() + " = " + f.get(obj) + "; ");
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.append("】");
		return sb.toString();
	}

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年10月9日 <br>
	 * 描述： 通过UserAgentUtils jar包类直接过去客户端信息
	 * 
	 * @param request
	 * @return
	 */
	private String getClientInfo(HttpServletRequest request) {
		// 获取浏览器信息
		String ua = request.getHeader("User-Agent");
		// 转成UserAgent对象
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		// 获取浏览器信息
		Browser browser = userAgent.getBrowser();
		// 获取系统信息
		OperatingSystem os = userAgent.getOperatingSystem();
		// 系统名称
		String system = os.getName();
		// 浏览器名称
		String browserName = browser.getName();
		return system + ";" + browserName;
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 此方法为返回参数类型以及相对应的参数值和参数值类型
	 * 
	 * @param paramNames
	 * @param joinPoint
	 * @return
	 */
	private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		StringBuilder sb = new StringBuilder();
		boolean clazzFlag = true;
		for (int k = 0; k < args.length; k++) {
			Object arg = args[k];
			if (arg != null) {// 如果参数列表有多个，但是参数值只有几个的话，添加非空判断
				sb.append(paramNames[k] + " ");
				// 获取对象类型
				String typeName = arg.getClass().getName();
				for (String t : types) {
					if (t.equals(typeName)) {
						sb.append("=" + arg + "; ");
					}
				}
				if (clazzFlag) {
					sb.append(getFieldsValue(arg));
				}
			}
		}
		return sb.toString();
	}
}
