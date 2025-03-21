# Projeto ContactCenter

## Visão Geral
O **ContactCenter** é um projeto desenvolvido utilizando o framework **Spring Boot**, com integração direta à API do **HubSpot** para gestão de contatos. O objetivo é permitir o processamento eficiente de informações e facilitar a comunicação entre sistemas.

## Tecnologias Utilizadas
- **Framework:** Spring Boot 3.4.3
- **Versão do Java:** JDK 17 (Target Release 17)
- **IDE utilizada:** NetBeans 25
- **Integração:** API do HubSpot

## Execução do Projeto
Para rodar o projeto localmente, siga os seguintes passos:

### 1. **Clonar o Repositório**
```sh
 git clone https://github.com/EdisonHundLucas/contact-center.git
```

### 2. **Configurar as Dependências**
Certifique-se de ter o **Maven** instalado e configurado corretamente no seu sistema.
Pacote adicional utilizado: jakarta.ejb para o uso da anotação @EJB, que injeta uma instância responsável por carregar as configurações do arquivo application.properties.

```sh
 cd contact-center
 mvn clean install
```

### 3. **Configuração do Ambiente**
Edite o arquivo `application.properties` e insira as credenciais **hubspot.client-id**, **hubspot.client-secret** e **hubspot.scope** necessárias para a integração com o HubSpot.
Por padrão, está configurada a URL de localhost, que deverá ser editada para a URL a ser definida para o sistema.

Exemplo de `application.properties`:
```properties
app.name=ContactCenter
app.basic-uri=http://localhost:8080
hubspot.client-id={CLIENT_ID}
hubspot.client-secret={CLIENT_SECRET}
hubspot.scope=oauth%20crm.objects.contacts.write%20crm.objects.contacts.read
hubspot.authorization-grant-type=authorization_code
hubspot.contacts-uri=https://api.hubapi.com/crm/v3/objects/contacts
hubspot.token-uri=https://api.hubapi.com/oauth/v1/token
```

### 4. **Executar a Aplicação**
Com as configurações ajustadas, execute o projeto utilizando o seguinte comando:
```sh
 mvn spring-boot:run
```

Ou, se estiver utilizando a **IDE NetBeans**, basta compilar e executar a classe principal com a anotação `@SpringBootApplication`.

### 5. **Acessar a API**
Após a inicialização, a API estará disponível em:
```
http://localhost:8080
```

## Configurações no HubSpot

[HubSpot](https://www.hubspot.com/)

### Criar uma conta de desenvolvedor
1. Acesse a [HubSpot](https://www.hubspot.com/) e crie uma conta de desenvolvedor.
2. Em **Aplicativos**:
   - Criar um Aplicativo.
   - Em **Informações Básicas / Autenticação**:
     - Nesse ponto pode-se identificar:
       - **ID do cliente (client_id)**
       - **Segredo do cliente (secret_id)**
     - Adicionar a URL de redirecionamento:
       ```
       http://localhost:8080/oauth/callback
       ```
     - Adicionar Escopos:
       ```
       crm.objects.contacts.read
       crm.objects.contacts.write
       oauth
   - Criar um **Webhook**
     - No menu **Webhooks**
       - Cadastre uma URL de destino onde o HubSpot enviará uma carga útil JSON com detalhes sobre os eventos quando eles forem disparados.
       O webhook não funciona com URLs de teste (como `localhost`). Para contornar isso, você pode usar o [ngrok](https://ngrok.com/).
       O **ngrok** é uma ferramenta que cria um túnel seguro entre a internet e um servidor local em sua máquina. Ele permite expor serviços rodando em `localhost`, gerando uma URL pública que pode ser acessada de qualquer lugar.
   
    	- Como usar o ngrok?
    		 1. Instale o ngrok a partir do site oficial: [ngrok.com/download](https://ngrok.com/download).
    		 2. Execute o seguinte comando no terminal:
		   ```bash
		   ngrok http http://localhost:8080
   
   		- Exemplo de uma URL criada pelo ngrok:
       ```bash
       https://https://deee-...caf5.ngrok-free.app

     - Cadastrar o Webhook (exemplo):
       ```
       http://localhost:8080/webhook -> https://https://deee-...caf5.ngrok-free.app/webhook

### Criar uma conta de teste de desenvolvedor
1. Em **Testar contas**:
   - Criar uma conta de teste de desenvolvedor.
   - Nessa conta serão feitas as alterações de acordo com os escopos cadastrados.
   - Nesse projeto, serão cadastrados e listados os contatos.
2. Para acessar a conta de desenvolvedor:
   - No **menu do usuário / Conta**, selecione a conta de desenvolvedor.
3. Na conta de desenvolvedor, vá para:
   - **CRM / Contatos**

## Gerar o Token de Autorização:

### 1. Fluxo do OAuth 2.0
O fluxo de autorização do HubSpot exige que o usuário:

- Acesse a URL de autorização.
- Faça login na conta do HubSpot.
- Selecione a conta à qual deseja conceder acesso.
- Autorize as permissões solicitadas.

Exemplo de URL de autorização desse projeto:
```
https://app.hubspot.com/oauth/authorize?client_id={CLIENT_ID}&redirect_uri=http://localhost:8080/oauth/callback&scope=crm.objects.contacts.write%20oauth%20crm.objects.contacts.read
```

Após selecionar a conta de teste, será redirecionado para o endpoint:
```
http://localhost:8080/oauth/callback?code=d2d03417-4c86...
```
que retornará um JSON como este:
```
{
    "token_type": "bearer",
    "refresh_token": "na1-d4eb-...",
    "access_token": "CN2fvqnaMhI...",
    "expires_in": 1800
}
```

O parâmetro **access_token** será usado nos cabeçalhos dos demais endpoints com a chave **Token**, como o GET contacts e o POST create-contact.

### Exemplo de requisições

#### **GET Contacts**
```
Method: GET
URL: http://localhost:8080/hubspot/contacts
Headers: 
    Token: CN2fvqnaMhIH...
```

#### **POST Create Contact**
```
Method: POST
URL: http://localhost:8080/hubspot/create-contact
Headers: 
    Content-Type: application/json
    Token: CN2fvqnaMhIH...
Body:
{
    "properties": {
        "email": "amanda@email.com",
        "firstname": "Amanda",
        "lastname": "Silva",
        "phone": "5549999998888",
        "company": "Empresa ContactCenter"
    }
} 
```

## Testes com Postman

Arquivo exportado do Postman com os endpoints citados acima, incluindo o endpoint de webhook para testes diretos. [Meetime.postman_collection.json](https://github.com/EdisonHundLucas/contact-center/blob/master/Meetime.postman_collection.json).


