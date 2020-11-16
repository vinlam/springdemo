
package com;


public class TestTaskmanService
{
	// ����IP
	private static final String IP = "104.0.44.43";
//	private static final String IP = "104.76.8.12";
	// �˿�
	private static final int PORT = 7988;
	// �߳���
	private static final int THREAD_NUM = 1;
	// ÿ���̷߳��ͱ��ĵĴ���
	private static final int SEND_NUM = 1;

	
	public static void main(String[] args)
	{
		//���������ͬ��
//		String tranCode = "PB000001";

		//�����콱�ӿ�
		String tranCode = "PB000009";
		
		String split = "#";
		
//		//����ļ��ֹ�ͬ��
//		String SEND_MSG =	
//				"<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
//				"<ebank>"+
//				"<head>"+
//					"<bankNo>000000001</bankNo>"+
//					"<sendChannel>01</sendChannel>"+
//					"<reviceChannel>12</reviceChannel>"+
//					"<hostTranCode>PB000001</hostTranCode>"+
//					"<channelDate>20141212</channelDate>"+
//					"<channelTime>1111</channelTime>"+
//					"<hostDate>20141212</hostDate>"+
//					"<hostTime>1212</hostTime>"+
//					"<hostReturnCode>20141212</hostReturnCode>"+
//					"<hostReturnMessage>20141212</hostReturnMessage>"+
//				"</head>"+
//				"<body>"+
//					"<certType>0</certType>"+
//					"<certNo>110226198809081234</certNo>"+
//					"<accountName>����</accountName>"+
//				"</body>"+
//				"</ebank>";
		
		
		//����ӿڱ���
//				String SEND_MSG =	
//						"<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
//						"<ebank>"+
//						"<head>"+
//							"<tellerId>0004</tellerId>"+
//							"<tellerName>0005</tellerName>"+
//							"<branchId>0006</branchId>"+
//							"<branchName>PB000001</branchName>"+
//							"<authTeller>20141212</authTeller>"+
//							"<authTellerName>1111</authTellerName>"+
//							"<certType>20141212</certType>"+
//							"<certType>1212</certType>"+
//							"<certNo>20141212</certNo>"+
//							"<lotteryNo>20141212</lotteryNo>"+
//							"<mobileNo>20141212</mobileNo>"+
//							"<lotteryStartTime>20141212</lotteryStartTime>"+
//							"<lotteryEndTime>20141212</lotteryEndTime>"+
//							"<lotteryStt>20141212</lotteryStt>"+
//							"<pageFlag>20141212</pageFlag>"+
//						"</head>"+
//						"<body>"+
//							"<certType>0</certType>"+
//							"<certNo>110226198809081234</certNo>"+
//							"<accountName>����</accountName>"+
//						"</body>"+
//						"</ebank>";
				
				
				
				String SEND_MSG =	"<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
				"<ebank>"+
				  "<head>"+
				    "<bankNo>03141</bankNo>"+
				    "<sendChannel>01</sendChannel>"+
				    "<reviceChannel/>"+
				    "<hostTranCode>PB000009</hostTranCode>"+
				    "<hostFlowNo/>"+
				    "<channelDate>20150418</channelDate>"+
				    "<channelTime>20150418</channelTime>"+
				    "<hostDate/>"+
				    "<hostTime/>"+
				    "<hostReturnCode>10001</hostReturnCode>"+
				    "<hostReturnMessage>ϵͳ�ڲ�����</hostReturnMessage>"+
				  "</head>"+
				  "<body>"+
				    "<tellerId>0001360</tellerId>"+
				    "<tellerName>���Թ�Ա</tellerName>"+
				    "<branchId>03141</branchId>"+
				    "<branchName>���Ի����</branchName>"+
				    "<authTeller/><authTellerName/>"+
				    "<certType>01</certType>"+
				    "<certNo>440602197509181553</certNo>"+
				    
				    "<lotteryNo/>"+
				    "<mobileNo/>"+
				    "<lotteryStartTime/><lotteryEndTime/>"+
				    "<lotteryStt/>"+
				    "<pageFlag>1</pageFlag>"+
				  "</body>"+
				"</ebank>";
				

		SEND_MSG = tranCode + split + SEND_MSG;
//		 ���ĳ���
		int length = SEND_MSG.getBytes().length;
		int n = 6;
		// ���ͱ��ĵ�ǰnλΪ���ĵĳ���ֵ������nλǰ�油0
		byte[] buf = new byte[length + n];
		String lenValue = String.valueOf(length);
		System.arraycopy(SEND_MSG.getBytes(), 0, buf, n, length);
		for (int i = 0; i < n; i++)
			buf[i] = '0';
		int idx = 0;
		System.out.println();
		for (int i = n - lenValue.length(); i < n; i++)
		{
			buf[i] = (byte) lenValue.charAt(idx++);
		}
		System.out.println(new String(buf));
		
		for(int i = 0; i < THREAD_NUM; i++)
		{
			TestThread r = new TestThread(IP,PORT,(new String(buf)).getBytes(),SEND_NUM);
			Thread t = new Thread(r);
			t.start();
		}
	}
}
