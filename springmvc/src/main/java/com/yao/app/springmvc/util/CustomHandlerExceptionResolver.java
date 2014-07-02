package com.yao.app.springmvc.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 返回的错误格式也是遵从内容协商的
 * 
 * @author summer
 * 
 */
public class CustomHandlerExceptionResolver extends
		AbstractHandlerExceptionResolver implements InitializingBean {

	private static final MediaType MEDIA_TYPE_APPLICATION = new MediaType(
			"application");

	private static final boolean jaxb2Present = ClassUtils.isPresent(
			"javax.xml.bind.Binder",
			CustomHandlerExceptionResolver.class.getClassLoader());

	private static final boolean jackson2Present = ClassUtils.isPresent(
			"com.fasterxml.jackson.databind.ObjectMapper",
			CustomHandlerExceptionResolver.class.getClassLoader())
			&& ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator",
					CustomHandlerExceptionResolver.class.getClassLoader());

	private ContentNegotiationManager contentNegotiationManager;

	private List<HttpMessageConverter<?>> messageConverters;

	private List<MediaType> allSupportedMediaTypes;

	public void setContentNegotiationManager(
			ContentNegotiationManager contentNegotiationManager) {
		this.contentNegotiationManager = contentNegotiationManager;
	}

	public void setMessageConverters(
			List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Method method = ((HandlerMethod) handler).getMethod();
		if (AnnotationUtils.findAnnotation(method, ResponseBody.class) != null) {
			ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(
					request);
			ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(
					response);

			Error returnValue = new Error(new Date(), ex.getMessage());

			try {
				writeWithMessageConverters(returnValue, inputMessage,
						outputMessage);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("ExceptionResolver : Error handling return value", e);
			}

			return new ModelAndView();
		}
		return null;
	}

	/**
	 * jaxb仅可以处理静态的内部类
	 * 
	 * @author summer
	 *
	 */
	@XmlRootElement
	@XmlAccessorType
	public static class Error {

		private Date time;

		private String message;

		/**
		 * jaxb必须有默认构造函数
		 */
		public Error(){
			
		}
		public Error(Date time, String message) {
			this.time = time;
			this.message = message;
		}

		@XmlJavaTypeAdapter(CustomDateAdapter.class)
		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	@SuppressWarnings("unchecked")
	protected <T> void writeWithMessageConverters(T returnValue,
			ServletServerHttpRequest inputMessage,
			ServletServerHttpResponse outputMessage) throws IOException,
			HttpMediaTypeNotAcceptableException {

		Class<?> returnValueClass = returnValue.getClass();
		HttpServletRequest servletRequest = inputMessage.getServletRequest();
		List<MediaType> requestedMediaTypes = getAcceptableMediaTypes(servletRequest);
		List<MediaType> producibleMediaTypes = getProducibleMediaTypes(
				servletRequest, returnValueClass);

		Set<MediaType> compatibleMediaTypes = new LinkedHashSet<MediaType>();
		for (MediaType requestedType : requestedMediaTypes) {
			for (MediaType producibleType : producibleMediaTypes) {
				if (requestedType.isCompatibleWith(producibleType)) {
					compatibleMediaTypes.add(getMostSpecificMediaType(
							requestedType, producibleType));
				}
			}
		}
		if (compatibleMediaTypes.isEmpty()) {
			throw new HttpMediaTypeNotAcceptableException(producibleMediaTypes);
		}

		List<MediaType> mediaTypes = new ArrayList<MediaType>(
				compatibleMediaTypes);
		MediaType.sortBySpecificityAndQuality(mediaTypes);

		MediaType selectedMediaType = null;
		for (MediaType mediaType : mediaTypes) {
			if (mediaType.isConcrete()) {
				selectedMediaType = mediaType;
				break;
			} else if (mediaType.equals(MediaType.ALL)
					|| mediaType.equals(MEDIA_TYPE_APPLICATION)) {
				selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
				break;
			}
		}

		if (selectedMediaType != null) {
			selectedMediaType = selectedMediaType.removeQualityValue();
			for (HttpMessageConverter<?> messageConverter : this.messageConverters) {
				if (messageConverter.canWrite(returnValueClass,
						selectedMediaType)) {
					((HttpMessageConverter<T>) messageConverter).write(
							returnValue, selectedMediaType, outputMessage);
					if (logger.isDebugEnabled()) {
						logger.debug("Written [" + returnValue + "] as \""
								+ selectedMediaType + "\" using ["
								+ messageConverter + "]");
					}
					return;
				}
			}
		}
		throw new HttpMediaTypeNotAcceptableException(
				this.allSupportedMediaTypes);
	}

	private List<MediaType> getAcceptableMediaTypes(HttpServletRequest request)
			throws HttpMediaTypeNotAcceptableException {
		List<MediaType> mediaTypes = this.contentNegotiationManager
				.resolveMediaTypes(new ServletWebRequest(request));
		return (mediaTypes.isEmpty() ? Collections.singletonList(MediaType.ALL)
				: mediaTypes);
	}

	@SuppressWarnings("unchecked")
	protected List<MediaType> getProducibleMediaTypes(
			HttpServletRequest request, Class<?> returnValueClass) {
		Set<MediaType> mediaTypes = (Set<MediaType>) request
				.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			return new ArrayList<MediaType>(mediaTypes);
		} else if (!this.allSupportedMediaTypes.isEmpty()) {
			List<MediaType> result = new ArrayList<MediaType>();
			for (HttpMessageConverter<?> converter : this.messageConverters) {
				if (converter.canWrite(returnValueClass, null)) {
					result.addAll(converter.getSupportedMediaTypes());
				}
			}
			return result;
		} else {
			return Collections.singletonList(MediaType.ALL);
		}
	}

	private MediaType getMostSpecificMediaType(MediaType acceptType,
			MediaType produceType) {
		MediaType produceTypeToUse = produceType.copyQualityValue(acceptType);
		return (MediaType.SPECIFICITY_COMPARATOR.compare(acceptType,
				produceTypeToUse) <= 0 ? acceptType : produceTypeToUse);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化默认的contentNegotiationManager
		if (this.contentNegotiationManager == null) {
			Properties props = new Properties();
			if (jaxb2Present) {
				props.put("xml", MediaType.APPLICATION_XML_VALUE);
			}
			if (jackson2Present) {
				props.put("json", MediaType.APPLICATION_JSON_VALUE);
			}
			
			// 默认favorPathExtension为true,即XXXX.xml等
			ContentNegotiationManagerFactoryBean factory = new ContentNegotiationManagerFactoryBean();
			factory.setFavorPathExtension(true);
			factory.setFavorParameter(false);
			factory.setIgnoreAcceptHeader(true);
			if (jackson2Present) {
			    factory.setDefaultContentType(MediaType.APPLICATION_JSON);
			}
			factory.setMediaTypes(props);

			factory.afterPropertiesSet();
			
			this.contentNegotiationManager = factory.getObject();
		}

		// 初始化默认的messageConverters
		if (this.messageConverters == null) {
			this.messageConverters = new ArrayList<HttpMessageConverter<?>>();
		}

		if (this.messageConverters.isEmpty()) {
			if (jaxb2Present) {
				messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
			}
			if (jackson2Present) {
				MappingJackson2HttpMessageConverter jackson2 = new MappingJackson2HttpMessageConverter();
				jackson2.setPrettyPrint(true);
				ObjectMapper om = new ObjectMapper();
				om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				
				jackson2.setObjectMapper(om);
				messageConverters.add(jackson2);
			}
		}

		// 获取支持的全部类型
		Set<MediaType> allSupportedMediaTypes = new LinkedHashSet<MediaType>();
		for (HttpMessageConverter<?> messageConverter : messageConverters) {
			allSupportedMediaTypes.addAll(messageConverter
					.getSupportedMediaTypes());
		}
		List<MediaType> result = new ArrayList<MediaType>(
				allSupportedMediaTypes);
		MediaType.sortBySpecificity(result);

		this.allSupportedMediaTypes = Collections.unmodifiableList(result);

	}

}
