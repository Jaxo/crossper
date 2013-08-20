/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services.impl;

import com.crossper.models.Business;
import com.crossper.models.Mail;
import com.crossper.representations.ContactDetailsRepresentation;
import com.crossper.services.EmailService;
import com.crossper.utils.QRCodeHelper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

@org.springframework.context.annotation.Configuration
@PropertySource({"classpath:/mail.properties"})
public class EmailServiceImpl  implements EmailService {
	private static Logger logger = Logger.getLogger(EmailServiceImpl.class);	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private Environment currentEnv;
	
	@Autowired
	private Configuration freemarkerConfiguration;
	
	
	/**
	 * Constructor.
	 */
	public EmailServiceImpl() {
		
	}
        
        public void sendCrossperMail(String email) {
            boolean isSuccess;
            Mail mailInstance = new Mail();

            mailInstance.setUserName("test email");
            mailInstance.setReceiverName("test receiver");
            mailInstance.setReceiver(email);
            mailInstance.setSubject("Test");
            mailInstance.setHasHtmlMessage(true);
            mailInstance.setHasAttachment(true);
            LinkedHashMap<String, FileSystemResource> attachments = new LinkedHashMap();
            
            FileSystemResource file = new FileSystemResource("/images/Flyer-V1.pdf");
            attachments.put("Flyer-1.pdf", file);
            mailInstance.setAttachments(attachments);
            try {
                    isSuccess = sendMail(mailInstance);
            }
            catch (Exception exSM) {
                    logger.fatal("Exception occurred while sending email.", exSM);
                    isSuccess = false;
                    
            }

        }

        @Async
         public void sendQRCodeMail(String bizEmail, String bizName, String attachment)  {
            boolean isSuccess = false;
            Mail mailInstance = new Mail();
            mailInstance.setMailType(Mail.MAIL_TYPES.crossper_biz_mail.toString());
            mailInstance.setUserName("test email");
            mailInstance.setReceiverName(bizName);
            mailInstance.setReceiver(bizEmail);
            mailInstance.setSubject(currentEnv.getProperty("mail.subject.welcome"));
            mailInstance.setHasHtmlMessage(true);
            mailInstance.setSender(currentEnv.getProperty("mail.sender"));
            mailInstance.setHasAttachment(true);
            LinkedHashMap<String, FileSystemResource> attachments = new LinkedHashMap();
            
            FileSystemResource file = new FileSystemResource(attachment); //("/images/Flyer-V1.pdf");
            attachments.put("Flyer-1.pdf", file);
            mailInstance.setAttachments(attachments);
            try {
                    isSuccess = sendMail(mailInstance);
            }
            catch (Exception exSM) {
                    logger.fatal("Exception occurred while sending email.", exSM);
                    isSuccess = false;
                    
            }
         }
        
