package com.gapshap.app.constants;


public class AppConstants {
	
	public static final String STATUS_DESTINATION ="/user/status/";
  
	public static final String MESSAGE = "message";
	public static final String TOKEN = "accessToken";

	public static final String DATA_MESSAGE = "data";
	public static final String PROFILE_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploads\\profileImages\\";
	
	
	
//     ----------- Authentication  -----------	
	public static final Long LINK_EXPIRATION_TIME = 30L;
	public static final String LINK_EXPIRED = "Verification link expired";
	public static final String INVALID_OTP = "Otp is invalid";
	public static final String OTP_SENT_SUCCESS = "Otp sent successfully";
	
	public static final String EMAIL_STATUS = "emailStatus";
    public static final String EMAIL_SEND_STATUS_FAILED = "Invalid email ";
    public static final String EMAIL_SEND_STATUS_SUCCESS = "Email sent successfully";
    
    
    public static final String USER_VERIFICATION_SUCCESS = "User verification successfull";
	public static final String USER_ALREADY_REGISTERED_WITH_EMAIL = "User already registered with this email ";
	public static final String USER_REGISTRATION_SUCCESS = "User registered successfully ";
	public static final String USER_REGISTRATION_FAILED = "User registered failed ";
	public static final String USER_REGISTRATION_NOT_FOUND = "User registered account not found";
	public static final String USER_RETIREVED_SUCCESS = "User reterived successfully";
	public static final String USER_UPDATED_SUCCESS = "User updated successfully";
	
	
	public static final String VERIFICATION_EMAIL_SEND = "Check your email for otp verification";
	public static final String TOKEN_GENERATED = "Token generated successfully";
	
//   ---------- Roles ----------
	public static final Integer USER_ROLE_ID = 1;
	
	
	public static final String INVALID_USER = "Invalid user";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String RESOURCE_NOT_FOUND = "Resource not found";
	
	public static final String USER_RETERIEVED_SUCCESS = "User reterieved successfully";
	
	
	
//   ---------- Chats ------------------
	public static final String INVITATION_SENT ="Invitation sent successfully";
	public static final String INVITATION_NOT_FOUND ="Invitation not found";
	public static final String INVITATION_RETREIVED ="Invitation retrived successfully";
	public static final String INVITATION_ALREADY_SENT ="Invitation has been sent already";
	public static final String INVITATION_UPDATE_SUCCESS ="Invitation has been updated successfully";
	public static final String INVITAION_DELETED="Invitation deleted successfully";
	public static final String EMAIL ="email";
	
	public static final String NOTIFICATION_SENT = "Notification sent successfully";
	public static final String NOTIFICATION_UPDATED = "Notification updated successfully";
	public static final String NOTIFICATION_NOT_FOUND = "Notification not found";
	
	public static final String CONVERSATION_RETREIVED ="Conversation retrived successfully";
	public static final String CONVERSATION_NOT_FOUND = "Conversation not found";
	public static final String CONVERSATION_DELETED_SUCCESS ="Conversation deleted successfully";
	public static final String MESSAGE_DELIVERED ="Message delivered successfully";
	
	public static final String MESSAGE_RETERIVED ="Message retreived successfully";
	public static final String STATUS_RETERIVED ="Status retreived successfully";
	
	
	
	public static final String RECIPIENT ="Recipient";
	public static final String SENDER ="Sender";
	
	
	
	public static final String STATUS_CODE = "statusCode";
	public static final String STATUS_CODE_404 = "404";
	
  
}
