package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.NamespaceManager;
import org.apache.xmlbeans.impl.values.TypeStore;
import org.apache.xmlbeans.impl.values.TypeStoreUser;
import org.apache.xmlbeans.impl.values.TypeStoreVisitor;
import org.springframework.stereotype.Service;

@Service("TypeMobile")
public class TypeStrategyMobileImp implements TypeStrategyInferface {

	@Override
	public String TypeRes(String[] types) throws Exception {
		String res = null;
		if (types != null && types.length > 0) {
			List<String> listd = new ArrayList<String>();
			Collections.addAll(listd, types);
			int pos = listd.indexOf("mobile");
			StringBuilder ds = new StringBuilder("00000000");
			if (pos > -1) {
				String dsd = String.valueOf(ds.charAt(pos));
				if ("0".equals(dsd)) {
					System.out.println("endpos:" + (pos + 1));
					ds.replace(pos, pos + 1, "1");
					System.out.println(ds.toString());
					res = ds.toString();
				}
			} else {
				throw new Exception("load type error");
			}
		} else {
			throw new Exception("types is null");
		}
		return res;
	}

}
