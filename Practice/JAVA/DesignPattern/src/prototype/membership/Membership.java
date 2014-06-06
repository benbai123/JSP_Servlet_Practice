package prototype.membership;

import java.util.ArrayList;
import java.util.List;

/** Gym membership, implements Cloneable, can copy self to create another instance
 * 
 * @author benbai123
 *
 */
public class Membership implements Cloneable {

	/** monthly fee */
	private int _monthlyFee;
	/** detail content of this membership */
	private List<String> _content;

	public Membership (int monthlyFee, List<String> content) {
		setMonthlyFee(monthlyFee);
		_content = new ArrayList<String>();
		_content.addAll(content);
	}
	public int getMonthlyFee() {
		return _monthlyFee;
	}

	public void setMonthlyFee(int monthlyFee) {
		_monthlyFee = monthlyFee;
	}

	public List<String> getContent() {
		return _content;
	}

	public void setContent(List<String> content) {
		_content = content;
	}

	public Membership clone () {
		try {
			Membership m = (Membership)super.clone();
			List<String> anotherContent = new ArrayList<String>();
			anotherContent.addAll(_content);
			m.setContent(anotherContent);
			
			return m;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
