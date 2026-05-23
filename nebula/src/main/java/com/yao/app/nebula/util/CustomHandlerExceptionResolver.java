package com.yao.app.nebula.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

/**
 * 返回的错误格式也是遵从内容协商的
 * 
 * @author summer
 * 
 */
public class CustomHandlerExceptionResolver extends AbstractHandlerExceptionResolver implements InitializingBean {

    private static final MediaType MEDIA_TYPE_APPLICATION = new MediaType("application");

    private static final boolean JAXB2PRESENT = ClassUtils.isPresent("javax.xml.bind.Binder",
            CustomHandlerExceptionResolver.class.getClassLoader());

    private static final boolean JACKSON2PRESENT = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper",
            CustomHandlerExceptionResolver.class.getClassLoader())
            && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator",
                    CustomHandlerExceptionResolver.class.getClassLoader());

    private ContentNegotiationManager contentNegotiationManager;

    private List<HttpMessageConverter<?>> messageConverters;

    private List<MediaType> allSupportedMediaTypes;

    public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
        this.contentNegotiationManager = contentNegotiationManager;
    }

    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        Method method = ((HandlerMethod) handler).getMethod();
        if (AnnotationUtils.findAnnotation(method, ResponseBody.class) != null) {
            ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);
            ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);

            CustError returnValue = new CustError(new Date(), ex.getMessage());

            try {
                writeWithMessageConverters(returnValue, inputMessage, outputMessage);
            } catch (Exception e) {
                // e.printStackTrace();
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
    public static class CustError {

        private Date time;

        private String message;

        /**
         * jaxb必须有默认构造函数
         */
        public CustError() {

        }

        public CustError(Date time, String message) {
            this.time = time;
            this.message = message;
        }

        @XmlJavaTypeAdapter(CustomDateAdapter.class)
        public Date getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }

    }

    @SuppressWarnings("unchecked")
    protected <T> void writeWithMessageConverters(T returnValue, ServletServerHttpRequest inputMessage,
            ServletServerHttpResponse outputMessage) throws IOException, HttpMediaTypeNotAcceptableException {

        Class<?> returnValueClass = returnValue.getClass();
        HttpServletRequest servletRequest = inputMessage.getServletRequest();
        List<MediaType> requestedMediaTypes = getAcceptableMediaTypes(servletRequest);
        List<MediaType> producibleMediaTypes = getProducibleMediaTypes(servletRequest, returnValueClass);

        Set<MediaType> compatibleMediaTypes = new LinkedHashSet<MediaType>();
        for (MediaType requestedType : requestedMediaTypes) {
            for (MediaType producibleType : producibleMediaTypes) {
                if (requestedType.isCompatibleWith(producibleType)) {
                    compatibleMediaTypes.add(getMostSpecificMediaType(requestedType, producibleType));
                }
            }
        }
        if (compatibleMediaTypes.isEmpty()) {
            throw new HttpMediaTypeNotAcceptableException(producibleMediaTypes);
        }

        List<MediaType> mediaTypes = new ArrayList<MediaType>(compatibleMediaTypes);
        MediaType selectedMediaType = null;
        for (MediaType mediaType : mediaTypes) {
            if (mediaType.isConcrete()) {
                selectedMediaType = mediaType;
                break;
            } else if (mediaType.equals(MediaType.ALL) || mediaType.equals(MEDIA_TYPE_APPLICATION)) {
                selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
                break;
            }
        }

        if (selectedMediaType != null) {
            selectedMediaType = selectedMediaType.removeQualityValue();
            for (HttpMessageConverter<?> messageConverter : this.messageConverters) {
                if (messageConverter.canWrite(returnValueClass, selectedMediaType)) {
                    ((HttpMessageConverter<T>) messageConverter).write(returnValue, selectedMediaType, outputMessage);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Written [" + returnValue + "] as \"" + selectedMediaType + "\" using ["
                                + messageConverter + "]");
                    }
                    return;
                }
            }
        }
        throw new HttpMediaTypeNotAcceptableException(this.allSupportedMediaTypes);
    }

    private List<MediaType> getAcceptableMediaTypes(HttpServletRequest request)
            throws HttpMediaTypeNotAcceptableException {
        List<MediaType> mediaTypes = this.contentNegotiationManager.resolveMediaTypes(new ServletWebRequest(request));
        return (mediaTypes.isEmpty() ? Collections.singletonList(MediaType.ALL) : mediaTypes);
    }

    @SuppressWarnings("unchecked")
    protected List<MediaType> getProducibleMediaTypes(HttpServletRequest request, Class<?> returnValueClass) {
        Set<MediaType> mediaTypes =
                (Set<MediaType>) request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
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

    private MediaType getMostSpecificMediaType(MediaType acceptType, MediaType produceType) {
        // 1. 安全检查：如果两者根本不兼容，直接返回服务端的原始能力
        if (!acceptType.isCompatibleWith(produceType)) {
            return produceType;
        }

        // 2. 利用官方推荐的静态方法进行特异性排序
        // 排序规则：更具体的（通配符少的）会被排在 List 的前面
        List<MediaType> types = Arrays.asList(acceptType, produceType);
        MimeTypeUtils.sortBySpecificity(types);
        MediaType mostSpecific = types.get(0);

        // 3. 安全退避：确保最终返回的类型不包含通配符
        // 比如 acceptType 是 application/*，排序后它可能依然因为某些权重排在前面，
        // 但作为 Content-Type 返回值，它必须是具体的
        if (mostSpecific.isWildcardType() || mostSpecific.isWildcardSubtype()) {
            mostSpecific = produceType;
        }

        // 4. 将客户端的权重偏好（q 值）保留到最终的具体类型中
        return mostSpecific.copyQualityValue(acceptType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化默认的contentNegotiationManager
        if (this.contentNegotiationManager == null) {
            Properties props = new Properties();
            if (JAXB2PRESENT) {
                props.put("xml", MediaType.APPLICATION_XML_VALUE);
            }
            if (JACKSON2PRESENT) {
                props.put("json", MediaType.APPLICATION_JSON_VALUE);
            }

            // 默认favorPathExtension为true,即XXXX.xml等
            ContentNegotiationManagerFactoryBean factory = new ContentNegotiationManagerFactoryBean();
            factory.setFavorParameter(false);
            factory.setIgnoreAcceptHeader(true);
            if (JACKSON2PRESENT) {
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
            if (JAXB2PRESENT) {
                messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
            }
            if (JACKSON2PRESENT) {
                JacksonJsonHttpMessageConverter jackson2 = new JacksonJsonHttpMessageConverter();
                messageConverters.add(jackson2);
            }
        }

        // 获取支持的全部类型
        Set<MediaType> allSupportedMediaTypesSet = new LinkedHashSet<MediaType>();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            allSupportedMediaTypesSet.addAll(messageConverter.getSupportedMediaTypes());
        }
        List<MediaType> result = new ArrayList<MediaType>(allSupportedMediaTypesSet);
        MimeTypeUtils.sortBySpecificity(result);

        this.allSupportedMediaTypes = Collections.unmodifiableList(result);

    }

}
