# Projeto Final – Sistema de Gerenciamento de Eventos Acadêmicos 🎓

### 📝 Contexto 
A instituição está desenvolvendo um sistema para gerenciar a organização de eventos acadêmicos, como semanas de cursos, congressos e feiras científicas.
O sistema deve permitir o controle completo de **eventos**, **atividades**, **participantes** e **pagamentos**, com foco em oferecer uma _plataforma simples, segura e extensível._

### 📁 O sistema foi estruturado da seguinte maneira: 


#### Arquitetura adotada: MVC (Model-View-Controller) adaptada para aplicação console.

**Model:** Representa as classes de domínio e entidades do sistema (pasta `model/`).

**View:** Interface de usuário e menus, controlando o fluxo da aplicação no console (pasta `view/`).

**Controller:** Implementado principalmente na camada `service/`, onde ficam as regras de negócio e validações.

#### Abaixo está a organização das pastas e arquivos do projeto:
- contracts/ → Contratos/interfaces genéricas;
- dao/ → Interfaces de persistência e suas implementações com SQLite (JDBC);
- exception/ → Exceções personalizadas;
- model/ → Classes de domínio (entidades do sistema);
- service/ → Regras de negócio e validações do sistema;
- util/ → Classes utilitárias (como conexão com banco e entrada de dados);
- view/ →  Interface de usuário e menus (fluxo do sistema);
- Principal.java → Menu principal do sistema.

### 🛠️ Tecnologias Utilizadas
☕ **Java 17** — Linguagem principal da aplicação
 
🗃️ **SQLite** — Banco de dados leve e embutido
 
🔌 **JDBC** — API de acesso a dados via Java
  
🖥️ **Visual Studio Code** — Editor/IDE utilizado no desenvolvimento
  
🧰 **NetBeans** — IDE alternativa utilizada em etapas do projeto






