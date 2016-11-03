package mobile.test.stage03;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageLOF;
import mobile.page.base.PageManager;
import mobile.page.module.Alert2;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * LOF基金申购测试
 */
public class TestF01LOFSG extends TestBase {
	private PageLOF mPage = PageManager.getPage(PageLOF.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("LOF基金申购", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testLOFSG(Map<String, String> param) {
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));

		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);

		// 输入数量
		mPage.doInputNumber(param.get("数量"));

		// 读取上限并替换
		String vActualPrice = mPage.doGetSX();
		String vCheckPoint1 = param.get("验证1");
		vCheckPoint1 = vCheckPoint1.replace("{SX}", vActualPrice);

		// 点击买入
		mPage.doTrade();

		// 获取对话框1内容并校验
		Alert2 alert = mPage.getAlert2();
		String vActualCheckPoint1 = alert.doGetConfirmText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAcceptConfirm();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetResultText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAcceptResult();

		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf(":") + 2, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
