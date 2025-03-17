# Projeto ContactCenter

## Visão Geral
O **ContactCenter** é um projeto desenvolvido utilizando o framework **Spring Boot**, com integração direta à API do **HubSpot** para gestão de contatos. O objetivo é permitir o processamento eficiente de informações e facilitar a comunicação entre sistemas.

## Tecnologias Utilizadas
- **Framework:** Spring Boot
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
Edite o arquivo `application.properties` e insira as credenciais hubspot.client-id e hubspot.client-secret necessárias para a integração com o HubSpot.
Por padrão, está configurada a URL de localhost, que deverá ser editada para a URL a ser definida para o sistema.

Exemplo de `application.properties`:
```properties
app.name=ContactCenter
app.basic-uri=http://localhost:8080
hubspot.client-id={CLIENT_ID}
hubspot.client-secret={CLIENT_SECRET}
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
       ```

### Criar uma conta de teste de desenvolvedor
1. Em **Testar contas**:
   - Criar uma conta de teste de desenvolvedor.
   - Nessa conta serão feitas as alterações de acordo com os escopos cadastrados.
   - Nesse projeto, serão cadastrados e listados os contatos.
2. Para acessar a conta de desenvolvedor:
   - No **menu do usuário / Conta**, selecione a conta de desenvolvedor.
3. Na conta de desenvolvedor, vá para:
   - **CRM / Contatos**


