/**
 * 
 */
package pagecode;

import java.util.List;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import dao.MemberDao;


import model.Member;


/**
 * @author Plansis
 *
 */
public class ProcessEdit extends PageCodeBase {
	private List<Member> members = null;

	public List<Member> getMembers(){
		if(members != null){
			MemberDao dao = new MemberDao();
			ExternalContext context = getFacesContext().getExternalContext();
			dao.setMembers((PortletRequest) context.getRequest());
			members = dao.getMembers();
		}
		return members;
	}
	
	protected HtmlForm frmProcess;
	protected HtmlSelectOneMenu membros;
	protected HtmlForm getFrmProcess() {
		if (frmProcess == null) {
			frmProcess = (HtmlForm) findComponentInRoot("frmProcess");
		}
		return frmProcess;
	}
	protected HtmlSelectOneMenu getMembros() {
		if (membros == null) {
			membros = (HtmlSelectOneMenu) findComponentInRoot("membros");
		}
		return membros;
	}

}