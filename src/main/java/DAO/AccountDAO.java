package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "Insert into Account (username, password) Values (?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int generated_id =(int) resultSet.getLong(1);

                return new Account(generated_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Account checkAccount(Account account){
        Connection connection =  ConnectionUtil.getConnection();

        try {
            String sql = "Select * from Account where username = ? and password = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account newAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return newAccount;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
