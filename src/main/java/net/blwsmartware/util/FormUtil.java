package net.blwsmartware.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;


public class FormUtil {

	public static <T> T toModel (Class<T> clazz, HttpServletRequest request) {
			T object = null;
			try {
				object = clazz.newInstance();
				BeanUtils.populate(object, request.getParameterMap());// tự động tham chiếu đến biến giống tên thuộc tính và giá trị name khai báo trong input UI
				
			} catch(InstantiationException|IllegalAccessException | InvocationTargetException e) {
				System.out.println(e.getMessage());
			}
			return object;
		
	  }
	  
}
