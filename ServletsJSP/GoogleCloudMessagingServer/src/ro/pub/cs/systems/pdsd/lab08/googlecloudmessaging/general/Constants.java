package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general;

public interface Constants {

    final public static String      APPLICATION_NAME                = "Google Cloud Messaging Registered Devices";

    final public static String      DATABASE_CONNECTION             = "jdbc:mysql://localhost:3306/googlecloudmessaging";
    final public static String      DATABASE_USERNAME               = "root";
    
    // TODO: exercise 17 modify DATABASE_PASSWORD according to your own settings
    final public static String      DATABASE_PASSWORD               = "******";
    
    final public static String      DATABASE_NAME                   = "googlecloudmessaging";
    final public static String      TABLE_NAME                      = "registered_devices";

    final public static boolean     DEBUG                           = true;
    
    final public static String      GCM_SERVER_ADDRESS              = "https://android.googleapis.com/gcm/send";
    
    // TODO: exercise 17 modify API_KEY according to your own settings
    final public static String      API_KEY                         = "AIzaSyBw9M1Dt2tnpFbWtc_l73jsvpJRFxoAuwE";
    
    final public static String      ID                              = "id";
    final public static String      REGISTRATION_ID                 = "registration_id";
    final public static String      USERNAME                        = "username";
    final public static String      EMAIL                           = "email";
    final public static String      TIMESTAMP                       = "timestamp";
    
    final public static int         ID_INDEX                        = 0;
    final public static int         REGISTRATION_ID_INDEX           = 1;
    final public static int         USERNAME_INDEX                  = 2;
    final public static int         EMAIL_INDEX                     = 3;
    final public static int         TIMESTAMP_INDEX                 = 4;
    
    final public static String      DATA                            = "data";
    final public static String      MESSAGE                         = "message";
    
    final public static String      MESSAGE_PUSH                    = "message_push";
    
    final public static String      REGISTRATION_IDS                = "registration_ids";
}