package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageRRYZZZ;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 融资融券银证转账测试
 */
public class TestM01RRYZZZ extends TestBase {
	private PageRRYZZZ mPage = PageManager.getPage(PageRRYZZZ.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("融资融券银证转账", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testRRYZZZ(Map<String, String> param) {
		String type = param.get("转账方式");
		mPage.doSwithTo(type);
		mPage.doSetBank(param.get("银行名称"), param.get("币种"));
		mPage.doInputNum(param.get("转账金额"));
		mPage.doTrade();

		if ("银行转证券".equals(type)) {
			mPage.doInputBankPwd(param.get("银行密码"));
		}

		mPage.doInputFundPwd(param.get("资金密码"));

		Alert alert = mPage.getAlert();
		String actual1 = alert.doGetText();
		AssertUtil.assertEquals(param.get("验证1"), actual1);
		alert.doAccept();

		String actual2 = alert.doGetText();
		AssertUtil.assertEquals(param.get("验证2"), actual2);
		alert.doAccept();
	}
}
