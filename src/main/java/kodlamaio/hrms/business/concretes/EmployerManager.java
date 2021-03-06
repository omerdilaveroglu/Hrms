package kodlamaio.hrms.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.abstracts.EmailSendService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager implements EmployerService{
	private EmployerDao employerDao;
	private EmailSendService emailSendService;
	private List<String> emails = new ArrayList<String>();

	@Autowired
	public EmployerManager(EmployerDao employerDao, EmailSendService emailSendService) {
		super();
		this.employerDao = employerDao;
		this.emailSendService=emailSendService;		
	}
	
	@Override
	public Result login(String email, String password) {
		Result result= new ErrorResult(Messages.loginFailed);
		for (int i = 0; i < getAll().size(); i++) {
			if(getAll().get(i).getEmail()==email && getAll().get(i).getPassword()==password) {
				result = new SuccessResult(Messages.LoginSuccessful);
			}
		}
		return result;
	}

	@Override
	public Result register(Employer employer) {
		Result result=new ErrorResult(Messages.RegisterFailed);
		if(emailIsItUsed(employer.getEmail())) {
			emailSendService.emailSend(employer.getEmail());
			employer.setVerificationStatus(false); 
			this.employerDao.save(employer);
			result = new SuccessResult(Messages.RegisterSuccessfully);
		}
		return result;
	}

	@Override
	public List<Employer> getAll() {
		return this.employerDao.findAll();
	}

	@Override
	public List<String> getAllEmails() {
		for (int i = 0; i < getAll().size(); i++) {
			emails.add(getAll().get(i).getEmail());
		}
		return emails;
	}
	
	public boolean emailIsItUsed(String email) {
		boolean result = true;
		if(getAllEmails().contains(email)) {
			result = false;
		}
		return result;
	}

	@Override
	public Result delete(int userId) {
		this.employerDao.deleteById(userId);
		return new SuccessResult(Messages.CompanyDeleted);
	}
	
	@Override
	public Result update(Employer employer) {
		this.employerDao.save(employer);
		return new SuccessResult(Messages.CompanyUpdated);
	}
}
