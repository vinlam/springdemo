package com.service;

import org.kie.api.KieServices;
import org.kie.api.cdi.KSession;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import com.entity.Book;


@Service
public class BookService {

	//@KSession("bookprice_ksession")
	//private KieSession kSession;

	/**
	 * 获取一本书的当前实际售价
	 * 
	 * @param b
	 * @return
	 */
	public double getBookSalePrice(Book b) {
		if (b == null) {
			throw new NullPointerException("Book can not be null.");
		}
//		KieServices ks = KieServices.Factory.get();  
//	    KieContainer kContainer = ks.getKieClasspathContainer();  
//	    KieSession kSession = kContainer.newKieSession("bookprice_KS"); 
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("rules/book_price.drl"), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }


        // 注释掉的是 drools 6.x API
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        StatefulKnowledgeSession kSession = kbase.newStatefulKnowledgeSession();

        // drools 7.x API
//        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
//        Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
//        kbase.addPackages(pkgs);
//        KieSession kieSession = kbase.newKieSession();
		
		kSession.insert(b);
		kSession.fireAllRules();

		return b.getSalesPrice();
	}
	
//	public static void main(String[] args) {
//		 Book b = new Book();
//	        b.setBasePrice(120.50);
//	        b.setClz("computer");
//	        b.setName("C plus programing");
//	        b.setSalesArea("China");
//	        b.setYears(2);
//	        
//	        
//		KieServices ks = KieServices.Factory.get();  
//	    KieContainer kContainer = ks.getKieClasspathContainer("bookprice_KB");  
//	    KieSession kSession = kContainer.newKieSession("bookprice_KS");  
//		
//		kSession.insert(b);
//		kSession.fireAllRules();
//		double realPrice = b.getSalesPrice();
//        System.out.println(b.getName() + ":" + realPrice);
//	}
}
