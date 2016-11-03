package mobile.page;

import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageTradeWithSelect;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 融资融券还款界面
 * @category Stage-02
 */
public class PageRRHK extends PageTradeWithSelect {
	private TestElement oBtnMQHK;	// 卖券还款
	private TestElement oBtnXJHK;	// 现金还款

	@ElementOf(ElementOfs.CODE)
	private TestElement oEditCode;	// 证券代码

	@ElementOf(ElementOfs.NAME)
	private TestElement oTextName;	// 证券名称
	
	private TestElement oEditPrice;	// 价格

	@ElementOf(ElementOfs.NUMBER)
	private TestElement oEditNum;	// 数量

	private TestElement oBtnMC;		// 卖出按钮
	private TestElement oBtnHK;		// 还款按钮

	private TestElement oEditMoney;	// 还款金额
	private TestElement oTextKHZJ;	// 可还资金
	private TestElement oTextXHKE;	// 需还款额

	private int current;	// 0: 卖券还款，1: 现金还款

	@Override
	public void doTrade() {
		switch (current) {
		case 0:
			oBtnMC.e().click();
			break;
		case 1:
			oBtnHK.e().click();
			break;
		default:
			throw new IllegalStateException();
		}
	}

	/**
	 * 切换还款类型：卖券还款、现金还款
	 * @param type
	 */
	public void doSwitchType(String type) {
		if ("卖券还款".equals(type)) {
			oBtnMQHK.e().click();
			current = 0;
		} else {
			oBtnXJHK.e().click();
			current = 1;
		}
	}

	/**
	 * 获取价格
	 * @return
	 */
	public String doGetPrice() {
		return getValue(oEditPrice.e());
	}

	/**
	 * 输入还款金额
	 * @param money
	 */
	public void doInputMoney(String money) {
		getKeyboard().doInput(oEditMoney.e(), money);
	}

	/**
	 * 获取可还资金数值
	 * @return
	 */
	public String doGetKHZJ() {
		return WaitUtil.waitForText(driver, oTextKHZJ.e(), WaitUtil.WAIT_MEDIUM, null, Conditions.NOTBLANK)
				.replace(",", "");
	}

	/**
	 * 获取需还款额数值
	 * @return
	 */
	public String doGetXHKE() {
		return getText(oTextXHKE.e()).replace(",", "");
	}

}
