package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

import java.util.Arrays;
import java.util.List;

import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private String firstName;
	private String lastName;
	private String email;
	private int yearsOfExperience;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;

	public Integer register(IRepository repository) throws Exception {

		if (this.firstName.isEmpty())
			throw new IllegalArgumentException("First Name is required");
		if (this.lastName.isEmpty())
			throw new IllegalArgumentException("Last name is required.");
		if (this.email.isEmpty())
			throw new IllegalArgumentException("Email is required.");
		if (this.sessions.size() == 0)
			throw new IllegalArgumentException("Can't register speaker with no sessions to present.");

		Integer speakerId = null;
		boolean isGood = false;
		boolean isApproved = false;

		isGood = meetsMinimunRequirements();

		if (!isGood)
			throw new SpeakerDoesntMeetRequirementsException(
					"Speaker doesn't meet our abitrary and capricious standards.");

		isApproved = hasSessionApproved();

		if (!isApproved)
			throw new NoSessionsApprovedException("No sessions approved.");

		this.registrationFee = repository.calculateRegistrationFee(this.yearsOfExperience);

		try {
			speakerId = repository.saveSpeaker(this);
		} catch (Exception e) {
			throw e;
		}

		return speakerId;
	}

	private boolean meetsMinimunRequirements() {
		boolean result;
		int requiredCertifications = 3;
		int requiredYearsOfExperience = 10;
		int minRequiredBrowserVersion = 9;
		List<String> emailDomains = Arrays.asList("aol.com", "hotmail.com", "prodigy.com", "compuserve.com");
		List<String> employers = Arrays.asList("Pluralsight", "Microsoft", "Google", "Fog Creek Software", "37Signals",
				"Telerik");
		String[] splitted = this.email.split("@");
		String emailDomain = splitted[splitted.length - 1];
		result = ((this.yearsOfExperience > requiredYearsOfExperience || this.hasBlog
				|| this.certifications.size() > requiredCertifications || employers.contains(this.employer)
				|| (!emailDomains.contains(emailDomain) && browser.getName() != WebBrowser.BrowserName.InternetExplorer
						&& browser.getMajorVersion() >= minRequiredBrowserVersion)));
		return result;
	}

	private boolean hasSessionApproved() {
		String[] oldTechnologies = new String[] { "Cobol", "Punch Cards", "Commodore", "VBScript" };
		boolean result = true;
		for (Session session : sessions) {		
			for (String technology : oldTechnologies) {
				if (session.getTitle().contains(technology) || session.getDescription().contains(technology)) {
					session.setApproved(false);
					result = false;
					break;
				} else {
					session.setApproved(true);
				}
			}
		}
		return result;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return yearsOfExperience;
	}

	public void setExp(int exp) {
		this.yearsOfExperience = exp;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
}