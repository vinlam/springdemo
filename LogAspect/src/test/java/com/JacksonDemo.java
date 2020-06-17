package com;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.util.JsonMapper;

public class JacksonDemo {
	public static void main(String[] args) throws IOException {
		String str="[{\"name\":\"jack\",\"age\":18},{\"name\":\"tom\",\"age\":20}]";
		String ob = "{\"name\":\"jack\",\"age\":18,\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\"}}";
		ObjectMapper mapper1 = new ObjectMapper();
		List<JsonDTO> jsonDTOs = mapper1.readValue(str,new TypeReference<List<JsonDTO>>(){});
		
		JsonDTO j = mapper1.readValue(ob,JsonDTO.class);
		System.out.println(JsonMapper.toJsonString(j));
		JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, JsonDTO.class);
		JavaType jType = JsonMapper.getInstance().createCollectionType(JsonDTO.class, NewUser.class);
		List<JsonDTO> jsonDTO = JsonMapper.getInstance().fromJson(str,javaType);
		JsonDTO<NewUser> jDTO = JsonMapper.getInstance().fromJson(ob,jType);
		System.out.println(JsonMapper.toJsonString(jDTO));
		System.out.println(JsonMapper.toJsonString(jsonDTO));
		System.out.println(JsonMapper.toJsonString(jsonDTOs));
		
		JsonDTO<NewUser> json = new JsonDTO<NewUser>();
		json.setAge("20");
		json.setName("Zhang");
		
		NewUser data = new NewUser();
		data.setAge(15);
		data.setName("Li");
		data.setSex("F");
		json.setData(data);
		System.out.println(JsonMapper.toJsonString(json));
		System.out.println(javaType.hasRawClass(List.class));//true
		String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]}";
		ObjectMapper mapper = new ObjectMapper();
		// Json映射为对象
		Student student = mapper.readValue(s, Student.class);
		// 对象转化为Json
		String jsonstr = mapper.writeValueAsString(student);
		System.out.println(jsonstr);
		System.out.println(student.toString());
		treemodel();
	}

	public static void treemodel() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		// 以下是对象转化为Json
		JsonNode root = mapper.createObjectNode();
		((ObjectNode) root).putArray("array");
		ArrayNode arrayNode = (ArrayNode) root.get("array");
		((ArrayNode) arrayNode).add("args1");
		((ArrayNode) arrayNode).add("args2");
		((ObjectNode) root).put("name", "小红");
		String json = mapper.writeValueAsString(root);
		System.out.println("使用树型模型构建的json:" + json);
		// 以下是树模型的解析Json
		String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"],"
				+ "\"test\":\"I'm test\",\"nullNode\":null,\"base\": {\"major\": \"物联网\",\"class\": \"3\"}}";
		// 读取rootNode
		JsonNode rootNode = mapper.readTree(s);
		// 通过path获取
		System.out.println("通过path获取值：" + rootNode.path("name").asText());
		// 通过JsonPointer可以直接按照路径获取
		JsonPointer pointer = JsonPointer.valueOf("/base/major");
		JsonNode node = rootNode.at(pointer);
		System.out.println("通过at获取值:" + node.asText());
		// 通过get可以取对应的value
		JsonNode classNode = rootNode.get("base");
		System.out.println("通过get获取值：" + classNode.get("major").asText());

		// 获取数组的值
		System.out.print("获取数组的值：");
		JsonNode arrayNode2 = rootNode.get("array");
		for (int i = 0; i < arrayNode2.size(); i++) {
			System.out.print(arrayNode2.get(i).asText() + " ");
		}
		System.out.println();
		// path和get方法看起来很相似，其实他们的细节不同，get方法取不存在的值的时候，会返回null。而path方法会
		// 返回一个"missing node"，该"missing
		// node"的isMissingNode方法返回值为true，如果调用该node的asText方法的话，
		// 结果是一个空字符串。
		System.out.println("get方法取不存在的节点，返回null:" + (rootNode.get("notExist") == null));
		JsonNode notExistNode = rootNode.path("notExist");
		System.out.println("notExistNode的value：" + notExistNode.asText());
		System.out.println("isMissingNode方法返回true:" + notExistNode.isMissingNode());

		// 当key存在，而value为null的时候，get和path都会返回一个NullNode节点。
		System.out.println(rootNode.get("nullNode") instanceof NullNode);
		System.out.println(rootNode.path("nullNode") instanceof NullNode);
	}
}
