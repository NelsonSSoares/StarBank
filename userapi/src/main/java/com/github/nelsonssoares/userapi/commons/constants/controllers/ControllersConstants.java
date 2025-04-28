package com.github.nelsonssoares.userapi.commons.constants.controllers;

public class ControllersConstants {
    // API INFO
    public static final String API_TAG = "STAR BANK - User API";
    public static final String API_DESCRIPTION = "API para gerenciamento de usuários e seus endereços da Star Bank";
    public static final String API_VERSION = "1.0.0";
    public static final String API_TAG_ADRESS = "STAR BANK - Endereços API";
    public static final String API_SECURITY_REQUIREMENT = "bearer-key";
    public static final String API_BASE_URL = "starbank/users";
    public static final String API_TAG_FILES = "STAR BANK - User Files API";

    // USER ENDPOINTS

    public static final String EMAIL = "/email/{email}";
    public static final String ID = "/{id}";
    public static final String CPF = "/cpf/{cpf}";
    public static final String NAME = "/name/{name}";
    public static final String ACTIVE = "/active/{id}";
    public static final String FILES = "/files";

    // ADDRESS ENDPOINTS

    public static final String ADDRESS = "/address";
    public static final String ADDRESS_ID = "/address/{id}";
    public static final String ADDRESS_USER_ID = "/users/{id}";


    //FILE ENDPOINT

    public static final String DOWNLOAD = "/downloadFile/";
    public static final String DOWNLOAD_FILE = DOWNLOAD+"{fileName:.+}";
}
