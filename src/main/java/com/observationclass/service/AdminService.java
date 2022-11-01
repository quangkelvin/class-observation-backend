package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.Role;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.model.response.AccountResponse;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class AdminService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    public ApiResponse getListAccount() {
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,accountRepository.findAllByDeleteFlag(Constants.DELETE_NONE));
    }
    public ApiResponse getAccountByRole(Integer roleId){
        List<AccountResponse> listAccountByRole = accountDao.listAccountByRole(roleId);
        if(listAccountByRole.isEmpty()){

        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,listAccountByRole);
    }
    public ApiResponse deleteAccountById(Integer id){
        Account account =accountRepository.findByIdAndDeleteFlag(id,Constants.DELETE_NONE).get();
        if(account!=null){
            account.setDeleteFlag(Constants.DELETE_TRUE);
        }
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);
    }
    public ApiResponse addNewAccount(AccountRequest accountRequest){
        Account account = new Account();
        String email =accountRequest.getEmail();

        if(!accountService.checkEmailExist(email)){
            setAccount(account,accountRequest);
            account.setCreate();
        }
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,null);
    }


    public ApiResponse updateAccount(AccountRequest accountRequest){
        String email =accountRequest.getEmail();
        Account account =accountRepository.findByEmailAndDeleteFlag(email,Constants.DELETE_NONE).get();
        setAccount(account,accountRequest);
        account.setUpdate();
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.UPDATE_SUCCESS,null);
    }
    public void setAccount(Account account, AccountRequest accountRequest){
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        account.getRoles().addAll(accountRequest.getRoles().stream().map(r->{
            Role role =roleService.findRoleById(r.getId());
            role.getAccounts().add(account);
            return role;
        }).collect(Collectors.toList()));
    }
}

