package mobile.page;

import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageTradeWithSelect;
import up.light.pagefactory.TestElement;

/**
 * LOF基金认申赎
 * @category Stage-02
 */
public class PageLOF extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private TestElement oEditJJDM;	// 基金代码

	@ElementOf(ElementOfs.NAME)
	private TestElement oTextJJMC;	// 基金名称

	private TestElement oTextSX;	// 上限

	@ElementOf(ElementOfs.NUMBER)
	private TestElement oEditSL;	// 份额

	@ElementOf(ElementOfs.BUTTON_OK)
	private TestElement oBtnTrade;

	/**
	 * 获取申购、认购、赎回上限
	 * @return
	 */
	public String doGetSX() {
		return getText(oTextSX.e());
	}

}
