package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService =  new AccountService();
        messageService =  new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountID);

        return app;
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account =  mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.addAccount(account);

        if(addAccount == null){
            ctx.status(400);
        }
        else{
            ctx.json(mapper.writeValueAsString(addAccount));
        }
        
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);

        if(loginAccount == null){
            ctx.status(401);
        }
        else{
            ctx.json(mapper.writeValueAsString(loginAccount));
        }

    }
    
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageMessage = messageService.addMessage(message);
        if(messageMessage == null){
            ctx.status(400);
        }
        else{
            ctx.json(mapper.writeValueAsString(messageMessage));
        }

    }
    

    private void getMessageHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByID(Context ctx){
        
        Integer message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message mess = messageService.getMessageByid(message_id);

        if(mess == null){
            ctx.json("");
        }
        else{
            ctx.json(mess);
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Integer message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMess = messageService.updateMessage(message_id, message);

        if(newMess != null){
            ctx.json(newMess);
        }
        else{
            ctx.status(400);
        }
    }

    private void deleteMessage(Context ctx) {
        Integer message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMess = messageService.deleteMessage(message_id);

        if(newMess != null){
            ctx.json(newMess);
        }
        else{
            ctx.json("");
        }
    }

    private void getMessageByAccountID(Context ctx){
        Integer account = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> mess = messageService.getMessageByAccountId(account);

        if(mess == null){
            ctx.json("");
        }
        else{
            ctx.json(mess);
        }
    }
}