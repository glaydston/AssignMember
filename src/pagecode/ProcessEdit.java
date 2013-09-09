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
import dao.ProcessDao;


import model.Member;
import model.Process;

import javax.faces.component.html.HtmlInputText;


/**
 * @author Plansis
 *
 */
public class ProcessEdit extends PageCodeBase {
	private List<Member> members = null;
	private Process process = new Process();
	private List<Process> listProcess = null;
	
	public void addProcess(){
		ProcessDao dao = new ProcessDao();
		
		dao.setProcess(process);
	}
	
	public List<Member> getMembers(){
		if(members != null){
			MemberDao dao = new MemberDao();
			ExternalContext context = getFacesContext().getExternalContext();
			dao.setMembers((PortletRequest) context.getRequest());
			members = dao.getMembers();
		}
		return members;
	}
	
	public List<Process> getListProcess(){
		if(listProcess != null){
			ProcessDao dao = new ProcessDao();
			listProcess = dao.getProcess();
		}
		return listProcess;
	}
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	/**
	 * Component's Page
	 */

	protected HtmlForm frmProcess;
	protected HtmlSelectOneMenu membros;
	protected HtmlInputText processo;
	protected HtmlInputText descricao;

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

	protected HtmlInputText getProcesso() {
		if (processo == null) {
			processo = (HtmlInputText) findComponentInRoot("processo");
		}
		return processo;
	}

	protected HtmlInputText getDescricao() {
		if (descricao == null) {
			descricao = (HtmlInputText) findComponentInRoot("descricao");
		}
		return descricao;
	}

}