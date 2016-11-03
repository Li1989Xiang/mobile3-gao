package mobile.page;

import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageTradeWithSelect;
import up.light.pagefactory.TestElement;

/**
 * 融资融券现券还券界面
 * @category Stage-02
 */
public class PageRRXQHQ extends PageTradeWithSelect {
	private TestElement oBtnXQHQ;	// 现券还券

	@ElementOf(ElementOfs.CODE)
	private TestElement oEditCode;	// 证券代码

	@ElementOf(ElementOfs.NAME)
	private TestElement oTextName;	// 证券名称

	@ElementOf(ElementOfs.NUMBER)
	private TestElement oEditNum;	// 数量

	@ElementOf(ElementOfs.BUTTON_OK)
	private TestElement oBtnOK;		// 交易按钮

	/**
	 * 切换到现券还券
	 * @param type
	 */
	public void doSwitchTo() {
		oBtnXQHQ.e().click();
	}

}
