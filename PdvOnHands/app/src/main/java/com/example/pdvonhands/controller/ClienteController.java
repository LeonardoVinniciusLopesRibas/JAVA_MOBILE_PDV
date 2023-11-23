package com.example.pdvonhands.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.pdvonhands.dao.ClienteDAO;
import com.example.pdvonhands.model.ClienteModel;
import com.example.pdvonhands.model.ProdutoModel;

import java.util.ArrayList;
import java.util.List;

public class ClienteController{
    private ClienteDAO clienteDAO;
    private Context context;
    public ClienteController(Context context) {
        this.clienteDAO = ClienteDAO.getInstancia(context);
        this.context = context;
    }
    public boolean insertCliente(String nome, String cpfCnpj, String email) {
        if (nome.isEmpty() || cpfCnpj.isEmpty() || email.isEmpty()) {
            return false;
        }


        if (!cpfCnpj.matches("\\d+")) {
            return false;
        }


        ClienteModel clienteExistente = clienteDAO.getClienteByCPF(cpfCnpj);
        if (clienteExistente != null){
            showToast("Cliente com o CNPJ informado já cadastrado!");
            return false;
        }

        ClienteModel cliente = new ClienteModel();
        cliente.setNome(nome);
        cliente.setCpfcnpj(cpfCnpj);
        cliente.setEmail(email);

        long resultado = clienteDAO.insert(cliente);

        return resultado != -1;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public boolean updateCliente(String nome, String cpfCnpj, String email) {
        if (nome.isEmpty() || cpfCnpj.isEmpty() || email.isEmpty()) {
            return false;
        }
        if (cpfCnpj.matches("\\d+")) {
            return false;
        }

        ClienteModel cliente = new ClienteModel();
        cliente.setNome(nome);
        cliente.setCpfcnpj(cpfCnpj);
        cliente.setEmail(email);

        long resultado = clienteDAO.update(cliente);

        return resultado != -1;
    }
    public boolean deleteCliente(String cpfCnpj) {
        if (cpfCnpj.isEmpty()) {
            return false;
        }

        ClienteModel cliente = clienteDAO.getClienteByCPF(cpfCnpj);

        if (cliente != null) {
            long resultado = clienteDAO.delete(cliente);
            return resultado != -1;
        }

        return false;
    }
    public List<ClienteModel> selectAllClients() {
        return clienteDAO.getAll();
    }
    public ClienteModel selectClienteById(int id){
        return clienteDAO.getById(id);
    }

    public ClienteModel getClienteByCNPJ(String cpfcnpj){
        return clienteDAO.getClienteByCPF(cpfcnpj);
    }
}
