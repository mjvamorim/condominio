 create table proprietario (
        id_proprietario bigint not null auto_increment,
        bairro varchar(255),
        celular varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        cpf varchar(255),
        email varchar(255),
        endereco varchar(255),
        ident varchar(255),
        nome varchar(255),
        senha varchar(255),
        telefone varchar(255),
        uf varchar(255),
        primary key (id_proprietario)
    );

    create table taxa (
        id_taxa bigint not null auto_increment,
        valor_extra decimal(19,2),
        anomes varchar(255),
        valor_condominio decimal(19,2),
        primary key (id_taxa)
    );

    create table titulo (
        codigo bigint not null auto_increment,
        baixa varchar(255),
        data_vencimento date,
        descricao varchar(255),
        valor decimal(19,2),
        primary key (codigo)
    );

    create table unidade (
        id_unidade bigint not null auto_increment,
        fator double precision,
        identificacao varchar(255),
        obs varchar(255),
        id_proprietario bigint,
        primary key (id_unidade)
    );

    alter table unidade 
        add constraint FK_it8e5ikdfsm8ky8mxykjy93t4 
        foreign key (id_proprietario) 
        references proprietario (id_proprietario);