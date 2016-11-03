package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageRRMQHQ;
import mobile.page.PageRRXQHQ;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 融资融券还券测试
 */
public class TestL02RRHQ extends TestBase {
	private PageRRMQHQ mPageMQHQ = PageManager.getPage(PageRRMQHQ.class);
	private PageRRXQHQ mPageXQHQ = PageManager.getPage(PageRRXQHQ.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("还券", mPageXQHQ);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testRRHQ(Map<String, String> param) {
		String type = param.get("还券类型");
		String vCheckPoint1 = param.get("验证1");

		if ("买券还券".equals(type)) {
			mPageMQHQ.doSwitchTo();

			String vActualName = mPageMQHQ.doInputCode(param.get("代码"));
			AssertUtil.assertEquals(param.get("名称"), vActualName);

			// 输入数量
			mPageMQHQ.doInputNumber(param.get("数量"));

			String vActualPrice = mPageMQHQ.doGetPrice();
			vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vActualPrice);

			// 点击买入
			mPageMQHQ.doTrade();
		} else {
			mPageXQHQ.doSwitchTo();

			String vActualName = mPageXQHQ.doInputCode(param.get("代码"));
			AssertUtil.assertEquals(param.get("名称"), vActualName);

			// 输入数量
			mPageXQHQ.doInputNumber(param.get("数量"));

			// 点击买入
			mPageXQHQ.doTrade();
		}

		// 获取对话框1内容并校验
		Alert alert = mPageXQHQ.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();

		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
