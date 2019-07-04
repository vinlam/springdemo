package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaTest {
	MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	
	@Test
    //对于BoundMapperFacade的双向映射，我们必须明确地调用mapReverse方法，而不是我们在默认MapperFacade中看到的map方法，否则下面示例测试会失败
	public void givenSrcAndDest_whenMapsUsingBoundMapperInReverse_thenCorrect() {
	    BoundMapperFacade<Source, Dest> 
	      boundMapper = mapperFactory.getMapperFacade(Source.class, Dest.class);
	    Dest src = new Dest("baeldung", 10);
	    Source dest = boundMapper.mapReverse(src);

	    assertEquals(dest.getAge(), src.getAge());
	    assertEquals(dest.getName(), src.getName());
	}
	
	
	@Test
	//假设我们确定索引0映射到firstName，索引1映射至lastName。Orika允许使用括号来表示集合成员
	public void givenSrcWithListAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() {
	    mapperFactory.classMap(PersonNameList.class, PersonNameParts.class)
	      .field("nameList[0]", "firstName")
	      .field("nameList[1]", "lastName").register();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    List<String> nameList = Arrays.asList(new String[] { "Sylvester", "Stallone" });
	    PersonNameList src = new PersonNameList(nameList);
	    PersonNameParts dest = mapper.map(src, PersonNameParts.class);

	    assertEquals(dest.getFirstName(), "Sylvester");
	    assertEquals(dest.getLastName(), "Stallone");
	}
	
	@Test
	//与上述示例类似，我们使用括号标识，但使用名称而不是使用索引获取源对象的值。 
	//Orika支持两种方式返回key对应值，测试代码如下
	//我们可以使用单引号或双引号，但后者必须转义
	public void givenSrcWithMapAndDestWithPrimitiveAttributes_whenMaps_thenCorrect() {
	    mapperFactory.classMap(PersonNameMap.class, PersonNameParts.class)
	      .field("nameMap['first']", "firstName")
	      .field("nameMap[\"last\"]", "lastName")
	      .register();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Map<String, String> nameMap = new HashMap<>();
	    nameMap.put("first", "Leornado");
	    nameMap.put("last", "DiCaprio");
	    PersonNameMap src = new PersonNameMap(nameMap);
	    PersonNameParts dest = mapper.map(src, PersonNameParts.class);

	    assertEquals(dest.getFirstName(), "Leornado");
	    assertEquals(dest.getLastName(), "DiCaprio");
	}
	
	@Test
	//需要控制null值是否映射，缺省情况下，遇到null值会映射
	public void givenSrcWithNullField_whenMapsThenCorrect() {
	    mapperFactory.classMap(Source.class, Dest.class).byDefault();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Source src = new Source(null, 10);
	    Dest dest = mapper.map(src, Dest.class);

	    assertEquals(dest.getAge(), src.getAge());
	    assertEquals(dest.getName(), src.getName());
	}
	
	@Test
	//为了访问嵌套DTO的属性，并映射至我们的目标对象，我们使用点号
	public void givenSrcWithNestedFields_whenMaps_thenCorrect() {
	    mapperFactory.classMap(PersonContainer.class, PersonNameParts.class)
	      .field("name.firstName", "firstName")
	      .field("name.lastName", "lastName").register();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    PersonContainer src = new PersonContainer(new PName("Nick", "Canon"));
	    PersonNameParts dest = mapper.map(src, PersonNameParts.class);

	    assertEquals(dest.getFirstName(), "Nick");
	    assertEquals(dest.getLastName(), "Canon");
	}
	
	@Test
	public void givenSrcAndDest_whenMaps_thenCorrect() {
	    mapperFactory.classMap(Source.class, Dest.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Source src = new Source("Baeldung", 10);
	    Dest dest = mapper.map(src, Dest.class);

	    assertEquals(dest.getAge(), src.getAge());
	    assertEquals(dest.getName(), src.getName());
	}
	
	//创建MapperFactory之前，进行设置映射null值或忽略null值
	MapperFactory mapperFactory1 = new DefaultMapperFactory.Builder().mapNulls(false).build();
	
	@Test
	//测试进行缺省，null值没有被映射
	public void givenSrcWithNullAndGlobalConfigForNoNull_whenFailsToMap_ThenCorrect() {
	    mapperFactory1.classMap(Source.class, Dest.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Source src = new Source(null, 10);
	    Dest dest = new Dest("Clinton", 55);
	    mapper.map(src, dest);

	    assertEquals(dest.getAge(), src.getAge());
	    assertEquals(dest.getName(), "Clinton");
	}

}
