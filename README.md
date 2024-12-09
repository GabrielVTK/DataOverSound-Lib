# DataOverSound-Lib

**DataOverSound-Lib** é uma biblioteca Java para transmissão e recepção de dados utilizando ondas sonoras. Essa biblioteca emprega modulação e demodulação QPSK (Quadrature Phase-Shift Keying) para converter mensagens de texto em sinais analógicos e vice-versa.

## Funcionalidades
- Modulação de mensagens para sinais analógicos.
- Demodulação de sinais analógicos em mensagens.
- Configuração flexível dos parâmetros de QPSK.

## Requisitos
- Java 8+.
- Maven para gerenciamento de dependências.

## Como usar
1. Importar ou clonar esta biblioteca para dentro do seu projeto
2. Configurar os parâmetros de QPSK.
   
   Exemplo:
   ```java
   parameters = new QPSKParameters(
     44100.0f, // Frequência de amostragem
     440.0f, // Frequência de operação
     1.0f, // Amplitude Máxima
     5000 // Quantidade de amostras por símbolo
   );
   ```

3. Inicializar os serviços

    **Para transmitir dados:**
    ```java
    QPSKModulationService qpskModulationService = new QPSKModulationService(parameters);
    ```
    **Para receber dados:**
    ```java
    QPSKDemodulationService qpskDemodulationService = new QPSKDemodulationService(parameters);
    ```
 
4. Utilizar as funções principais

   **Modular uma mensagem (texto para sinal):**
   ```java
   double[] signal = qpskModulationService.modulateMessage("Hello World!");
   ```

   **Demodular um sinal (sinal para texto):**
   ```java
   String message = qpskDemodulationService.demodulateMessage(signal);
   System.out.println("Mensagem recebida: " + message);
   ```

## Exemplos
### Aplicação Emissora
```java
QPSKParameters qpskParameters = new QPSKParameters(44100.0f, 440.0f, 1.0f, 5000);
QPSKModulationService qpskModulationService = new QPSKModulationService(qpskParameters);

String message = "Hello, Sound!";
double[] signal = qpskModulationService.modulateMessage(message);
// O sinal pode agora ser transmitido.

```

### Aplicação Receptora
```java
QPSKParameters qpskParameters = new QPSKParameters(44100.0f, 440.0f, 1.0f, 5000);
QPSKDemodulationService qpskDemodulationService = new QPSKDemodulationService(qpskParameters);

double[] receivedSignal = /* Sinal recebido via microfone ou outro meio */;
String message = qpskDemodulationService.demodulateMessage(receivedSignal);
System.out.println("Mensagem recebida: " + message);

```
