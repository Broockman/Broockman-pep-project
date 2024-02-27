package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO =  new AccountDAO();

    public AccountService(){
        accountDAO = new AccountDAO();
    }


    public Account addAccount(Account account){
        if(account.getUsername() != "" && account.getPassword().length() > 5){
            //add the new account
            return accountDAO.insertAccount(account);
        }
        else{
            return null;
        }
    }

    public Account loginAccount(Account account){
        return accountDAO.checkAccount(account);
    }
}
