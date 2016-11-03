package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageJJZH;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 基金转换测试
 */
public class TestD06JJZH extends TestBase {
	private PageJJZH mPage = PageManager.getPage(PageJJZH.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("基金转换", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJJZH(Map<String, String> param) {
		// 输入转出代码并校验名称
		String vActualZCMC = mPage.doInputZCDM(param.get("转出代码"));
		AssertUtil.assertEquals(param.get("转出名称"), vActualZCMC);

		// 输入转入代码并校验名称
		String vActualZRMC = mPage.doInputZRDM(param.get("转入代码"));
		AssertUtil.assertEquals(param.get("转入名称"), vActualZRMC);

		// 获取最大可转
		String vKZ = mPage.doGetZDKZ();
		// 输入数量、点击转换
		mPage.doInputZHFE(param.get("数量"));
		mPage.doTrade();

		// 处理风险提示框
		mPage.doHandleRiskAlert();

		// 替换验证1中的最大可转数量
		String vCheckPoint1 = param.get("验证1").replace("{ZDKZ}", vKZ);

		// 校验对话框1
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

		// 校验对话框2
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();

		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
