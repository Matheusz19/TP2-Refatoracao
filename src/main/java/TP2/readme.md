# Refatorando Pipeline


O objetivo foi melhorar a qualidade do código aplicando boas práticas de engenharia de software, com foco em:

* Legibilidade
* Manutenibilidade
* Testabilidade
* Princípios SOLID

---

## Funcionamento do sistema

A aplicação simula um pipeline de integração contínua com as seguintes etapas:

1. Execução de testes
2. Deploy da aplicação
3. Envio de notificação por e-mail

O comportamento depende do estado do projeto (testes passando/falhando, sucesso de deploy, etc).

---

## Testes automatizados

Foram implementados testes cobrindo:

* Fluxo principal
* Falha nos testes
* Falha no deploy
* Execução sem testes
* Validação de logs
* Validação das mensagens de e-mail
* Testes unitários da classe `PipelineResult`

Os testes garantem segurança durante a refatoração e evitam regressões.

---

## Etapas de Refatoração

### Etapa 1 — Verificação inicial

* Projeto compilado com sucesso
* Análise do comportamento existente
* Estrutura inicial compreendida

---

### Etapa 2 — Métodos complexos

* Método `run()` da classe `Pipeline` foi refatorado
* Extração de métodos:

    * `executeTests`
    * `executeDeployment`
    * `handleEmail`

✔ Resultado: código mais legível e organizado

---

### Etapa 3 — Expressividade

* Remoção de strings mágicas (`"success"`, `"failure"`)
* Criação de métodos mais expressivos:

    * `testsPassed()`
    * `deploySuccessful()`

✔ Resultado: código mais seguro e autoexplicativo

---

### Etapa 4 — Encapsulamento

* Criação da classe `PipelineResult`
* Remoção de múltiplos booleanos soltos
* Centralização da lógica de resultado

✔ Resultado: melhor modelagem de domínio

---

### Etapa 5 — Reorganização de classes

Separação de responsabilidades:

* `Pipeline` → orquestração
* `TestRunner` → execução de testes
* `Deployer` → execução de deploy
* `PipelineResult` → estado do resultado

✔ Resultado:

* Aplicação do princípio de responsabilidade única
* Principio de aberto e  fechado
* Encapsulamento
* Clean Code
* Código modular e extensível

---

## Melhorias obtidas

* Redução de complexidade
* Eliminação de duplicações
* Melhor organização estrutural
* Facilidade de manutenção
* Maior cobertura de testes

---