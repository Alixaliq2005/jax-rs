package com.example.demo.controller;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.request.TransferAccount;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
@RequiredArgsConstructor
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    private final AccountService accountService = new AccountServiceImpl ();


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(AccountRequest accountRequest) {
        accountService.createAccount (accountRequest);
        return Response.status (Response.Status.CREATED).build ();
    }


    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllActiveAccounts(@PathParam("userId") Long id) {
        List<AccountResponse> activeAccounts = accountService.findAllActiveAccounts (id);

        if (activeAccounts.isEmpty ()) {
            return Response.status (Response.Status.NOT_FOUND).entity ("No active accounts found for user ID: " + id).build ();
        }

        return Response.ok (activeAccounts).build ();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(TransferAccount transferRequest) {
        accountService.transferFunds (transferRequest);
        return Response.ok ().build ();
    }

}