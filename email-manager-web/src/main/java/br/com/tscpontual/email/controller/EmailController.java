package br.com.tscpontual.email.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.tscpontual.controller.BaseController;
import br.com.tscpontual.email.manager.EMailManager;
import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.AttachmentInfo;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.util.JqGridResponse;
import br.com.tscpontual.util.SecurityHelper;

@Controller
@RequestMapping("/email")
public class EmailController extends BaseController {

	private static Logger log = Logger.getLogger(EmailController.class);
	
	private static Ehcache cache;
	
	@Autowired
	private EMailManager  eMailManager;
	
	@PostConstruct
	public void init(){
		cache = CacheManager.getInstance().getCache("tempAttachments");
	}
	
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String loadNewEmail(){
		return "email/NewEmail";
	}
	
	@RequestMapping(value = "new", method = RequestMethod.POST)
	public String createNewEmail(){
		return "email/NewEmail";
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(){
		return "email/Test";
	}
	
	@RequestMapping(value = "addAtachment", method = RequestMethod.POST)
	public @ResponseBody boolean uploadAttachment(@RequestParam(value = "thefile", required = true) MultipartFile file, HttpServletRequest request) throws IOException{
		String sessionId = request.getSession().getId();
		List<Attachment> attachments = null;
		Element element = cache.get(sessionId);
		try {
			Attachment attachment = new Attachment(file);
			attachment.setUserStamp(SecurityHelper.getUserName());
			attachment.setTimeStamp(new Timestamp(new Date().getTime()));
			if(element == null){
				attachments = new ArrayList<Attachment>();
				attachments.add(attachment);
				element = new Element(sessionId, attachments);
				cache.put(element);
			}
			else {
				attachments = (List<Attachment>) element.getValue();
				attachments.add(attachment);
			}
		} catch (IOException e) {
			log.error("Error while getting the attachment: ", e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "removeAttachment", method = RequestMethod.POST)
	public @ResponseBody boolean removeAttachment(@RequestParam(value = "attachmentIndex", required = true) String attachmentIndex, HttpServletRequest request){
		String sessionId = request.getSession().getId();
		Element element = cache.get(sessionId);
		if(element != null){
			List<Attachment> attachments = (List<Attachment>) element.getValue();
			attachments.remove(Integer.parseInt(attachmentIndex));
		}
		return true;
	}
	
	@RequestMapping(value = "getAttachments", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<AttachmentInfo> getNewEmailAttachments(HttpServletRequest request){
		JqGridResponse<AttachmentInfo> response = new JqGridResponse<AttachmentInfo>();
		String sessionId = request.getSession().getId();
		Element element = cache.get(sessionId);
		if(element != null){
			List<Attachment> attachments = (List<Attachment>) element.getValue();
			if(attachments != null && !attachments.isEmpty()){
				List<AttachmentInfo> attachmentsInfo =  new ArrayList<AttachmentInfo>();
				for (Attachment attachment : attachments) {
					attachmentsInfo.add(attachment.getAttachmentInfo());
				}
				response.setRows(attachmentsInfo);
				response.setRecords(Long.valueOf(attachments.size()).toString());
			}
		}
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public @ResponseBody boolean sendEmail(
            @RequestParam(value = "senderId") Integer senderId,
            @RequestParam(value = "groupId") Integer groupId,
			@RequestParam(value = "additionalEmails") String additionalEmails,
			@RequestParam(value = "emailBody") String emailBody,
			@RequestParam(value= "emailSubject") String emailSubject,
			HttpServletRequest request) {
		try {
			String sessionId = request.getSession().getId();
			Element element = cache.get(sessionId);
			List<Attachment> attachments = null;
			if(element != null){
				attachments = (List<Attachment>) element.getValue();
			}
			eMailManager.sendEmail(senderId, groupId, emailSubject, emailBody, additionalEmails, attachments);
			return true;
		} catch (Exception e) {
			log.error("Error while seding the email: ", e);
			return false;
		}
	}
	
	@RequestMapping(value = "loadPreviousEmailAttachments", method = RequestMethod.GET)
	public @ResponseBody boolean loadPreivousEmailAttachments(@RequestParam("emailId") String emailId, HttpServletRequest request) throws TechnicalException {
		List<Attachment> attachments = eMailManager.loadAttachmentFiles(Integer.parseInt(emailId));
		String sessionId = request.getSession().getId();
		cache.remove(sessionId);
		Element element = new Element(sessionId, attachments);
		cache.put(element);
		return true;
	}
	
	@RequestMapping(value = "clearTempAttachments", method = RequestMethod.POST)
	public @ResponseBody void clearTempAttachments(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		cache.remove(sessionId);
	}
	
	@RequestMapping(value = "sentEmails", method = RequestMethod.GET)
	public String loadSentEmails(){
		return "email/SentEmails";
	}
	
	@RequestMapping(value = "listSentEmails", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<Email> listSentEmails(
            @RequestParam(value = "senderConfigId", required = false) Integer senderConfigId,
            @RequestParam(value = "rowsPerPage") String rowsPerPage,
            @RequestParam(value = "page") String page){
		JqGridResponse<Email> response = new JqGridResponse<Email>();
		List<Email> emails = eMailManager.listSentEmails(senderConfigId, Integer.parseInt(page), Integer.parseInt(rowsPerPage));
		Collections.sort(emails);
		response.setRows(emails);
		int numberOfPages = eMailManager.getNumberOfPagesForSentEmails(Integer.parseInt(rowsPerPage));
		response.setRecords(rowsPerPage);
		response.setTotal(String.valueOf(numberOfPages));
		response.setPage(page);
		return response;
	}
	
	@RequestMapping(value = "getEmail", method = RequestMethod.GET)
	public @ResponseBody Email getEmail(@RequestParam(value = "emailId") String emailId){
		return eMailManager.loadEmail(Integer.parseInt(emailId));
	}
	
	public @ResponseBody boolean forwardEmail(
            @RequestParam(value = "senderId") Integer senderId,
            @RequestParam(value = "emailId") Integer emailId,
			@RequestParam(value = "groupId") Integer groupId){
		try {
			eMailManager.forwardEmail(senderId, emailId, groupId);
			return true;
		} catch (TechnicalException e) {
			log.error("Error while seding forwarding the email: ", e);
			return false;
		}
	}
	
}