package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    /**
     *  message_id integer primary key auto_increment,
        posted_by integer,
        message_text varchar(255),
        time_posted_epoch long,
        foreign key (posted_by) references Account(account_id)
```
     */

     public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "Insert into Message (posted_by, message_text, time_posted_epoch) Values (?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());


            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                int generated_id =(int) resultSet.getLong(1);

                return new Message(generated_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Message existMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Select * from Message where posted_by = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message.getPosted_by());

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message messages = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                 return messages;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesDAO(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "Select * from Message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer newMessageId = rs.getInt("message_id");
                Integer newPost_By  = rs.getInt("posted_by");
                String newMessageText = rs.getString("message_text");
                Long newTimePosted = rs.getLong("time_posted_epoch");

                messages.add(new Message(newMessageId, newPost_By, newMessageText, newTimePosted));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageFromID(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Select * from Message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Integer newMessageId = rs.getInt("message_id");
                Integer newPost_By  = rs.getInt("posted_by");
                String newMessageText = rs.getString("message_text");
                Long newTimePosted = rs.getLong("time_posted_epoch");

                return new Message(newMessageId, newPost_By, newMessageText, newTimePosted);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void updateMessages(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Update Message Set message_text = ? where message_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message_id);

            System.out.println(ps);

            ps.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteMessage(int messge_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Delete from Message where message_id = ?;";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messge_id);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
