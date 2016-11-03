package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageRRHK;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 融资融券还款测试
 */
public class TestL01RRHK extends TestBase {
	private PageRRHK mPage = PageManager.getPage(PageRRHK.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("还款", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testRRHK(Map<String, String> param) {
		String type = param.get("还款类型");
		mPage.doSwitchType(type);
		String vCheckPoint1 = param.get("验证1");
		
		if ("卖券还款".equals(type)) {
			String vActualName = mPage.doInputCode(param.get("代码"));
			AssertUtil.assertEquals(param.get("名称"), vActualName);

			// 输入数量
			mPage.doInputNumber(param.get("数量"));

			// 读取实际价格并替换
			String vActualPrice = mPage.doGetPrice();
			vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vActualPrice);

		} else {
			mPage.doInputMoney(param.get("数量"));
			String vKHZJ = mPage.doGetKHZJ();
			String vXHKE = mPage.doGetXHKE();
			vCheckPoint1 = vCheckPoint1.replace("{KHZJ}", vKHZJ).replace("{XHKE}", vXHKE);
		}

		// 点击买入
		mPage.doTrade();
		
		// 获取对话框1内容并校验
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText().replace(",", "");
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();

		if("卖券还款".equals(type)) {
			// 参数中加入委托编号
			String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
			param.put("委托编号", vNo);
		}
	}
}
