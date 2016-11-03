package mobile.page;

import org.openqa.selenium.By;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.utils.ArgumentUtil;
import up.light.wait.WaitUtil;

/**
 * 融资融券银证转账
 * @category Stage-02
 */
public class PageRRYZZZ extends AbstractPage {
	private TestElement oTitleYZZ;		//银转证
	private TestElement oTitleZZY;		//证转银
	private TestElement oMenuBank;		//银行选择下拉菜单
	private TestElement oEditNum;		//转账金额
	private TestElement oEditBankPwd;	//银行密码输入框
	private TestElement oEditFundPwd;	//资金密码输入框
	private TestElement oBtnOK;			//确定按钮
	private TestElement oImgLoad;		//加载浮层
	
	/**
	 * 切换类型：银转证、证转银
	 * @param type 类型：银行转证券、证券转银行
	 */
	public void doSwithTo(String type) {
		TestElement e = null;
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		
		if ("银行转证券".equals(type)) {
			e = oTitleYZZ;
		} else if ("证券转银行".equals(type)) {
			e = oTitleZZY;
		}

		ArgumentUtil.notNull(e, "unsupport type: " + type);
		e.e().click();
		WaitUtil.sleep(500);
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
	}

	/**
	 * 设置银行
	 * @param bank 银行名称
	 * @param currency 币种
	 */
	public void doSetBank(String bank, String currency) {
		oMenuBank.e().click();
		WaitUtil.sleep(200);
		String xpath = "//label[text()='" + bank + "']";
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * 输入金额
	 * @param num 转账金额
	 */
	public void doInputNum(String num) {
		getKeyboard().doInput(oEditNum.e(), num);
	}

	/**
	 * 输入银行密码
	 * @param pwd 密码
	 */
	public void doInputBankPwd(String pwd) {
		getKeyboard().doInput(oEditBankPwd.e(), pwd);
	}
	
	/**
	 * 输入资金密码
	 * @param pwd 密码
	 */
	public void doInputFundPwd(String pwd) {
		getKeyboard().doInput(oEditFundPwd.e(), pwd);
	}

	/**
	 * 点击确定按钮
	 */
	public void doTrade() {
		oBtnOK.e().click();
	}
}
