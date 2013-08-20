/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.models;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;

public class Mail {
	private static Logger logger = Logger.getLogger(Mail.class);
	
	private String userName;
	private String receiverName;
	private String receiver;
	private String sender;
	private String subject;
	private boolean hasHtmlMessage;
	private String message;
	private String mailType;
	private boolean hasAttachment;
	private LinkedHashMap<String, FileSystemResource> attachments;
	private HashMap<String, String> additionalParams;
	
        public enum MAIL_TYPES { verification_mail, crossper_biz_mail, generic_mail, biz_activation_mail }
        public enum MAIL_PARAMS { activationUrl, activationCode, message };
        public Mail() {
            
        }
	/**
	 * Parameterized constructor.
	 * 
	 * @param userName
	 * @param receiverName
	 * @param receiver
	 * @param sender
	 * @param subject
	 * @param hasHtmlMessage
	 * @param message
	 * @param activationCode
	 * @param mailType
	 * @param hasAttachment
	 * @param attachments
	 */
	public Mail(String userName, String receiverName, String receiver, String sender, String subject, boolean hasHtmlMessage, String message, String mailType, boolean hasAttachment, LinkedHashMap<String, FileSystemResource> attachments) {
		super();
		this.userName = (userName == null || userName.isEmpty()) ? userName: userName.trim();
		this.receiverName = (receiverName == null || receiverName.isEmpty()) ? receiverName: receiverName.trim();
		this.receiver = (receiver == null || receiver.isEmpty()) ? receiver: receiver.trim();
		this.sender = (sender == null || sender.isEmpty()) ? sender: sender.trim();
		this.subject = (subject == null || subject.isEmpty()) ? subject: subject.trim();
		this.hasHtmlMessage = hasHtmlMessage;
		this.message = (message == null || message.isEmpty()) ? message: message.trim();
		this.mailType = (mailType == null || mailType.isEmpty()) ? mailType: mailType.trim();
		this.hasAttachment = hasAttachment;
		this.attachments = attachments;
		this.additionalParams  = new HashMap<String, String>();
	}

	/**
	 * Getter for field 'userName'.
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for field 'userName'.
	 * 
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = (userName == null || userName.isEmpty()) ? userName: userName.trim();
	}

	/**
	 * Getter for field 'receiverName'.
	 * 
	 * @return the receiverName.
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * Setter for field 'receiverName'.
	 * 
	 * @param receiverName the receiverName to set.
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = (receiverName == null || receiverName.isEmpty()) ? receiverName: receiverName.trim();
	}

	/**
	 * Getter for field 'receiver'.
	 * 
	 * @return receiver The 'receiver' email identifier.
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * Setter for field 'receiver'.
	 * 
	 * @param receiver The 'receiver' to set.
	 */
	public void setReceiver(String receiver) {
		this.receiver = (receiver == null || receiver.isEmpty()) ? receiver: receiver.trim();
	}
	
	/**
	 * Getter for field 'sender'.
	 * 
	 * @return sender The 'sender' email identifier.
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * Setter for field 'sender'.
	 * 
	 * @param sender The 'sender' to set.
	 */
	public void setSender(String sender) {
		this.sender = (sender == null || sender.isEmpty()) ? sender: sender.trim();
	}
	/**
	 * Getter for field 'subject'.
	 * 
	 * @return subject The 'subject' string.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 
	 * Setter for field 'subject'.
	 * 
	 * @param subject The 'subject' to set.
	 */
	public void setSubject(String subject) {
		this.subject = (subject == null || subject.isEmpty()) ? subject: subject.trim();
	}
	
	/**
	 * Getter for field 'htmlContentMessage'.
	 * 
	 * @return the htmlContentMessage.
	 */
	public boolean isHtmlMessage() {
		return hasHtmlMessage;
	}


	/**
	 * Setter for field 'htmlContentMessage'.
	 * 
	 * @param htmlContentMessage The htmlContentMessage indicator.
	 */
	public void setHasHtmlMessage(boolean hasHtmlMessage) {
		this.hasHtmlMessage = hasHtmlMessage;
	}


	/**
	 * Getter for field 'message'.
	 * 
	 * @return message The message string.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Setter for field 'message'.
	 * 
	 * @param message The 'message' to set.
	 */
	public void setMessage(String message) {
		this.message = (message == null || message.isEmpty()) ? message: message.trim();
	}
	
	/**
	 * Getter for field 'mailType'.
	 * @return the mailType
	 */
	public String getMailType() {
		return mailType;
	}

	/**
	 * Setter for field 'mailType'.
	 * 
	 * @param mailType the mailType to set
	 */
	public void setMailType(String mailType) {
		this.mailType = (mailType == null || mailType.isEmpty()) ? mailType: mailType.trim();
	}

	/**
	 * Getter for field 'hasAttachment'.
	 * 
	 * @return hasAttachment The status of attachment. Returns <code>true</code> when attachment present else <code>false</code>  
	 */
	public boolean hasAttachment() {
		return hasAttachment;
	}
	/**
	 * Setter for field 'hasAttachment'.
	 * 
	 * @param hasAttachment The 'hasAttachment' to set.
	 */
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * Getter for field 'attachments'.
	 * 
	 * @return attachments The range of attachments.
	 */
	public LinkedHashMap<String, FileSystemResource> getAttachments() {
		return attachments;
	}
	/**
	 * Setter for field 'sender'.
	 * 
	 * @param attachments The range of 'attachments' to set.
	 */
	public void setAttachments(LinkedHashMap<String, FileSystemResource> attachments) {
		this.attachments = attachments;
	}
	
	public HashMap<String, String> getAdditionalParams() {
		return additionalParams;
	}
	
	public void setAdditionalParams(HashMap<String, String > addParams){
		this.additionalParams = new HashMap<String, String>();
		this.additionalParams.putAll(addParams);
	}
	
	public void setParam(String key, String value) {
		if ( this.additionalParams == null )
			this.additionalParams = new HashMap<String, String>();
		this.additionalParams.put(key, value);
	}
        
        public boolean hasAdditionalParams() {
            if (this.additionalParams != null && ! this.additionalParams.isEmpty())
                return true;
            else
                return false;
        }
}
