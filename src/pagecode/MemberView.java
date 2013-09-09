package pagecode;

import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import bo.Puma;

import dao.ProcessDao;

import model.Member;
import model.Process;

/**
 * @author Glaydston Veloso
 * @since 1.0.0
 * @date 08/09/2013
 * @mail glaydston.veloso@plansis.com
 */
class MemberView extends PageCodeBase {
	private List<Process> process = null;
	private Member member = null;

	protected HtmlDataTable tabela;

	protected HtmlDataTable getTabela() {
		if (tabela == null) {
			tabela = (HtmlDataTable) findComponentInRoot("tabela");
		}
		return tabela;
	}

	public List<Process> getProcess() {
		if (process != null) {
			if (member != null) {
				Puma puma = new Puma();

				// Get portlet's request
				ExternalContext context = getFacesContext().getExternalContext();
				puma.setPortletRequest((PortletRequest) context.getRequest());

				// Get from LDAP the current user
				member = puma.getCurrentUser();
			}
			ProcessDao dao = new ProcessDao();
			process = dao.getProcess(member.getName());
		}

		return process;
	}

}