        @Async
         public void sendActivationMail(Business business, String activationUrl, String attachment )  {
            boolean isSuccess = false;
            Mail mailInstance = new Mail();
            mailInstance.setMailType(Mail.MAIL_TYPES.biz_activation_mail.toString());
            mailInstance.setUserName("CrossperAdmin");
            mailInstance.setReceiverName(business.getName());
            mailInstance.setReceiver(business.getEmail());
            mailInstance.setSubject(currentEnv.getProperty("mail.subject.welcome"));
            mailInstance.setHasHtmlMessage(true);
            mailInstance.setSender(currentEnv.getProperty("mail.sender"));
            mailInstance.setHasAttachment(true);
            HashMap <String, String> params = new HashMap<String, String>();
            
            params.put(Mail.MAIL_PARAMS.activationCode.toString(), business.getActivationCode());
            params.put(Mail.MAIL_PARAMS.activationUrl.toString(), activationUrl);

            mailInstance.setAdditionalParams(params);
            LinkedHashMap<String, FileSystemResource> attachments = new LinkedHashMap();
            
            FileSystemResource file = new FileSystemResource(attachment); //("/images/Flyer-V1.pdf");
            attachments.put("Flyer-1.pdf", file);
            mailInstance.setAttachments(attachments);
            try {
                    isSuccess = sendMail(mailInstance);
            }
            catch (Exception exSM) {
                    logger.fatal("Exception occurred while sending email.", exSM);
                    isSuccess = false;
                    
            }
           
         }
        public boolean sendValidationMail(String userEmail, String userName) {
            boolean isSuccess;
            Mail mailInstance = new Mail();
            mailInstance.setMailType(Mail.MAIL_TYPES.verification_mail.toString());
            mailInstance.setUserName(userName);
            mailInstance.setReceiverName(userName);
            mailInstance.setReceiver(userEmail);
            mailInstance.setSubject(currentEnv.getProperty("mail.subject.welcome"));
            mailInstance.setHasHtmlMessage(true);
            mailInstance.setHasAttachment(false);
            mailInstance.setSender(currentEnv.getProperty("mail.sender"));
            try {
                    isSuccess = sendMail(mailInstance);
            }
            catch (Exception exSM) {
                    logger.error("Exception occurred while sending email.", exSM);
                    isSuccess = false;
                    
            }
            return isSuccess;
        }
        public boolean sendSupportEmail(ContactDetailsRepresentation contact) {
            boolean isSuccess;
            Mail mailInstance = new Mail();
            mailInstance.setMailType(Mail.MAIL_TYPES.generic_mail.toString());
            mailInstance.setUserName(contact.getSenderName());
            mailInstance.setReceiverName(contact.getContactEmail());
            mailInstance.setReceiver(contact.getContactEmail());
            mailInstance.setSubject("Contact Crossper Mail From : "+ contact.getSenderName());
            mailInstance.setHasHtmlMessage(true);
            mailInstance.setHasAttachment(false);
            mailInstance.setSender(contact.getSenderEmail());
            HashMap <String, String> params = new HashMap<String, String>();
            params.put(Mail.MAIL_PARAMS.message.toString(), contact.getSenderMessage());
            mailInstance.setAdditionalParams(params);
            try {
                    isSuccess = sendMail(mailInstance);
            }
            catch (Exception exSM) {
                    logger.error("Exception occurred while sending email.", exSM);
                    isSuccess = false;
                    
            }
            return isSuccess;
        }
	/**
	 * Service method to email .
	 * 
	 * @param mail The Mail bean.
	 * @return operationStatus The status of the performed operation.
	 * 
	 */
	public boolean sendMail(Mail mail){
            boolean hasSucceeded = true;
            Template template = null;
            String htmlMessage = null;
            String defaultEncoding = "UTF-8";
            Map<String, Object> mailModel = new HashMap<String, Object>();	
            if(null != mailSender) {
		if(null != mail) {
                    MimeMessageHelper mimeMessageHelper = null;
                            if(logger.isInfoEnabled()) {
                                    logger.info("Preparing to send mail.");
                            }
                            MimeMessage mimeMessage = mailSender.createMimeMessage();

                            try {
                                    /**
                                     * Message Helper instance creation depending upon the presence or absence of attachments.
                                     */
                                    mimeMessageHelper = (mail.hasAttachment()) ? new MimeMessageHelper(mimeMessage, true, defaultEncoding): new MimeMessageHelper(mimeMessage, false, defaultEncoding);

                                    mimeMessageHelper.setTo(mail.getReceiver());
                                    mimeMessageHelper.setFrom((mail.getSender() == null || mail.getSender().isEmpty()) ? (currentEnv.getProperty("mail.sender").toString()) : mail.getSender());
                                    mimeMessageHelper.setSubject(mail.getSubject());

                                    if(mail.isHtmlMessage()) {
                                            mailModel.put("name", mail.getReceiverName());
                                            if ( mail.hasAdditionalParams())
                                                mailModel.putAll(mail.getAdditionalParams());
                                            template = freemarkerConfiguration.getTemplate(getEmailTemplate(mail.getMailType()), defaultEncoding);
                                            htmlMessage = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel);

                                            if(null != htmlMessage) {
                                                    mimeMessageHelper.setText(htmlMessage, true);
                                            }
                                            else {
                                                    logger.error("Email message text cannot be null: htmlMessage" + htmlMessage);
                                            }

                                    }
                                    if(mimeMessageHelper.isMultipart()) {
                                            LinkedHashMap<String, FileSystemResource> mailAttachments = mail.getAttachments();
                                            if(null != mailAttachments && !mailAttachments.isEmpty()) {
                                                    for(String attachmentName : mailAttachments.keySet()) {
                                                            if(null != attachmentName) {
                                                                    mimeMessageHelper.addAttachment(attachmentName, mailAttachments.get(attachmentName));
                                                            }
                                                    }

                                            }
                                    }
                                } 
                            catch (MessagingException mEx) {
                                   logger.error("Exception occured while preparing mail. Exception:" + mEx.getMessage());
                                    mEx.printStackTrace();
                                    hasSucceeded = false;
                            }
                            catch (Exception ex) {
                                    logger.error("Exception occured while preparing mailing. Exception:" + ex.getMessage());
                                    ex.printStackTrace();
                                    hasSucceeded = false;
                            }

                            try {
                                    if(hasSucceeded) {
                                            mailSender.send(mimeMessage);
                                    }
                            }
                            catch(Exception ex) {
                                    logger.error("Exception occured while mailing. Exception:" + ex.getMessage());
                                    ex.printStackTrace();
                                    hasSucceeded = false;
                            }

                            if(logger.isInfoEnabled()) {
                                    logger.info("Mail with attachments "  + ((hasSucceeded) ? "successfully" : "not") + " sent.");
                            }
                    }
                    else {
                            logger.error("Improper mail instance is being sent. Mail instance:" + mail);
                            hasSucceeded = false;
                    }
            }
            else {
                    logger.fatal("Mail sender bean is not/improperly configured. MailSender instance:" + mailSender);
                    hasSucceeded = false;
            }

	return(hasSucceeded);
	}

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public Configuration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }
	
    private String getEmailTemplate(String mailType) {
        String mailTemplateName = "generic_mail.ftl";
        if (mailType != null  )
            mailTemplateName = mailType + ".ftl";
        return mailTemplateName;
    }

}
