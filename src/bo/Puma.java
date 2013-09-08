package bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.PortletRequest;

import model.Member;

import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.um.PumaLocator;
import com.ibm.portal.um.PumaProfile;
import com.ibm.portal.um.User;
import com.ibm.portal.um.exceptions.PumaAttributeException;
import com.ibm.portal.um.exceptions.PumaMissingAccessRightsException;
import com.ibm.portal.um.exceptions.PumaModelException;
import com.ibm.portal.um.exceptions.PumaSystemException;
import com.ibm.portal.um.portletservice.PumaHome;

/**
 * @author Glaydston Veloso
 * @since 1.0.0
 * @date 08/09/2013
 * @mail glaydston.veloso@plansis.com.br
 */
public class Puma {
	private PortletServiceHome portletServiceHome;
	private PumaHome home = null;
	private PortletRequest request = null;

	public Puma() {
		Context ctx;
		try {
			ctx = new InitialContext();
			this.portletServiceHome = (PortletServiceHome) ctx.lookup("portletservice/com.ibm.portal.um.portletservice.PumaHome");
			this.home = (PumaHome) portletServiceHome.getPortletService(PumaHome.class);

		} catch (NamingException e) {
			System.out.println("-> Naming(): NamingException caught  attempting to get the service's name.");
		} catch (PortletServiceUnavailableException e) {
			System.out.println("-> PumaHelper(): PortletServiceUnavaibleException caught  attempting to get the PumaHome service.");
		}

	}
	
	/**
	 * Get current user from PortletRequest
	 * @return
	 */
	public Member getCurrentUser() {
		Member m = null;		
		List<String> attr = new ArrayList<String>();	
		attr.add("givenName");	
		attr.add("sn");
		attr.add("ibm-primaryEmail");

		PumaProfile profile = home.getProfile(getRequest());
		try {
			Map<String, Object> attrMap = profile.getAttributes(profile.getCurrentUser(), attr);

			m = new Member();
			String fullName = (String) attrMap.get("givenName") + " " + (String) attrMap.get("sn");
			m.setName(fullName);
			m.setEmail((String) attrMap.get("ibm-primaryEmail"));		
			
		} catch (PumaSystemException e) {
			System.out.println("-> PumaLocator() >  caught PumaSystemException: " + e.getMessage());
		} catch (PumaAttributeException e) {
			System.out.println("-> PumaLocator() >  caught PumaAttributeException: " + e.getMessage());
		} catch (PumaMissingAccessRightsException e) {
			System.out.println("-> PumaLocator() >  caught PumaMissingAcesssRightsException: " + e.getMessage());
		} catch (PumaModelException e) {
			System.out.println("-> PumaLocator() >  caught PumaModelException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("-> isMember() > Exception caught: " + e.getMessage());
		} finally {
		}
		return m;
	}

	/**
	 * Get All users from WS
	 * @return
	 */
	public List<Member> getAllMembers() {
		List<Member> members = new ArrayList<Member>();
		List<String> attributes = new ArrayList<String>();
		List<User> users = new ArrayList<User>();

		// Set request in profile and locator
		PumaProfile profile = home.getProfile(getRequest());
		PumaLocator locator = home.getLocator(getRequest());

		// Set attributes to get with Puma
		attributes.add("uid");
		attributes.add("givenName");
		attributes.add("sn");
		attributes.add("ibm-primaryEmail");

		// Get result from filter
		try {
			users = locator.findUsersByAttribute("uid", "*");
			Member member;
			for (User user : users) {
				Map<String, Object> attrMap = profile.getAttributes(user,
						attributes);
				// New object member
				member = new Member();
				String fullName = (String) attrMap.get("givenName") + " " + (String) attrMap.get("sn");
				
				member.setName(fullName);
				member.setEmail((String) attrMap.get("ibm-primaryEmail"));

				// Set member in collection
				members.add(member);
			}
		} catch (PumaSystemException e) {
			System.out.println("-> PumaLocator() >  caught PumaSystemException: " + e.getMessage());
		} catch (PumaAttributeException e) {
			System.out.println("-> PumaLocator() >  caught PumaAttributeException: " + e.getMessage());
		} catch (PumaMissingAccessRightsException e) {
			System.out.println("-> PumaLocator() >  caught PumaMissingAcesssRightsException: " + e.getMessage());
		} catch (PumaModelException e) {
			System.out.println("-> PumaLocator() >  caught PumaModelException: " + e.getMessage());
		} finally {
			attributes = null;
			users = null;
		}

		return members;
	}
	
	/**
	 * Get PortletRequest
	 * @return
	 */
	private PortletRequest getRequest() {
		return request;
	}

	/**
	 * Set PortletRequest
	 * @param request
	 */
	public void setPortletRequest(PortletRequest request) {
		this.request = request;
	}

}
