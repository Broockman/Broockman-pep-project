package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    AccountDAO accountDAO = new AccountDAO();
    MessageDAO messageDAO =  new MessageDAO();

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message message){
        if(message.getMessage_text() == ""){
            return null;
        }
        else if((message.getMessage_text().length() > 225) ){
            return null;
        }
        else if(messageDAO.existMessage(message) == null){
            return null;
        }
        else{
            return messageDAO.insertMessage(message);
        }
    }
    

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessagesDAO();
    }

    public Message getMessageByid(int message_id){
        return messageDAO.getMessageFromID(message_id);        
    }

    

    public Message updateMessage(int message_id, Message message){

        if(messageDAO.getMessageFromID(message_id) == null){
            System.out.println("In");
            return null;
        }
        else if(message.getMessage_text() == ""){
            return null;
        }
        else if(message.getMessage_text().length() > 225){
            return null;
        }
        else{
            messageDAO.updateMessages(message_id, message);
            return messageDAO.getMessageFromID(message_id);
        }
    }

    public Message deleteMessage(int message_id){
        Message mess = messageDAO.getMessageFromID(message_id);

        if(messageDAO.getMessageFromID(message_id) != null){
            messageDAO.deleteMessage(message_id);
            return mess;
        }
        else{
            return null;
        }
    }

    public List<Message> getMessageByAccountId(int account_id){
        return messageDAO.getMessageFromAccountID(account_id);        
    }
}
