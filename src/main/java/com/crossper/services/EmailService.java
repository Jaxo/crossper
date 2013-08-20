/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services;

import com.crossper.models.Business;
import com.crossper.models.Mail;
import com.crossper.representations.ContactDetailsRepresentation;


public interface EmailService {
    
    public boolean sendValidationMail(String email, String userName);
    public void sendQRCodeMail(String bizEmail, String bizName, String attachment);
    public boolean sendMail(Mail mailObj);
    public void sendActivationMail(Business business, String activationUrl, String attachment);
    public boolean sendSupportEmail(ContactDetailsRepresentation contact);
    
}
