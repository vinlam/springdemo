package com;
import java.util.EnumSet;
import java.util.Iterator;

enum Gfg 
{
    CODE, LEARN, CONTRIBUTE, QUIZ, MCQ
};
//cd /Users/vinlam/work/gitproject/springdemo/LogAspect/target/test-classes
// java com.EnumSetDemo
public class EnumSetDemo
{
    public static void main(String[] args) 
    {
        // create a set
        EnumSet<Gfg> set1, set2, set3, set4;
 
       
        // add elements
        set1 = EnumSet.of(Gfg.QUIZ, Gfg.CONTRIBUTE, Gfg.LEARN, Gfg.CODE);
        Iterator<Gfg> iterator = set1.iterator();
        while (iterator.hasNext()) {
        	Gfg g = (Gfg) iterator.next();
        	System.out.println(g.name());
			
		}
        set2 = EnumSet.complementOf(set1);
        set3 = EnumSet.allOf(Gfg.class);
        set4 = EnumSet.range(Gfg.CODE, Gfg.CONTRIBUTE);
        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);
        System.out.println("Set 3: " + set3);
        System.out.println("Set 4: " + set4);
    }
}
