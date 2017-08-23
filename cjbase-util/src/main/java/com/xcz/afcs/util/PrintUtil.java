package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class PrintUtil {

	private static final Logger LOG = LoggerFactory.getLogger(PrintUtil.class);


	private static Map<String, PrintType> printFilter = new HashMap<String, PrintType>();
    
    static {
    	printFilter.put("image", PrintType.BIGDATA);
        printFilter.put("content", PrintType.BIGDATA);   //文章内容
        printFilter.put("insurance", PrintType.BIGDATA); //安全保障
        printFilter.put("description", PrintType.BIGDATA); //产品描述
		printFilter.put("imageData", PrintType.BIGDATA);   //图片数据
		printFilter.put("requirement", PrintType.BIGDATA); //申请条件
		printFilter.put("describe", PrintType.BIGDATA);   //其他需求
    }

    public static void putPrintFilter(String fildName, PrintType type) {
        printFilter.put(fildName, type);
    }

    
    public static String printSensitiveRealLength(String info) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            return info.replaceAll(".", "*");
        }
    }
    
    public static String printSensitiveFixLength(String info) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            return "******";
        }
    }
    
    public static String printSensitiveKeepTerminal(String info) {
        return printSensitiveKeepTerminal(info, 50);
    }
    
    public static String printSensitiveKeepTerminal(String info, int maskPercentage) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            if (maskPercentage > 100) {
                maskPercentage = 100;
            } else if (maskPercentage < 0) {
                maskPercentage = 0;
            }
            int fullLength = info.length();
            int maskLength = fullLength * maskPercentage / 100;
            if (0 == maskLength && maskPercentage > 0) {
                maskLength = 1;
            }
            int plainLength = fullLength - maskLength;
            int plainHalfLength = plainLength / 2;
            if (0 == plainHalfLength && maskPercentage < 100) {
                plainHalfLength = 1;
            }
            int maskStart = plainHalfLength;
            int maskEnd = maskStart + maskLength;
            if (maskEnd > fullLength) {
                maskEnd = fullLength;
            }
            StringBuilder sBuilder = new StringBuilder(info);
            for (int i = maskStart; i <= maskEnd; i++) {
                sBuilder.setCharAt(i, '*');
            }
            return sBuilder.toString();
        }
    }
    
    public static String printBigDataLength(String data) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("(length=");
        if (null == data) {
            sBuilder.append(0);
        } else {
            sBuilder.append(data.length());
        }
        sBuilder.append(')');
        return sBuilder.toString();
    }
    
    public static String printPwd(String pwd) {
        return printSensitiveFixLength(pwd);
    }
    
    public static String printIdNo(String idNo) {
        return printSensitiveKeepTerminal(idNo);
    }
    
    public static String printBankCardNo(String bankCardNo) {
        return printSensitiveKeepTerminal(bankCardNo);
    }

    @SuppressWarnings("unchecked")
	public static String printObjectData(Object data) {
    	return printObjectData(data, null);
    }
    
    public static String printObjectData(String name, Object data) {
    	return printObjectData(data, printFilter.get(name));
    }
    
    private static String printObjectData(Object data, PrintType type) {
    	StringBuilder sb = new StringBuilder();
    	if (data instanceof String){
    		if (type == null) {
    			sb.append(data);
    		}else{
    			switch (type){
    				case IDNO : 
    					 sb.append(printIdNo(String.valueOf(data)));
    					 break;
    				case PASSWORD:
    					sb.append(printPwd(String.valueOf(data)));
    					break;
    				case BIGDATA:
    					sb.append(printBigDataLength(String.valueOf(data)));
    					break;
    				default:
    					sb.append(String.valueOf(data));
    			}
    		}
    	}
    	else if (data instanceof Map) {
    		sb.append(printMapData((Map<Object, Object>)data));
    	}
    	else if (data instanceof Collection){
    		sb.append(printListData((Collection<Object>)data));
    	}
    	else if (data instanceof Object[]){
    		sb.append(printArrayData((Object[])data));
    	}
    	else if (data instanceof Number || data instanceof Boolean){
    		sb.append(data);
    	}
		else if (data instanceof Date) {
			sb.append(DateTimeUtil.getDateStr((Date)data));
		}
    	else{
    		sb.append(printObjectString(data));
    	}
    	return sb.toString();
    }
    

    private static String printMapData(Map<Object, Object> map) {
		Iterator<Entry<Object, Object>> i = map.entrySet().iterator();
		if (!i.hasNext())
			return "{}";
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (;;) {
			Entry<Object, Object> e = i.next();
			Object key = e.getKey();
			Object value = e.getValue();
			sb.append(printObjectData(key));
			sb.append('=');
			if (key instanceof String) {
				sb.append(printObjectData(value, printFilter.get(key)));
			}
			else{
				sb.append(printObjectData(value));
			}
			if (!i.hasNext())
				return sb.append('}').toString();
			sb.append(',').append(' ');
		}
    }
    
    private static String printListData(Collection<Object> data) {
		Iterator<Object> it = data.iterator();
		if (!it.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			Object e = it.next();
			sb.append(printObjectData(e));
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(',').append(' ');
		}
    }
    
    public static String printArrayData(Object[] data) {
    	StringBuilder sb = new StringBuilder();
    	if (data != null) {
    		sb.append("[");
    		for(int i=0; i<data.length; i++) {
    			sb.append(printObjectData(data[i]));
    		}
    		sb.append("]");
    	}else{
    		sb.append("[]");
    	}
    	return sb.toString();
    }
    
    private static String printObjectString(Object data) {
    	if (data == null) {
    		return null;
    	}
		StringBuilder sb = new StringBuilder();
		BeanInfo beanInfo;
		try {
			sb.append(data.getClass().getSimpleName()).append("{");
			beanInfo = Introspector.getBeanInfo(data.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			if (pds != null) {
				for (PropertyDescriptor pd : pds) {
					Method method = pd.getReadMethod();
					if(method == null){
						continue;
					}
					if ("getClass".equals(method.getName())) {
						continue;
					}
					String name = pd.getName();
					Object value = method.invoke(data);
					sb.append(name).append("=")
					.append(PrintUtil.printObjectData(name, value))
					.append(",");
				}
			}
			sb.append("}");
		} catch (Exception e) {
			LOG.error("print object error "+data, e);
			return String.valueOf(data);
		}
		return sb.toString();
	}
    
    
    
    public enum PrintType {
    	 PASSWORD, 
    	 BIGDATA,
    	 IDNO,
    }
 
    
}
