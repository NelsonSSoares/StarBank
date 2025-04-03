package com.github.nelsonssoares.userapi.commons.constants.controllers;

public class ControllersConstants {
    // API INFO
    public static final String API_TAG = "Ecommerce - User API";
    public static final String API_DESCRIPTION = "API para gerenciamento de usuários e endereços do ecommerce";
    public static final String API_VERSION = "1.0.0";
    public static final String API_TAG_ADRESS = "Ecommerce - Endereços API";
    public static final String API_PRODUCES = "application/json";
    public static final String API_SECURITY_REQUIREMENT = "bearer-key";
    public static final String API_BASE_URL = "ecommerce/usuarios";

    // USER ENDPOINTS

    public static final String EMAIL = "/email/{email}";
    public static final String ID = "/{id}";
    public static final String CPF = "/cpf/{cpf}";

    public static final String NAME = "/nome";
    public static final String ACTIVE = "/active/{id}";

    // ADDRESS ENDPOINTS

    public static final String ADDRESS = "/endereco";
    public static final String ADDRESS_ID = "/endereco/{id}";
    public static final String ADDRESS_USER_ID = "/usuario/{id}";

}
